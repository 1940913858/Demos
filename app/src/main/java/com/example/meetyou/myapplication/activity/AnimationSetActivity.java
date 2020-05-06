package com.example.meetyou.myapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.meetyou.myapplication.R;

public class AnimationSetActivity extends Activity {

    ImageView imageview;
    ImageView imageview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animationset);

        imageview = findViewById(R.id.imageview);
        imageview2 = findViewById(R.id.imageview2);


        /*1.“+”icon500ms速率往左旋转90度并渐隐，同时“勾”icon从往右90度位置500ms速率往左旋转90度并渐显；

        2.“勾”icon显示0.5秒后，500ms速率渐隐；*/
        ObjectAnimator animator1 =
                ObjectAnimator.ofFloat(imageview, "rotation", 0f, -90f);
        ObjectAnimator animator2 =
                ObjectAnimator.ofFloat(imageview, "alpha", 1f, 0f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1, animator2);
        set.setDuration(500);
        set.start();//第一个imageview ，

        ObjectAnimator animator3 =
                ObjectAnimator.ofFloat(imageview2, "rotation", 90f, 0f);

        ObjectAnimator animator4 =
                ObjectAnimator.ofFloat(imageview2, "alpha", 0f, 1f);

        animator4.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator animator =
                        ObjectAnimator.ofFloat(imageview2, "alpha", 1f, 0f);
                AnimatorSet set = new AnimatorSet();
                set.setStartDelay(500);
                set.playTogether(animator);
                set.setDuration(500);
                set.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(animator3, animator4);
        set2.setDuration(500);
        set2.start();
    }

}
