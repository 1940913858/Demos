package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.WheelView.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HabitTimeChooseView extends LinearLayout implements View.OnClickListener {

    private Context mContext;

    private TextView habit_choose_sure_tx;
    private TextView habit_choose_sevent_tx;
    private TextView habit_choose_year_tx;
    private TextView habit_choose_month_tx;
    private TextView habit_choose_all_tx;

    private LinearLayout habit_choose_month_ll;
    private WheelView habit_choose_year_wv;

    private Calendar calendarNow;

    private int mType = HABIT_TYPE_LAST_SEVEN_DAYS;//展示类型，对应1 2 3 4

    public static int HABIT_TYPE_LAST_SEVEN_DAYS = 1;//最近7天
    public static int HABIT_TYPE_MONTH = 2;//某年某月
    public static int HABIT_TYPE_YEAR = 3;//某年
    public static int HABIT_TYPE_ALL = 4;//全部

    public HabitTimeChooseView(Context context) {
        this(context, null);
    }

    public HabitTimeChooseView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;
        initAdapterValue();
        initView();
    }

    private List<TextView> chooseTextList = new ArrayList<>();
    public static String[] mYearStrs, mYearMonthsStrs, mMonthStrs;
    public static int[] mYears, mYearMonths, mMonths;

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_habit_time_choose, this);

        habit_choose_sure_tx = findViewById(R.id.habit_choose_sure_tx);
        habit_choose_sure_tx.setOnClickListener(this);

        habit_choose_sevent_tx = findViewById(R.id.habit_choose_sevent_tx);
        habit_choose_month_tx = findViewById(R.id.habit_choose_month_tx);
        habit_choose_year_tx = findViewById(R.id.habit_choose_year_tx);
        habit_choose_all_tx = findViewById(R.id.habit_choose_all_tx);
        setTitleText();
        //init list
        chooseTextList.clear();
        chooseTextList.add(habit_choose_sevent_tx);
        chooseTextList.add(habit_choose_month_tx);
        chooseTextList.add(habit_choose_year_tx);
        chooseTextList.add(habit_choose_all_tx);

        habit_choose_sevent_tx.setOnClickListener(this);
        habit_choose_month_tx.setOnClickListener(this);
        habit_choose_year_tx.setOnClickListener(this);
        habit_choose_all_tx.setOnClickListener(this);

        habit_choose_month_ll = findViewById(R.id.habit_choose_month_ll);
        habit_choose_year_wv = findViewById(R.id.habit_choose_year_wv);

        WheelView habit_choose_year_wv = findViewById(R.id.habit_choose_year_wv);
        habit_choose_year_wv.setVisibleItems(3);
        habit_choose_year_wv.setAdapter(mYearStrs);
        habit_choose_year_wv.setCyclic(false);
        habit_choose_year_wv.setCurrentItem(mYearStrs.length - 1);
        habit_choose_year_wv.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mYear = mYears[newValue];
            }
        });

        WheelView habit_choose_month_year_wv = findViewById(R.id.habit_choose_month_year_wv);
        habit_choose_month_year_wv.setVisibleItems(3);
        habit_choose_month_year_wv.setAdapter(mYearMonthsStrs);
        habit_choose_month_year_wv.setCyclic(false);
        habit_choose_month_year_wv.setCurrentItem(mYearMonthsStrs.length - 1);
        habit_choose_month_year_wv.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mYearMonth = mYearMonths[newValue];
            }
        });

        WheelView habit_choose_month_wv = findViewById(R.id.habit_choose_month_wv);
        habit_choose_month_wv.setVisibleItems(3);
        habit_choose_month_wv.setAdapter(mMonthStrs);
        habit_choose_month_wv.setCyclic(false);
        habit_choose_month_wv.setCurrentItem(calendarNow.get(Calendar.MONTH));
        habit_choose_month_wv.addChangingListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mMonth = mMonths[newValue];
            }
        });

        //default
        checkViewSelector();
    }

    private void setTitleText() {
        if (mMonth < 10) {
            habit_choose_month_tx.setText(mYearMonth + "-0" + mMonth);
        } else {
            habit_choose_month_tx.setText(mYearMonth + "-" + mMonth);
        }

        if (mYear == calendarNow.get(Calendar.YEAR)) {
            habit_choose_year_tx.setText("今年");
        } else {
            habit_choose_year_tx.setText(mYear + "年");
        }
    }

    private int mYear = 0, mYearMonth = 0, mMonth = 0;//年，月模式下的年份，月份

    private void initAdapterValue() {
        calendarNow = Calendar.getInstance();
        //init
        mYear = calendarNow.get(Calendar.YEAR);
        mYearMonth = calendarNow.get(Calendar.YEAR);
        mMonth = calendarNow.get(Calendar.MONTH) + 1;

        int yearNow = calendarNow.get(Calendar.YEAR);
        int yearRange = calendarNow.get(Calendar.YEAR) - 2017 + 1;//默认2017年开始到今年

        int[] years = new int[yearRange];
        String[] yearStrs = new String[yearRange];
        for (int i = yearRange - 1; i >= 0; i--) {
            years[i] = yearNow - (yearRange - 1 - i);
            yearStrs[i] = yearNow - (yearRange - 1 - i) + "年";
        }
        mYears = years;
        mYearStrs = yearStrs;
        mYearMonths = years;
        mYearMonthsStrs = yearStrs;

        int monthRange = 12;//月份12个月都可选
        int[] months = new int[monthRange];
        String[] monthsStrs = new String[monthRange];
        for (int i = 0; i < monthRange; i++) {
            months[i] = i + 1;
            monthsStrs[i] = (i + 1) + "月";
        }

        mMonths = months;
        mMonthStrs = monthsStrs;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //从新测量控件的大小，确保在没有指定控件大小的情况下正常显示
        int height = measureHeight(heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);

        setMeasuredDimension(width, height);

    }

    private int measureHeight(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result += getPaddingTop() + getPaddingBottom();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }

    private int measureWidth(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                // result = mTextBound.width();
                result += getPaddingLeft() + getPaddingRight();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.habit_choose_sevent_tx) {
            mType = HABIT_TYPE_LAST_SEVEN_DAYS;
        } else if (id == R.id.habit_choose_year_tx) {
            mType = HABIT_TYPE_YEAR;
        } else if (id == R.id.habit_choose_month_tx) {
            mType = HABIT_TYPE_MONTH;
        } else if (id == R.id.habit_choose_all_tx) {
            mType = HABIT_TYPE_ALL;
        } else if (id == R.id.habit_choose_sure_tx) {//确定按钮
            onResultListener.onOk(mType, mYear, mYearMonth, mMonth);
        }

        checkViewSelector();


    }

    private void checkViewSelector() {
        for (int i = 0; i < chooseTextList.size(); i++) {
            TextView textView = chooseTextList.get(i);
            if (mType == i + 1) {//type 从1开始
                textView.setSelected(true);
            } else {
                textView.setSelected(false);
            }

        }

        showWheelView();
    }

    /**
     * 根据type类型判断wheelview滚轮展示
     */
    private void showWheelView() {
        if (mType == HABIT_TYPE_YEAR) {
            habit_choose_year_wv.setVisibility(VISIBLE);
            habit_choose_month_ll.setVisibility(GONE);
        } else if (mType == HABIT_TYPE_MONTH) {
            habit_choose_year_wv.setVisibility(GONE);
            habit_choose_month_ll.setVisibility(VISIBLE);
        } else {
            habit_choose_year_wv.setVisibility(GONE);
            habit_choose_month_ll.setVisibility(GONE);
        }
    }


    public void setListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    private OnResultListener onResultListener;

    public interface OnResultListener {
        void onOk(int type, int yearMonth, int month, int year);

        void onCancel();
    }
}
