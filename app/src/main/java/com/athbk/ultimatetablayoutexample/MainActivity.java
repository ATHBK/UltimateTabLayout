package com.athbk.ultimatetablayoutexample;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.athbk.ultimatetablayout.UltimateTabLayout;

public class MainActivity extends AppCompatActivity {

    UltimateTabLayout tabLayout;
    ViewPager viewPager;

    FragmentAdapterDemo adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (UltimateTabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        adapter = new FragmentAdapterDemo(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager, adapter);
    }
}
