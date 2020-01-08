package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.AutoScrollTextView;
import com.example.meetyou.myapplication.view.AutoScrollTextViewMeiyou;

import java.util.ArrayList;
import java.util.List;

public class AutoScrollTxtActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String packageName = "com.menstrual.menstrualcycle";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", packageName);
            try {
                int uid = getPackageManager().getPackageUid(packageName,PackageManager.GET_ACTIVITIES);
                intent.putExtra("app_uid", uid);
                startActivity(intent);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                //jumpToAppDetailSettingIntent(context,packageName);
            }
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName);
            startActivity(intent);
            //jumpToAppDetailSettingIntent(context, packageName);
        }else{
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName);
            startActivity(intent);
        }

        setContentView(R.layout.activity_auto_scroll_text);

        AutoScrollTextViewMeiyou hehe = findViewById(R.id.hehe);
        hehe.setText("哈哈哈哈哈");
        hehe.startScroll();


        List textList = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            textList.add("哈哈" + i);
        }

        final AutoScrollTextView vertical = findViewById(R.id.vertical);
        TextView textView = vertical.makeView();
        textView.setTextColor(Color.RED);


        vertical.setTextList(textList);
//        vertical.setText("哈哈哈哈哈");
        vertical.startAutoScroll(false);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                vertical.setTextColor();
//
//            }
//        },2000);


    }

    private void jumpToNotifySetting(){
        try {
            // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, getApplicationInfo().uid);

            //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
            intent.putExtra("app_package", getPackageName());
            intent.putExtra("app_uid", getApplicationInfo().uid);

            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
            Intent intent = new Intent();

            //下面这种方案是直接跳转到当前应用的设置界面。
            //https://blog.csdn.net/ysy950803/article/details/71910806
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    /**
     * 作者：CnPeng
     * 时间：2018/7/12 上午9:02
     * 功用：检查是否已经开启了通知权限
     * 说明：
     */
    private void checkNotifySetting() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        boolean isOpened = manager.areNotificationsEnabled();

//        if (isOpened) {
//            mBinding.tvMsg.setText("通知权限已经被打开" +
//                    "\n手机型号:" + android.os.Build.MODEL +
//                    "\nSDK版本:" + android.os.Build.VERSION.SDK +
//                    "\n系统版本:" + android.os.Build.VERSION.RELEASE +
//                    "\n软件包名:" + getPackageName());
//
//        } else {
//            mBinding.tvMsg.setText("还没有开启通知权限，点击去开启");
//        }
    }


}
