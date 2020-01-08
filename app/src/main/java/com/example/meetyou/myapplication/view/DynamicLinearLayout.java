package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class DynamicLinearLayout extends LinearLayout {

    private Context mContext;

    public DynamicLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private GradientDrawable drawable;
    private GradientDrawable checkDrawable;

    private void init() {
        setOrientation(HORIZONTAL);

        drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setStroke(3, Color.parseColor("#FF5658"));
        drawable.setSize(24, 24);
        drawable.setColor(Color.parseColor("#FF5658"));

        checkDrawable = new GradientDrawable();
        checkDrawable.setShape(GradientDrawable.OVAL);
        checkDrawable.setStroke(3, Color.parseColor("#FF5658"));
        checkDrawable.setSize(24, 24);

        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(15,0,0,0);

    }

    private LinearLayout.LayoutParams lp;

    public DynamicLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicLinearLayout(Context context) {
        this(context, null);
    }

    public void setTotalNum(int totalNum, int checkNum) {
        removeAllViews();
        for (int i = 0; i < totalNum; i++) {
            ImageView imageView = new ImageView(mContext);
            if (i < checkNum) {
                imageView.setBackgroundDrawable(drawable);
            } else {
                imageView.setBackgroundDrawable(checkDrawable);
            }
            imageView.setLayoutParams(lp);
            addView(imageView, i);
        }

    }
}
