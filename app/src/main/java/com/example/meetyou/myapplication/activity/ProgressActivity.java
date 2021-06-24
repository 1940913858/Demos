package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.ProgressView;

import java.math.BigDecimal;

public class ProgressActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        BigDecimal b = new BigDecimal((2 / 3)).setScale(1, BigDecimal.ROUND_HALF_UP);
        float pressent = (float) 2 / 3 * 100;
        int round = Math.round(pressent);


        setContentView(R.layout.activity_progress);
        ProgressView progress = (ProgressView) findViewById(R.id.progress);

        progress.setMaxProgress(100);
        progress.setProgress(11);

    }

    public static float divide(int v1, int v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return (float) b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}
