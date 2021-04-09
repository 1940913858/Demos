package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.SearchHotView;

import java.util.ArrayList;


public class TagsViewActivity extends Activity {


    private ArrayList<String> textBaseList = new ArrayList<>();
    private ArrayList<String> textList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tagview);
        View button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textBaseList.add("什么鬼");
                mSearchHotView.setList(textBaseList);
            }
        });

        for (int i = 0; i < 9; i++) {
            if (i == 0) {
                textBaseList.add("容易");
            } else if (i == 1) {
                textBaseList.add("搜索");
            } else if (i == 2) {
                textBaseList.add("啥呢会");
            } else if (i == 3) {
                textBaseList.add("IOS哦让我送");
            } else if (i == 4) {
                textBaseList.add("是很好");
            }
            else if (i == 5) {
                textBaseList.add("哈哈");
            } else if (i == 6) {
                textBaseList.add("宝宝巴士");
            }else if (i == 7) {
                textBaseList.add("小鸡彩虹8");
            }
            else if (i == 8) {
                textBaseList.add("小鸡彩虹7");
            }
            else if (i == 9) {
                textBaseList.add("呵呵呵呵呵");
            }
            else {
                textBaseList.add(i + "11");
            }

        }

        mSearchHotView = findViewById(R.id.search_hotview);
        mSearchHotView.setCheckLinesCount(2);
        mSearchHotView.setIsNeedCheckLines(true);

        mSearchHotView.setList(textBaseList);

    }

    SearchHotView mSearchHotView;

}
