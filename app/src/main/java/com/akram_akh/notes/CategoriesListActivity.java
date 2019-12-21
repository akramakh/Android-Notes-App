package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CategoriesListActivity extends AppCompatActivity {
    FloatingActionButton main_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);


        main_fab = findViewById(R.id.add_note_fab);

        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesListActivity.this, NoteCreateActivity.class);
                startActivity(intent);
            }
        });
    }
}
