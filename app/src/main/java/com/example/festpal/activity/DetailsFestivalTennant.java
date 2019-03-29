package com.example.festpal.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.festpal.ImageSliderAdapter;
import com.example.festpal.R;

public class DetailsFestivalTennant extends AppCompatActivity {
    private ViewPager mImageSlider;
    private TabLayout mDotsIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_festival_tennant);
        mImageSlider = (ViewPager) findViewById(R.id.image_slider);
        mDotsIndicator = (TabLayout) findViewById(R.id.tabDots);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this);
        mImageSlider.setAdapter(adapter);
        mDotsIndicator.setupWithViewPager(mImageSlider);
    }
}
