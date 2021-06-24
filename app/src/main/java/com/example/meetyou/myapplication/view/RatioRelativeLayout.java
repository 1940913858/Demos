package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.meetyou.myapplication.R;

/**
 * 支持按宽高比显示的相对布局
 * 在xml中，如设置了view_ratio不为0则高度必须是wrap_content，设置其他值都无效
 * 如果高度想自定义，就不能设置view_ratio或者设置为0
 * view_ratio是float类型，值是宽除以高
 * Created by LinXin on 2017/12/6.
 */
public class RatioRelativeLayout extends RelativeLayout {

    private float ratio;//控件的宽高比

    public RatioRelativeLayout(Context context) {
        this(context, null);
    }

    public RatioRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null)
            return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioRelativeLayout);
        ratio = a.getFloat(R.styleable.RatioRelativeLayout_view_ratio, 0);//0则不用宽高比显示
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (ratio > 0) {
            float width = MeasureSpec.getSize(widthMeasureSpec);
            float height = width / ratio;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
        requestLayout();
    }
}
