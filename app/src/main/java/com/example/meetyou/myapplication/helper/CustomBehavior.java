package com.example.meetyou.myapplication.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.OverScroller;

import java.lang.reflect.Field;

/**
 * 解决CoordinatorLayout滑动抖动问题
 */
public class CustomBehavior extends AppBarLayout.Behavior {

    private OverScroller mOverScroller;

    public CustomBehavior() {
    }

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        if (mOverScroller == null) {
            super.onTouchEvent(parent, child, ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            try {
                reflectOverScroller();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return super.onTouchEvent(parent, child, ev);
    }

    /**
     *
     */
    public void stopFling() {
        if (mOverScroller != null) {
            mOverScroller.abortAnimation();
        }


    }

    /**
     * 解决AppbarLayout在fling的时候，再主动滑动RecyclerView导致的动画错误的问题
     */
    private void reflectOverScroller() throws NoSuchFieldException, IllegalAccessException {
        if (mOverScroller == null) {
            Field field = null;
            try {
                field = getClass().getSuperclass().getSuperclass().getDeclaredField("mScroller");
                field.setAccessible(true);
                Object object = field.get(this);
                mOverScroller = (OverScroller) object;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            try {
                field = getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("scroller");
                field.setAccessible(true);
                Object object = field.get(this);
                mOverScroller = (OverScroller) object;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void setDragCallback(@Nullable BaseDragCallback callback) {
        super.setDragCallback(new DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return true;
            }
        });
    }
}
