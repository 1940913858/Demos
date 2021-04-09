package com.example.meetyou.myapplication.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public final class EduMediaListScrollHelper {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    public EduMediaListScrollHelper(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    public int autoScroll(boolean isSmoothScroll) {
        int centerItem = -1;
        int minDiff = Integer.MAX_VALUE;
        int recyclerViewCenterX = recyclerView.getWidth() / 2;
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        if (firstVisibleItemPosition > lastVisibleItemPosition) {
            for (int i = firstVisibleItemPosition; i <= minDiff; i++) {
                View viewByPosition = layoutManager.findViewByPosition(i);
                int abs = Math.abs(recyclerViewCenterX - (viewByPosition != null ? getCenterX(layoutManager.findViewByPosition(i)) : 0));
                if (abs < minDiff) {
                    minDiff = abs;
                    centerItem = i;
                }
            }

            for (int i = 0; i <= lastVisibleItemPosition; i++) {
                View viewByPosition = layoutManager.findViewByPosition(i);
                int abs = Math.abs(recyclerViewCenterX - (viewByPosition != null ? getCenterX(layoutManager.findViewByPosition(i)) : 0));
                if (abs < minDiff) {
                    minDiff = abs;
                    centerItem = i;
                }
            }
        } else {
            for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
                View viewByPosition = layoutManager.findViewByPosition(i);
                int abs = Math.abs(recyclerViewCenterX - (viewByPosition != null ? getCenterX(layoutManager.findViewByPosition(i)) : 0));
                if (abs < minDiff) {
                    minDiff = abs;
                    centerItem = i;
                }
            }

        }
        // Already centered
        if (minDiff <= 1) {
            return centerItem;
        }

        scroll2Position(centerItem, isSmoothScroll);

        return centerItem;
    }

    private int getCenterX(View view) {
        return (view.getLeft() + (view.getWidth() / 2));
    }

    public void scroll2Position(int position, final boolean isSmoothScroll) {
        int recyclerViewCenterX = recyclerView.getWidth() / 2;
        if (position >= 0 && position < recyclerView.getAdapter().getItemCount()) {
            final int diff = getCenterX(layoutManager.findViewByPosition(position)) - recyclerViewCenterX;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    if (isSmoothScroll) {
                        recyclerView.smoothScrollBy(diff, 0);
                    } else {
                        recyclerView.scrollBy(diff, 0);
                    }
                }
            });
        }
    }

    public final int autoScroll2Position(int position) {
        recyclerView.scrollToPosition(position);
        return autoScroll(false);
    }

}
