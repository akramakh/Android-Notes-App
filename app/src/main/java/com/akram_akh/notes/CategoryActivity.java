package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class CategoryActivity extends AppCompatActivity {

    private BottomSheetBehavior m_bottom_sheet_behavior;
    ImageView color_chooser_btn;
    boolean show = false;

    EditText cat_title_ET;
//    EditText cat_slug_ET;
    ImageView add_notebook_image_holder;
    Button create_category_btn;

    FrameLayout color_chooser;

    DatabaseReference categories_database;

    int color = R.drawable.notebook_1;
    ImageView color_btn_1,color_btn_2,color_btn_3,color_btn_4,color_btn_5,color_btn_6,color_btn_7,color_btn_8,color_btn_9,color_btn_10,color_btn_11,color_btn_12;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categories_database = FirebaseDatabase.getInstance().getReference("categories");
        cat_title_ET = (EditText) findViewById(R.id.cat_title_ET);
//        cat_slug_ET = (EditText) findViewById(R.id.cat_slug_ET);
        add_notebook_image_holder = findViewById(R.id.add_notebook_image_holder);
        create_category_btn = findViewById(R.id.create_cat_btn);

        color_chooser = (FrameLayout) findViewById(R.id.color_chooser);

        add_notebook_image_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                color_chooser.setVisibility(v.VISIBLE);
            }
        });


        View bottom_sheet = findViewById(R.id.color_chooser_botton_sheet);
        m_bottom_sheet_behavior = BottomSheetBehavior.from(bottom_sheet);
        color_chooser_btn = findViewById(R.id.add_notebook_color_chooser_btn);

        color_chooser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show){
                    m_bottom_sheet_behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    show = false;
                }else{
                    m_bottom_sheet_behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    show = true;
                }
            }
        });


        create_category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = "user_id";
                String cat_title = cat_title_ET.getText().toString();
                String cat_slug = cat_title_ET.getText().toString().trim();
                int cat_image = color;
                long created_at = new Date().getTime();
                long updated_at = new Date().getTime();
                String id = categories_database.push().getKey();
                Category cat = new Category(id,cat_title, cat_slug, cat_image, user_id, created_at, updated_at);
                categories_database.child(user_id).child(id).setValue(cat);
                Toast.makeText(CategoryActivity.this,cat_title+" "+cat_slug, Toast.LENGTH_LONG).show();
            }
        });


        color_btn_1 = findViewById(R.id.add_notebook_color_1);
        color_btn_2 = findViewById(R.id.add_notebook_color_2);
        color_btn_3 = findViewById(R.id.add_notebook_color_3);
        color_btn_4 = findViewById(R.id.add_notebook_color_4);
        color_btn_5 = findViewById(R.id.add_notebook_color_5);
        color_btn_6 = findViewById(R.id.add_notebook_color_6);
        color_btn_7 = findViewById(R.id.add_notebook_color_7);
        color_btn_8 = findViewById(R.id.add_notebook_color_8);
        color_btn_9 = findViewById(R.id.add_notebook_color_9);
        color_btn_10 = findViewById(R.id.add_notebook_color_10);
        color_btn_11 = findViewById(R.id.add_notebook_color_11);
        color_btn_12 = findViewById(R.id.add_notebook_color_12);


        color_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(1);
//                color = 1;
            }
        });
        color_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(2);
//                color = 2;
            }
        });
        color_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(3);
//                color = 3;
            }
        });
        color_btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(4);
//                color = 4;
            }
        });
        color_btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(5);
//                color = 5;
            }
        });
        color_btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(6);
//                color = 6;
            }
        });
        color_btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(7);
//                color = 7;
            }
        });
        color_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(1);
//                color = 1;
            }
        });
        color_btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(8);
//                color = 8;
            }
        });
        color_btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(9);
//                color = 9;
            }
        });
        color_btn_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(10);
//                color = 10;
            }
        });
        color_btn_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(11);
//                color = 11;
            }
        });
        color_btn_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(12);
//                color = 12;
            }
        });


    }

    private void changeBackground(int i){
        int[] images = {R.drawable.notebook_1, R.drawable.notebook_2, R.drawable.notebook_3, R.drawable.notebook_4, R.drawable.notebook_5,
                R.drawable.notebook_6, R.drawable.notebook_7, R.drawable.notebook_8, R.drawable.notebook_9, R.drawable.notebook_10,
                R.drawable.notebook_11, R.drawable.notebook_12};
        color = images[i-1];
        add_notebook_image_holder.setImageResource(images[i-1]);
    }


    public void closeAddCategoryView(View view) {
        finish();
    }
}
