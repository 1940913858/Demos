package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meetyou.myapplication.view.LineRecyclerView;
import com.example.meetyou.myapplication.R;

import java.util.ArrayList;

public class HorizotalRVMaxActivity extends Activity {

    String content = "方案1 对Adapter进行修改\n" +
            "\n" +
            "网上大部分博客的解决方案都是这种方案，对Adapter做修改。具体如下\n" +
            "首先，让 Adapter 的 getItemCount() 方法返回 Integer.MAX_VALUE，使得position数据达到很大很大；\n" +
            "其次，在 onBindViewHolder() 方法里对position参数取余运算，拿到position对应的真实数据索引，然后对itemView绑定数据\n" +
            "最后，在初始化RecyclerView的时候，让其滑动到指定位置，如 Integer" +
            ".MAX_VALUE/2，这样就不会滑动到边界了，如果用户一根筋，真的滑动到了边界位置，再加一个判断，如果当前索引是0，就重新动态调整到初始位置\n" +
            "这个方案是挺简单，但并不完美。一是对我们的数据和索引做了计算操作，二是如果滑动到边界，再动态调整到中间，会有一个不明显的卡顿操作，使得滑动不是很顺畅。所以，直接看方案二。\n" +
            "\n" +
            "方案2 自定义LayoutManager，修改RecyclerView的布局方式\n" +
            "\n" +
            "这个算得上是一劳永逸的解决方案了，也是我今天要详细介绍的方案。我们都知道，RecyclerView的数据绑定是通过Adapter来处理的，而排版方式以及View" +
            "的回收控制等，则是通过LayoutManager来实现的，因此我们直接修改itemView的排版方式就可以实现我们的目标，让RecyclerView无限循环。";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizotalrvmax);
//        TextView contentTxt = findViewById(R.id.content);
//        contentTxt.setText(content);

        initData();

        LineRecyclerView ssss = (LineRecyclerView)findViewById(R.id.recyclerview);
        ssss.setLayoutManager(new LinearLayoutManager(this));
        ssss.setAdapter(new MyAdapter(mEntityList));
    }


    class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
        ArrayList<String> bEntityList;

        public MyAdapter(ArrayList<String> entityList) {
            bEntityList = entityList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(HorizotalRVMaxActivity.this).inflate(R.layout
                    .rv_item_horizotal, viewGroup, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.fruitName.setText(bEntityList.get(i));
        }

        @Override
        public int getItemCount() {
            return bEntityList.size();
        }
    }

    /**
     * RecyclerView的持有者类
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            fruitName = view.findViewById(R.id.hehe);
        }
    }

    private ArrayList<String> mEntityList;

    private void initData() {
        mEntityList = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            mEntityList.add((char) i + "");
        }
    }

}
