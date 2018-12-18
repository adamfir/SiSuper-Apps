package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vitorizkiimanda.sisuper_apps.R;
import com.example.vitorizkiimanda.sisuper_apps.provider.OnboardingAdapter;
import com.example.vitorizkiimanda.sisuper_apps.provider.SessionManagement;

public class Onboarding extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotsLayout;

    private TextView[] mDots;
    private Button actionButton;

    private OnboardingAdapter onboardingAdapter;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);


        // Session Manager
        session = new SessionManagement(getApplicationContext());
        session.checkLogin();

        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotsLayout = findViewById(R.id.dotsLayout);
        actionButton = findViewById(R.id.action_button);

        onboardingAdapter = new OnboardingAdapter(this);

//        initialization
        mSlideViewPager.setAdapter(onboardingAdapter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
        actionButton.setVisibility(View.INVISIBLE);

//        onClickListener
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(Onboarding.this, LoginActivity.class);
                startActivity(moveIntent);
                finish();
            }
        });
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[5];

        for (int i = 0 ; i < mDots.length ; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorPurpleDark));

            mDotsLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    public void changeDotsIndicator(int position){
        for (int i = 0 ; i < mDots.length ; i++){
            if(position == i) mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
            else mDots[i].setTextColor(getResources().getColor(R.color.colorPurpleDark));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            changeDotsIndicator(position);
            if(position == mDots.length - 1){
                actionButton.setVisibility(View.VISIBLE);
            }
            else{
                actionButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
