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

import com.akram_akh.notes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText recover_email;
    Button recover_btn;
    ProgressBar progress_bar;

    FirebaseAuth f_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        recover_email = findViewById(R.id.forget_pass_email_ET);
        recover_btn = findViewById(R.id.recover_pass_btn);
        progress_bar = findViewById(R.id.forget_pass_PB);

        f_auth = FirebaseAuth.getInstance();

        recover_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = recover_email.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ForgetPasswordActivity.this, "Your email is not exist in our database", Toast.LENGTH_LONG).show();
                    return;
                }
                progress_bar.setVisibility(View.VISIBLE);
                f_auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progress_bar.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful()){
                            Toast.makeText(ForgetPasswordActivity.this, "A reset password link was sent to your email successfully", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ForgetPasswordActivity.this, "ERROR >> "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    public void goToSignIn(View view) {
        startActivity(new Intent(ForgetPasswordActivity.this, SignInActivity.class));
        finish();
    }
}
