package com.example.meetyou.myapplication;

import android.app.Application;

import com.tencent.rtmp.TXLiveBase;
import com.tencent.ugc.TXUGCBase;

public class DemoApplication extends Application {
    String ugcLicenceUrl = "http://license.vod2.myqcloud.com/license/v1/3af57afaec336d85245c684ab94c4d38/TXUgcSDK.licence"; //您从控制台申请的 licence url
    String ugcKey = "41c2bbd7503dd2f37e337f6469f9ff16";                                                                 //您从控制台申请的 licence key
    private static DemoApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        TXUGCBase.getInstance().setLicence(getBaseContext(), ugcLicenceUrl, ugcKey);

        //设置是否在 Android Studio 的控制台打印 SDK 的相关输出。
        TXLiveBase.getInstance().setConsoleEnabled(true);
    }

    public static DemoApplication getApplication() {
        return instance;
    }
}