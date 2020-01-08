//package com.example.meetyou.myapplication.activity;
//
//import android.app.Fragment;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.example.meetyou.myapplication.R;
//import com.example.meetyou.myapplication.fragment.MyFragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TabViewpagerActivity extends FragmentActivity implements View.OnClickListener {
//
//    private List<TextView> mTabTextList = new ArrayList<TextView>();
//    private TextView mTabRankingDownTxt;
//    private TextView mTabRankingNewTxt;
//    private TextView mTabRankingGameTxt;
//
//    private ViewPager mViewPager;
//
//    private View mTabLayout;
//    private ImageView mTabUnderlineImg;
//    private RelativeLayout.LayoutParams lp;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_tab_viewpager);
//
//        mTabRankingDownTxt = (TextView) findViewById(R.id.tab_ranking_down);
//        mTabRankingNewTxt = (TextView) findViewById(R.id.tab_ranking_new);
//        mTabRankingGameTxt = (TextView) findViewById(R.id.tab_ranking_game);
//
//        mTabUnderlineImg = (ImageView) findViewById(R.id.ranking_tab_underline);
//        mTabLayout = (View) findViewById(R.id.tab_layout);
//
//        lp = (RelativeLayout.LayoutParams) mTabUnderlineImg.getLayoutParams();
//
//        // tab的text的集合
//        mTabTextList.clear();
//        mTabTextList.add(mTabRankingDownTxt);
//        mTabTextList.add(mTabRankingNewTxt);
//        mTabTextList.add(mTabRankingGameTxt);
//
//        mViewPager = (ViewPager) findViewById(R.id.fragment_rank_list_viewPager);
//
//        mTabRankingDownTxt.setOnClickListener(this);
//        mTabRankingNewTxt.setOnClickListener(this);
//        mTabRankingGameTxt.setOnClickListener(this);
//
//
//        // create new fragments
//        pages.add(new MyFragment());
//        pages.add(new MyFragment());
//        pages.add(new MyFragment());
//
//        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
//        mViewPager.setOnPageChangeListener(new PageListener());
//
//        // average screen
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        screenWidth = dm.widthPixels;
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)
//                mTabLayout.getLayoutParams();
//
//        mTabLeftMargin = layoutParams.leftMargin;
//        mAverageScreen = (screenWidth - mTabLeftMargin * 2 -90 -120) / pages.size();
//
//    }
//
//    private int mAverageScreen;
//    int screenWidth;
//    int mTabLeftMargin;
//
//    private class PageListener implements ViewPager.OnPageChangeListener {
//
//        @Override
//        public void onPageScrolled(int position, float positionOffset,
//                                   int positionOffsetPixels) {
//            /*
//             * mAverageScreen 300
//             * offset 106
//             * 因为外层layout的marginleft 以及right = 30dp. 所以计算坐标需要扣去marginleft * 2
//             */
//
//            int offset = (mAverageScreen - mTabUnderlineImg.getLayoutParams().width) / 2;
//            //外层layout marginletf = 30dp
//            if (positionOffsetPixels > screenWidth - mTabLeftMargin * 2 - 52*3-44*3) {
//                positionOffsetPixels = screenWidth - mTabLeftMargin * 2- 52*3-44*3;
//            }
//            lp.leftMargin = (int) (positionOffsetPixels / pages
//                    .size()) + mAverageScreen * position + offset;
//
//            mTabUnderlineImg.setLayoutParams(lp);
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//
//        @Override
//        public void onPageSelected(int pPosition) {
//            setTabSelector(pPosition);
//        }
//    }
//
//    private void setTabSelector(int pPosition) {
//        for (int i = 0; i < mTabTextList.size(); i++) {
//            if (i == pPosition) {
//                mTabTextList.get(i).setSelected(true);
//            } else {
//                mTabTextList.get(i).setSelected(false);
//            }
//        }
//    }
//
//    ArrayList<Fragment> pages = new ArrayList<Fragment>();
//
//    private class MyAdapter extends FragmentPagerAdapter {
//        public MyAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return pages.size();
//        }
//
//        @Override
//        public Fragment getItem(int arg0) {
//            return pages.get(arg0);
//        }
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//    }
//}
