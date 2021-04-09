package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.adapter.EduMediaListAdapter;
import com.example.meetyou.myapplication.adapter.EduMediaListHelper;
import com.example.meetyou.myapplication.adapter.HorizontalItemDecoration;
import com.example.meetyou.myapplication.adapter.LoopLinearLayoutManager;

import java.util.ArrayList;

public class HorizontalRvActivity extends Activity implements EduMediaListHelper.OnItemSelectListener {

    private LoopLinearLayoutManager loopLayoutManager;
    private EduMediaListAdapter eduMediaListAdapter;
    private EduMediaListHelper eduMediaListHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_horizontal_rv);

        RecyclerView recyclerview = findViewById(R.id.recyclerview);

        loopLayoutManager = new LoopLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(loopLayoutManager);
        recyclerview.addItemDecoration(new HorizontalItemDecoration(30));
        eduMediaListAdapter = new EduMediaListAdapter(this);
        recyclerview.setAdapter(eduMediaListAdapter);

        eduMediaListHelper = new EduMediaListHelper();
        int itemWidth = 104 * 3;
        eduMediaListHelper.attachToRecyclerView(recyclerview, itemWidth);
        eduMediaListHelper.setOnItemSelectListener(this);


        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(i + "");
        }

        eduMediaListAdapter.setNewData(list);

    }


    @Override
    public void onItemSelect(int position) {

    }
}
