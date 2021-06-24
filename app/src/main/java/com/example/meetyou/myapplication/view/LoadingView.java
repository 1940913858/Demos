package com.example.meetyou.myapplication.view;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;

import java.util.HashMap;

/**
 * 加载视图 有三种状态，加载中，无数据，无网络 Created by Administrator on 13-8-7.
 */
public class LoadingView extends LinearLayout {
    // 加载中
    public static final int STATUS_LOADING = 111101;
    // 无数据
    public static final int STATUS_NODATA = 20200001;
    // 小提示
    public static final int STATUS_TIP = 40400001;
    // 无网络
    public static final int STATUS_NONETWORK = 30300001;
    //重试
    public static final int STATUS_RETRY = 50500001;
    //底部有个按钮
    public static final int TYPE_BUTTON_HINT = 7070001;
    //增加无数据时，真正可以显示底部按钮的结束状态标识
    public static final int STATUS_NODATA_WITH_BUTTON = 80400011;
    public static final int STATUS_HIDDEN = 0;
    private static final int DEFAULT_BG_COLOR = -1;

    private ImageView mImageView;
    private TextView tvResultContent;
    private TextView mTextView;
    private Button mButton;
    public TextView noNetButton;


    protected int bgColor = DEFAULT_BG_COLOR;
    protected Integer[] imageResIds = new Integer[16];//预先设置大小
    protected StringRes[] resultContentIds = new StringRes[16];
    protected Integer[] loadTextIds = new Integer[16];
    private OnClickListener listener;
    private Context context;
    private int status;
    //
    private boolean isStatusChange;

    public TextView getTextView() {
        return mTextView;
    }

    public Button getButton() {
        return mButton;
    }

