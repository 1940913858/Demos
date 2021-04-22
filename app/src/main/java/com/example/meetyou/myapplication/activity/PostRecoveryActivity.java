package com.example.meetyou.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.fragment.ContentListFragment;
import com.example.meetyou.myapplication.fragment.MyFragment;
import com.example.meetyou.myapplication.fragment.MyFragment2;
import com.example.meetyou.myapplication.view.CategoryTabStrip;

import java.util.ArrayList;


/**
 * 产后恢复界面
 */
public class PostRecoveryActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private MyAdapter myAdapter;

    ArrayList<TabFragment> pages = new ArrayList<>();


    ArrayList<TabFragment> bottoms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_recovery);

        //强制设置为可滑动 fix不可滑动的bug
        AppBarLayout appbarlayout = findViewById(R.id.appbarlayout);

        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) appbarlayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return true;
            }
        });
        params.setBehavior(behavior);

        CategoryTabStrip mSlidingTabLayout = findViewById(R.id.category_strip);

        // create new fragments
        pages.add(new TabFragment(MyFragment2.newInstance(1111), "1111"));
        pages.add(new TabFragment(MyFragment2.newInstance(2222), "2222"));
        pages.add(new TabFragment(MyFragment2.newInstance(3333), "3333"));
        pages.add(new TabFragment(MyFragment2.newInstance(4444), "4444"));
        pages.add(new TabFragment(MyFragment2.newInstance(5555), "5555"));
        pages.add(new TabFragment(MyFragment2.newInstance(6666), "6666"));
        pages.add(new TabFragment(MyFragment2.newInstance(7777), "7777"));

        bottoms.add(new TabFragment(ContentListFragment.newInstance("bottom1111"), "bottom1111"));
        bottoms.add(new TabFragment(ContentListFragment.newInstance("bottom1111"), "bottom2222"));
        bottoms.add(new TabFragment(ContentListFragment.newInstance("bottom1111"), "bottom3333"));
        bottoms.add(new TabFragment(ContentListFragment.newInstance("bottom1111"), "bottom4444"));

        myAdapter = new MyAdapter(getSupportFragmentManager(), pages);
        mViewPager = (ViewPager) findViewById(R.id.fragment_rank_list_viewPager);
        mViewPager.setAdapter(myAdapter);

        mSlidingTabLayout.setViewPager(mViewPager);
        //mViewPager.setOnPageChangeListener(new PageListener());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                bottoms.clear();
                bottoms.add(new TabFragment(ContentListFragment.newInstance(i+"==new"), "new11"));
                bottoms.add(new TabFragment(ContentListFragment.newInstance((i+1)+"==new"), "new22"));
                bottoms.add(new TabFragment(ContentListFragment.newInstance((i+2)+"==new"), "new33"));
                bottoms.add(new TabFragment(ContentListFragment.newInstance((i+3)+"==new"), "new44"));

                initBottom(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        initBottom(false);
    }
    ViewPager bottom_viewpager;
    MyAdapter listMyAdapter;
    //显示第一个fragment
    private void initBottom(boolean isRefresh) {
        bottom_viewpager = findViewById(R.id.bottom_viewpager);
        CategoryTabStrip bottom_strip = findViewById(R.id.bottom_strip);

        if (listMyAdapter == null){
            listMyAdapter = new MyAdapter(getSupportFragmentManager(), bottoms);
            bottom_viewpager.setAdapter(listMyAdapter);

        }

        bottom_strip.setViewPager(bottom_viewpager);

        if (isRefresh){
            bottom_viewpager.setCurrentItem(0);
            listMyAdapter.notifyDataSetChanged();
        }

    }

    private class MyAdapter extends FragmentPagerAdapter {
        ArrayList<TabFragment> mLists = new ArrayList<>();
        public MyAdapter(FragmentManager fm, ArrayList<TabFragment> lists) {
            super(fm);
            this.mLists = lists;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int i) {
            return mLists.get(i).getmFragment();
        }

        @Override
        public int getCount() {
            return mLists.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mLists.get(position).getmTitle();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }


    class TabFragment {
        Fragment mFragment;
        String mTitle;

        public TabFragment(Fragment fragment, String title) {
            this.mFragment = fragment;
            this.mTitle = title;
        }

        public Fragment getmFragment() {
            return mFragment;
        }

        public String getmTitle() {
            return mTitle;
        }
    }


}
