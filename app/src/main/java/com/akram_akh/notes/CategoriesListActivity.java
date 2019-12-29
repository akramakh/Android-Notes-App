package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CategoriesListActivity extends AppCompatActivity {
    FloatingActionButton main_fab;
    ImageView back_btn;

    RecyclerView rvCatrgories;
    SearchView search_bar;
    ProgressBar search_pb;
    int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);

        Utils.setDarkIconsStatusBar(this, R.color.colorWhite);

        back_btn = findViewById(R.id.category_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return ;
            }
        });

        rvCatrgories = findViewById(R.id.catrgories_list_rv);
        search_bar = findViewById(R.id.search_bar);
        search_pb = findViewById(R.id.search_pb);

        loadCategories();

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.trim().isEmpty()){
                    search_pb.setVisibility(View.GONE);
                    loadCategories();
                    return false;
                }else{
                    return doSearch(newText);
                }
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

    private void loadCategories(){
        new FirebaseDatabaseHelper().readCategory(false,new FirebaseDatabaseHelper.CategoryDataStatus() {
            @Override
            public void DataIsLoaded(List<Category> categories, List<String> keys) {
                search_pb.setVisibility(View.GONE);
                new CategoriesRecyclerViewConfig().setConfig(rvCatrgories,false, CategoriesListActivity.this, categories, keys);
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
    }

    private boolean doSearch(String newText){
        search_pb.setVisibility(View.VISIBLE);
        new FirebaseDatabaseHelper().searchForCategory(newText, new FirebaseDatabaseHelper.CategoryDataStatus() {
            @Override
            public void DataIsLoaded(List<Category> categories, List<String> keys) {
                search_pb.setVisibility(View.GONE);
                length = keys.size();
                new CategoriesRecyclerViewConfig().setConfig(rvCatrgories,false, CategoriesListActivity.this, categories, keys);
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
        return length > 0;
    }
}
