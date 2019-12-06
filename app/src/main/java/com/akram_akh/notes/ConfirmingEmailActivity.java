package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmingEmailActivity extends AppCompatActivity {

    Button got_to_email_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirming_email);

        got_to_email_btn = findViewById(R.id.confirming_email_btn);

        got_to_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmingEmailActivity.this, SignInActivity.class));
            }
        });
    }
}
