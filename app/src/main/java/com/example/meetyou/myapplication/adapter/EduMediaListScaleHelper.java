package com.example.meetyou.myapplication.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class EduMediaListScaleHelper {

    private String TAG = "LoopLinearLayoutManager";

    private RecyclerView recyclerView;
    private int itemWidth;

    private LinearLayoutManager layoutManager;
    private int itemMargin;


    public EduMediaListScaleHelper(RecyclerView recyclerView, int itemWidth) {
        this.recyclerView = recyclerView;
        this.itemWidth = itemWidth;
        this.layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        itemMargin = 10 * 3;
    }


    public void autoScale() {
        int minDiff = Integer.MAX_VALUE;

        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        if (firstVisibleItemPosition > lastVisibleItemPosition) {
            for (int i = firstVisibleItemPosition; i <= minDiff; i++) {
                scale2Position(i);
            }

            for (int i = 0; i <= lastVisibleItemPosition; i++) {
                scale2Position(i);
            }
        } else {
            for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
                scale2Position(i);
            }

        }
    }

    private int maxDiff;

    public void scale2Position(int position) {
        int recyclerCenterX = this.recyclerView.getWidth() / 2;
        View viewByPosition = layoutManager.findViewByPosition(position);
        int diff = Math.abs(recyclerCenterX - getCenterX(viewByPosition));
        maxDiff = itemWidth + itemMargin;
        float scale = diff > maxDiff ? 0.8F : 1.0F - (float) diff / (float) maxDiff * 0.2F;

        viewByPosition.setPivotX((float) viewByPosition.getWidth() / 2.0F);
        viewByPosition.setPivotY((float) viewByPosition.getHeight() / 2.0F);
        viewByPosition.setScaleX(scale);
        viewByPosition.setScaleY(scale);
    }

    private int getCenterX(View view) {
        return (view.getLeft() + (view.getWidth() / 2));
    }


}