    public TextView getResultTextView() {
        return tvResultContent;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    private LoadStatusListener mLoadStatusListener = null;

    public interface LoadStatusListener {
        public void performHide();

        public void performShow();
    }

    public void setLoadStatusListener(LoadStatusListener mLoadStatusListener) {
        this.mLoadStatusListener = mLoadStatusListener;
    }

    private static float  mDensity;
    /**
     * dip到px的转换
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        if(mDensity==0)
            mDensity = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * mDensity + 0.5f);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        //小柚子和下面的文字屏幕居中，所以增加了 RelativeLayout
        RelativeLayout relativeLayout = new RatioRelativeLayout(context);
        relativeLayout.setId(R.id.loading_view_relativeLayout);
        addView(relativeLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //要居中的view，防止外部获取imageview 等view的LayoutParams 导致异常，因此在嵌套一层LinearLayout
        LinearLayout centerLinearLayout = new LinearLayout(context);
        centerLinearLayout.setId(R.id.loading_view_centerLinearLayout);
        centerLinearLayout.setOrientation(LinearLayout.VERTICAL);
        centerLinearLayout.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams centerLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        centerLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        relativeLayout.addView(centerLinearLayout, centerLayoutParams);
        // 加入小柚图标
        mImageView = new ImageView(context);
        mImageView.setVisibility(View.GONE);
        //mImageView.setBackgroundResource(R.drawable.loading_frame);
        centerLinearLayout.addView(mImageView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        //
        tvResultContent = new TextView(context);
        tvResultContent.setTextColor(Color.parseColor("#b5b5b5"));
        tvResultContent.setPadding(10, 10, 10, 10);
        tvResultContent.setTextSize(14);
        tvResultContent.setGravity(Gravity.CENTER);
        LayoutParams param0 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        param0.topMargin = dip2px(getContext(), 12);
        param0.gravity = Gravity.CENTER;
        centerLinearLayout.addView(tvResultContent, param0);
        //

        // 加入文字
        mTextView = new TextView(context);
        mTextView.setText(getResources().getString(R.string.loading));
        mTextView.setTextColor(Color.parseColor("#b5b5b5"));
        mTextView.setPadding(10, 10, 10, 10);
        mTextView.setTextSize(14);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setVisibility(View.INVISIBLE);
        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        param.topMargin = dip2px(getContext(), 12);
        centerLinearLayout.addView(mTextView, param);

        //防止外部获取imageview 等view的LayoutParams 导致异常，因此在嵌套一层LinearLayout
        LinearLayout buttonLinearLayout = new LinearLayout(context);
        buttonLinearLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, dip2px(context, 70));
        buttonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonLayoutParams.addRule(RelativeLayout.BELOW, R.id.loading_view_centerLinearLayout);
        relativeLayout.addView(buttonLinearLayout, buttonLayoutParams);
        //
        mButton = new Button(context);
        mButton.setVisibility(View.GONE);
        mButton.setText("去逛逛~");

        mButton.setTextSize(15);
        mButton.setTextColor(Color.parseColor("#ffffff"));
        int width = dip2px(getContext(),92);
        int height = dip2px(getContext(),32);
        int padding = dip2px(context, 16);
        mButton.setMinWidth(width);
        mButton.setPadding(padding,0,padding,0);
        mButton.setGravity(Gravity.CENTER);
        mButton.setStateListAnimator(null);//去除阴影

        LayoutParams param_button = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        param_button.topMargin = dip2px(context, 20);
        buttonLinearLayout.addView(mButton, param_button);

        //无网络的按钮
        noNetButton = new TextView(context);
        noNetButton.setVisibility(GONE);
        //noNetButton.setBackgroundResource(R.drawable.selector_no_net_btn_bg);
        noNetButton.setTextColor(Color.parseColor("#7f0601d3"));
        noNetButton.setTextSize(14);
        noNetButton.setGravity(Gravity.CENTER);
        noNetButton.setText("重新加载");
        noNetButton.setSingleLine(true);
        noNetButton.setPadding(dip2px(context, 10), 0, dip2px(context, 10), 0);
        LayoutParams noNetBtnLp = new LayoutParams(LayoutParams.WRAP_CONTENT,dip2px(context, 30));
        noNetBtnLp.topMargin = dip2px(context, 15);
        buttonLinearLayout.addView(noNetButton, noNetBtnLp);
        //
        initAttrs(attrs);
        initNormalStatus();
        setListener();
    }

    private void initAttrs(AttributeSet attrs) {
//        TypedArray a = context.getTheme().obtainStyledAttributes(
//                attrs,
//                R.styleable.LoadingView,
//                0, 0);
//        try {
//            bgColor = a.getInteger(R.styleable.LoadingView_bgColor, bgColor);
//        } finally {
//            a.recycle();
//        }
        bgColor = Color.parseColor("#ffffff");
    }


    private void initJingqiApp(int widthMeasureSpec, int heightMeasureSpec) {
        //是经期项目就使用专属的背景颜色
            if (bgColor == DEFAULT_BG_COLOR) {
                setBackgroundColor(Color.parseColor("#ffffff"));
            } else {
                setBackgroundColor(bgColor);
            }
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                setLayoutParams(layoutParams);
            }
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        initJingqiApp(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void setListener() {
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (listener != null) {
                    listener.onClick(v);
                    pressStatisticsExposure();
                }*/
            }
        });

        noNetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    isStatusChange = false;
                    listener.onClick(v);
                    pressStatisticsExposure();
                    if (!isStatusChange) {
                        setStatus(STATUS_LOADING);
                    }
                }
            }
        });
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * 曝光统计
     */
    private void statisticsExposure(int result) {
        if (result == 2 || result == 3) {
            HashMap<String, String> map = new HashMap<>();
            if (result == 2) {
                map.put("标签", "有网络");
                map.put("容器", context.getClass().getSimpleName());
            } else {
                map.put("标签", "无网络");
                map.put("容器", context.getClass().getSimpleName());
            }
        }
    }

    /**
     * 点击统计
     */
    private void pressStatisticsExposure() {
        int result = status % 100000000 / 10000000;
        if (result == 2 || result == 3) {
            HashMap<String, String> map = new HashMap<>();
            if (result == 2) {
                map.put("标签", "有网络");
            } else {
                map.put("标签", "无网络");
            }
        }
    }

    private void initNormalStatus() {

        for (int i = 0; i < loadTextIds.length; ++i) {
            loadTextIds[i] = 0;
        }
        for (int i = 0; i < resultContentIds.length; ++i) {
            resultContentIds[i] = new StringRes(StringRes.TYPE_RES, null, 0);
        }
        for (int i = 0; i < imageResIds.length; ++i) {
            imageResIds[i] = 0;
        }

        loadTextIds[1] = R.string.loading;

        resultContentIds[2] = new StringRes(StringRes.TYPE_RES, null, R.string.no_record);// 与 imageResIds[2]对应
        resultContentIds[3] = new StringRes(StringRes.TYPE_RES, null, R.string.no_internet_for_loading);
        resultContentIds[5] = new StringRes(StringRes.TYPE_RES, null, R.string.failed_to_retry);


        imageResIds[1] = R.drawable.loading_frame;
        imageResIds[2] = R.drawable.apk_girlone;     //产品需求
        imageResIds[3] = R.drawable.apk_girlone;
        imageResIds[4] = R.drawable.apk_girltwo;
        imageResIds[5] = R.drawable.apk_girlone;
    }


    public int getStatus() {
        return status;
    }

    @Deprecated
    public int setContent(Activity activity, int originStatus, String value) {
        return setContent(originStatus, value);
    }

    @Deprecated
    public int setContent(int originStatus, String value) {
        int i = 3;
        for (; i < resultContentIds.length; ++i) {
            if (resultContentIds[i].getrValue() == 0
                    &&
                    (TextUtils.isEmpty(resultContentIds[i].getValue())
                            || TextUtils.equals(resultContentIds[i].getValue(), value))) {
                break;
            }
        }
        if (i < resultContentIds.length) {
            resultContentIds[i].setValue(value);
        }
        int newStatus = i * 10000000 + originStatus % 10000000;
        setStatus(newStatus);
        return newStatus;
    }

    public int setContent(int originStatus, int stringId) {
        int i = 3;
        for (; i < resultContentIds.length; ++i) {
            if (TextUtils.isEmpty(resultContentIds[i].getValue())
                    &&
                    (resultContentIds[i].getrValue() == stringId
                            || resultContentIds[i].getrValue() == 0)) {
                break;
            }

        }
        if (i < resultContentIds.length) {
            resultContentIds[i].setrValue(stringId);
        }
        int newStatus = i * 10000000 + originStatus % 10000000;
        setStatus(newStatus);
        return newStatus;
    }

    public void setStatus(int status) {//20200000
        setStatus(status,null);
    }

    public void setStatus(int status,String content) {
        isStatusChange = true;
        this.status = status;
        int result = status % 100000000 / 10000000;
        int imag = status % 1000000 / 100000;
        final int animation = status % 10000 / 1000;
        int text = status % 1000 / 100;   //
        int button = status % 100 / 10;   //
        int visible = status % 10;      //0 or 1
        statisticsExposure(result);
        if (visible == 0) {
            setVisibility(GONE);
        } else {
            setAlpha(1);
            setVisibility(VISIBLE);
            if (mLoadStatusListener != null) {
                mLoadStatusListener.performShow();
            }
        }
        if (imag > imageResIds.length) {
            imag = 1;
        }
        if (imag == 0) {
            mImageView.setVisibility(GONE);
        } else {
            mImageView.setVisibility(VISIBLE);
            if(status==LoadingView.STATUS_NODATA || status==LoadingView.STATUS_NODATA_WITH_BUTTON){
                mImageView.setBackgroundResource(R.drawable.apk_girltwo);
            }else{
                mImageView.setBackgroundResource(imageResIds[imag]);
            }

        }

        if (mImageView.getBackground() instanceof AnimationDrawable) {
            AnimationDrawable frameAnimation = (AnimationDrawable) mImageView.getBackground();
            if (animation == 0) {
                frameAnimation.stop();
            } else {
                frameAnimation.start();
            }
        }

        if (result > resultContentIds.length) {
            result = 1;
        }
        if (result == 0) {
            tvResultContent.setVisibility(GONE);
        } else {
            tvResultContent.setVisibility(VISIBLE);
            if(!TextUtils.isEmpty(content)){
                tvResultContent.setText(content);
            }else{
                tvResultContent.setText(resultContentIds[result].getStringValue(getContext()));
            }
        }

        if (text > loadTextIds.length) {
            text = 1;
        }
        if (text == 0) {
            mTextView.setVisibility(GONE);
        } else {
            if (tvResultContent.getVisibility() != View.VISIBLE) {
                mTextView.setVisibility(VISIBLE);
                mTextView.setText(loadTextIds[text]);
            }
        }

        if (button == 0) {
            mButton.setVisibility(GONE);
        } else {
            showButton();
        }

        if (status == STATUS_NONETWORK || status == STATUS_RETRY) {
            noNetButton.setVisibility(VISIBLE);
            noNetButton.setBackgroundResource(R.drawable.selector_no_net_btn_bg);
            mButton.setVisibility(GONE);
        } else {
            noNetButton.setVisibility(GONE);
        }

    }


    public static class StringRes {
        public static final int TYPE_RES = 0;
        public static final int TYPE_STR = 1;
        private int type;
        private int rValue;
        private String value;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getrValue() {
            return rValue;
        }

        public void setrValue(int rValue) {
            this.rValue = rValue;
            this.type = TYPE_RES;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
            this.type = TYPE_STR;
        }

        public StringRes(int type, String value, int rValue) {
            this.type = type;
            this.value = value;
            this.rValue = rValue;
        }

        public String getStringValue(Context context) {
            try {
                switch (type) {
                    case TYPE_RES:
                        return context.getResources().getString(rValue);
                    case TYPE_STR:
                        return value;
                    default:
                        return context.getResources().getString(rValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    public Button getmButton() {
        mButton.setBackground(getResources().getDrawable(R.drawable.btn_red_selector));
        return mButton;
    }
    private void showButton(){
        mButton.setVisibility(VISIBLE);
        mButton.setBackground(getResources().getDrawable(R.drawable.btn_red_selector));
    }
    @Deprecated
    public void setStatus(Activity activity, int status) {
        setStatus(status);
    }

    /**
     * 一定要停止动画 不然持续耗费绘制时间
     */
    public void hide() {
        if (mImageView.getBackground() instanceof AnimationDrawable) {
            AnimationDrawable frameAnimation = (AnimationDrawable) mImageView.getBackground();
            frameAnimation.stop();
        }
        //调整顺序， 防止setVisible 由于动画导致隐藏慢；
        this.clearAnimation();
        setVisibility(GONE);

        if (mLoadStatusListener != null) {
            mLoadStatusListener.performHide();
        }
    }

    /**
     * 设置颜色
     */
    public void setTextViewColor(int color) {
        if (mTextView != null) {
            mTextView.setTextColor(getResources().getColor(color));
        }
    }

    /**
     * 设置颜色
     */
    public void setResultContentColor(int color) {
        if (tvResultContent != null) {
            tvResultContent.setTextColor(getResources().getColor(color));
        }
    }

    /**
     * 设置换肤的颜色
     */
    public void setResultSkinContentColor(int color) {
        if (tvResultContent != null) {
            tvResultContent.setTextColor(color);
        }
    }

    /**
     * 渐隐消失
     */
    public void fadeHide() {
        animate().alpha(0).setDuration(300).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                hide();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        ProtocolUIManager.getInstance().registerLoadingView(this);
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        ProtocolUIManager.getInstance().unregisterLoadingView(this);
//    }
}
