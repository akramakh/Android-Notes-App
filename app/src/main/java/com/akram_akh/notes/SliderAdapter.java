package com.akram_akh.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layout_inflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int slide_imgs[] = {
            R.drawable.first_onboarding_msg,
            R.drawable.second_onboarding_img,
            R.drawable.third_onboarding_img
    };

    public String slide_headers[] = {
            "Notebooks",
            "Add Notes to Notebook",
            "Notebooks"
    };

    public String slide_descriptions[] = {
            "Notebooks are the best place to manage your Notes ",
            "Simply create your note and add it to your favorite notebook",
            "Notebooks are the best place to manage your Notes "
    };

    @Override
    public int getCount() {
        return slide_headers.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layout_inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layout_inflater.inflate(R.layout.slide_layout, container, false);

        ImageView img = (ImageView) view.findViewById(R.id.onboarding_img);
        TextView header = (TextView) view.findViewById(R.id.onboarding_header);
        TextView description = (TextView) view.findViewById(R.id.onboarding_description);

        img.setImageResource(slide_imgs[position]);
        header.setText(slide_headers[position]);
        description.setText(slide_descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
