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

import com.akram_akh.notes.Auth.SignInActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private BottomSheetBehavior m_bottom_sheet_behavior;
    ImageView color_chooser_btn;
    boolean show = false;

    String key, user_id;

    FirebaseAuth f_auth;

    EditText cat_title_ET;
//    EditText cat_slug_ET;
    ImageView add_notebook_image_holder;
    Button create_category_btn;

    FrameLayout color_chooser;

    DatabaseReference categories_database;
    DatabaseReference single_category_database;
    Category item;

    int color = R.drawable.notebook_1;
    ImageView color_btn_1,color_btn_2,color_btn_3,color_btn_4,color_btn_5,color_btn_6,
            color_btn_7,color_btn_8,color_btn_9,color_btn_10,color_btn_11,color_btn_12;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        f_auth = FirebaseAuth.getInstance();

        categories_database = FirebaseDatabase.getInstance().getReference("categories");
        cat_title_ET = (EditText) findViewById(R.id.cat_title_ET);
//        cat_slug_ET = (EditText) findViewById(R.id.cat_slug_ET);
        add_notebook_image_holder = findViewById(R.id.add_notebook_image_holder);
        create_category_btn = findViewById(R.id.create_cat_btn);

        color_chooser = (FrameLayout) findViewById(R.id.color_chooser);

        key = getIntent().getStringExtra("key");
        if(!key.equals("add_cat_id")){
            cat_title_ET.setText(getIntent().getStringExtra("title"));
//            Integer img = (Integer) getIntent().getIntExtra("image");
//            add_notebook_image_holder.setImageResource(img);
            user_id = getIntent().getStringExtra("user_id");

            single_category_database = categories_database.child(user_id).child(key);

            single_category_database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    item = new Category();
                    item.setId(dataSnapshot.child("id").getValue().toString());
                    item.setTitle(dataSnapshot.child("title").getValue().toString());
                    item.setSlug(dataSnapshot.child("slug").getValue().toString());
                    item.setImage(Integer.valueOf(dataSnapshot.child("image").getValue().toString()));
                    item.setCreated_at((Long) dataSnapshot.child("created_at").getValue());
                    item.setUpdated_at((Long) dataSnapshot.child("updated_at").getValue());
                    item.setUser_id(dataSnapshot.child("user_id").getValue().toString());
                    add_notebook_image_holder.setImageResource(item.getImage());
                    color = item.getImage();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            create_category_btn.setText("Update");

            create_category_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cat_title = cat_title_ET.getText().toString();
                    String cat_slug = cat_title_ET.getText().toString().trim();
                    int cat_image = color;
                    long updated_at = new Date().getTime();
                    item.setImage(cat_image);
                    item.setSlug(cat_slug);
                    item.setTitle(cat_title);
                    item.setUpdated_at(updated_at);
                    new FirebaseDatabaseHelper().updateCategory(key, item, new FirebaseDatabaseHelper.CategoryDataStatus() {
                        @Override
                        public void DataIsLoaded(List<Category> categoies, List<String> keys) {
                        }

                        @Override
                        public void DataIsInserted() {

                        }

                        @Override
                        public void DataIsUpdated() {
                            Toast.makeText(CategoryActivity.this,"updated successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void DataIsDeleted() {

                        }
                    });

                }
            });
        }else{
            create_category_btn.setText("Create");
            create_category_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user_id = f_auth.getUid();
                    String cat_title = cat_title_ET.getText().toString();
                    String cat_slug = cat_title_ET.getText().toString().trim();
                    int cat_image = color;
                    long created_at = new Date().getTime();
                    long updated_at = new Date().getTime();
                    String id = categories_database.push().getKey();
                    Category cat = new Category(id,cat_title, cat_slug, cat_image, user_id, created_at, updated_at);
                    new FirebaseDatabaseHelper().addCategory(cat, new FirebaseDatabaseHelper.CategoryDataStatus() {
                        @Override
                        public void DataIsLoaded(List<Category> categoies, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {
                            Toast.makeText(CategoryActivity.this,"Notebook Created successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void DataIsUpdated() {

                        }

                        @Override
                        public void DataIsDeleted() {

                        }
                    });
//                    categories_database.child(user_id).child(id).setValue(cat);

                }
            });
        }

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
            }
        });
        color_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(2);
            }
        });
        color_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(3);
            }
        });
        color_btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(4);
            }
        });
        color_btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(5);
            }
        });
        color_btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(6);
            }
        });
        color_btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(7);
            }
        });
        color_btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(8);
            }
        });
        color_btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(9);
            }
        });
        color_btn_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(10);
            }
        });
        color_btn_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(11);
            }
        });
        color_btn_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(12);
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
