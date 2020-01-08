package com.example.meetyou.myapplication.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetyou.myapplication.R;

public class OpenButtonActivity extends Activity {
    View nestedScrollView = null;

    TextView btn1;
    TextView btn2;
    TextView btn3;
    boolean isOpen = false;
    float distanceX = 240;
    ObjectAnimator objAnimatorX1 = null;
    ObjectAnimator objAnimatorX2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_open_button);

        //变色text
        /*String content = "感谢您使用西柚大姨妈！我们非常重视您的个人隐私和个人信息保护。在您使用西柚大姨妈前，请认真阅读《用户使用协议》及《西柚大姨妈隐" +
                "私政策》，您同意并接受全部条款后方可开始使用西柚大姨妈。";
        SpannableStringBuilder contentSpannable = new SpannableStringBuilder(content);
        contentSpannable.setSpan(new ForegroundColorSpan(Color.RED),0,5,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        contentSpannable.setSpan(new ForegroundColorSpan(Color.RED),10,15,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        TextView hahaha = findViewById(R.id.hahaha);
        hahaha.setText(contentSpannable);*/


        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(0);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    update(1);
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    update(2);
                }
            }
        });

    }

    String[] s = new String[]{"周期", "流量", "痛经"};

    int index = 0;

    private void update(final int clickIndex) {
        if (isOpen) {
            // 收回
            objAnimatorX1 = ObjectAnimator.ofFloat(
                    btn2, "x",
                    btn2.getX(), btn2.getX() - distanceX);
            objAnimatorX1.setDuration(200);


            objAnimatorX2 = ObjectAnimator.ofFloat(
                    btn3, "x",
                    btn3.getX(), btn3.getX() - (2 * distanceX));
            objAnimatorX2.setDuration(200);

            objAnimatorX1.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Toast.makeText(getApplicationContext(), "bingo,我收回来咯", Toast.LENGTH_SHORT)
                            .show();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (clickIndex == 0) {
                        for (int i = 0; i < s.length; i++) {
                            if (btn1.getText().equals(s[i])) {
                                index = i;
                                break;
                            }
                        }
                    } else if (clickIndex == 1) {
                        for (int i = 0; i < s.length; i++) {
                            if (btn2.getText().equals(s[i])) {
                                index = i;
                                break;
                            }
                        }
                    } else if (clickIndex == 2) {
                        for (int i = 0; i < s.length; i++) {
                            if (btn3.getText().equals(s[i])) {
                                index = i;
                                break;
                            }
                        }
                    }

                    btn1.setText(s[index%3]);
                    btn2.setText(s[(index+1)%3]);
                    btn3.setText(s[(index+2)%3]);

                    btn1.setSelected(false);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            objAnimatorX1.start();
            objAnimatorX2.start();
            isOpen = false;

        } else {
            // 弹出
            // X方向移动

            objAnimatorX1 = ObjectAnimator.ofFloat(
                    btn2, "x",
                    btn2.getX(), btn2.getX() + distanceX);
            objAnimatorX1.setDuration(200);


            objAnimatorX2 = ObjectAnimator.ofFloat(
                    btn3, "x",
                    btn3.getX(), btn3.getX() + (2 * distanceX));
            objAnimatorX2.setDuration(200);

            objAnimatorX1.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Toast.makeText(getApplicationContext(), "bingo,我弹出来咯", Toast.LENGTH_SHORT)
                            .show();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            objAnimatorX1.start();
            objAnimatorX2.start();
            btn1.setSelected(true);
            isOpen = true;
        }
    }

}
