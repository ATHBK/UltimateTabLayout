package com.athbk.ultimatetablayoutexample;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.athbk.ultimatetablayout.IFTabAdapter;

/**
 * Created by athbk on 8/25/17.
 */

public class FragmentAdapterDemo extends FragmentPagerAdapter implements IFTabAdapter {


    public FragmentAdapterDemo(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentDemo.newInstance();
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public String getTitle(int position) {
        return "";
    }

    @Override
    public int getIcon(int position) {
        return R.drawable.tab_1_selected;
    }

    @Override
    public boolean isEnableBadge(int position) {
        if (position == 0){
            return true;
        }
        return false;
    }
}
