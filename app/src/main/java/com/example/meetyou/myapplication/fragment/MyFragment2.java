package com.example.meetyou.myapplication.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.activity.PostRecoveryActivity;
import com.example.meetyou.myapplication.adapter.YueziItemAdapter;
import com.example.meetyou.myapplication.adapter.YueziPageAdapter;
import com.example.meetyou.myapplication.view.CategoryTabStrip;
import com.example.meetyou.myapplication.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MyFragment2 extends Fragment {

    public MyFragment2() {
        initData();

    }

    public static MyFragment2 newInstance(int day) {
        MyFragment2 testFragment = new MyFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt("DAY", day);
        testFragment.setArguments(bundle);
        return testFragment;
    }

    private void initData(){
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
    }

    List<String> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int addPosition = 0;
        if (bundle != null) {
            addPosition = bundle.getInt("DAY");
        }

        View inflate = getLayoutInflater().inflate(R.layout.activity_fragment2_item, null);

        ViewPager viewpager = inflate.findViewById(R.id.viewpager);
        CategoryTabStrip strip = inflate.findViewById(R.id.strip);


        YueziPageAdapter yueziPageAdapter = new YueziPageAdapter(getActivity(), list);
        viewpager.setAdapter(yueziPageAdapter);
        strip.setViewPager(viewpager);

        return inflate;
    }

}