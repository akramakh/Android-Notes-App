package com.akram_akh.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akram_akh.notes.Auth.SignInActivity;
import com.akram_akh.notes.Auth.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;

public class OnboardingScreenActivity extends AppCompatActivity {

    ViewPager view_pager;
    RelativeLayout slide_controller;
    LinearLayout dots_container;
    TextView dots_TV[];
    Button next_btn, skip_btn;

    SliderAdapter slider_adapter;
    int current_page = 0;

    FirebaseAuth f_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        f_auth = FirebaseAuth.getInstance();

        if(f_auth.getCurrentUser() != null){
            startActivity(new Intent(OnboardingScreenActivity.this, MainActivity.class));
            finish();
        }

        view_pager = (ViewPager) findViewById(R.id.slide_view_pager);

        dots_container = findViewById(R.id.dots_indicator);
        next_btn = findViewById(R.id.onboarding_next_btm);
        skip_btn = findViewById(R.id.onboarding_skip_btm);
        slide_controller = findViewById(R.id.onboarding_control);

        slider_adapter = new SliderAdapter(this);

        view_pager.setAdapter(slider_adapter);
        addDotsIndecator(0);
        view_pager.addOnPageChangeListener(view_listener);


        RelativeLayout.LayoutParams slide_controller_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
        slide_controller_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        slide_controller_params.setMargins(16, 10, 16, 32);
        slide_controller.setLayoutParams(slide_controller_params);

        RelativeLayout.LayoutParams next_button_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        next_button_params.addRule(RelativeLayout.ALIGN_PARENT_END,RelativeLayout.TRUE);
        next_btn.setLayoutParams(next_button_params);
        next_btn.setBackground(getResources().getDrawable(R.drawable.btn_rounded_3x_primary));

        RelativeLayout.LayoutParams skip_button_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        skip_button_params.addRule(RelativeLayout.ALIGN_PARENT_START,RelativeLayout.TRUE);
        skip_btn.setLayoutParams(skip_button_params);

        next_btn.setText("Next");
        next_btn.setOnClickListener(next_listener);
        skip_btn.setText("Skip");
        skip_btn.setOnClickListener(skip_listener);



        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_pager.setCurrentItem(current_page+1);
            }
        });

        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_pager.setCurrentItem(dots_TV.length-1);
            }
        });
    }

    private void addDotsIndecator(int position){
        dots_TV = new TextView[3];
        dots_container.removeAllViews();
        for (int i =0; i<dots_TV.length; i++){
            dots_TV[i] = new TextView(this);
            dots_TV[i].setText(Html.fromHtml("&#8226"));
            dots_TV[i].setTextSize(35);
            dots_TV[i].setTextColor(getResources().getColor(R.color.colorGray));

            dots_container.addView(dots_TV[i]);
        }
        dots_TV[2].setVisibility(View.INVISIBLE);
        if(dots_TV.length > 0){

            dots_TV[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    ViewPager.OnPageChangeListener view_listener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndecator(position);
            current_page = position;
            if(position == 0){
                dots_container.setVisibility(View.VISIBLE);
                next_btn.setEnabled(true);
                skip_btn.setEnabled(true);
                skip_btn.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams slide_controller_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
                slide_controller_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                slide_controller_params.setMargins(16, 10, 16, 32);
                slide_controller.setLayoutParams(slide_controller_params);

                RelativeLayout.LayoutParams next_button_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                next_button_params.addRule(RelativeLayout.ALIGN_PARENT_END,RelativeLayout.TRUE);
                next_btn.setLayoutParams(next_button_params);
                next_btn.setBackground(getResources().getDrawable(R.drawable.btn_rounded_3x_primary));

                RelativeLayout.LayoutParams skip_button_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                skip_button_params.addRule(RelativeLayout.ALIGN_PARENT_START,RelativeLayout.TRUE);
                skip_btn.setLayoutParams(skip_button_params);

                next_btn.setText("Next");
                next_btn.setOnClickListener(next_listener);
                skip_btn.setText("Skip");
                skip_btn.setOnClickListener(skip_listener);

            }else if(position == dots_TV.length - 1){
                dots_container.setVisibility(View.INVISIBLE);
                next_btn.setEnabled(true);
                skip_btn.setEnabled(true);

                RelativeLayout.LayoutParams slide_controller_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 140);
                slide_controller_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                slide_controller_params.setMargins(16, 10, 16, 16);
                slide_controller.setLayoutParams(slide_controller_params);
                slide_controller.setPadding(0,0,0,0);

                RelativeLayout.LayoutParams next_button_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                next_button_params.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
                next_button_params.setMargins(0,0,0,0);
                next_btn.setBackground(getResources().getDrawable(R.drawable.btn_rounded_1x_primary));
                next_btn.setLayoutParams(next_button_params);


                RelativeLayout.LayoutParams skip_button_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                skip_button_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                skip_button_params.setMargins(0,0,0,0);
                skip_btn.setLayoutParams(skip_button_params);

                next_btn.setText("sign in");
                next_btn.setOnClickListener(signin_listener);
                skip_btn.setText("sign up");
                skip_btn.setOnClickListener(signup_listener);

            }else{
                dots_container.setVisibility(View.VISIBLE);
                next_btn.setEnabled(true);
                skip_btn.setEnabled(true);
                skip_btn.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams slide_controller_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
                slide_controller_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                slide_controller_params.setMargins(16, 10, 16, 32);
                slide_controller.setLayoutParams(slide_controller_params);

                RelativeLayout.LayoutParams next_button_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                next_button_params.addRule(RelativeLayout.ALIGN_PARENT_END,RelativeLayout.TRUE);
                next_btn.setLayoutParams(next_button_params);
                next_btn.setBackground(getResources().getDrawable(R.drawable.btn_rounded_3x_primary));

                RelativeLayout.LayoutParams skip_button_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                skip_button_params.addRule(RelativeLayout.ALIGN_PARENT_START,RelativeLayout.TRUE);
                skip_btn.setLayoutParams(skip_button_params);

                next_btn.setText("Next");
                next_btn.setOnClickListener(next_listener);
                skip_btn.setText("Skip");
                skip_btn.setOnClickListener(skip_listener);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    Button.OnClickListener next_listener = new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                view_pager.setCurrentItem(current_page+1);
            }
    };

    Button.OnClickListener skip_listener = new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                view_pager.setCurrentItem(dots_TV.length-1);
            }
    };

    Button.OnClickListener signin_listener = new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnboardingScreenActivity.this, SignInActivity.class));
                finish();
            }
    };

    Button.OnClickListener signup_listener = new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnboardingScreenActivity.this, SignUpActivity.class));
                finish();
            }
    };
}
