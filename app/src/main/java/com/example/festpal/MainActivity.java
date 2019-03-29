package com.example.festpal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.festpal.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private CustomViewPager viewPager;
    private TextView mTextMessage;
    private MenuItem prevMenuItem;
    private Booked bookedFragment;
    private Explore exploreFragment;
    private Profile profileFragment;
    BottomNavigationView navigation;
    private boolean isTourist = false;
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
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        exploreFragment = new Explore();
        bookedFragment = new Booked();
        profileFragment = new Profile();
        viewPagerAdapter.addFragment(exploreFragment);
        if(isTourist){
            viewPagerAdapter.addFragment(bookedFragment);
        }else{
            viewPagerAdapter.addFragment(new Favorite());
        }
        viewPagerAdapter.addFragment(profileFragment);
        viewPager.setAdapter(viewPagerAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}
