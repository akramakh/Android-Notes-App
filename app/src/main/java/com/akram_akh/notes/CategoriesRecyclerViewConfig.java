package com.akram_akh.notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CategoriesRecyclerViewConfig {

    private Context mContext;
    private CategoriesAdapter mCategoryAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Category> categories, List<String> keys){
        mContext = context;
        mCategoryAdapter = new CategoriesAdapter(categories, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mCategoryAdapter);
    }

    public class CategoryItemView extends RecyclerView.ViewHolder{

        TextView cat_name;
        ImageView cat_img;

        private String key, user_id;

        public CategoryItemView(@NonNull final ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.category_col, parent, false));

            cat_img = itemView.findViewById(R.id.cat_image_col);
            cat_name = itemView.findViewById(R.id.cat_name_col);

//            if(key.equals("add_cat_id")){
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext, CategoryActivity.class);
//                        mContext.startActivity(intent);
//                    }
//                });
//            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CategoryActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("title", cat_name.getText().toString());
                    Integer resource = (Integer)cat_img.getTag();
                    intent.putExtra("image", resource);

                    mContext.startActivity(intent);
                }
            });
        }

        public  void bind(Category cat, String key){
            cat_name.setText(getShortExcerpt(cat.getTitle()));
            cat_img.setImageResource(cat.getImage());
            cat_img.setTag(cat.getImage());

            this.key = key;
            this.user_id = cat.getUser_id();
        }

        private String getShortExcerpt(String long_text){
            String short_text = long_text;
            if(long_text.length() > 15){
                short_text = long_text.substring(0, 11) + "...";
            }
            return short_text;
        }
    }

    class CategoriesAdapter extends RecyclerView.Adapter<CategoryItemView>{
        private List<Category> mCategoriesList;
        private List<String> mKeys;

        public CategoriesAdapter(List<Category> mCAtegoriesList, List<String> mKeys) {
            this.mCategoriesList = mCAtegoriesList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public CategoryItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CategoryItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryItemView holder, int position) {
            holder.bind(mCategoriesList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mCategoriesList.size();
        }
    }
}
