package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CategoriesListActivity extends AppCompatActivity {
    FloatingActionButton main_fab;

    RecyclerView rvCatrgories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);

        Utils.setDarkIconsStatusBar(this, R.color.colorWhite);

        rvCatrgories = findViewById(R.id.catrgories_list_rv);

        new FirebaseDatabaseHelper().readCategory(false,new FirebaseDatabaseHelper.CategoryDataStatus() {
            @Override
            public void DataIsLoaded(List<Category> categoies, List<String> keys) {
                new CategoriesRecyclerViewConfig().setConfig(rvCatrgories,false, CategoriesListActivity.this, categoies, keys);
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
