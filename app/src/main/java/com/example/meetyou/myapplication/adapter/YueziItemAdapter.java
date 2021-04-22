package com.example.meetyou.myapplication.adapter;


import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.meetyou.myapplication.R;



/**
 * 产后 月子adapter
 * created by chensenfa
 * 2021/04/13
 */
public class YueziItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public YueziItemAdapter(Context context) {
        super(R.layout.yuezi_item);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text,"哈哈哈哈");
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}