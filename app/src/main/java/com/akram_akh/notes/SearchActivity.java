package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.akram_akh.notes.Auth.SignUpActivity;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void goToMain(View view) {
        startActivity(new Intent(SearchActivity.this, MainActivity.class));
        finish();
    }
}
