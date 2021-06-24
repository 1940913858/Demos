package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.meetyou.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 文本自动垂直轮播
 * <p/>
 * Created by senfa.chen
 */
public class AutoScrollTextView extends TextSwitcher implements
        ViewSwitcher.ViewFactory {

    private static final int FLAG_START_AUTO_SCROLL = 1001;
    private static final int FLAG_STOP_AUTO_SCROLL = 1002;

    /**
     * 轮播时间间隔
     */
    private int scrollDuration = 8000;
    /**
     * 动画时间
     */
    //Modify begin by meitu.zenglx
    /*
    private int animDuration = 1000;
    */
    //=======div=======
    private int animDuration = 500;
    //Modify end


    /**
     * 文字大小
     */
    private float mTextSize = 24;
    /**
     * 文字Padding
     */
    private int mPadding = 20;
    /**
     * 文字颜色
     */
    private int textColor = Color.WHITE;

    private OnItemClickListener itemClickListener;
    private Context mContext;
    /**
     * 当前显示Item的ID
     */
    private int currentId = 0;
    private ArrayList<String> textList = new ArrayList<String>();
    private Handler handler;

    public AutoScrollTextView(Context context) {
        this(context, null);
        mContext = context;
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoScrollTextView);
        mTextSize = a.getDimensionPixelSize(R.styleable.AutoScrollTextView_textSize, 36);
        mPadding = (int) a.getDimension(R.styleable.AutoScrollTextView_padding, 0);
        scrollDuration = a.getInteger(R.styleable.AutoScrollTextView_scrollDuration, 4000);
        animDuration = a.getInteger(R.styleable.AutoScrollTextView_animDuration, 500);
        textColor = a.getColor(R.styleable.AutoScrollTextView_textColor, Color.WHITE);
        a.recycle();
        init();
    }

    private void init() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Calendar c = Calendar.getInstance();
                int second = c.get(Calendar.SECOND);
                switch (msg.what) {
                    case FLAG_START_AUTO_SCROLL:
                        if (textList.size() > 1) {
                            currentId++;
                            setText(textList.get(currentId % textList.size()));
                        }
                        handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, scrollDuration);
                        break;
                    case FLAG_STOP_AUTO_SCROLL:
                        handler.removeMessages(FLAG_START_AUTO_SCROLL);
                        break;
                }
            }
        };

        setFactory(this);

//        Animation in = new TranslateAnimation(0, 0, 100, 0);
//        in.setDuration(animDuration);
//        in.setInterpolator(new AccelerateInterpolator());
//        Animation out = new TranslateAnimation(0, 0, 0, -100);
//        out.setDuration(animDuration - 200);
//        out.setInterpolator(new AccelerateInterpolator());
//        setInAnimation(in);
//        setOutAnimation(out);

    }

    private void setAnimation(){
        if (getInAnimation() != null || getOutAnimation() != null){
            return;
        }
        Animation in = new TranslateAnimation(0, 0, 100, 0);
        in.setDuration(animDuration);
        in.setInterpolator(new AccelerateInterpolator());
        Animation out = new TranslateAnimation(0, 0, 0, -100);
        out.setDuration(animDuration);
        out.setInterpolator(new AccelerateInterpolator());
        setInAnimation(in);
        setOutAnimation(out);
    }

    /**
     * 设置数据源
     *
     * @param titles
     */
    public void setTextList(List<String> titles) {
        textList.clear();
        textList.addAll(titles);
        currentId = 0;
        if (textList.size() > 0){//优先settext，否则第一次会滚动展示
            setText(textList.get(0));
        }
    }

    public String getKeyWord(int position) {
        if (textList == null || textList.size() <= 0) {
            return null;
        }
        return textList.get(position);
    }

    /**
     * 开始轮播
     */
    //Modify begin by meitu.zenglx
    /*
    public void startAutoScroll(boolean isFirst) {
        handler.removeMessages(FLAG_START_AUTO_SCROLL);
        handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
    }
     */
    //=======div=======
    public void startAutoScroll(boolean isFirst) {
        handler.removeMessages(FLAG_START_AUTO_SCROLL);
        if (isFirst) {
            handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
        } else {
            int middleScrollDuration = 2000;
            handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, middleScrollDuration);
        }

        setAnimation();
    }
    //Modify end
    /**
     * 停止轮播
     */
    public void stopAutoScroll() {
        handler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
    }

    @Override
    public TextView makeView() {
        TextView t = new TextView(mContext);
        t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        t.setMaxLines(1);
        t.setPadding(mPadding, mPadding, mPadding, mPadding);
        t.setTextColor(textColor);
        t.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) mTextSize);

        //the textview gravity center
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER | Gravity.LEFT;
        t.setLayoutParams(lp);

//        t.setClickable(true);
//        t.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (itemClickListener != null) {
//                    if (textList.size() > 0 && currentId != -1) {
//                        itemClickListener.onItemClick(currentId % textList.size());
//                    } else {
//                        itemClickListener.onItemClick(-1);
//                    }
//                }
//            }
//        });

        return t;
    }

    /**
     * 设置点击事件监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 轮播文本点击监听器
     */
    public interface OnItemClickListener {

        /**
         * 点击回调
         *
         * @param position 当前点击ID
         */
        public void onItemClick(int position);

    }

    //Add begin by meitu.zenglx
    public int getCurrentPosition() {
        if (textList != null && textList.size() > 0) {
            return currentId % textList.size();
        }
        return -1;
    }
    //Add end

    public void setTextColor(){
        textColor = Color.BLACK;

//        currentId = 0;
//        removeAllViews();
//        setFactory(this);

    }

}