package com.example.meetyou.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

public class TestAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> list1;
    private List<String> list2;

    public TestAdapter(Context context, List<String> list1,
                       List<String> list2) {
        this.context = context;
        this.list1 = list1;
        this.list2 = list2;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            return list1 == null ? 0 : list1.size();
        } else if (groupPosition == 1) {
            return list2 == null ? 0 : list2.size();
        }

        return 0;
    }


    @Override
    public Object getGroup(int groupPosition) {
        if (groupPosition == 0) return "list1";
        else if (groupPosition == 1) return "list2";
        return null;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupPosition == 0) {
            return list1 != null && list1.size() > 0 ? list1.get(childPosition) : null;
        } else if (groupPosition == 1) {
            return list2 != null && list2.size() > childPosition ? list2.get(childPosition) : null;
        }
        return null;
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(getGroup(groupPosition).toString());
        return textView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}