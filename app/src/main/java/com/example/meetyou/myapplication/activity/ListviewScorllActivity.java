package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.adapter.SectionAdapter;
import com.example.meetyou.myapplication.bean.MySection;
import com.example.meetyou.myapplication.bean.Video;
import com.example.meetyou.myapplication.view.HabitTimeChooseView;
import com.example.meetyou.myapplication.view.WheelView.WheelView;

import java.util.ArrayList;
import java.util.List;

public class ListviewScorllActivity extends Activity{


    private String [] data ={"苹果","橘子","芒果","香蕉","柠檬","火龙果","西瓜","李子",
            "芭乐","石榴","葡萄","荔枝","圣女果","杨梅","柿子","山竹","杨桃","雪梨","猕猴桃","榴莲"
            ,"枇杷","樱桃","柚子","水蜜桃","桑葚","莲雾"};

    public static List<MySection> getSampleData() {
        List<MySection> list = new ArrayList<>();
        list.add(new MySection(true, "Section 1", true));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(true, "Section 2", false));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(true, "Section 3", false));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(true, "Section 4", false));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(true, "Section 5", false));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        list.add(new MySection(new Video(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        return list;
    }

    private static final String HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK =
            "https://avatars1.githubusercontent.com/u/7698209?v=3&s=460";
    private static final String CYM_CHAD = "CymChad";

    RecyclerView recyclerview;
    SectionAdapter sectionAdapter;
    TextView text2;
    int top;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listview_scroll);


         recyclerview = findViewById(R.id.listview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

         text2 = findViewById(R.id.text2);


        final List<MySection> mData = getSampleData();
         sectionAdapter = new SectionAdapter(R.layout.item_section_content, R
                .layout.def_section_head, mData);


        View inflate = getLayoutInflater().inflate(R.layout.activity_listview_head, null);
        recyclerview.setAdapter(sectionAdapter);
        sectionAdapter.addHeaderView(inflate);

//        View childAt = recyclerview.getChildAt(0);


        recyclerview.post(new Runnable() {
            @Override
            public void run() {
                View viewById = sectionAdapter.getViewByPosition(recyclerview,0, R.id.head);
//                View viewById = childView.findViewById(R.id.head);
                if (viewById == null){
//                    text2.setVisibility(View.VISIBLE);
                    return;
                }

                 top = viewById.getTop();
            }
        });

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy = totalDy + dy;
                //Log.e("senfa", "dx: "+dx+"--dy = "+dy+"---totalDy = "+totalDy);


                int headerLayoutCount = sectionAdapter.getHeaderLayoutCount();
//                View child = recyclerview.getChildAt(0);//getchildat 只能获取到显示的item
//                View childView = recyclerView.getLayoutManager().findViewByPosition(0);

//                View viewById = sectionAdapter.getViewByPosition(recyclerview,0, R.id.head);
////                View viewById = childView.findViewById(R.id.head);
//                if (viewById == null){
//                    text2.setVisibility(View.VISIBLE);
//                    Log.e("senfa", "onScrolled: "+ viewById);
//                    return;
//                }
//
//                int top = viewById.getTop();
//                int height = viewById.getHeight();
//                int scrollY = viewById.getScrollY();
                //Log.e("Senfa", "top: "+ top+"--height = "+height+"--scrollY = "+scrollY);


                if (totalDy>=top){
                    text2.setVisibility(View.VISIBLE);
                }else{
                    text2.setVisibility(View.GONE);
                }
            }
        });
    }

    int totalDy = 0;




}
