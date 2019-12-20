package com.akram_akh.notes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akram_akh.notes.Note;
import com.akram_akh.notes.NoteDetailActivity;
import com.akram_akh.notes.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVh> {

    Context context ;
    List<Note> note_list;
    List<String> mkeys;
    Note item;

    public NoteAdapter(Context context ,  List<Note> note_list) {
        this.context = context;
        this.note_list = note_list;
    }

    @NonNull
    @Override
    public NoteVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.row_note , parent , false);
        return new NoteVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVh holder, int position) {
        holder.setData(note_list.get(position));
        item = note_list.get(position);
    }

    @Override
    public int getItemCount() {
        return note_list.size();
    }

    class NoteVh extends RecyclerView.ViewHolder{
        TextView  note_key, note_title , note_description , updated_at , cat_id;
        ImageView note_dot;

        String key, category_id;
        int color;

        int[] colors = {R.drawable.oval_0, R.drawable.oval_1, R.drawable.oval_2, R.drawable.oval_3, R.drawable.oval_4, R.drawable.oval_5,
                R.drawable.oval_6, R.drawable.oval_7, R.drawable.oval_8, R.drawable.oval_9, R.drawable.oval_10,
                R.drawable.oval_11, R.drawable.oval_12};

        public NoteVh(@NonNull final View itemView) {
            super(itemView);
            note_key = itemView.findViewById(R.id.row_note_key);
            cat_id = itemView.findViewById(R.id.row_note_category_id);
            note_title = itemView.findViewById(R.id.row_note_key);
            note_title = itemView.findViewById(R.id.note_title);
            note_description = itemView.findViewById(R.id.note_desc);
            note_dot = itemView.findViewById(R.id.note_dot);
            updated_at = itemView.findViewById(R.id.note_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NoteDetailActivity.class);
                    intent.putExtra("key", note_key.getText().toString());
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("description", item.getDescription());
                    intent.putExtra("color", color);
                    intent.putExtra("created_at", item.getCreated_at());
                    intent.putExtra("updated_at", item.getUpdated_at());
                    intent.putExtra("category_id", cat_id.getText().toString());
                    context.startActivity(intent);
                }
            });
        }



        public void setData(Note note) {
            category_id = note.getCategory_id();
            note_title.setText(getShortExcerpt(note.getTitle()));
            note_description.setText(getLongExcerpt(note.getDescription()));
            note_dot.setImageResource(colors[note.getColor()]);
            updated_at.setText(getDate(note.getUpdated_at()));
            color = colors[note.getColor()];
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
}