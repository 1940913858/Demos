package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.HabitTimeChooseView;
import com.example.meetyou.myapplication.view.rvinscrollview.RcvAdapter;

public class RvInsideNestedScrollViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rv_scrollview);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RcvAdapter());
    }

}
