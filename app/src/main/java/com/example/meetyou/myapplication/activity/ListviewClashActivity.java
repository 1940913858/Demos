package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.HabitTimeChooseView;
import com.example.meetyou.myapplication.view.WheelView.WheelView;

/**
 *
 *
 * 在子viewWheelView的ontouchevent里添加
 * if (getParent() != null){
 *                 getParent().requestDisallowInterceptTouchEvent(true);
 *             }
 *
 *      判断父布局是否存在，若存在，则让父布局禁止拦截
 *
 */
public class ListviewClashActivity extends Activity implements HabitTimeChooseView.OnResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listview_clash);


        ListView listview = findViewById(R.id.listview);

        View inflate = getLayoutInflater().inflate(R.layout.activity_wheelview, null);
        WheelView wheelview = inflate.findViewById(R.id.wheelview);
        wheelview.setAdapter(new String[]{"哈哈哈","222","333","333"});
        listview.addHeaderView(inflate);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//适配器
        listview.setAdapter(adapter);


    }

    private String [] data ={"苹果","橘子","芒果","香蕉","柠檬","火龙果","西瓜","李子",
            "芭乐","石榴","葡萄","荔枝","圣女果","杨梅","柿子","山竹","杨桃","雪梨","猕猴桃","榴莲"
            ,"枇杷","樱桃","柚子","水蜜桃","桑葚","莲雾"};


    @Override
    public void onOk(int type, int year, int yearMonth, int month) {
    }

    @Override
    public void onCancel() {

    }
}
