package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.AnalysisHabitView;
import com.example.meetyou.myapplication.view.WheelView.WheelView;

public class WheelViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wheelview);
        WheelView wheelview = findViewById(R.id.wheelview);
        wheelview.setAdapter(new String[]{"哈哈哈","222","333"});
    }


}
