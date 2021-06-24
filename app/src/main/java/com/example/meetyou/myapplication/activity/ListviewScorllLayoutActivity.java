package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.adapter.SectionAdapter;
import com.example.meetyou.myapplication.bean.MySection;
import com.example.meetyou.myapplication.bean.Video;

import java.util.ArrayList;
import java.util.List;

public class ListviewScorllLayoutActivity extends Activity {


    private String[] data = {"苹果", "橘子", "芒果", "香蕉", "柠檬", "火龙果", "西瓜", "李子",
            "芭乐", "石榴", "葡萄", "荔枝", "圣女果", "杨梅", "柿子", "山竹", "杨桃", "雪梨", "猕猴桃", "榴莲"
            , "枇杷", "樱桃", "柚子", "水蜜桃", "桑葚", "莲雾"};

    public static List<MySection> getSampleData() {
        List<MySection> list = new ArrayList<>();
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        return list;
    }

    private static final String HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK =
            "https://avatars1.githubusercontent.com/u/7698209?v=3&s=460";
    private static final String CYM_CHAD = "CymChad";

    RecyclerView recyclerview;
    SectionAdapter sectionAdapter;
    int top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_scroll_layout);

        recyclerview = findViewById(R.id.listview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        final List<MySection> mData = getSampleData();
        sectionAdapter = new SectionAdapter(R.layout.item_section_content, R
                .layout.def_section_head, mData);

        recyclerview.setAdapter(sectionAdapter);

    }

}
