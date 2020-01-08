package com.example.meetyou.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;

import java.util.List;

public class EditFlowItemAdapter extends BaseAdapter {
    List<String> datas;
    Context mContext;
    public static final String TAG = "EditFlowItemAdapter";


    public EditFlowItemAdapter(Context context, List<String> mPeriodLists) {
        this.mContext = context;
        this.datas = mPeriodLists;
    }


    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int postion) {
        return datas.get(postion);
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }


    public class ViewHolder {
        private CheckBox liuliangone, liuliangtwo, liuliangthree, liuliangfour, liuliangfive;
        private RelativeLayout rlLiuLiang;
        private TextView hehe;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_analysis_history,
                    parent, false);

//            viewHolder.rlLiuLiang = (RelativeLayout) convertView.findViewById(R.id
// .linearLiuliangContainer);
//            viewHolder.liuliangone = (CheckBox) convertView.findViewById(R.id.liuliangone);
//            viewHolder.liuliangtwo = (CheckBox) convertView.findViewById(R.id.liuliangtwo);
//            viewHolder.liuliangthree = (CheckBox) convertView.findViewById(R.id.liuliangthree);
//            viewHolder.liuliangfour = (CheckBox) convertView.findViewById(R.id.liuliangfour);
//            viewHolder.liuliangfive = (CheckBox) convertView.findViewById(R.id.liuliangfive);

            viewHolder.hehe = (TextView) convertView.findViewById(R.id.analysis_history_time_txt);
            viewHolder.hehe.setText(datas.get(position));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }


}
