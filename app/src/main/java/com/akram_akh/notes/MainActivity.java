package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button create_category_btn;

    FirebaseAuth f_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        f_auth = FirebaseAuth.getInstance();

        if(f_auth.getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        }

        create_category_btn = findViewById(R.id.add_category_btn);

        create_category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this," ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        finish();
    }

}
