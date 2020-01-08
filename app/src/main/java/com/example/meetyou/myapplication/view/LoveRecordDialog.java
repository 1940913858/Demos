package com.example.meetyou.myapplication.view;

import android.app.Activity;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.meetyou.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.3.1版本爱爱记录框
 *
 * @author chensenfa
 * @since 2019/8/19
 */
public class LoveRecordDialog extends BottomSheetDialog {

    private List datas = new ArrayList();


    private void checkHeight() {
        //listview  限制最低高度为5个item高度
        ViewGroup.LayoutParams params = listview.getLayoutParams();
        int itemHeight = 52 * 3;
        if (datas.size() < 5) {
            params.height = itemHeight * 5;
        } else {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        listview.setLayoutParams(params);
    }

    ListView listview;
    View aa;

    public LoveRecordDialog(Activity context) {
        super(context);

        for (int i = 0; i < 10; i++) {
            datas.add(i + "----");
        }

        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_open, null, false);

        aa = inflate.findViewById(R.id.aa);
        listview = inflate.findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout
                .simple_list_item_1, datas);//适配器
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datas.remove(position);
                adapter.notifyDataSetChanged();
                checkHeight();
            }
        });

        setContentView(inflate);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        //设置透明背景
        getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color
                .transparent);

        bottomSheet = getWindow().findViewById(R.id.design_bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottomSheet);
        mBehavior.setPeekHeight(1920);
    }

    FrameLayout bottomSheet;

    private BottomSheetBehavior<FrameLayout> mBehavior;


}
