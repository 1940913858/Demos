package com.example.meetyou.myapplication.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.adapter.ChatListAdapter;
import com.example.meetyou.myapplication.bean.ChatList;
import com.example.meetyou.myapplication.bean.PhotoModel;

import java.util.ArrayList;
import java.util.List;

public class WxEmojiActivity extends Activity implements View.OnClickListener {

    LinearLayout ll_emotion_layout;
    EditText bar_edit_text;
    private InputMethodManager mInputManager;//软键盘管理类

    private SharedPreferences sp;

    private static final String SHARE_PREFERENCE_NAME = "EmotionKeyboard";
    private static final String SHARE_PREFERENCE_SOFT_INPUT_HEIGHT = "soft_input_height";

    public static final int _all = 0;
    public static final int Chat = 1;

    RecyclerView recyclerview;
    private List<ChatList> list = new ArrayList<>();
    private ChatListAdapter chatListAdapter;

    // 放置图片的控件
    private int[] mImageIds = new int[]{R.id.publish_iv1, R.id.publish_iv2, R.id.publish_iv3,R.id.publish_iv4};
    private ImageView[] mImageViews = new ImageView[mImageIds.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wx_emoji);

        Button bar_btn_send = findViewById(R.id.bar_btn_send);
        bar_btn_send.setOnClickListener(this);

        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        sp = getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);


        ll_emotion_layout = findViewById(R.id.ll_emotion_layout);
        bar_edit_text = findViewById(R.id.bar_edit_text);

        ImageView bar_image_add_btn = findViewById(R.id.bar_image_add_btn);


        bar_image_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_emotion_layout.isShown()) {
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout(true);//隐藏表情布局，显示软件盘
                    unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                } else {
                    if (isSoftInputShown()) {//同上
                        lockContentHeight();
                        showEmotionLayout();
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout();//两者都没显示，直接显示表情布局
                    }
                }
            }
        });

        bar_edit_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && ll_emotion_layout.isShown()) {
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout(true);//隐藏表情布局，显示软件盘

                    //软件盘显示后，释放内容高度
                    bar_edit_text.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            unlockContentHeightDelayed();
                        }
                    }, 200L);
                }
                return false;
            }
        });


        initData();
        showRecycler();

        initPhotoView();
        resetImageViewList();
    }

    public static int getScreenWidth(Context context) {
        try {
            return  context.getResources().getDisplayMetrics().widthPixels;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 480;
    }

    /**
     * 初始化添加图片的功能
     */
    private void initPhotoView() {
        int mIconHeight = (getScreenWidth(this) - (int) 30 * 6) / 4;
        for (int i = 0; i < mImageViews.length; i++) {
            mImageViews[i] = (ImageView) findViewById(mImageIds[i]);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mImageViews[i].getLayoutParams();
            layoutParams.height = mIconHeight;
            layoutParams.width = mIconHeight;
            mImageViews[i].requestLayout();
        }
        for (int i = 0; i < mImageViews.length; i++) {
            mImageViews[i] = (ImageView) findViewById(mImageIds[i]);
            mImageViews[i].setOnClickListener(this);
        }
    }

    // 存放图片路径
    private List<String> mPictureFilePaths = new ArrayList<>();
    private List<PhotoModel> listPhotoSelected = new ArrayList<>();
    private int mLimitCount = 4;

    private void resetImageViewList() {
        for (int i = 0; i < mImageViews.length; i++) {
            if (i > listPhotoSelected.size() || i >= mLimitCount) {
                mImageViews[i].setVisibility(View.GONE);
            } else if (i == listPhotoSelected.size()) {
                mImageViews[i].setVisibility(View.VISIBLE);
                mImageViews[i].setImageBitmap(null);
                if (listPhotoSelected.size() == 0) {
                    mImageViews[i].setBackgroundResource(R.drawable.apk_all_photo_add_camera_selector);
                } else {
                    mImageViews[i].setBackgroundResource(R.drawable.apk_all_photo_add_selector);
                }

            } else {
                mImageViews[i].setVisibility(View.VISIBLE);
                PhotoModel photoModel = listPhotoSelected.get(i);
                Context context = getApplicationContext();
//                ImageLoadParams imageLoadParams = new ImageLoadParams();
//                imageLoadParams.width = mIconHeight;
//                imageLoadParams.height = mIconHeight;

                if (!TextUtils.isEmpty(photoModel.UrlThumbnail)) {
                    Glide
                            .with(context)
                            .load(listPhotoSelected.get(i).UrlThumbnail)
                            //禁止缓存
                            .skipMemoryCache(true)
                            .error(R.mipmap.ic_launcher)
                            .into(mImageViews[i]);
//                    ImageLoader.getInstance()
//                            .displayImage(context, mImageViews[i], listPhotoSelected.get(i).UrlThumbnail, imageLoadParams, null);
                } else if (!TextUtils.isEmpty(photoModel.Url)) {
                    Glide
                            .with(context)
                            .load(listPhotoSelected.get(i).Url)
                            //禁止缓存
                            .skipMemoryCache(true)
                            .error(R.mipmap.ic_launcher)
                            .into(mImageViews[i]);
//                    ImageLoader.getInstance()
//                            .displayImage(context, mImageViews[i], listPhotoSelected.get(i).Url, imageLoadParams, null);
                } else {
                    mImageViews[i].setBackgroundDrawable(null);
                }
            }
        }
    }


    public void showRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setStackFromEnd(true); //关键

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(linearLayoutManager);
        chatListAdapter = new ChatListAdapter(this, list, Chat);
        recyclerview.setAdapter(chatListAdapter);
        recyclerview.smoothScrollToPosition(list.size() - 1);

        chatListAdapter.setItemClickListener(new ChatListAdapter.MyItemClickListener() {

            @Override
            public void onItemChatImage(View view, int position) {
                //UIHelper.showMySetUpFragment(ActivityChatList.this);
            }

            @Override
            public void onItemChatPlayer(View view, int position) {
                Log.i("image", "222222");

            }

            @Override
            public void onItemChatHead(View view, int position) {
                Log.i("image", "111111");
            }

            @Override
            public void onItemChatVoice(View view, int position) {
            }
        });
    }

    private ChatList.Builder builder = new ChatList.Builder();
    private ChatList chatList;

    public void initData() {
        for (int i = 0; i < 17; i++) {
            if (i == 0) {
                chatList = builder.Types("Receive").MultipleOptions("Text").Time("09:55:01")
                        .Content("广东省根深蒂固十点半").build();
            } else if (i == 1) {
                chatList = builder.Types("SendOut").MultipleOptions("Text").Time("7月08日 " +
                        "09:55:01").Content("时的gas的gas的高大上v阿萨德").build();
            } else if (i == 2) {
//                chatList = builder.Types("Receive").MultipleOptions("Image").Time("7月08日 " +
//                        "09:55:01").Images("https://p0.meituan.net/wedding/7022680e0992f71a8f1b1b6fafe82b0d796361.jpg%40800w_600h_0e_1l%7Cwatermark%3D1%26%26r%3D1%26p%3D9%26x%3D2%26y%3D2%26relative%3D1%26o%3D20").build();
            } else if (i == 3) {
//                chatList = builder.Types("SendOut").MultipleOptions("Image").Time("7月08日 " +
//                        "09:55:01").Images("https://p0.meituan.net/wedding/67fe3675108eeb20b1553fd51a99c3e3515115.jpg%40640w_480h_0e_1l%7Cwatermark%3D0").build();
            } else if (i == 4) {
                chatList = builder.Types("Receive").MultipleOptions("Text").Time("7月08日 " +
                        "09:55:01").Content("HAHASTEASDG").build();
            } else if (i == 5) {
                chatList = builder.Types("SendOut").MultipleOptions("Text").Time("7月08日 " +
                        "09:55:01").Content("你好").build();
            } else if (i == 6) {
                ArrayList arrayList = new ArrayList();
                arrayList.clear();
                arrayList.add("https://p1.meituan.net/wedding/b7820e6fde3834aeee88e920ef744c81594939.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                arrayList.add("https://p1.meituan.net/wedding/b7820e6fde3834aeee88e920ef744c81594939.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                arrayList.add("https://p1.meituan.net/wedding/b7820e6fde3834aeee88e920ef744c81594939.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");

                chatList = builder.Types("SendOut").MultipleOptions("Image").Time("09:55:01")
                        .Images(arrayList)
                        .build();
            } else if (i == 7) {
                chatList = builder.Types("Receive").MultipleOptions("Text").Time("09:55:01")
                        .Content("GGDGAEG").build();
            } else if (i == 8) {
                ArrayList arrayList = new ArrayList();
                arrayList.clear();
                arrayList.add("https://p1.meituan.net/wedding/49ea2a65e4e543e882247e68a0d314fc652247.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                arrayList.add("https://p1.meituan.net/wedding/b7820e6fde3834aeee88e920ef744c81594939.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                arrayList.add("https://p1.meituan.net/wedding/b7820e6fde3834aeee88e920ef744c81594939.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                chatList = builder.Types("SendOut").MultipleOptions("Image").Time("12:55:01")
                        .Images(arrayList)
                        .build();
            } else if (i == 9) {
                chatList = builder.Types("Receive").MultipleOptions("Text").Time("19:55:01")
                        .Content("FASDFASDFASDFSDA").build();
            } else if (i == 10) {
                ArrayList arrayList = new ArrayList();
                arrayList.clear();
                arrayList.add("https://p1.meituan.net/wedding/49ea2a65e4e543e882247e68a0d314fc652247.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                chatList = builder.Types("SendOut").MultipleOptions("Image").Time("19:55:01")
                        .Images(arrayList)
                        .build();
            } else if (i == 11) {
//                chatList = builder.Types("SendOut").MultipleOptions("Image").Time("19:55:01")
//                        .Images("https://p0.meituan.net/wedding/19a835c73c73a101eaf39d7b3036a2a4434229.jpg%40640w_480h_0e_1l%7Cwatermark%3D0")
//                        .build();
            } else if (i == 12) {
//                chatList = builder.Types("Receive").MultipleOptions("Image").Time("19:55:01")
//                        .Images("https://p1.meituan.net/wedding/10d80174aea2ddec4c1657810b34b8f8301615.jpg%40740w_2048h_0e_1l%7Cwatermark%3D1%26%26r%3D1%26p%3D9%26x%3D2%26y%3D2%26relative%3D1%26o%3D20")
//                        .build();
            } else if (i == 13) {
//                chatList = builder.Types("SendOut").MultipleOptions("Image").Time("19:55:01")
//                        .Images("https://p0.meituan.net/wedding/db8bde12e0a8c010a1472a295fee3de2312449.jpg%40740w_2048h_0e_1l%7Cwatermark%3D1%26%26r%3D1%26p%3D9%26x%3D2%26y%3D2%26relative%3D1%26o%3D20")
//                        .build();
            } else if (i == 16) {
                ArrayList arrayList = new ArrayList();
                arrayList.clear();
                arrayList.add("https://p1.meituan.net/wedding/49ea2a65e4e543e882247e68a0d314fc652247.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                arrayList.add("https://p1.meituan.net/wedding/b7820e6fde3834aeee88e920ef744c81594939.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                chatList = builder.Types("SendOut").MultipleOptions("Image").Time("19:55:01")
                        .Images(arrayList)
                        .build();
            } else {
                ArrayList arrayList = new ArrayList();
                arrayList.clear();
                arrayList.add("https://p1.meituan.net/wedding/49ea2a65e4e543e882247e68a0d314fc652247.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                arrayList.add("https://p1.meituan.net/wedding/b7820e6fde3834aeee88e920ef744c81594939.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                arrayList.add("https://p1.meituan.net/wedding/e1a63ac4a90cb3535c7aa31c4573d148380618.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                arrayList.add("https://p1.meituan.net/wedding/e1a63ac4a90cb3535c7aa31c4573d148380618.jpg%40640w_480h_0e_1l%7Cwatermark%3D0");
                chatList = builder.Types("Receive").MultipleOptions("Image").Time("19:55:01")
                        .Images(arrayList)
                        .build();
            }
            list.add(chatList);
        }
    }

    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerview
// .getLayoutParams();
//        params.height = recyclerview.getHeight();
//        params.weight = 0.0F;
    }

    /**
     * 释放被锁定的内容高度
     */
    private void unlockContentHeightDelayed() {
//        bar_edit_text.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ((LinearLayout.LayoutParams) recyclerview.getLayoutParams()).weight = 1.0F;
//            }
//        }, 200L);
    }

    private void showEmotionLayout() {
        int softInputHeight = getSupportSoftInputHeight();
        if (softInputHeight == 0) {
            softInputHeight = getKeyBoardHeight();
        }
        hideSoftInput();
//        ll_emotion_layout.getLayoutParams().height = 400;
//        ll_emotion_layout.setVisibility(View.VISIBLE);

        ll_emotion_layout.postDelayed(new Runnable() {//延迟弹出，否则界面会跳变
            @Override
            public void run() {
                ll_emotion_layout.setVisibility(View.VISIBLE);
            }
        }, 200L);
    }

    /**
     * 隐藏软件盘
     */
    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(bar_edit_text.getWindowToken(), 0);
    }

    /**
     * 获取软键盘高度，由于第一次直接弹出表情时会出现小问题，787是一个均值，作为临时解决方案
     *
     * @return
     */
    public int getKeyBoardHeight() {
        return sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, 787);

    }

    /**
     * 获取软件盘的高度
     *
     * @return
     */
    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        /**
         * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
         * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
         */
        getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕的高度
        int screenHeight = getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - r.bottom;

        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of
            // softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }

        if (softInputHeight < 0) {
            //LogUtils.w("EmotionKeyboard--Warning: value of softInputHeight is below zero!");
        }
        //存一份到本地
        if (softInputHeight > 0) {
            sp.edit().putInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, softInputHeight).apply();
        }
        return softInputHeight;
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 是否显示软件盘
     *
     * @return
     */
    private boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    /**
     * 隐藏表情布局
     *
     * @param showSoftInput 是否显示软件盘
     */
    private void hideEmotionLayout(boolean showSoftInput) {
        if (ll_emotion_layout.isShown()) {
            ll_emotion_layout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    /**
     * 编辑框获取焦点，并显示软件盘
     */
    private void showSoftInput() {
        bar_edit_text.requestFocus();
        bar_edit_text.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(bar_edit_text, 0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bar_btn_send) {
            if (bar_edit_text != null && bar_edit_text.getText().length() > 0) {
                chatList = builder.Types("SendOut").MultipleOptions("Text").Time("09:55:01")
                        .Content(bar_edit_text.getText().toString()).build();
                chatListAdapter.addData(chatList);
                recyclerview.smoothScrollToPosition(list.size() - 1);
                bar_edit_text.getText().clear();
            }
        }else{
            handleClickImage(v);
        }
    }

    private void handleClickImage(View v) {
        try {
            for (int i = 0; i < mImageIds.length; i++) {
                if (v.getId() == mImageIds[i]) {
                    if (listPhotoSelected.size() >= i + 1) {//已经有照片了
//                        int size = listPhotoSelected.size();
//                        List<PreviewImageModel> listPrev = new ArrayList<PreviewImageModel>();
//                        for (int j = 0; j < size; j++) {
//                            PreviewImageModel model = new PreviewImageModel();
//                            model.bLoaded = false;
//                            model.strPathName = listPhotoSelected.get(j).Url;
//                            listPrev.add(model);
//                        }
//                        LogUtils.i(TAG, "进入预览页面：" + listPrev.size());
//                        PreviewImageActivity.enterActivity(
//                                FeedBackActivity.this, true, false,
//                                PreviewImageActivity.MODE_DELETE, listPrev, i,
//                                new PreviewImageActivity.OnOperationListener() {
//                                    @Override
//                                    public void onDelete(int position) {
//                                        try {
//                                            LogUtils.i(TAG, "------->onDelete position:" + position);
//                                            listPhotoSelected.remove(position);
//                                            mPictureFilePaths.remove(position);
//                                            resetImageViewList();
//                                        } catch (Exception ex) {
//                                            ex.printStackTrace();
//                                        }
//
//                                    }
//                                });
                    } else {
                        getPhoto();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取图片
     */
    private void getPhoto() {
            Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
            // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
            intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
            startActivityForResult(intentToPickPic, 1);


//        PhotoConfig photoConfig=new PhotoConfig(3, false, UserController
//                .getInstance()
//                .getUserId(getApplicationContext()), "feedback");
//        photoConfig.setComeFrom("帮助与反馈");
//        PhotoActivity.enterActivity(getApplicationContext(), listPhotoSelected, photoConfig, new OnSelectPhotoListener() {
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onResultSelect(List<PhotoModel> listSelected) {
//                if (listSelected == null || listSelected.size() == 0)
//                    return;
//                listPhotoSelected.clear();
//                listPhotoSelected.addAll(listSelected);
//                resetImageViewList();
//            }
//
//            @Override
//            public void onResultSelectCompressPath(final List<String> compressPath) {
//                if (compressPath == null || compressPath.size() == 0)
//                    return;
//                for (int i = 0; i < compressPath.size(); i++) {
//                    String pathname = compressPath.get(i);
//                    listPhotoSelected.get(i).UrlThumbnail = pathname;
//                    listPhotoSelected.get(i).compressPath = pathname;
//                }
//                mPictureFilePaths.clear();
//                mPictureFilePaths.addAll(compressPath);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
                // 获取图片
                try {
                    //该uri是上一个Activity返回的
                    Uri imageUri = data.getData();

                    PhotoModel photoModel = new PhotoModel();
                    photoModel.Id = System.currentTimeMillis();
                    photoModel.Url = imageUri.toString();
                    photoModel.UrlThumbnail = "";

                    listPhotoSelected.add(photoModel);

                    resetImageViewList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

    }
}
