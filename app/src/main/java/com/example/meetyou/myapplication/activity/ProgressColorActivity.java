package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.progresscolor.RoundedRectangleProgress;

public class ProgressColorActivity extends Activity {

    RoundedRectangleProgress rrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_color);
//        mView = (ColorTrackView) findViewById(R.id.id_changeTextColorView);

        RoundedRectangleProgress progress = (RoundedRectangleProgress) findViewById(R.id.progress);
        progress.setProgress(80/100f);

        rrp = (RoundedRectangleProgress) findViewById(R.id.rrp);
        rrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rrp.setClickable(false);
                handler.sendEmptyMessageDelayed(1, 100);
            }
        });
    }


    private int count = 0;

    Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (count < 100) {
                count++;
                float progress = count / 100f;
                rrp.setProgress(progress);
                handler.sendEmptyMessageDelayed(1, 100);
            } else {
                rrp.setClickable(true);
                handler.removeMessages(1);
                count = 0;
                rrp.setText("下载完成");
            }
        }
    };


}
