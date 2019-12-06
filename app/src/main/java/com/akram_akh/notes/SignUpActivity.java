package com.akram_akh.notes;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText email_ET, password_ET;
    Button signup_btn;
    ProgressBar progress_bar;

    FirebaseAuth f_auth;
    Activity current_user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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

    public void goToSignIn(View view) {
        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        finish();
    }

}
