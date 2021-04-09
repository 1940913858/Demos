package com.example.meetyou.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.meetyou.myapplication.R;

public class EduMediaViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    public EduMediaViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.iv_media);
    }
}
