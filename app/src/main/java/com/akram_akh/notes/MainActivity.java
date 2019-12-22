package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akram_akh.notes.Adapter.CategoryAdapter;
import com.akram_akh.notes.Auth.SignInActivity;
import com.akram_akh.notes.Auth.SignUpActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton main_fab;
    ProgressBar main_pb;
    LinearLayout top_menu;
    TextView top_edit_btn;
    boolean show_menu = false, notes_loaded = false, categories_loaded = false;

    FirebaseAuth f_auth;

    RecyclerView cat_list_rv;
    CategoryAdapter cat_adapter;

    RecyclerView note_list_rv;

    List<Category> cat_list  = new ArrayList<>();

    DatabaseReference categories_database;

    TextView show_all_notes_btn, show_all_categories_btn;


    @Override
    protected void onStart() {
        super.onStart();

        categories_database = FirebaseDatabase.getInstance().getReference("categories");
//        categories_database.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                cat_list.clear();
//                Category add_cat_btn = new Category("add_cat_id", "Create Notebook", "create-notebook", R.drawable.add_notebook_img,"user_id", new Date().getTime(),new Date().getTime());
//                cat_list.add(add_cat_btn);
//                for (DataSnapshot user: dataSnapshot.getChildren()) {
//                    for (DataSnapshot category : user.getChildren()) {
//                        System.out.println(category.getValue());
//                        Category cat = category.getValue(Category.class);
//                        cat_list.add(cat);
//                    }
//                }
//                cat_adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        f_auth = FirebaseAuth.getInstance();


        if(f_auth.getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        }

        top_edit_btn = findViewById(R.id.edit_main_btn);
        top_menu = findViewById(R.id.top_menu);

        top_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show_menu){
                    show_menu = false;
                    top_menu.setVisibility(View.GONE);
                }else{
                    show_menu = true;
                    top_menu.setVisibility(View.VISIBLE);
                }
            }
        });

        main_pb = findViewById(R.id.main_pb);
        main_fab = findViewById(R.id.main_fab);

        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteCreateActivity.class);
                startActivity(intent);
            }
        });

        cat_list_rv = findViewById(R.id.cat_list_rv);
//        cat_list_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        cat_adapter = new CategoryAdapter(this ,cat_list);
//        cat_list_rv.setAdapter(cat_adapter);

        new FirebaseDatabaseHelper().readCategory(new FirebaseDatabaseHelper.CategoryDataStatus() {
            @Override
            public void DataIsLoaded(List<Category> categoies, List<String> keys) {
                new CategoriesRecyclerViewConfig().setConfig(cat_list_rv, MainActivity.this, categoies, keys);
                categories_loaded = true;
                if(notes_loaded){
                    main_pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        note_list_rv = findViewById(R.id.notes_list_rv);

        new FirebaseDatabaseHelper().readNotes(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Note> notes, List<String> keys) {
                new RecyclerViewConfig().setConfig(note_list_rv, MainActivity.this, notes, keys);
                notes_loaded = true;
                if(categories_loaded){
                    main_pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        show_all_notes_btn = findViewById(R.id.show_all_notes_btn);
        show_all_notes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotesListActivity.class));
            }
        });

        show_all_categories_btn = findViewById(R.id.show_all_categories_btn);
        show_all_categories_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CategoriesListActivity.class));
            }
        });


    }


    public void goToAddCategory(View v){

            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(intent);
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        finish();
        return;
    }

    public void goToSearch(View view) {
        startActivity(new Intent(MainActivity.this, SearchActivity.class));
    }
}
