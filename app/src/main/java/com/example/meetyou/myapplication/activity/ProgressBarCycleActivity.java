package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.RoundProgressBar;
import com.example.meetyou.myapplication.view.WheelView.WheelView;

public class ProgressBarCycleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_progress_cycle);
        RoundProgressBar round_progress_bar = findViewById(R.id.round_progress_bar);
        round_progress_bar.setProgress(30);
    }


}
