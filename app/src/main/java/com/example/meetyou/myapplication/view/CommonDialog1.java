package com.example.meetyou.myapplication.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.meetyou.myapplication.R;

public class CommonDialog1 extends Dialog {

    Context mContext;

    public CommonDialog1(Context context) {
        super(context);
        mContext = context;
        setOwnerActivity((Activity) context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_full_screen_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(true);
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);//


        edittext = findViewById(R.id.edittext);
        edittext.setDialog(this);
        edittext.requestFocus();
        edittext.setFocusable(true);
        imageview = findViewById(R.id.imageview);

        new Handler().postDelayed(new Runnable() {//为了弹出效果
            @Override
            public void run() {
//                showInputMethod(mContext, imageview);
                edittext.setVisibility(View.VISIBLE);
            }
        }, 200);
    }

    EditTextPreIme edittext;
    ImageView imageview;

    public static void showInputMethod(Context context, View view) {
        //自动弹出键盘
        InputMethodManager inputManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}