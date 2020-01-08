package com.example.meetyou.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.meetyou.myapplication.R;

import java.util.List;

/**
 * 西柚分析界面
 *
 * @author chensenfa <chensenfa@xiaoyouzi.com>
 * @since 2019/7/3
 */
public class EditFlowRvAdapter extends RecyclerView.Adapter<EditFlowRvAdapter.EditFlowlHolder> {

    public static final String TAG = "EditFlowAdapter";
    private Context mContext;
    private List<String> mData;

    private static int ITEM_TYPE_FLOW = 1;
    private static int ITEM_TYPE_TONGJING = 2;

    public EditFlowRvAdapter(Context context, List<String> data) {
        mContext = context;
        this.mData = data;
    }

    @Override
    public EditFlowlHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_analysis_history, parent, false);
        return new EditFlowlHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EditFlowlHolder holder, int position) {
//        holder.ll_edit_list.setLayoutManager(new LinearLayoutManager(mContext));
//        EditFlowItemAdapter editFlowItemAdapter = new EditFlowItemAdapter(mContext, mData);
//        holder.ll_edit_list.setRemoveDivider(true);
//        holder.ll_edit_list.setAdapter(editFlowItemAdapter);
        holder.analysis_history_time_txt.setText(mData.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_FLOW;
        } else {
            return ITEM_TYPE_TONGJING;
        }
    }

    @Override
    public int getItemCount() {
        //return mData == null ? 0 : mData.size() * 2;
        return 2;
    }


    public class EditFlowlHolder extends BaseViewHolder {
//        private RelativeLayout rlLiuLiang;
//        private CheckBox liuliangone, liuliangtwo, liuliangthree, liuliangfour, liuliangfive;

        private RecyclerView ll_edit_list;
        private TextView analysis_history_time_txt;

        public EditFlowlHolder(View view) {
            super(view);

            analysis_history_time_txt = (TextView) convertView.findViewById(R.id.analysis_history_time_txt);
            ll_edit_list = (RecyclerView) view.findViewById(R.id.ll_edit_list);
//            rlLiuLiang = (RelativeLayout) view.findViewById(R.id.linearLiuliangContainer);
//            liuliangone = (CheckBox) view.findViewById(R.id.liuliangone);
//            liuliangtwo = (CheckBox) view.findViewById(R.id.liuliangtwo);
//            liuliangthree = (CheckBox) view.findViewById(R.id.liuliangthree);
//            liuliangfour = (CheckBox) view.findViewById(R.id.liuliangfour);
//            liuliangfive = (CheckBox) view.findViewById(R.id.liuliangfive);
        }
    }


    public class EditTongjingHolder extends BaseViewHolder {
        private RelativeLayout rlTongjing;
        private CheckBox tongjingone, tongjingtwo, tongjingthree, tongjingfour, tongjingfive;

        public EditTongjingHolder(View view) {
            super(view);

//            rlTongjing = (RelativeLayout) view.findViewById(R.id.linearTongjingContainer);
//            tongjingone = (CheckBox) view.findViewById(R.id.tongjingone);
//            tongjingtwo = (CheckBox) view.findViewById(R.id.tongjingtwo);
//            tongjingthree = (CheckBox) view.findViewById(R.id.tongjingthree);
//            tongjingfour = (CheckBox) view.findViewById(R.id.tongjingfour);
//            tongjingfive = (CheckBox) view.findViewById(R.id.tongjingfive);
        }
    }


}
