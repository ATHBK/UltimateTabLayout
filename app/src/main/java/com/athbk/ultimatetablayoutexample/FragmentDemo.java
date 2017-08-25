package com.athbk.ultimatetablayoutexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by athbk on 8/25/17.
 */

public class FragmentDemo extends Fragment{

    public static FragmentDemo newInstance() {
        Bundle args = new Bundle();
        FragmentDemo fragment = new FragmentDemo();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_demo, container, false);
        return v;
    }
}
