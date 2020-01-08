package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.WheelView.WheelView;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);


        RelativeLayout layout = findViewById(R.id.layout);

        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);


        layout.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.width=28*3;
            lp.height = 28*3;
            lp.setMargins(-150, 0, 0, 0);

            TextView imageView = new TextView(this);
//            imageView.setBackgroundResource(R.drawable.yima_aajl_wucuoshi);
            if (i > 0) {
                View childAt = layout.getChildAt(i - 1);
                lp.addRule(RelativeLayout.LEFT_OF, childAt.getId());
            }


            if (i==0){
                imageView.setBackgroundResource(R.drawable.yima_aajl_wucuoshi);
            }
            if (i==1){
                imageView.setBackgroundResource(R.drawable.yima_aiajl_biyuntao);
            }
            if (i==2){
                imageView.setBackgroundResource(R.drawable.yima_aajl_biyunyao);
            }
            if (i==3){
                imageView.setBackgroundResource(R.drawable.yima_aajl_more);
            }


            //imageView.setText(i+"-");
            imageView.setLayoutParams(lp);
            imageView.setId(i+1);
            layout.addView(imageView);
        }





        //先移除之前的view
//        layout.removeAllViews();
//        for (int i = 0; i < list.size(); i++) {
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT);
////            lp.setMargins(30, 0, 0, 0);
//            ImageView imageView = new ImageView(this);
//            imageView.setBackgroundResource(R.drawable.yima_aajl_wucuoshi);
//            if (i > 0) {
//                View childAt = layout.getChildAt(i - 1);
//                lp.addRule(RelativeLayout.RIGHT_OF, childAt.getId());
//            }
//            imageView.setLayoutParams(lp);
//            imageView.setId(i+1);
//            layout.addView(imageView);
//        }

    }


}
