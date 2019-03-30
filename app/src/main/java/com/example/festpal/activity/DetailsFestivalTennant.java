package com.example.festpal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.festpal.dialog.LoadingDialog;
import com.example.festpal.dialog.PaymentConfirmDialog;
import com.example.festpal.model.Event;
import com.example.festpal.model.User;
import com.example.festpal.utils.UtilsManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.festpal.utils.Constant.PUT_EVENTS_BOOKED;

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
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_festival_tennant);
        loadingDialog = new LoadingDialog(this);
        Intent intent = getIntent();
        final Event event = new Gson().fromJson(intent.getStringExtra("EVENT"), Event.class);
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
                                    new AddBook(event, UtilsManager.getUser(DetailsFestivalTennant.this)).execute();
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

    private class AddBook extends AsyncTask<Void, Void, Boolean> {

        Event event;
        User user;
        public AddBook(Event event, User user) {
            this.event = event;
            this.user = user;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            String url = PUT_EVENTS_BOOKED;
            Map<String, String> val = new HashMap<>();
            val.put("idUser", user.getId());
            val.put("idEvent", event.getId());
            String json = new Gson().toJson(val);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(mediaType, json);
            Request request = new Request.Builder().url(url).put(requestBody).build();
            try {
                Response response = client.newCall(request).execute();
                Log.d(TAG, "doInBackground: response code " + response.code());
                Log.d(TAG, "doInBackground: response body " + response.body().string());
                if (response.code() != 200) {
                    return false;
                }
                else {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Intent intent = new Intent(DetailsFestivalTennant.this , PaymentActivity.class);
                DetailsFestivalTennant.this.startActivity(intent);
                DetailsFestivalTennant.this.finish();
            }
        }
    }
}
