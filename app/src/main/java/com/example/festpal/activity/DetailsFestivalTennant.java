package com.example.festpal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.festpal.ImageSliderAdapter;
import com.example.festpal.R;
import com.example.festpal.dialog.PaymentConfirmDialog;
import com.example.festpal.model.Event;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailsFestivalTennant extends AppCompatActivity {
    private final static String TAG = DetailsFestivalTennant.class.getSimpleName();
    private ViewPager mImageSlider;
    private TabLayout mDotsIndicator;
    private Activity activity = this;
    private Button mPayment;
    private ImageView mBackButton;
    private TextView tvName;
    private TextView tvPlace;
    private TextView tvDate;
    private TextView tvDesc;
    private TextView tvPrice;
    private TextView tvStandCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_festival_tennant);
        Intent intent = getIntent();
        Event event = new Gson().fromJson(intent.getStringExtra("EVENT"), Event.class);
        Log.d(TAG, "onCreate: event " + intent.getStringExtra("EVENT"));
        ArrayList<String> urls = new ArrayList<>();
        urls.add(event.getImage());
        mImageSlider = (ViewPager) findViewById(R.id.image_slider);
        mDotsIndicator = (TabLayout) findViewById(R.id.tabDots);
        mPayment = (Button) findViewById(R.id.profile_details_booking);
        mBackButton = (ImageView) findViewById(R.id.profile_details_back);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this, urls);
        mImageSlider.setAdapter(adapter);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PaymentConfirmDialog paymentConfirmDialog = new PaymentConfirmDialog(activity);
                        paymentConfirmDialog.show();
                        paymentConfirmDialog.setDialogResult(new PaymentConfirmDialog.OnMyDialogResult() {
                            @Override
                            public void finish(boolean result) {
                                if(result){
                                    Log.d("SAP","SAP");
                                }
                            }
                        });
                    }
                }, 1000);

            }
        });

        mDotsIndicator.setupWithViewPager(mImageSlider);



        tvName = findViewById(R.id.festival_details_name);
        tvPlace = findViewById(R.id.festival_details_place);
        tvDate = findViewById(R.id.festival_details_date);
        tvDesc = findViewById(R.id.profile_details_about);
        tvPrice = findViewById(R.id.festival_details_price);
        tvStandCount = findViewById(R.id.festival_details_stand_count);

        tvName.setText(event.getName());
        tvPlace.setText(event.getVenue());

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");

        tvDate.setText(df.format(event.getDate()));
        tvDesc.setText(event.getDescription());
        tvPrice.setText("Rp " + event.getPricePerStands() + "/stand");
        tvStandCount.setText("Tersedia " + event.getAvailableStands() + " stand");

        String url = event.getImage();
    }
}
