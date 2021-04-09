package com.example.meetyou.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class TabViewpagerActivity extends FragmentActivity implements View.OnClickListener {

    private List<TextView> mTabTextList = new ArrayList<TextView>();
    private TextView mTabRankingDownTxt;
    private TextView mTabRankingNewTxt;
    private TextView mTabRankingGameTxt;

    private ViewPager mViewPager;

    private View mTabLayout;
    private ImageView mTabUnderlineImg;
    private RelativeLayout.LayoutParams lp;

    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab_viewpager);

        View category_stripsss = findViewById(R.id.category_stripsss);


        category_stripsss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("senfa", "onClick: " );


                // create new fragments
                pages.clear();
                pages.add(MyFragment.newInstance(444));
                pages.add(MyFragment.newInstance(555));
                pages.add(MyFragment.newInstance(666));

                if (myAdapter != null){
                    myAdapter.changeId(System.currentTimeMillis());
                    myAdapter.notifyDataSetChanged();
                }
            }
        });

        mTabRankingDownTxt = (TextView) findViewById(R.id.tab_ranking_down);
        mTabRankingNewTxt = (TextView) findViewById(R.id.tab_ranking_new);
        mTabRankingGameTxt = (TextView) findViewById(R.id.tab_ranking_game);

        mTabUnderlineImg = (ImageView) findViewById(R.id.ranking_tab_underline);
        mTabLayout = (View) findViewById(R.id.tab_layout);

        lp = (RelativeLayout.LayoutParams) mTabUnderlineImg.getLayoutParams();

        // tab的text的集合
        mTabTextList.clear();
        mTabTextList.add(mTabRankingDownTxt);
        mTabTextList.add(mTabRankingNewTxt);
        mTabTextList.add(mTabRankingGameTxt);

        mViewPager = (ViewPager) findViewById(R.id.fragment_rank_list_viewPager);

        mTabRankingDownTxt.setOnClickListener(this);
        mTabRankingNewTxt.setOnClickListener(this);
        mTabRankingGameTxt.setOnClickListener(this);


        // create new fragments
        pages.add(MyFragment.newInstance(1111));
        pages.add(MyFragment.newInstance(2222));
        pages.add(MyFragment.newInstance(3333));

        myAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myAdapter);
        mViewPager.setOnPageChangeListener(new PageListener());

        // average screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
                mTabLayout.getLayoutParams();

        mTabLeftMargin = layoutParams.leftMargin;
        mAverageScreen = (screenWidth - mTabLeftMargin * 2 -90 -120) / pages.size();

    }

    private int mAverageScreen;
    int screenWidth;
    int mTabLeftMargin;

    private class PageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            /*
             * mAverageScreen 300
             * offset 106
             * 因为外层layout的marginleft 以及right = 30dp. 所以计算坐标需要扣去marginleft * 2
             */

            int offset = (mAverageScreen - mTabUnderlineImg.getLayoutParams().width) / 2;
            //外层layout marginletf = 30dp
            if (positionOffsetPixels > screenWidth - mTabLeftMargin * 2 - 52*3-44*3) {
                positionOffsetPixels = screenWidth - mTabLeftMargin * 2- 52*3-44*3;
            }
            lp.leftMargin = (int) (positionOffsetPixels / pages
                    .size()) + mAverageScreen * position + offset;

            mTabUnderlineImg.setLayoutParams(lp);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageSelected(int pPosition) {
            setTabSelector(pPosition);
        }
    }

    private void setTabSelector(int pPosition) {
        for (int i = 0; i < mTabTextList.size(); i++) {
            if (i == pPosition) {
                mTabTextList.get(i).setSelected(true);
            } else {
                mTabTextList.get(i).setSelected(false);
            }
        }
    }

    ArrayList<Fragment> pages = new ArrayList<Fragment>();

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        private long baseId = 0;

        @Override
        public long getItemId(int position) {
            Log.e("senfa", "getItemId: " );
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        public void changeId(long n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Log.e("senfa", "instantiateItem: " );
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int i) {
            Log.e("senfa", "getItem: " );
            return pages.get(i);
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }


    @Override
    public void onClick(View v) {

    }
}
