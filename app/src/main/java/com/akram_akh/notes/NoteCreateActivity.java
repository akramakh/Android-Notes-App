package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteCreateActivity extends AppCompatActivity {

    private BottomSheetBehavior m_bottom_sheet_behavior;
    ImageView color_chooser_btn, close_btn;
    boolean show = false;

    ProgressBar edit_note_pb;
    LinearLayout main_container;
    Button save_btn;
    TextView title_tv, description_tv;
    EditText title_et, description_et;
    Spinner cat_list_spinner;
    ArrayAdapter<String> adapter;

    DatabaseReference notes_database;
    DatabaseReference categories_database;

    List<Category> categories_list;
    List<String> strings_list;

    ImageView color_btn_1,color_btn_2,color_btn_3,color_btn_4,color_btn_5,color_btn_6,color_btn_7,color_btn_8,color_btn_9,color_btn_10,color_btn_11,color_btn_12;

    public String category_id = new String("category_id");
    int color = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        main_container = findViewById(R.id.edit_note_page_bg);

        notes_database = FirebaseDatabase.getInstance().getReference("notes");
        categories_database = FirebaseDatabase.getInstance().getReference("categories");

        View bottom_sheet = findViewById(R.id.color_chooser_botton_sheet);
        m_bottom_sheet_behavior = BottomSheetBehavior.from(bottom_sheet);

        color_chooser_btn = findViewById(R.id.edit_note_color_chooser_btn);
        close_btn = findViewById(R.id.edit_note_close_btn);


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

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        title_tv = findViewById(R.id.edit_note_title_tv);
        title_et = findViewById(R.id.edit_note_title_et);
//        description_tv = findViewById(R.id.edit_note_description_tv);
        description_et = findViewById(R.id.edit_note_description_et);
        save_btn = findViewById(R.id.edit_note_save_btn);
        cat_list_spinner = findViewById(R.id.edit_note_category_spinner);

        categories_list = new ArrayList<>();
        strings_list = new ArrayList<>();

        edit_note_pb = findViewById(R.id.edit_note_pb);


        categories_database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot user: dataSnapshot.getChildren()) {
                    for (DataSnapshot category : user.getChildren()) {
                        Category cat = category.getValue(Category.class);
                        categories_list.add(cat);
                        strings_list.add(cat.getTitle());
                    }
                }
                adapter.notifyDataSetChanged();
                edit_note_pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                strings_list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat_list_spinner.setAdapter(adapter);


        cat_list_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category cat = categories_list.get(position);
                category_id = cat.getId();
                Toast.makeText(NoteCreateActivity.this, id+" selected "+position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_et.getText().toString();
                String description = description_et.getText().toString();
                long created_at = new Date().getTime();
                long updated_at = new Date().getTime();
//                String id = notes_database.push().getKey();
                Note note = new Note("note_id", title, description, color, category_id, created_at, updated_at);
                new FirebaseDatabaseHelper().addNote(note, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Note> notes, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NoteCreateActivity.this, "New Note Inserted Successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });


        color_btn_1 = findViewById(R.id.edit_note_color_1);
        color_btn_2 = findViewById(R.id.edit_note_color_2);
        color_btn_3 = findViewById(R.id.edit_note_color_3);
        color_btn_4 = findViewById(R.id.edit_note_color_4);
        color_btn_5 = findViewById(R.id.edit_note_color_5);
        color_btn_6 = findViewById(R.id.edit_note_color_6);
        color_btn_7 = findViewById(R.id.edit_note_color_7);
        color_btn_8 = findViewById(R.id.edit_note_color_8);
        color_btn_9 = findViewById(R.id.edit_note_color_9);
        color_btn_10 = findViewById(R.id.edit_note_color_10);
        color_btn_11 = findViewById(R.id.edit_note_color_11);
        color_btn_12 = findViewById(R.id.edit_note_color_12);


        color_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(1);
                color = 1;
            }
        });
        color_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(2);
                color = 2;
            }
        });
        color_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(3);
                color = 3;
            }
        });
        color_btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(4);
                color = 4;
            }
        });
        color_btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(5);
                color = 5;
            }
        });
        color_btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(6);
                color = 6;
            }
        });
        color_btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(7);
                color = 7;
            }
        });
        color_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(1);
                color = 1;
            }
        });
        color_btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(8);
                color = 8;
            }
        });
        color_btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(9);
                color = 9;
            }
        });
        color_btn_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(10);
                color = 10;
            }
        });
        color_btn_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(11);
                color = 11;
            }
        });
        color_btn_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(12);
                color = 12;
            }
        });


    }

    private void changeBackground(int i){
        int[] colors = { R.drawable.color_bg_0, R.drawable.color_bg_1, R.drawable.color_bg_2, R.drawable.color_bg_3, R.drawable.color_bg_4, R.drawable.color_bg_5,
                R.drawable.color_bg_6, R.drawable.color_bg_7, R.drawable.color_bg_8, R.drawable.color_bg_9, R.drawable.color_bg_10,
                R.drawable.color_bg_11, R.drawable.color_bg_12};
        main_container.setBackgroundResource(colors[i]);
    }
}
