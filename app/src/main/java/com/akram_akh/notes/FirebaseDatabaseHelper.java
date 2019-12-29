package com.akram_akh.notes;


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
    private DatabaseReference mCategoriesDatabasse;
    private List<Note> mNotes = new ArrayList<>();
    private List<Note> mSearchedNotes = new ArrayList<>();
    private List<Category> mCategories = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Note> notes, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public interface CategoryDataStatus{
        void DataIsLoaded(List<Category> categoies, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        this.mDatabasse = FirebaseDatabase.getInstance();
        this.mNotesDatabasse = this.mDatabasse.getReference("notes");
        this.mCategoriesDatabasse = this.mDatabasse.getReference("categories");
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

    public void searchForNote(final String query, final DataStatus dataStatus){
        this.mNotesDatabasse.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mSearchedNotes.clear();
                List<String> mKeys = new ArrayList<>();
                for (DataSnapshot categories: dataSnapshot.getChildren()){
                    for (DataSnapshot noteNode: categories.getChildren()){
                        Note note = noteNode.getValue(Note.class);
                        if(note.getTitle().toLowerCase().contains(query.toLowerCase().trim())){
                            mKeys.add(noteNode.getKey());
                            mSearchedNotes.add(note);
                        }
                    }
                }
                dataStatus.DataIsLoaded(mSearchedNotes, mKeys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void readCategory(final boolean isWithAddView,final CategoryDataStatus dataStatus){
        this.mCategoriesDatabasse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCategories.clear();
                List<String> mKeys = new ArrayList<>();
                if(isWithAddView) {
                    Category add_cat_btn = new Category("add_cat_id", "Create Notebook", "create-notebook", R.drawable.add_notebook_img, "user_id", new Date().getTime(), new Date().getTime());
                    mCategories.add(add_cat_btn);
                    mKeys.add(add_cat_btn.getId());
                }
                for (DataSnapshot user: dataSnapshot.getChildren()){
                    for (DataSnapshot categoryNode: user.getChildren()){
                        mKeys.add(categoryNode.getKey());
                        Category cat = categoryNode.getValue(Category.class);
                        mCategories.add(cat);
                    }
                }
                dataStatus.DataIsLoaded(mCategories, mKeys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addCategory(Category cat, final CategoryDataStatus dataStatus){
        String id = mCategoriesDatabasse.child(cat.getUser_id()).push().getKey();
        cat.setId(id);
        mCategoriesDatabasse.child(cat.getUser_id()).child(id).setValue(cat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });

    }

    public void updateCategory(String key, Category cat, final CategoryDataStatus dataStatus){
        mCategoriesDatabasse.child(cat.getUser_id()).child(key).setValue(cat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }

    public void deleteCategory(String key, String userId, final CategoryDataStatus dataStatus){
        mCategoriesDatabasse.child(userId).child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }

}
