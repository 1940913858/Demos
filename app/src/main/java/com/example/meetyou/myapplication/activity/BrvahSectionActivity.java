package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.meetyou.myapplication.adapter.SectionAdapter;
import com.example.meetyou.myapplication.bean.MySection;
import com.example.meetyou.myapplication.bean.Video;
import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.WheelView.WheelView;

import java.util.ArrayList;
import java.util.List;

public class BrvahSectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_brvah_section);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.hehe);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        final List<MySection> mData = getSampleData();
        SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_section_content, R
                .layout.def_section_head, mData);


        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MySection mySection = mData.get(position);
                if (mySection.isHeader)
                    Toast.makeText(BrvahSectionActivity.this, mySection.header, Toast
                            .LENGTH_LONG).show();
                else
                    Toast.makeText(BrvahSectionActivity.this, mySection.t.getName(), Toast
                            .LENGTH_LONG).show();
            }
        });
        sectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(BrvahSectionActivity.this, "onItemChildClick" + position, Toast
                        .LENGTH_LONG).show();
            }
        });
        mRecyclerView.setAdapter(sectionAdapter);
    }


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
}
