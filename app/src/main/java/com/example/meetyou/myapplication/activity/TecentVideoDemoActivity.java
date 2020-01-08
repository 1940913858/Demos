package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.video.VerticalViewPager;
import com.example.meetyou.myapplication.video.bean.TCVideoInfo;
import com.example.meetyou.myapplication.video.list.TCVideoListMgr;
import com.example.meetyou.myapplication.video.login.TCUserMgr;
import com.example.meetyou.myapplication.video.utils.TCConstants;
import com.example.meetyou.myapplication.video.utils.TCUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TecentVideoDemoActivity extends Activity implements ITXVodPlayListener {


    VerticalViewPager mVerticalViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d(TAG, "liteav sdk version is : " + sdkver);

        mVerticalViewPager = findViewById(R.id.vertical_view_pager);

        reloadLiveList();


        initView();

    }

    private int mCurrentPosition;
    private static final String TAG = "TecentVideoDemoActivity";
    /**
     * SDK播放器以及配置
     */
    private TXVodPlayer mTXVodPlayer;

    private void initView() {

//        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.player_cloud_view);
//        mIvCover = (ImageView) findViewById(R.id.player_iv_cover);
//        mTvBack = (TextView) findViewById(R.id.player_tv_back);
//        mTvBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        ImageButton mImgBtnFollowShot = (ImageButton)findViewById(R.id.imgBtn_follow_shot);
        mImgBtnFollowShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TecentVideoDemoActivity.this,
                        TecentRecordDemoActivity.class));
            }
        });

        mVerticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_view_pager);
        mVerticalViewPager.setOffscreenPageLimit(2);
        mVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                TXLog.i(TAG, "mVerticalViewPager, onPageScrolled position = " + position);
