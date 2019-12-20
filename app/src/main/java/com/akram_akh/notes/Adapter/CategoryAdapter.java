package com.akram_akh.notes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akram_akh.notes.Category;
import com.akram_akh.notes.CategoryActivity;
import com.akram_akh.notes.R;

import java.util.List;
import com.akram_akh.notes.MainActivity;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVh> {

    Context context ;
    List<Category> cat_List;

    public CategoryAdapter(Context context ,  List<Category> cat_List) {
        this.context = context;
        this.cat_List = cat_List;
    }

    @NonNull
    @Override
    public CategoryVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_col , parent , false);
        return new CategoryVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVh holder, int position) {
        holder.setData(cat_List.get(position), position);
    }

    @Override
    public int getItemCount() {
        return cat_List.size();
    }

    class CategoryVh extends RecyclerView.ViewHolder{
        TextView cat_name;
        ImageView cat_img;
        int notebook_images[] = {R.drawable.notebook_1, R.drawable.notebook_2, R.drawable.notebook_3, R.drawable.notebook_4, R.drawable.notebook_5};
        public CategoryVh(@NonNull View itemView) {
            super(itemView);
            cat_img = itemView.findViewById(R.id.cat_image_col);
            cat_name = itemView.findViewById(R.id.cat_name_col);

        }

        public void setData(Category category, int position) {
                cat_name.setText(category.getTitle());
                cat_img.setImageResource(category.getImage());
            if(category.getId() == "add_cat_id"){

                cat_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, CategoryActivity.class));
                    }
                });
            }
        }
    }
}
