package com.example.festpal;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class Details_Festival extends AppCompatActivity {
    private ViewPager mImageSlider;
    private TabLayout mDotsIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__festival);
        mImageSlider = (ViewPager) findViewById(R.id.image_slider);
        mDotsIndicator = (TabLayout) findViewById(R.id.tabDots);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this);
        mImageSlider.setAdapter(adapter);
        mDotsIndicator.setupWithViewPager(mImageSlider);
    }
}
