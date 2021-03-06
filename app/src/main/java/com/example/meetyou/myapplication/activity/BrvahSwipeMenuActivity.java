package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.EasySwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

public class BrvahSwipeMenuActivity extends Activity {

    String TAG = "senfa";

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<String> listData;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_swipe_menu);

        initIView();
        initIData();
    }


    private void initIView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(R.layout.item_rv_swipemenu, null);
        recyclerView.setAdapter(myAdapter);
        inflater = getLayoutInflater();

    }

    private void initIData() {
        listData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            listData.add("index is =" + i);
        }
        myAdapter.addData(listData);
        myAdapter.notifyDataSetChanged();
    }

    public class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            helper.setText(R.id.content_txt,"内容区域"+item);

            //收藏
            helper.getView(R.id.right_menu_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BrvahSwipeMenuActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                    EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);

                    easySwipeMenuLayout.resetStatus();
                }
            });
            //删除
            helper.getView(R.id.right_menu_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getData().remove(helper.getLayoutPosition());
                    notifyDataSetChanged();

                    Toast.makeText(BrvahSwipeMenuActivity.this, "删除", Toast
                            .LENGTH_SHORT).show();

                }
            });

            helper.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BrvahSwipeMenuActivity.this, "setOnClickListener", Toast
                            .LENGTH_SHORT).show();

                }
            });

        }

    }


}
