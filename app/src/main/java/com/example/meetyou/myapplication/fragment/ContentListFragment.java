package com.example.meetyou.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.activity.HorizotalRVMaxActivity;

import java.util.ArrayList;

public class ContentListFragment extends Fragment {

    public ContentListFragment() {
//        initData();
    }

    public static ContentListFragment newInstance(String ss) {
        ContentListFragment testFragment = new ContentListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DAY", ss);
        testFragment.setArguments(bundle);
        return testFragment;
    }

    private ArrayList<String> mEntityList= new ArrayList<>();
//
//    private void initData() {
//        mEntityList = new ArrayList<>();
//        for (int i = 1; i <= 5; i++) {
//            mEntityList.add( i + "");
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        View inflate = getLayoutInflater().inflate(R.layout.fragment_list_item, null);
        final LinearLayout root = inflate.findViewById(R.id.rootview);


        Bundle bundle = getArguments();
        String ss = "";
        if (bundle != null) {
            ss = bundle.getString("DAY");
        }

        mEntityList.clear();
        for (int i = 1; i <= 5; i++) {
            mEntityList.add( i + ss);
        }

        final RecyclerView recyclerview = inflate.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(new MyAdapter(mEntityList));

        return inflate;
    }

    class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
        ArrayList<String> bEntityList;

        public MyAdapter(ArrayList<String> entityList) {
            bEntityList = entityList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout
                    .rv_item_horizotal, viewGroup, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.fruitName.setText(bEntityList.get(i));

            Log.e("senfa", "onBindViewHolder: "+i );
        }

        @Override
        public int getItemCount() {
            return bEntityList.size();
        }
    }

    /**
     * RecyclerView的持有者类
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            fruitName = view.findViewById(R.id.hehe);
        }
    }
}