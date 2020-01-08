package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.meetyou.myapplication.R;

public class LottieActivity extends Activity {

    LottieAnimationView animation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lottie);

        animation_view = findViewById(R.id.animation_view);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animation_view.setAnimation("mood/mood.json");
                animation_view.setProgress(0);
                animation_view.loop(true);
                animation_view.setImageAssetsFolder("mood/images");
                animation_view.playAnimation();
            }
        });


        int money = 30000;
        int lixi = 135;
        for (int i = 0;i<6;i++){

            int lilv = lixi / money;
            Log.e("senfa", "onCreate: "+lilv );

            money = money-5000;


        }

    }

}
