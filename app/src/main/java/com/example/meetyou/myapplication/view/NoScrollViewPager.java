package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by chensenfa
 */
public class NoScrollViewPager extends ViewPager {

    private boolean noScroll = true;
    private int touchSlop;
    private boolean smoothScroll = true;//是否要滑动动画

    public NoScrollViewPager(Context context) {
        super(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    float mLastX;
//    float mLastY;
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mLastX = x;
//                mLastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("senfa", "ACTION_MOVE: ");
//                getParent().requestDisallowInterceptTouchEvent(false);
//                break;
//            case MotionEvent.ACTION_UP:
//                int dx = (int) Math.abs(x - mLastX);
//                int dy = (int) Math.abs(y - mLastY);
//                if (dx <= touchSlop && dy <= touchSlop) {
//                    Log.e("senfa", "点击: ");
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    //去除页面切换时的滑动翻页效果
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) //采用最大的view的高度。
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, smoothScroll);
    }

    public void setSmoothScroll(boolean smoothScroll) {
        this.smoothScroll = smoothScroll;
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }
}
