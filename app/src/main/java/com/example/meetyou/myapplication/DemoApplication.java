package com.example.meetyou.myapplication;

import android.app.Application;

import com.tencent.rtmp.TXLiveBase;
import com.tencent.ugc.TXUGCBase;

public class DemoApplication extends Application {
    private static DemoApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static DemoApplication getApplication() {
        return instance;
    }
}