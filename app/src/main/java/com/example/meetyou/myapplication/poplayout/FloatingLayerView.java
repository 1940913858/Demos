package com.example.meetyou.myapplication.poplayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class FloatingLayerView extends FrameLayout {

    private Context mContext;
    private LayoutInflater inflater;

    public FloatingLayerView(Context context) {
        this(context, null);
    }

    public FloatingLayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingLayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context) {
        this.mContext = context;
        this.inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 设置内容视图View
     *
     * @param layoutResID
     */
    public void setContentView(int layoutResID) {
        if (layoutResID < 0) {
            return;
        }
        View view = inflater.inflate(layoutResID, null);
        removeAllViews();
        addView(view);
    }
}