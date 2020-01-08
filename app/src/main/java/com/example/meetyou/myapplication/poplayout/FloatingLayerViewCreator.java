package com.example.meetyou.myapplication.poplayout;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class FloatingLayerViewCreator {

    private Activity mActivity;
    private FrameLayout mParentView;
    private FloatingLayerView mFloatView;
    private OnFloatStateListener onFloatStateListener;

    /**
     * 创建实例
     * <p>Create instances</p>
     *
     * @param activity
     * @return
     */
    public static FloatingLayerViewCreator create(Activity activity) {
        return new FloatingLayerViewCreator(activity);
    }

    /**
     * 默认的构造函数（Constructor）
     *
     * @param activity
     */
    private FloatingLayerViewCreator(Activity activity) {
        if (activity == null || isHasFloatView()) {
            return;
        }
        mActivity = activity;
        mParentView = (FrameLayout) mActivity.getWindow().getDecorView();
        if (mFloatView == null) {
            mFloatView = new FloatingLayerView(mActivity);
        }

        //设置触摸监听
        mFloatView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                close();
                return true;
            }
        });

    }

    /**
     * 设置浮层显示内容
     *
     * @param layoutId
     * @return
     */
    public FloatingLayerViewCreator setContentView(int layoutId) {
        if (mFloatView != null) {
            //int paddingTop = DisplayUnitManager.getStatusBarHeight(mActivity);
            mFloatView.setPadding(0, 120, 0, 0);
            mFloatView.setContentView(layoutId);
        }
        return this;
    }

    /**
     * 显示浮层
     */
    public void show() {
        if (isHasFloatView()) {
            return;
        }
        if (mParentView != null) {
            mParentView.addView(mFloatView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (onFloatStateListener != null) {
            onFloatStateListener.onShow();
        }
    }

    /***
     * 是否含有FloatView
     * @return
     */
    private boolean isHasFloatView() {
        if (mParentView == null) {
            return false;
        }
        for (int i = 0; i < mParentView.getChildCount(); i++) {
            View view = mParentView.getChildAt(i);
            if (view instanceof FloatingLayerView) {
                return true;
            }
        }
        return false;
    }

    /**
     * 关闭浮层
     */
    public void close() {

        if (mFloatView != null && mParentView != null) {
            mParentView.removeView(mFloatView);
            if (onFloatStateListener != null) {
                onFloatStateListener.onClose();
            }
        }
    }

    /**
     * 设置浮层显示监听
     *
     * @param onFloatStateListener
     */
    public FloatingLayerViewCreator setOnFloatStateListener(OnFloatStateListener onFloatStateListener) {
        this.onFloatStateListener = onFloatStateListener;
        return this;
    }

    /**
     * 浮层显示监听
     */
    public interface OnFloatStateListener {
        /***
         * 浮层显示
         */
        void onShow();

        /***
         * 浮层关闭
         */
        void onClose();
    }
}