//                mCurrentPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                TXLog.i(TAG, "mVerticalViewPager, onPageSelected position = " + position);
                mCurrentPosition = position;
                // 滑动界面，首先让之前的播放器暂停，并seek到0
                TXLog.i(TAG, "滑动后，让之前的播放器暂停，mTXVodPlayer = " + mTXVodPlayer);
                if (mTXVodPlayer != null) {
                    mTXVodPlayer.seek(0);
                    mTXVodPlayer.pause();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mVerticalViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                TXLog.i(TAG, "mVerticalViewPager, transformPage pisition = " + position + " " +
                        "mCurrentPosition" + mCurrentPosition);
                if (position != 0) {
                    return;
                }

                ViewGroup viewGroup = (ViewGroup) page;
                mIvCover = (ImageView) viewGroup.findViewById(R.id.player_iv_cover);
                mTXCloudVideoView =
                        (TXCloudVideoView) viewGroup.findViewById(R.id.player_cloud_view);


                PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(mCurrentPosition);
                if (playerInfo != null) {
                    playerInfo.txVodPlayer.resume();
                    mTXVodPlayer = playerInfo.txVodPlayer;
                }
            }
        });


    }

    MyPagerAdapter mPagerAdapter;
    ImageView mIvCover;
    TXCloudVideoView mTXCloudVideoView;

    private List<TCVideoInfo> mVideoList;

    private boolean reloadLiveList() {
        mVideoList = new ArrayList<>();
        TCVideoListMgr.getInstance().fetchUGCList(new TCVideoListMgr.Listener() {
            @Override
            public void onVideoList(final int retCode, final ArrayList<TCVideoInfo> result,
                                    final int index, final int total, final boolean refresh) {
                if (this != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (retCode == 0) {
                                /*if (mPullIndex == index) {
                                    //更新当前页
                                    mVideoList.clear();
                                } else {
                                    //更新下一页
                                }*/
                                if (result != null) {
                                    mVideoList.addAll((ArrayList<TCVideoInfo>) result.clone());
                                }

                                //init viewpager adapter
                                mPagerAdapter = new MyPagerAdapter();
                                mVerticalViewPager.setAdapter(mPagerAdapter);
                                initPlayerSDK();

                                /*if (refresh) {
                                    mUGCListViewAdapter.notifyDataSetChanged();
                                }
                                mPullIndex = index;*/
                            } else {
                                Toast.makeText(TecentVideoDemoActivity.this,
                                        getResources().getString(R.string.tc_live_list_fragment_refresh_list_failed),
                                        Toast.LENGTH_LONG).show();
                            }
                            //mEmptyView.setVisibility(mUGCListViewAdapter.getItemCount() == 0 ?
                            // View.VISIBLE : View.GONE);
                            //mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
        return true;
    }

    @Override
    public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
            int width = param.getInt(TXLiveConstants.EVT_PARAM1);
            int height = param.getInt(TXLiveConstants.EVT_PARAM2);
            //FIXBUG:不能修改为横屏，合唱会变为横向的
//            if (width > height) {
//                player.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
//            } else {
//                player.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
//            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            restartPlay();
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {// 视频I帧到达，开始播放

            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null) {
                playerInfo.isBegin = true;
            }
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event I FRAME, player = " + player);
                mIvCover.setVisibility(View.GONE);
                TCUserMgr.getInstance().uploadLogs(TCConstants.ELK_ACTION_VOD_PLAY,
                        TCUserMgr.getInstance().getUserId(), event, "点播播放成功", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                            }
                        });
            }
        } else if (event == TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED) {
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);
                mTXVodPlayer.resume();
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null && playerInfo.isBegin) {
                mIvCover.setVisibility(View.GONE);
                TXCLog.i(TAG, "onPlayEvent, event begin, cover remove");
            }
        } else if (event < 0) {
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);

                String desc = null;
                switch (event) {
                    case TXLiveConstants.PLAY_ERR_GET_RTMP_ACC_URL_FAIL:
                        desc = "获取加速拉流地址失败";
                        break;
                    case TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND:
                        desc = "文件不存在";
                        break;
                    case TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL:
                        desc = "h265解码失败";
                        break;
                    case TXLiveConstants.PLAY_ERR_HLS_KEY:
                        desc = "HLS解密key获取失败";
                        break;
                    case TXLiveConstants.PLAY_ERR_GET_PLAYINFO_FAIL:
                        desc = "获取点播文件信息失败";
                        break;
                }
                TCUserMgr.getInstance().uploadLogs(TCConstants.ELK_ACTION_VOD_PLAY,
                        TCUserMgr.getInstance().getUserId(), event, desc, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i(TAG, "onFailure");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.i(TAG, "onResponse");
                            }
                        });
            }
            Toast.makeText(this, "event:" + event, Toast.LENGTH_SHORT).show();
        }
    }

    private void initPlayerSDK() {
        if (mVideoList.size() > 0) {
            mVerticalViewPager.setCurrentItem(0);
        }
    }

    private void restartPlay() {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.resume();
        }
    }

    @Override
    public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

    }

    class PlayerInfo {
        public TXVodPlayer txVodPlayer;
        public String playURL;
        public boolean isBegin;
        public View playerView;
        public int pos;
        public int reviewstatus;
    }

    class MyPagerAdapter extends PagerAdapter {

        ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();


        protected PlayerInfo instantiatePlayerInfo(int position) {
            TXCLog.d(TAG, "instantiatePlayerInfo " + position);

            PlayerInfo playerInfo = new PlayerInfo();
            TXVodPlayer vodPlayer = new TXVodPlayer(TecentVideoDemoActivity.this);
            vodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
            vodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
            vodPlayer.setVodListener(TecentVideoDemoActivity.this);
            TXVodPlayConfig config = new TXVodPlayConfig();
            config.setCacheFolderPath(Environment.getExternalStorageDirectory().getPath() +
                    "/txcache");
            config.setMaxCacheItems(5);
            vodPlayer.setConfig(config);
            vodPlayer.setAutoPlay(false);

            TCVideoInfo tcLiveInfo = mVideoList.get(position);
            playerInfo.playURL = TextUtils.isEmpty(tcLiveInfo.hlsPlayUrl) ? tcLiveInfo.playurl :
                    tcLiveInfo.hlsPlayUrl;
            playerInfo.txVodPlayer = vodPlayer;
            playerInfo.reviewstatus = tcLiveInfo.review_status;
            playerInfo.pos = position;
            playerInfoList.add(playerInfo);

            return playerInfo;
        }

        protected void destroyPlayerInfo(int position) {
            while (true) {
                PlayerInfo playerInfo = findPlayerInfo(position);
                if (playerInfo == null)
                    break;
                playerInfo.txVodPlayer.stopPlay(true);
                playerInfoList.remove(playerInfo);

                TXCLog.d(TAG, "destroyPlayerInfo " + position);
            }
        }

        public PlayerInfo findPlayerInfo(int position) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.pos == position) {
                    return playerInfo;
                }
            }
            return null;
        }

        public PlayerInfo findPlayerInfo(TXVodPlayer player) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.txVodPlayer == player) {
                    return playerInfo;
                }
            }
            return null;
        }

        public void onDestroy() {
            for (PlayerInfo playerInfo : playerInfoList) {
                playerInfo.txVodPlayer.stopPlay(true);
            }
            playerInfoList.clear();
        }

        @Override
        public int getCount() {
            return mVideoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TXCLog.i(TAG, "MyPagerAdapter instantiateItem, position = " + position);
            TCVideoInfo tcLiveInfo = mVideoList.get(position);

            View view =
                    LayoutInflater.from(container.getContext()).inflate(R.layout.view_player_content, null);
            view.setId(position);
            // 封面
            ImageView coverImageView = (ImageView) view.findViewById(R.id.player_iv_cover);
            if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_PORN) { //涉黄的图片不显示
                coverImageView.setImageResource(R.drawable.bg);
            } else {
                TCUtils.blurBgPic(TecentVideoDemoActivity.this, coverImageView,
                        tcLiveInfo.frontcover, R.drawable.bg);
            }
            // 头像
            CircleImageView ivAvatar = (CircleImageView) view.findViewById(R.id.player_civ_avatar);
            Glide.with(TecentVideoDemoActivity.this).load(tcLiveInfo.headpic).error(R.drawable.face).into(ivAvatar);
            // 姓名
            TextView tvName = (TextView) view.findViewById(R.id.player_tv_publisher_name);
            if (TextUtils.isEmpty(tcLiveInfo.nickname) || "null".equals(tcLiveInfo.nickname)) {
                tvName.setText(TCUtils.getLimitString(tcLiveInfo.userid, 10));
            } else {
                tvName.setText(tcLiveInfo.nickname);
            }
            TextView tvVideoReviewStatus =
                    (TextView) view.findViewById(R.id.tx_video_review_status);
            if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_NOT_REVIEW) {
                tvVideoReviewStatus.setVisibility(View.VISIBLE);
                tvVideoReviewStatus.setText(R.string.video_not_review);
            } else if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_PORN) {
                tvVideoReviewStatus.setVisibility(View.VISIBLE);
                tvVideoReviewStatus.setText(R.string.video_porn);
            } else if (tcLiveInfo.review_status == TCVideoInfo.REVIEW_STATUS_NORMAL) {
                tvVideoReviewStatus.setVisibility(View.GONE);
            }

            // 获取此player
            TXCloudVideoView playView =
                    (TXCloudVideoView) view.findViewById(R.id.player_cloud_view);
            PlayerInfo playerInfo = instantiatePlayerInfo(position);
            playerInfo.playerView = playView;
            playerInfo.txVodPlayer.setPlayerView(playView);

            if (playerInfo.reviewstatus == TCVideoInfo.REVIEW_STATUS_NORMAL) {
                playerInfo.txVodPlayer.startPlay(playerInfo.playURL);
            } else if (playerInfo.reviewstatus == TCVideoInfo.REVIEW_STATUS_NOT_REVIEW) { // 审核中
            } else if (playerInfo.reviewstatus == TCVideoInfo.REVIEW_STATUS_PORN) {       // 涉黄

            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            TXCLog.i(TAG, "MyPagerAdapter destroyItem, position = " + position);

            destroyPlayerInfo(position);

            container.removeView((View) object);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCVideoListMgr.getInstance().release();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onDestroy();
            mTXCloudVideoView = null;
        }
        if (mPagerAdapter != null) {
            mPagerAdapter.onDestroy();
        }
        stopPlay(true);
        mTXVodPlayer = null;

    }

    protected void stopPlay(boolean clearLastFrame) {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.stopPlay(clearLastFrame);
        }
    }
}
