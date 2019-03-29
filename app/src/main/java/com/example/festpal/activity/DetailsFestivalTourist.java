package com.example.festpal.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.festpal.ImageSliderAdapter;
import com.example.festpal.R;

public class DetailsFestivalTourist extends AppCompatActivity {
    private ViewPager mImageSlider;
    private TabLayout mDotsIndicator;
    private ImageView mFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_festival_tourist);
        mImageSlider = (ViewPager) findViewById(R.id.image_slider);
        mDotsIndicator = (TabLayout) findViewById(R.id.tabDots);
        mFavorite = (ImageView) findViewById(R.id.festival_details_favorite);
        mFavorite.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mFavorite.setImageResource(R.drawable.ic_favorite_redacc_24dp);
                return true;
            }
        });
        ImageSliderAdapter adapter = new ImageSliderAdapter(this);
        mImageSlider.setAdapter(adapter);
        mDotsIndicator.setupWithViewPager(mImageSlider);

    }
}
