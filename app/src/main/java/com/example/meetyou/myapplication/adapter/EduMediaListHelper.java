package com.example.meetyou.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public class EduMediaListHelper {

    private String TAG = "LoopLinearLayoutManager";

    private RecyclerView recyclerView;
    private int itemWidth;
    private EduMediaListScaleHelper scaleHelperEdu;
    private EduMediaListScrollHelper scrollHelperEdu;

    public void attachToRecyclerView(RecyclerView recyclerView, int itemWidth) {
        this.recyclerView = recyclerView;
        this.itemWidth = itemWidth;
        scaleHelperEdu = new EduMediaListScaleHelper(recyclerView, itemWidth);
        scrollHelperEdu = new EduMediaListScrollHelper(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int selectedItem = scrollHelperEdu.autoScroll(false);
                    mOnItemSelectListener.onItemSelect(selectedItem);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                scaleHelperEdu.autoScale();
            }
        });
    }

    private OnItemSelectListener mOnItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        mOnItemSelectListener = onItemSelectListener;
    }

    public interface OnItemSelectListener {
        void onItemSelect(int position);
    }

    public void scroll2Center(int position) {
        scaleHelperEdu.scale2Position(position);
        scrollHelperEdu.scroll2Position(position, true);
    }

    public void autoScroll2Position(final int position) {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int lastPostion = scrollHelperEdu.autoScroll2Position(position);
                mOnItemSelectListener.onItemSelect(lastPostion);
            }
        });
    }
}
