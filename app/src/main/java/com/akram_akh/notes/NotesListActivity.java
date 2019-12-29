package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.akram_akh.notes.Adapter.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {

    FloatingActionButton main_fab;

    RecyclerView note_list_rv;
    SearchView search_bar;
    ProgressBar search_pb;
    ImageView back_btn;

    int length = 0;
//    NoteAdapter note_adapter;
//    List<Note> notes_list  = new ArrayList<>();
//
//    DatabaseReference notes_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        back_btn = findViewById(R.id.notes_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return ;
            }
        });


        main_fab = findViewById(R.id.add_note_fab);
        search_bar = findViewById(R.id.search_bar);
        search_pb = findViewById(R.id.search_pb);

        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesListActivity.this, NoteCreateActivity.class);
                startActivity(intent);
            }
        });

        note_list_rv = findViewById(R.id.notes_list_rv);
        loadNotes();

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.trim().isEmpty()){
                    search_pb.setVisibility(View.GONE);
                    loadNotes();
                    return false;
                }else{
                    return doSearch(newText);
                }
            }
        });
    }

    private void loadNotes(){
        new FirebaseDatabaseHelper().readNotes(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Note> notes, List<String> keys) {
                search_pb.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfig(note_list_rv, NotesListActivity.this, notes, keys);
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
        new FirebaseDatabaseHelper().searchForNote(newText, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Note> notes, List<String> keys) {
                search_pb.setVisibility(View.GONE);
                length = keys.size();
                new RecyclerViewConfig().setConfig(note_list_rv, NotesListActivity.this, notes, keys);
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
