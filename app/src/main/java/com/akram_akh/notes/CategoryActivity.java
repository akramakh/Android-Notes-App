package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class CategoryActivity extends AppCompatActivity {

    EditText cat_title_ET;
    EditText cat_slug_ET;
    Button create_category_btn;

    DatabaseReference categories_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categories_database = FirebaseDatabase.getInstance().getReference("categories");
        cat_title_ET = (EditText) findViewById(R.id.cat_title_ET);
        cat_slug_ET = (EditText) findViewById(R.id.cat_slug_ET);
        create_category_btn = findViewById(R.id.create_cat_btn);



        create_category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = "user_id";
                String cat_title = cat_title_ET.getText().toString();
                String cat_slug = cat_slug_ET.getText().toString().trim();
                long created_at = new Date().getTime();
                long updated_at = new Date().getTime();
                String id = categories_database.push().getKey();
                Category cat = new Category(id,cat_title, cat_slug, user_id, created_at, updated_at);
                categories_database.child(user_id).child(id).setValue(cat);
                Toast.makeText(CategoryActivity.this,cat_title+" "+cat_slug, Toast.LENGTH_LONG).show();
            }
        });
    }
}
