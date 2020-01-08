package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;


/**
 * ClassName: AnalysisView <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:2019-6-25
 *
 * @author chensenfa@xiaoyouzi.com
 * @version 1.1.2
 */
public class AnalysisView extends TextView {

    private int mNumber = 0;
    private Context mContext;
    private int width;

    public AnalysisView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public AnalysisView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnalysisView(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredHeight = (int) 60;
        int screenWidth = 1080;
        //最大180，按180份平分
        int itemWidth = (screenWidth - getPaddingLeft() - getPaddingRight()) / 180;
        //根据数值计算长度
        width = itemWidth * mNumber;

        setWidth(width);
        //MUST CALL THIS
        setMeasuredDimension(width, desiredHeight);
    }

    @Override
    protected void onDraw(Canvas pCanvas) {
        super.onDraw(pCanvas);
//        setWidth(width);
        setText(mNumber + "");
        setBackgroundResource(R.drawable.markview_icon_buble);
        setGravity(Gravity.RIGHT);
    }

    public void setNumber(int pValue) {
        this.mNumber = pValue;
        postInvalidate();
    }

}
