package com.example.meetyou.myapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.meetyou.myapplication.R;

public class RequestPermissionActivity extends Activity {

    String[] permissions = {Manifest
            .permission.CAMERA, Manifest.permission
            .RECORD_AUDIO, Manifest
            .permission.READ_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permission);

        boolean checkPermissionsGroup = checkPermissionsGroup(permissions);

        if (!checkPermissionsGroup){
            requestPermission();
        }
    }

    public boolean checkPermissionsGroup(String[] permissions) {
        boolean result = false;
        for (String permission : permissions) {
            result = checkPermission(permission);
            if (!result) {
                return false;
            }
            Log.e("PermissionUtils", "result" + result);
        }
        return result;
    }

    public boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            return false;
        }

        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,permissions, 1);

    }

}
