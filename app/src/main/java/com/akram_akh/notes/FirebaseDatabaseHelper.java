package com.akram_akh.notes;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabasse;
    private DatabaseReference mNotesDatabasse;
    private List<Note> mNotes = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Note> notes, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        this.mDatabasse = FirebaseDatabase.getInstance();
        this.mNotesDatabasse = this.mDatabasse.getReference("notes");
    }

    public void readNotes(final DataStatus dataStatus){
        this.mNotesDatabasse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mNotes.clear();
                List<String> mKeys = new ArrayList<>();
                for (DataSnapshot categories: dataSnapshot.getChildren()){
                    for (DataSnapshot noteNode: categories.getChildren()){
                        mKeys.add(noteNode.getKey());
                        Note note = noteNode.getValue(Note.class);
                        mNotes.add(note);
                    }
                }
                dataStatus.DataIsLoaded(mNotes, mKeys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addNote(Note note, final DataStatus dataStatus){
        String id = mNotesDatabasse.child(note.getCategory_id()).push().getKey();
        note.setId(id);
        mNotesDatabasse.child(note.getCategory_id()).child(id).setValue(note)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });

    }

    public void updateNote(String key, Note note, final DataStatus dataStatus){
        mNotesDatabasse.child(note.getCategory_id()).child(key).setValue(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }

    public void deleteNote(String key, String categoryId, final DataStatus dataStatus){
        mNotesDatabasse.child(categoryId).child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
}
