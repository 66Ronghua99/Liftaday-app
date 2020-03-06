package com.example.liftaday;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class MainActivity extends AppCompatActivity {

    private WeatherFragment weatherFragment;
    private NewsFragment newsFragment;
    private HealthFragment healthFragment;
    private OutfitFragment outfitFragment;
    private List mFragment;
    private FragmentPagerAdapter mAdapter;
    private ViewPagerForScrollView viewPager;
    private BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        navigationView = findViewById(R.id.navigator);
        navigationView.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@Nullable MenuItem item) {
                int selectedFragment = 0;

                switch (item.getItemId()) {
                    case R.id.nav_weather:
                        selectedFragment = 0;
                        break;
                    case R.id.nav_outfit:
                        selectedFragment = 1;
                        break;
                    case R.id.nav_news:
                        selectedFragment = 2;
                        break;
                    case R.id.nav_health:
                        selectedFragment = 3;
                        break;
                }
                viewPager.setCurrentItem(selectedFragment);
                return true;
            }
        };

    private void init(){
        weatherFragment = new WeatherFragment();
        newsFragment = new NewsFragment();
        healthFragment = new HealthFragment();
        outfitFragment = new OutfitFragment();
        viewPager = findViewById(R.id.fragment_container);
        mFragment = new ArrayList<Fragment>();
        mFragment.add(weatherFragment);
        mFragment.add(outfitFragment);
        mFragment.add(newsFragment);
        mFragment.add(healthFragment);


        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return (Fragment) mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };
        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int arg0)
            {

                int currentItem = viewPager.getCurrentItem();
                switch (currentItem){
                    case 0:navigationView.setSelectedItemId(R.id.nav_weather);break;
                    case 1:navigationView.setSelectedItemId(R.id.nav_outfit);break;
                    case 2:navigationView.setSelectedItemId(R.id.nav_news);break;
                    case 3:navigationView.setSelectedItemId(R.id.nav_health);break;
                }
//                navigationView.setSelected(true);
                Log.i("tag",String.valueOf(currentItem)+"  "+String.valueOf(navigationView.getSelectedItemId()));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
// TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
// TODO Auto-generated method stub

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void settingClicked(MenuItem item){
        Intent intent= new Intent(MainActivity.this, Setting.class);
//        Intent intent= new Intent(MainActivity.this, news1.class);
        startActivity(intent);

    }

}


