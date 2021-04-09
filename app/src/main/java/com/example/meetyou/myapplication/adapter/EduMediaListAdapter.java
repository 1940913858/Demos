package com.example.meetyou.myapplication.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meetyou.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class EduMediaListAdapter extends RecyclerView.Adapter<EduMediaViewHolder> {

    private Context context;

    private List<String> mData;

    private OnItemClickListener onItemClickListener;

    public EduMediaListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public EduMediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ybb_item_music_media, parent, false);
        return new EduMediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EduMediaViewHolder holder, int position) {
        if (mData == null || mData.isEmpty()){
            return;
        }
        int mPos = position;
        int realPos = getRealModel(mPos);
        if (mData.size() > realPos && mData.get(realPos) != null){
            String model = mData.get(realPos);
        }
    }

    public List<String> getData(){
        return mData;
    }

    public int getRealModel(int position){
        int realPos;
        if (position > Integer.MAX_VALUE / 2){
            realPos = Math.abs(position - Integer.MAX_VALUE) % mData.size();
        }else{
            realPos = position % mData.size();
        }
        return realPos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public void setNewData(@Nullable List<String> data) {
        this.mData = data == null ? new ArrayList<String>() : data;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(String model, int position);
    }
}
