package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
//    NoteAdapter note_adapter;
//    List<Note> notes_list  = new ArrayList<>();
//
//    DatabaseReference notes_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);


        main_fab = findViewById(R.id.add_note_fab);

        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesListActivity.this, NoteCreateActivity.class);
                startActivity(intent);
            }
        });

//        notes_database = FirebaseDatabase.getInstance().getReference("notes");
//        notes_database.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                notes_list.clear();
//                for (DataSnapshot category: dataSnapshot.getChildren()) {
//                    for (DataSnapshot note : category.getChildren()) {
//                        Note item = note.getValue(Note.class);
//                        notes_list.add(item);
//                    }
//                }
//                note_adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });

        note_list_rv = findViewById(R.id.notes_list_rv);
        new FirebaseDatabaseHelper().readNotes(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Note> notes, List<String> keys) {
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
}
