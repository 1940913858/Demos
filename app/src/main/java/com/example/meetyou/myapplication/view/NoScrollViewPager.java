package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by chensenfa
 */
public class NoScrollViewPager extends ViewPager {

    private boolean noScroll = true;
    private boolean smoothScroll = true;//是否要滑动动画

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float lastRawY;
    private float lastRawX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !noScroll && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastRawY = event.getRawY();
//                lastRawX = event.getRawX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float deltaX = event.getRawX() - lastRawX;
//                float deltaY = event.getRawY() - lastRawY;
//                lastRawY = event.getRawY();
//                lastRawX = event.getRawX();
//// || Math.abs(deltaX) < Math.abs(deltaY)
//                Log.e("senfa", "onInterceptTouchEvent: "+(Math.abs(deltaX) > Math.abs(deltaY)) );
//                if (Math.abs(deltaX) > Math.abs(deltaY)) {//左右滑
//                    return true;
//                } else {//上下滑
//                    return super.onInterceptTouchEvent(event);
//                }
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                lastRawY = 0;
//                lastRawX = 0;
//                break;
//        }
//        return false;

        return true;
    }

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

    public void setNoScroll(boolean noScroll){
        this.noScroll = noScroll;
    }
}
