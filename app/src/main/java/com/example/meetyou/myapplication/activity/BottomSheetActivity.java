package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.LoveRecordDialog;

public class BottomSheetActivity extends Activity {

    private String[] data = {"苹果", "橘子", "芒果", "香蕉", "柠檬", "火龙果", "西瓜", "李子",
            "芭乐", "石榴", "葡萄", "荔枝", "圣女果", "杨梅", "柿子", "山竹", "杨桃", "雪梨", "猕猴桃", "榴莲"
            , "枇杷", "樱桃", "柚子", "水蜜桃", "桑葚", "莲雾"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analysis_habit);

        //方式一 直接继承BottomSheetDialog
        LoveRecordDialog answerSheetDialog = new LoveRecordDialog(this);
        answerSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        answerSheetDialog.show();


        //方式二  CoordinatorLayout + BottomSheetBehavior
    }


}
