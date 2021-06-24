package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.CourseTagsView;

import java.util.ArrayList;


public class TestActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        GradientDrawable background = (GradientDrawable) play_all_txt.getBackground();
//        background.setColor("#000000");
//        play_all_txt.setBackgroundDrawable(background);

//        play_all_txt.setBackgroundDrawable(setShapeColor(Color.parseColor("#000000"),new float[]{24,24,0,0,24,24,0,0}));


        //初始化数据
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add("" + i);
        }


        CourseTagsView item_tag_layout = findViewById(R.id.item_tag_layout);

        item_tag_layout.removeAllViews();
        item_tag_layout.setVisibility(View.VISIBLE);
        item_tag_layout.setExpand(true);
        item_tag_layout.setList(list);
    }

    /**
     * 设置圆角背景
     *
     * @param
     * @param radii new float[] { r0, r0, r1, r1, r2, r2, r3,r3 }
     *              设置图片四个角圆形半径：1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
     * @return
     */
    public static GradientDrawable setShapeColor(int color, float[] radii) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        drawable.setCornerRadii(radii);
        drawable.setColor(color);
        return drawable;
    }

}
