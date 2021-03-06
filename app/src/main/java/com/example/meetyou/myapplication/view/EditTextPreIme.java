package com.example.meetyou.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditTextPreIme extends EditText {
    public EditTextPreIme(Context context) {
        super(context);
    }

    public EditTextPreIme(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextPreIme(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//          ((Activity)this.getContext()).onKeyDown(KeyEvent.KEYCODE_BACK, event);
//          或者粗鲁一点，直接在调用finish()关闭activity
//          (推荐第1种方式，由activity的onKeyDown统一处理）:
            if (mDialog != null) {
                mDialog.dismiss();
            }
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    private CommonDialog1 mDialog;

    public void setDialog(CommonDialog1 dialog) {
        mDialog = dialog;
    }
}