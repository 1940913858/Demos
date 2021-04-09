package com.example.meetyou.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFragment extends Fragment {

    public MyFragment() {


    }

    public static MyFragment newInstance(int day) {
        MyFragment testFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("DAY", day);
        testFragment.setArguments(bundle);
        return testFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int addPosition = 0;
        if (bundle != null) {
            addPosition = bundle.getInt("DAY");
        }



        TextView tv = new TextView(getActivity());
        tv.setBackgroundColor(Color.RED);
        tv.setText(addPosition + "嘿嘿");
        return tv;
    }
}