package com.akram_akh.notes.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akram_akh.notes.MainActivity;
import com.akram_akh.notes.OnboardingScreenActivity;
import com.akram_akh.notes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText email_ET, password_ET;
    Button signup_btn;
    ProgressBar progress_bar;

    FirebaseAuth f_auth;
    Activity current_user = null;
    DatabaseReference users_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        users_database = FirebaseDatabase.getInstance().getReference("users");

        email_ET = findViewById(R.id.signup_email_ET);
        password_ET = findViewById(R.id.signup_password_ET);
        signup_btn = findViewById(R.id.signup_btn);
        progress_bar = findViewById(R.id.signup_PB);

        f_auth = FirebaseAuth.getInstance();

        if(f_auth.getCurrentUser() != null){
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        }

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_ET.getText().toString().trim();
                String password = password_ET.getText().toString();


                if(TextUtils.isEmpty(email)){
                    email_ET.setError("Enter a valid Email address");
                    return;
                }
                if(password.length() < 6){
                    password_ET.setError("Enter a valid Password (more than 6 chars)");
                    return;
                }
                progress_bar.setVisibility(View.VISIBLE);

                f_auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            f_auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progress_bar.setVisibility(View.INVISIBLE);
                                        addUser(f_auth.getCurrentUser().getUid(),f_auth.getCurrentUser().getEmail());
                                        f_auth.signOut();
                                        startActivity(new Intent(SignUpActivity.this, ConfirmingEmailActivity.class));
                                    }
                                    else {
                                        progress_bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(SignUpActivity.this, "ERROR >> "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                            });
//                            startActivity(new Intent(SignUpActivity.this, ConfirmingEmailActivity.class));
                        }else{
                            Toast.makeText(SignUpActivity.this, "ERROR >> "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });


            }
        });
    }

    private void addUser(String id, String email){
        long created_at = new Date().getTime();
        long updated_at = new Date().getTime();
        long last_login = new Date().getTime();

        HashMap<String, Object> data = new HashMap<>();

        data.put("id",id);
        data.put("email",email);
        data.put("created_at",created_at);
        data.put("updated_at",updated_at);
        data.put("last_login",last_login);

        users_database.child(id).setValue(data);

    }

    public void goToSignIn(View view) {
        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        finish();
    }

    public void goToOnboarding(View view) {
        startActivity(new Intent(SignUpActivity.this, OnboardingScreenActivity.class));
        finish();
    }

}
