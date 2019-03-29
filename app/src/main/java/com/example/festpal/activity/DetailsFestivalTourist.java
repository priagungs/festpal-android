package com.example.festpal.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.festpal.ImageSliderAdapter;
import com.example.festpal.R;
import com.example.festpal.model.Event;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;

public class DetailsFestivalTourist extends AppCompatActivity {
    private final static String TAG = DetailsFestivalTourist.class.getSimpleName();
    private ViewPager mImageSlider;
    private TabLayout mDotsIndicator;
    private ImageView mFavorite;
    private ImageView mBackButton;
    private TextView tvName;
    private TextView tvPlace;
    private TextView tvDate;
    private TextView tvDesc;

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

        mBackButton = findViewById(R.id.profile_details_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Event event = new Gson().fromJson(intent.getStringExtra("EVENT"), Event.class);
        Log.d(TAG, "onCreate: event " + intent.getStringExtra("EVENT"));

        tvName = findViewById(R.id.festival_details_name);
        tvPlace = findViewById(R.id.festival_details_place);
        tvDate = findViewById(R.id.festival_details_date);
        tvDesc = findViewById(R.id.profile_details_about);

        tvName.setText(event.getName());
        tvPlace.setText(event.getVenue());

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");

        tvDate.setText(df.format(event.getDate()));
        tvDesc.setText(event.getDescription());
    }
}
