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

public class FixedBehavior extends AppBarLayout.Behavior {
    private OverScroller mOverScroller;

    public FixedBehavior() {
        super();
    }

    public FixedBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams params) {
        super.onAttachedToLayoutParams(params);
    }

    @Override
    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            reflectOverScroller();
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
    private void reflectOverScroller() {
        if (mOverScroller == null) {
            Field field = null;
            try {
                field = getClass().getSuperclass()
                        .getSuperclass().getDeclaredField("mScroller");
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

//    @Override
//    public void setDragCallback(@Nullable BaseDragCallback callback) {
//        super.setDragCallback(new BaseDragCallback() {
//            @Override
//            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
//                return true;
//            }
//        });
//    }
}