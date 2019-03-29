package com.example.festpal.activity;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.festpal.model.User;
import com.example.festpal.utils.Constant;
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

    private Boolean favorited;
    User user;
    Event event;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_festival_tourist);
        Intent intent = getIntent();
        event = new Gson().fromJson(intent.getStringExtra("EVENT"), Event.class);
        Log.d(TAG, "onCreate: event " + intent.getStringExtra("EVENT"));
        ArrayList<String> urls = new ArrayList<>();
        urls.add(event.getImage());
        mImageSlider = (ViewPager) findViewById(R.id.image_slider);
        mDotsIndicator = (TabLayout) findViewById(R.id.tabDots);
        mFavorite = (ImageView) findViewById(R.id.festival_details_favorite);
        user = UtilsManager.getUser(this);
        Log.d(TAG, "onCreate: favorite festivals\n" + user.getFavoriteFestivals());
        Log.d(TAG, "onCreate: id event " + event.getId());
        if (user.getFavoriteFestivals().contains(event.getId())) {
            favorited = true;
        }
        else {
            favorited = false;
        }
        if (favorited) {
            mFavorite.setImageResource(R.drawable.ic_favorite_redacc_24dp);
        }
        else {
            mFavorite.setImageResource(R.drawable.ic_favorite_border_redacc_24dp);
        }
        mFavorite.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                favorited = !favorited;
                if (favorited) {
                    new AddFavorite(true).execute();
                    mFavorite.setImageResource(R.drawable.ic_favorite_redacc_24dp);
                }
                else {
                    new AddFavorite(false).execute();
                    mFavorite.setImageResource(R.drawable.ic_favorite_border_redacc_24dp);
                }
                return true;
            }
        });
        ImageSliderAdapter adapter = new ImageSliderAdapter(this, urls);
        mImageSlider.setAdapter(adapter);
        mDotsIndicator.setupWithViewPager(mImageSlider);

        mBackButton = findViewById(R.id.profile_details_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    private class AddFavorite extends AsyncTask<Void, Void, Boolean> {

        private Boolean isFavorite;
        public AddFavorite(Boolean isFavorite) {
            this.isFavorite = isFavorite;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            String url = null;
            if (isFavorite) {
                url = Constant.PUT_FAVORITE;
            }
            else {
                url = Constant.PUT_FAVORITE + "/remove";
            }
            Map<String, String> val = new HashMap<>();
            val.put("idUser", user.getId());
            val.put("idEvent", event.getId());
            String json = new Gson().toJson(val);
            Log.d(TAG, "doInBackground: json " + json);
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
                if (isFavorite) {
                    user.getFavoriteFestivals().add(event.getId());
                }
                else {
                    user.getFavoriteFestivals().remove(event.getId());
                }
                UtilsManager.saveUser(DetailsFestivalTourist.this, user);
            }
        }
    }
}
