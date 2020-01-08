package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;

/**
 * ClassName: CategoryTabStrip <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:2017-3-2
 *
 * @author senfa.chen
 * @version 0.1
 * @since MT 3.5
 */
public class CategoryTabStrip extends HorizontalScrollView {
    private LayoutInflater mLayoutInflater;
    private final PageListener pageListener = new PageListener();
    private ViewPager pager;
    private LinearLayout tabsContainer;
    private int tabCount;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private Rect indicatorRect;

    private LinearLayout.LayoutParams defaultTabLayoutParams;

    private int scrollOffset = 10;

    private Drawable indicator;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private Scroller mScroller;

    //if first into, scroll fast the tab to the specified location
    private boolean isFirstInto = true;

    public static final int HORIZATOL_SCROLLVIEW_SPEED = 1000;

    public CategoryTabStrip(Context context) {
        this(context, null);
    }

    public CategoryTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mLayoutInflater = LayoutInflater.from(context);
        indicatorRect = new Rect();
        setFillViewport(true);
        setWillNotDraw(false);
        // 标签容器
        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        scrollOffset = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

        // 绘制高亮区域作为滑动分页指示器
        indicator = getResources()
                .getDrawable(R.drawable.bg_category_indicator);

        mIndicatorWidth = getResources().getDimensionPixelSize(
                R.dimen.classify_indicator_width);
        mIndicatorHeight = getResources().getDimensionPixelSize(
                R.dimen.classify_indicator_height);

        mScroller = new Scroller(context);
    }

    // 绑定与CategoryTabStrip控件对应的ViewPager控件，实现联动
    public void setViewPager(ViewPager pager) {
        this.pager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException(
                    "ViewPager does not have adapter instance.");
        }
        pager.setOnPageChangeListener(pageListener);
        notifyDataSetChanged();
        pageListener.onPageSelected(0);
    }

    // 当附加在ViewPager适配器上的数据发生变化时,应该调用该方法通知CategoryTabStrip刷新数据
    public void notifyDataSetChanged() {
        tabsContainer.removeAllViews();
        tabCount = pager.getAdapter().getCount();
        for (int i = 0; i < tabCount; i++) {
            addTab(i, pager.getAdapter().getPageTitle(i).toString());
        }

    }

    // 添加一个标签到导航菜单
    private void addTab(final int position, String title) {
        ViewGroup tab = (ViewGroup) mLayoutInflater.inflate(
                R.layout.category_tab, this, false);
        TextView category_text = (TextView) tab
                .findViewById(R.id.category_text);
        category_text.setText(title);
        category_text.setGravity(Gravity.CENTER);
        category_text.setSingleLine();
        category_text.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(position);
            }
        });

        tabsContainer.addView(tab, position, defaultTabLayoutParams);
    }

    // 计算滑动过程中矩形高亮区域的上下左右位置
    private void calculateIndicatorRect(Rect rect) {
        ViewGroup currentTab = (ViewGroup) tabsContainer
                .getChildAt(currentPosition);
        TextView category_text = (TextView) currentTab
                .findViewById(R.id.category_text);

        float left = (float) (currentTab.getLeft() + category_text.getLeft());
        float width = ((float) category_text.getWidth()) + left;

        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
            ViewGroup nextTab = (ViewGroup) tabsContainer
                    .getChildAt(currentPosition + 1);
            TextView next_category_text = (TextView) nextTab
                    .findViewById(R.id.category_text);

            float next_left = (float) (nextTab.getLeft() + next_category_text
                    .getLeft());
            left = left * (1.0f - currentPositionOffset) + next_left
                    * currentPositionOffset;
            width = width * (1.0f - currentPositionOffset)
                    + currentPositionOffset
                    * (((float) next_category_text.getWidth()) + next_left);
        }

        int leftStart = ((((int) left) + getPaddingLeft() * 2 + (int) width) / 2);
        int topStart = getPaddingTop() + currentTab.getTop()
                + category_text.getTop() + category_text.getHeight();

        rect.set(leftStart - mIndicatorWidth / 2, topStart - mIndicatorHeight,
                leftStart + mIndicatorWidth / 2, topStart);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // 绘制高亮背景矩形红框
        calculateIndicatorRect(indicatorRect);

        // draw indicator
        if (indicator != null) {
            indicator.setBounds(indicatorRect);
            indicator.draw(canvas);
        }
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;
            invalidate();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                isFirstInto = false;
                scrollTab();
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (isFirstInto) {
                scrollTabFast();
            }
            // 判断是否选中
            for (int j = 0; j < tabsContainer.getChildCount(); j++) {
                View checkView = tabsContainer.getChildAt(j);
                boolean ischeck;
                if (j == position) {
                    ischeck = true;
                } else {
                    ischeck = false;
                }
                checkView.setSelected(ischeck);
            }
        }
    }

    private void scrollTabFast() {
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View checkView = tabsContainer.getChildAt(pager.getCurrentItem());
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - 1080 / 2;
            smoothScrollTo(i2, 0);
        }
    }

    private void scrollTab() {
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View checkView = tabsContainer.getChildAt(pager.getCurrentItem());
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - 1080 / 2;
            smoothScrollToSlow(i2, 0, HORIZATOL_SCROLLVIEW_SPEED);
        }
    }

    public void smoothScrollToSlow(int fx, int fy, int duration) {
        int dx = fx - getScrollX();//mScroller.getFinalX();
        int dy = fy - getScrollY();  //mScroller.getFinalY();
        smoothScrollBySlow(dx, dy, duration);
    }

    public void smoothScrollBySlow(int dx, int dy, int duration) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }


}
