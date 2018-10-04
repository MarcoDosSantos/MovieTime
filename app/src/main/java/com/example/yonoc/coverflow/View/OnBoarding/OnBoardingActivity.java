package com.example.yonoc.coverflow.View.OnBoarding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.MainActivityPortada.MainActivity;
import com.gc.materialdesign.views.ButtonFlat;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class OnBoardingActivity extends FragmentActivity {

    private ViewPager pager;
    private SmartTabLayout indicator;
    private ButtonFlat skip;
    private ButtonFlat next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        pager = findViewById(R.id.pager);
        indicator = findViewById(R.id.indicator);
        skip = findViewById(R.id.skip);
        next = findViewById(R.id.next);

        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

        if (preferences.getBoolean("onboarding_complete", false)) {
            Intent principal = new Intent(this, MainActivity.class);
            startActivity(principal);
            finish();
            return;
        } else {
            preferences.edit().putBoolean("onboarding_complete", true).apply();
        }
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new OnBoardingScreen1Fragment();
                    case 1:
                        return new OnBoardingScreen2Fragment();
                    case 2:
                        return new OnBoardingScreen3Fragment();
                    case 3:
                        return new OnBoardingScreen4Fragment();
                    case 4:
                        return new OnBoardingScreen5Fragment();
                    default:
                        return null;
                }
            }

        };

        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

        skip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finishOnboarding();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pager.getCurrentItem() == 4) {
                    finishOnboarding();
                } else {
                    pager.setCurrentItem(
                            pager.getCurrentItem() + 1,
                            true
                    );
                }
            }
        });

        indicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 4) {
                    skip.setVisibility(View.GONE);
                    next.setText("Done");
                } else {
                    skip.setVisibility(View.VISIBLE);
                    next.setText("Next");
                }
            }
        });
    }

    private void finishOnboarding() {

        Intent onboarding = new Intent(this, OnBoardingActivity.class);
        startActivity(onboarding);
        finish();
        return;
    }
}



