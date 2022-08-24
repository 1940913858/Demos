package com.example.meetyou.myapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.meetyou.myapplication.R;

public class AnimationTranActivity extends Activity implements View.OnClickListener {

    ImageView mine_mode_change_tran_iv;
    RelativeLayout rl_header_change_model;

    RelativeLayout rl_change_period, rl_change_beiyun, rl_change_pregnancy, rl_change_baby;

    int itemWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animationtran);

        mine_mode_change_tran_iv = findViewById(R.id.mine_mode_change_tran_iv);

        rl_header_change_model = findViewById(R.id.rl_header_change_model);
        rl_header_change_model.post(new Runnable() {
            @Override
            public void run() {
                if (rl_header_change_model != null) {
                    itemWidth = rl_header_change_model.getWidth() / 4;

                    mine_mode_change_tran_iv.getLayoutParams().width = itemWidth;
                    mine_mode_change_tran_iv.requestLayout();
                }
            }
        });


        rl_change_period = (RelativeLayout) findViewById(R.id.ll_change_period);
        rl_change_beiyun = (RelativeLayout) findViewById(R.id.ll_change_beiyun);
        rl_change_pregnancy = (RelativeLayout) findViewById(R.id.ll_change_pregnancy);
        rl_change_baby = (RelativeLayout) findViewById(R.id.ll_change_baby);
        rl_change_period.setOnClickListener(this);
        rl_change_beiyun.setOnClickListener(this);
        rl_change_pregnancy.setOnClickListener(this);
        rl_change_baby.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ll_change_period) {
            changeMode(0);
        } else if (i == R.id.ll_change_beiyun) {
            changeMode(1);
        } else if (i == R.id.ll_change_pregnancy) {
            changeMode(2);
        } else if (i == R.id.ll_change_baby) {
            changeMode(3);
        } else {
        }
    }

    private void changeMode(int index) {
        // 1. 创建动画作用对象：此处以Button为例


    // 2. 获得当前按钮的位置

        float curTranslationX = mine_mode_change_tran_iv.getTranslationX();

    // 3. 创建动画对象

        ObjectAnimator animator = ObjectAnimator.ofFloat(mine_mode_change_tran_iv, "translationX", curTranslationX, itemWidth * index);

    // 表示的是:

    // 动画作用对象是mButton

    // 动画作用的对象的属性是X轴平移(在Y轴上平移同理，采用属性"translationY"

    // 动画效果是:从当前位置平移到 x=1500 再平移到初始位置

    // 4. 设置动画时长

        animator.setDuration(280);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Intent intent = new Intent(AnimationTranActivity.this, AnimationSetActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.e("senfa", "onAnimationUpdate: "+valueAnimator.getAnimatedValue() );
            }
        });
    // 5. 启动动画

        animator.start();
    }
}
