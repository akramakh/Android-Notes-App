package com.akram_akh.notes;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akram_akh.notes.Adapter.NoteAdapter;
import com.akram_akh.notes.Note;
import com.akram_akh.notes.NoteDetailActivity;
import com.akram_akh.notes.R;

import java.util.List;

public class RecyclerViewConfig {

    private Context mContext;
    private NotesAdapter mNoteAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Note> notes, List<String> keys){
        mContext = context;
        mNoteAdapter = new NotesAdapter(notes, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mNoteAdapter);
    }

    public class NoteItemView extends RecyclerView.ViewHolder{
        private TextView note_key, note_title , note_description , updated_at , cat_id;
        ImageView note_dot;
        private String key, category_id;

        int[] colors = {R.drawable.oval_0, R.drawable.oval_1, R.drawable.oval_2, R.drawable.oval_3, R.drawable.oval_4, R.drawable.oval_5,
                R.drawable.oval_6, R.drawable.oval_7, R.drawable.oval_8, R.drawable.oval_9, R.drawable.oval_10,
                R.drawable.oval_11, R.drawable.oval_12};

        public NoteItemView(@NonNull final ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.row_note, parent, false));
            note_key = itemView.findViewById(R.id.row_note_key);
            cat_id = itemView.findViewById(R.id.row_note_category_id);
            note_title = itemView.findViewById(R.id.note_title);
            note_description = itemView.findViewById(R.id.note_desc);
            note_dot = itemView.findViewById(R.id.note_dot);
            updated_at = itemView.findViewById(R.id.note_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NoteDetailActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("category_id", category_id);
                    intent.putExtra("title", note_title.getText().toString());
                    intent.putExtra("description", note_description.getText().toString());

                    mContext.startActivity(intent);
                }
            });
        }

        public  void bind(Note note, String key){
            note_title.setText(getShortExcerpt(note.getTitle()));
            note_description.setText(getLongExcerpt(note.getDescription()));
            note_dot.setImageResource(colors[note.getColor()]);
            updated_at.setText(getDate(note.getUpdated_at()));

            this.key = key;
            this.category_id = note.getCategory_id();
        }

        private String getDate(long time) {
            String date = DateFormat.format("dd-MM-yyyy", time).toString();
            return date;
        }

        private String getLongExcerpt(String long_text){
            String short_text = long_text;
            if(long_text.length() > 70){
                short_text = long_text.substring(0, 66) + "...";
            }
            return short_text;
        }

        private String getShortExcerpt(String long_text){
            String short_text = long_text;
            if(long_text.length() > 15){
                short_text = long_text.substring(0, 11) + "...";
            }
            return short_text;
        }
    }

    class NotesAdapter extends RecyclerView.Adapter<NoteItemView>{
        private List<Note> mNoteList;
        private List<String> mKeys;

        public NotesAdapter(List<Note> mNoteList, List<String> mKeys) {
            this.mNoteList = mNoteList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public NoteItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NoteItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteItemView holder, int position) {
            holder.bind(mNoteList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mNoteList.size();
        }
    }
}
