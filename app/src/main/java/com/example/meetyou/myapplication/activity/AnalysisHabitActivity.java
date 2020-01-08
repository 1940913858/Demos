package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.AnalysisHabitView;
import com.example.meetyou.myapplication.view.HabitTimeChooseView;

import java.util.Calendar;

public class AnalysisHabitActivity extends Activity implements HabitTimeChooseView.OnResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analysis_habit);
//        AnalysisHabitView analysis_habit_view = findViewById(R.id.analysis_habit_view);
//        analysis_habit_view.setProgress(0.4f);


        HabitTimeChooseView habitTimeChooseView= findViewById(R.id.habitTimeChooseView);
        habitTimeChooseView.setListener(this);


    }


    @Override
    public void onOk(int type, int year, int yearMonth, int month) {
    }

    @Override
    public void onCancel() {

    }
}
