
package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 带展开按钮的tag型布局
 */
public class SearchHotView extends ViewGroup {

    private Context mContext;

    public SearchHotView(Context context) {
        this(context, null);
    }

    public SearchHotView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchHotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 父控件传进来的宽度和高度以及对应的测量模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果当前ViewGroup的宽高为wrap_content的情况
        int width = 0;// 自己测量的 宽度
        int height = 0;// 自己测量的高度
        // 记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        // 获取子view的个数
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // 测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            // 子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            // 子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // 换行时候
            if (lineWidth + childWidth > sizeWidth) {
                // 对比得到最大的宽度
                width = Math.max(width, lineWidth);
                // 重置lineWidth
                lineWidth = childWidth;
                // 记录行高
                height += lineHeight;
                lineHeight = childHeight;
            } else {// 不换行情况
                // 叠加行宽
                lineWidth += childWidth;
                // 得到最大行高
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // 处理最后一个子View的情况
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight - lp.bottomMargin;
            }
        }
        // wrap_content
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }

    // 存储所有子View
    private List<List<View>> mAllChildViews = new ArrayList<List<View>>();
    // 每一行的高度
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    // 记录当前行的view
    private List<View> lineViews = new ArrayList<View>();

    /**
     * 是否是被break出来的
     */
    private boolean isBreak = false;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        mAllChildViews.clear();
        mLineHeight.clear();
        lineViews.clear();
        // 获取当前ViewGroup的宽度
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (mAllChildViews.size() == linesCount - 1 && mIsNeedCheckLines) {//第二行开始,需要加上按钮计算
                // 如果需要换行
                if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin + 28 * 3 > width){
                    // 记录LineHeight
                    mLineHeight.add(lineHeight);
                    // 记录当前行的Views
                    if (lineWidth + lp.leftMargin + lp.rightMargin + 28 * 3 > width){
                        lineViews.remove(lineViews.size() - 1);//先remove最后一个，再添加到容器里
                    }
                    mAllChildViews.add(lineViews);
                    // 重置行的宽高
                    lineWidth = 0;
                    lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                    // 重置view的集合
                    lineViews = new ArrayList();
                    isBreak = true;
                    break;
                }
            } else {
                // 如果需要换行
                if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width) {
                    // 记录LineHeight
                    mLineHeight.add(lineHeight);
                    // 记录当前行的Views
                    mAllChildViews.add(lineViews);
                    // 重置行的宽高
                    lineWidth = 0;
                    lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                    // 重置view的集合
                    lineViews = new ArrayList();
                }
            }


            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);

            lineViews.add(child);
        }
        // 处理最后一行

        if (isBreak) {
            removeAllViews();

            int all = 0;
            for (int i = 0; i < mAllChildViews.size(); i++) {
                List<View> views = mAllChildViews.get(i);
                all += views.size();
            }

            if (mOnLineListener != null) {
                mOnLineListener.getChildView(all);
            }

            for (int i = 0; i < all; i++) {
                String s = mTextBaseList.get(i);
                textList.add(s);
            }

            textList.add("1");

//            mIsNeedCheckLines = false;
            isBreak = false;

            initHotWordShow(textList);
        }
        else{
            mLineHeight.add(lineHeight);
            mAllChildViews.add(lineViews);
        }

        // 设置子View的位置
        int left = 0;
        int top = 0;
        // 获取行数
        int lineCount = mAllChildViews.size();
        for (int i = 0; i < lineCount; i++) {
            // 当前行的views和高度
            lineViews = mAllChildViews.get(i);
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                // 判断是否显示
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int cLeft = left + lp.leftMargin;
                int cTop = top + lp.topMargin;
                int cRight = cLeft + child.getMeasuredWidth();
                int cBottom = cTop + child.getMeasuredHeight();
                // 进行子View进行布局
                child.layout(cLeft, cTop, cRight, cBottom);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }

    }

    //展示2行的list
    private ArrayList<String> textList = new ArrayList<>();
    //全部list
    private ArrayList<String> mTextBaseList = new ArrayList<>();

    public void setList(ArrayList<String> textBaseList) {
        mTextBaseList.clear();
        mTextBaseList.addAll(textBaseList);
        initHotWordShow(mTextBaseList);
    }

    /**
     * 是否展开状态
     */
    private boolean mExpand = false;

    private void initHotWordShow(ArrayList<String> list) {
        if (getChildCount() > 0) {
            removeAllViews();
        }

        MarginLayoutParams lp = new MarginLayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = 12 * 3;
        lp.topMargin = 16 * 3;

        for (int i = 0; i < list.size(); i++) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.activity_item, null);
            ImageView image = inflate.findViewById(R.id.image);
            TextView text = inflate.findViewById(R.id.text);

            image.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);

            text.setText(list.get(i));

            //最后一个设置为28 28按钮
            if (mIsNeedCheckLines && i == list.size() - 1) {
                image.setVisibility(View.VISIBLE);
                text.setVisibility(View.GONE);

                image.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mIsNeedCheckLines = false;
                        initHotWordShow(mTextBaseList);
                    }
                });
            }

            addView(inflate, lp);
        }
    }

    /**
     * 与当前ViewGroup对应的LayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 是否要判断第二行显示更多按钮
     */
    private boolean mIsNeedCheckLines;

    public void setIsNeedCheckLines(boolean isNeedCheckLines) {
        this.mIsNeedCheckLines = isNeedCheckLines;
    }

    private int linesCount;

    public void setCheckLinesCount(int linesCount) {
        this.linesCount = linesCount;
    }

    public OnLineListener mOnLineListener;

    public void setOnLineListener(OnLineListener onLineListener) {
        this.mOnLineListener = onLineListener;
    }

    public interface OnLineListener {
        /***
         * 2行时个数
         */
        void getChildView(int count);
    }

}
