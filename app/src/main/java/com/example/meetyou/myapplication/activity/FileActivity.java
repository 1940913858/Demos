package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.example.meetyou.myapplication.R;

import java.io.File;

public class FileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_topic);

            File sdcardDir = getExternalFilesDir(null);
//            if (sdcardDir != null) {
//                mPasterSDcardFolder =
//                        sdcardDir + File.separator + PASTER_FOLDER_NAME + File.separator;
//            }

        getDiskCacheDir(this,"");



    }

    private static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }



}
