package com.example.festpal.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.festpal.dialog.LoadingDialog;
import com.example.festpal.fragment.BookedFragment;
import com.example.festpal.CustomViewPager;
import com.example.festpal.fragment.ExploreFragment;
import com.example.festpal.fragment.FavoriteFragment;
import com.example.festpal.fragment.ListFestivalFragment;
import com.example.festpal.fragment.ProfileFragment;
import com.example.festpal.R;
import com.example.festpal.adapter.ViewPagerAdapter;
import com.example.festpal.fragment.RootFragment;
import com.example.festpal.model.Event;
import com.example.festpal.utils.Constant;
import com.example.festpal.utils.UtilsManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    LoadingDialog loadingDialog;

    private CustomViewPager viewPager;
    private TextView mTextMessage;
    private MenuItem prevMenuItem;
    private BookedFragment bookedFragment;
    private RootFragment rootFragment;
    private ProfileFragment profileFragment;
    BottomNavigationView navigation;
    private boolean isTourist = false;
    private ViewPagerAdapter viewPagerAdapter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_explore:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_booked:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_profile:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_favourite:
                    viewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };
    private ViewPager.OnPageChangeListener mViewPagerChangeListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            if(prevMenuItem != null){
                prevMenuItem.setChecked(false);
            }else{
                navigation.getMenu().getItem(0).setChecked(false);
            }
            navigation.getMenu().getItem(position).setChecked(true);
            prevMenuItem = navigation.getMenu().getItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
    private void setupViewPager(ViewPager viewPager){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        rootFragment = new RootFragment();
        bookedFragment = new BookedFragment();
        profileFragment = new ProfileFragment();
        viewPagerAdapter.addFragment(rootFragment);
        if(isTourist){
            viewPagerAdapter.addFragment(bookedFragment);
        }else{
            viewPagerAdapter.addFragment(new FavoriteFragment());
        }
        viewPagerAdapter.addFragment(profileFragment);
        viewPager.setAdapter(viewPagerAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingDialog = new LoadingDialog(this);

        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        if(isTourist){
            navigation.inflateMenu(R.menu.navigation_tourist);
        }else{
            navigation.inflateMenu(R.menu.navigation);
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.addOnPageChangeListener(mViewPagerChangeListener);
        setupViewPager(viewPager);
    }

    public void searchFestival(String query) {
        new SearchFestival().execute(query);
    }

    private class SearchFestival extends AsyncTask<String, Void, Integer> {

        private String result;
        private String query;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            query = strings[0];
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            String url = Constant.GET_EVENTS + query;
            Log.d(TAG, "doInBackground: url " + url);
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                Log.d(TAG, "doInBackground: response code " + response.code());
                if (response.code() != 200) {
                    return -1;
                }
                result = response.body().string();
                return 0;
            } catch (IOException e) {
                e.printStackTrace();
                return -2;
            }
        }

        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            loadingDialog.dismiss();
            if (i == -1) {
                UtilsManager.showToast("Festival tidak ditemukan", MainActivity.this);
            }
            else if (i == -2) {
                UtilsManager.showToast("Koneksi Bermasalah", MainActivity.this);
            }
            else if (i == 0) {
                ListFestivalFragment listFestivalFragment = new ListFestivalFragment();
                Bundle bundle = new Bundle();
                bundle.putString("EVENTS", result);
                bundle.putString("QUERY", query);
                Log.d(TAG, "onPostExecute: result " + result);
                listFestivalFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.root_frame, listFestivalFragment).addToBackStack(null).commit();
            }
        }
    }
    private class FavoriteFestival extends AsyncTask<String, Void, Integer> {

        private String result;
        private String query;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            String url = Constant.GET_EVENTS + query;
            Log.d(TAG, "doInBackground: url " + url);
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                Log.d(TAG, "doInBackground: response code " + response.code());
                if (response.code() != 200) {
                    return -1;
                }
                result = response.body().string();
                return 0;
            } catch (IOException e) {
                e.printStackTrace();
                return -2;
            }
        }

        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            loadingDialog.dismiss();
            if (i == -1) {
                UtilsManager.showToast("Festival tidak ditemukan", MainActivity.this);
            }
            else if (i == -2) {
                UtilsManager.showToast("Koneksi Bermasalah", MainActivity.this);
            }
            else if (i == 0) {
                ListFestivalFragment listFestivalFragment = new ListFestivalFragment();
                Bundle bundle = new Bundle();
                bundle.putString("EVENTS", result);
                bundle.putString("QUERY", query);
                Log.d(TAG, "onPostExecute: result " + result);
                listFestivalFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.root_frame, listFestivalFragment).addToBackStack(null).commit();
            }
        }
    }
}
