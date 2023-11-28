package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.pagerecyclerview.MainFragment;


public class PageRecyclerviewActivity extends AppCompatActivity {

    private MainFragment mainFragment;
    private int lastIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_rv);
        FragmentManager fragmentManager = getSupportFragmentManager();
            mainFragment = MainFragment.newInstance();
        if (savedInstanceState == null) {
            mainFragment = MainFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.container, mainFragment, MainFragment.class.getName())
                    .commit();
        } else {
            fragmentManager.beginTransaction().show(mainFragment = (MainFragment) fragmentManager.findFragmentByTag(MainFragment.class.getName()))
                    .commit();
        }
    }

}
