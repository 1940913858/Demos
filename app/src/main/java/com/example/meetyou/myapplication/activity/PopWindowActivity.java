package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.poplayout.FloatingLayerViewCreator;
import com.example.meetyou.myapplication.poplayout.MyViewHelper;

import static java.security.AccessController.getContext;

public class PopWindowActivity extends Activity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pop);

         textView = findViewById(R.id.text);



        textView.post(new Runnable() {
            @Override
            public void run() {
                MyViewHelper.getInstance().showLightView(PopWindowActivity.this, textView, "第一个");
            }
        });


//        // 2. 显示提示用户操作的浮层
//        FloatingLayerViewCreator.create(this)
//                .setContentView(R.layout.activity_pop_example)
//                .setOnFloatStateListener(new FloatingLayerViewCreator.OnFloatStateListener() {
//                    @Override
//                    public void onShow() {
//                        //do nothing
//                    }
//
//                    @Override
//                    public void onClose() {
//
//                    }
//                }).show();


//        textView.post(new Runnable() {
//            @Override
//            public void run() {
//                showPopWindow();
//
//                darkenBackground(0.2f);
//            }
//        });
    }

    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }


    private GuidePopUpWindow popupWindow;
    private void showPopWindow() {
            String key = "has_video_entry_guide_shown";
//            boolean hashown = FileStoreProxy.getBooleanValue(key, false);
//            if (!hashown) {
                View vPopupWindow = getLayoutInflater().inflate(R.layout.layout_entry_c_pop, null, false);//引入弹窗布局
                popupWindow = new GuidePopUpWindow(vPopupWindow, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setFocusable(false);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAsDropDown(textView, -100, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //FileStoreProxy.setValue(key, true);
                        darkenBackground(1f);
                    }
                });
//                rlvideo_feeds_entry.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        popupWindow.realDismiss();
//                    }
//                }, 8000);
//            }

    }

    static class GuidePopUpWindow extends PopupWindow{

        public GuidePopUpWindow(View vPopupWindow, int wrapContent, int wrapContent1, boolean b) {
            super(vPopupWindow,wrapContent,wrapContent1,b);
        }

        @Override
        public void dismiss() {

        }
        public void realDismiss(){
            super.dismiss();
        }
    }
}
