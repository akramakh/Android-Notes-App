package com.akram_akh.notes.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignInActivity extends AppCompatActivity {

    EditText email_ET, password_ET;
    Button signin_btn;
    ProgressBar progress_bar;

    FirebaseAuth f_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email_ET = findViewById(R.id.signin_email_ET);
        password_ET = findViewById(R.id.signin_password_ET);
        signin_btn = findViewById(R.id.signin_btn);
        progress_bar = findViewById(R.id.signin_PB);

        f_auth = FirebaseAuth.getInstance();

        if(f_auth.getCurrentUser() != null){
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }



        signin_btn.setOnClickListener(new View.OnClickListener() {
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

                f_auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progress_bar.setVisibility(View.INVISIBLE);
                            if(!f_auth.getCurrentUser().isEmailVerified()){
                                f_auth.signOut();
                                Toast.makeText(SignInActivity.this, "You have to verify your Email Address", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignInActivity.this, ConfirmingEmailActivity.class));
                                return;
                            }
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        }else{
                            progress_bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignInActivity.this, "ERROR >> "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });

            }
        });

    }

    public void goToSignUp(View view) {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        finish();
    }
    public void goToREsetPassword(View view) {
        startActivity(new Intent(SignInActivity.this, ForgetPasswordActivity.class));
        finish();
    }

    public void goToOnboarding(View view) {
        startActivity(new Intent(SignInActivity.this, OnboardingScreenActivity.class));
        finish();
    }
}
