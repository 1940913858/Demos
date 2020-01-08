package com.example.meetyou.myapplication.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.HttpTextView;
import com.example.meetyou.myapplication.view.SendTextView;
import com.example.meetyou.myapplication.view.SpannableStringBuilderAllVer;

public class TextActivity extends Activity implements View.OnClickListener {
    View nestedScrollView = null;
    private HttpTextView mHttpTextView;
    private EditText mEditText;
    private Button mButton;
    private Button mButtonWithSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text);

        /*TextView text = findViewById(R.id.text);
        //变色text
        String content = "感谢www.baidu.com您使用西柚大姨妈！我们非常重视您的个人隐私和个人信息保护。在您使用西柚大姨妈前，请认真阅读《用户使用协议》及《西柚大姨妈隐" +
                "私政策》，您同意并接受全部条款后方可开始使用西柚大姨妈。";
        SpannableStringBuilder contentSpannable = new SpannableStringBuilder(content);
        contentSpannable.setSpan(new ForegroundColorSpan(Color.RED),0,5,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        contentSpannable.setSpan(new ForegroundColorSpan(Color.RED),10,15,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(contentSpannable);*/


        initView();
    }

    private void initView() {
        mHttpTextView= (HttpTextView) findViewById(R.id.tx_test);
        mEditText= (EditText) findViewById(R.id.ed_test);
        mButton= (Button) findViewById(R.id.btn_test);
        mButtonWithSpan=(Button) findViewById(R.id.btn_test2);

        mButton.setOnClickListener(this);
        mButtonWithSpan.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test:
                test1();
                break;
            case R.id.btn_test2:
                test2();
                break;
            default:break;
        }
    }

    private void test1() {
        if (mEditText.getText().toString().equals("")){
            mHttpTextView.setUrlText(mHttpTextView.testText);
        }else {
            mHttpTextView.setUrlText(mEditText.getText().toString());
        }
    }
    private String testStr="大灯泡说。。。:";
    private void test2() {
        SpannableStringBuilderAllVer spannableStringBuilderAllVer=new SpannableStringBuilderAllVer();
        spannableStringBuilderAllVer.append(testStr+"1",new TestClick(testStr+"1"),0);
        spannableStringBuilderAllVer.append(testStr+"2",new TestClick(testStr+"2"),0);
        spannableStringBuilderAllVer.append(testStr+"3",new TestClick(testStr+"3"),0);
        spannableStringBuilderAllVer.append('\n');
        spannableStringBuilderAllVer.append(mHttpTextView.testText);
        mHttpTextView.setUrlText(spannableStringBuilderAllVer);

    }
    //=====================测试用的clickspan========================================
    class TestClick extends ClickableSpan {
        private String text;

        public TestClick(String text) {
            this.text = text;
        }

        @Override
        public void onClick(View widget) {
            Toast.makeText(widget.getContext(), text, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.RED);
            ds.setUnderlineText(false);
        }
    }
}
