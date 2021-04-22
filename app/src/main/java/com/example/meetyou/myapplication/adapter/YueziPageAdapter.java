package com.example.meetyou.myapplication.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meetyou.myapplication.R;

import java.util.List;

/**
 * 月子餐adapter
 * created by chensenfa
 * 2021/04/13
 */
public class YueziPageAdapter extends PagerAdapter{
    private List<String> mYueziList;
    private Context context;

    public YueziPageAdapter(Context context, List<String> list) {
        this.context = context;
        this.mYueziList = list;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_recovery_yuezi_item, null);

        RecyclerView yuezi_recyclerview = view.findViewById(R.id.yuezi_recyclerview);
        yuezi_recyclerview.setLayoutManager(new GridLayoutManager(context, 4));
        // 间距6dp
        final int itemGap = 6*3;
        yuezi_recyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = itemGap;
                outRect.right = itemGap;
            }
        });

        YueziItemAdapter yueziItemAdapter = new YueziItemAdapter(context);
        yuezi_recyclerview.setAdapter(yueziItemAdapter);

        yueziItemAdapter.setNewData(mYueziList);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mYueziList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mYueziList.get(position);
    }
}