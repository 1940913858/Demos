package com.example.meetyou.myapplication;

import android.app.Application;

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