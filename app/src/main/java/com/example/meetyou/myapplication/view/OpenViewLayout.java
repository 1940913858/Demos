package com.example.meetyou.myapplication.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;

/**
 * Created by chensenfa on 2019/8/23.
 */

public class OpenViewLayout extends LinearLayout {


    public OpenViewLayout(Context context) {
        super(context);
        init();
    }

    public OpenViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OpenViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    TextView btn1;
    TextView btn2;
    TextView btn3;
    boolean isOpen = false;
    float distanceX = 240;
    ObjectAnimator objAnimatorX1 = null;
    ObjectAnimator objAnimatorX2 = null;

    private void init() {
        View view = View.inflate(getContext(), R.layout.activity_open_button, this);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);

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
//                    Toast.makeText(mContext, "bingo,我收回来咯", Toast.LENGTH_SHORT)
//                            .show();
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

                    btn1.setText(s[index % 3]);
                    btn2.setText(s[(index + 1) % 3]);
                    btn3.setText(s[(index + 2) % 3]);

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
//                    Toast.makeText(mContext, "bingo,我弹出来咯", Toast.LENGTH_SHORT)
//                            .show();
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
