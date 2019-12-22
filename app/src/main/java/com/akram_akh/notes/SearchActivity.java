package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.akram_akh.notes.Auth.SignUpActivity;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView note_list_rv;
    SearchView search_bar;
    LinearLayout blank_bg;
    ProgressBar search_pb;

    int len = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_bar = findViewById(R.id.search_bar);
        blank_bg = findViewById(R.id.blank_bg);
        note_list_rv = findViewById(R.id.search_rv);
        search_pb = findViewById(R.id.search_pb);

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().isEmpty()){
                    blank_bg.setVisibility(View.VISIBLE);
                    note_list_rv.setVisibility(View.GONE);
                    search_pb.setVisibility(View.GONE);
                    return false;
                }else{
                    blank_bg.setVisibility(View.GONE);
                    note_list_rv.setVisibility(View.VISIBLE);
                    search_pb.setVisibility(View.GONE);
                    return doSearch(newText);
                }
            }
        });
    }

    private boolean doSearch(String newText){

        new FirebaseDatabaseHelper().searchForNote(newText, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Note> notes, List<String> keys) {
                len = keys.size();
                new RecyclerViewConfig().setConfig(note_list_rv, SearchActivity.this, notes, keys);
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
        return len > 0;
    }

    public void goToMain(View view) {
        finish();
        return;
    }
}
