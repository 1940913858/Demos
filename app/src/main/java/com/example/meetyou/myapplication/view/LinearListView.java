package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public class LinearListView extends LinearLayout {
    private boolean isRemoveDivider;
    private boolean isHorizontal;
    private Context context;
    private ListAdapter mAdapter;
    private int mDividerLeftMargin;
    public static final String TAG = "divider";
    private boolean useNewLayoutParams = true;

    public LinearListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public LinearListView(Context context) {
        super(context);
        this.context = context;
    }

    private DataSetObserver mDataObserver = new DataSetObserver() {

        @Override
        public void onChanged() {
            setupChildren();
        }

        @Override
        public void onInvalidated() {
            setupChildren();
        }

    };

    public void setAdapter(ListAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mDataObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mDataObserver);
        }
        setupChildren();
    }

    public void setAdapter(ListAdapter adapter, boolean useNewLayoutParams) {
        setAdapter(adapter);
        this.useNewLayoutParams = useNewLayoutParams;
    }

    private void setupChildren() {
        if (mAdapter == null) {
            return;
        }
        removeAllViews();
        if (isHorizontal) {
            setOrientation(LinearLayout.HORIZONTAL);
        } else {
            setOrientation(LinearLayout.VERTICAL);

        }
        int density = (int) getResources().getDisplayMetrics().density;
        density = density <= 0 ? 1 : 1;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View view = mAdapter.getView(i, null, null);
            addView(view, useNewLayoutParams || view.getLayoutParams() == null ? new LayoutParams
                    (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT) : view.getLayoutParams
                    ());
            if (i != mAdapter.getCount() - 1 && !isRemoveDivider) {
                View divider = new View(getContext());
                divider.setTag(TAG);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, density);
                layoutParams.leftMargin = 20;
                addView(divider, layoutParams);
            }
        }
    }


    public void setAdapter(ListAdapter adapter, int leftWidth) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mDataObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mDataObserver);
        }
        mDividerLeftMargin = leftWidth;
        setupChildren();
    }

    public void setRemoveDivider(boolean isRemoveDivider) {
        this.isRemoveDivider = isRemoveDivider;
    }

    public void setHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }


}



