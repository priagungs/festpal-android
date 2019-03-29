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

import com.example.festpal.ImageSliderAdapter;
import com.example.festpal.R;
import com.example.festpal.dialog.PaymentConfirmDialog;

public class DetailsFestivalTennant extends AppCompatActivity {
    private ViewPager mImageSlider;
    private TabLayout mDotsIndicator;
    private Activity activity = this;
    private Button mPayment;
    private ImageView mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_festival_tennant);
        mImageSlider = (ViewPager) findViewById(R.id.image_slider);
        mDotsIndicator = (TabLayout) findViewById(R.id.tabDots);
        mPayment = (Button) findViewById(R.id.profile_details_booking);
        mBackButton = (ImageView) findViewById(R.id.profile_details_back);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this);
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
    }
}
