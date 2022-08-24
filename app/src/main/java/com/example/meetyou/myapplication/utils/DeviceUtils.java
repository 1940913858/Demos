//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.meetyou.myapplication.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

@SuppressLint({"DefaultLocale"})
public class DeviceUtils {
    private static final String TAG = "DeviceUtils";
    private static String strUA = "";
    private static String mCurrentUA;
    private static String mWebViewUA;
    private static String mIMSI;
    private static String mUUID;
    private static final int PORTRAIT = 0;
    private static final int LANDSCAPE = 1;
    private static int mDensityDpi;
    private static float mDensity;
    private static float mScaledDensity;
    private static String imsi;
    private static int phoneType = -1;
    private static String phoneModel;
    private static int mStatusBarHeight;

    public DeviceUtils() {
    }

    public static void setUserAgent(String userAgent) {
        strUA = userAgent;
    }

    public static String getUserAgent() {
        return strUA;
    }


    public static String getWebViewUserAgent(Context context) {
        if (mWebViewUA == null) {
            try {
                WebView webview = new WebView(context);
                mWebViewUA = webview.getSettings().getUserAgentString();
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        }

        return mWebViewUA;
    }


    /** @deprecated */
    @Deprecated
    public static String getAdvertsingId() {
        return "";
    }

    public static String getPhoneModel(Context context) {
        return Build.MODEL;
    }


    public static synchronized String getUUID(Context context) {
        if (mUUID == null) {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
            String identity = preference.getString("identity_android", (String)null);
            if (identity == null) {
                identity = UUID.randomUUID().toString();
                preference.edit().putString("identity_android", identity).apply();
            }

            mUUID = identity;
        }

        return mUUID;
    }

    public static int getScreenWidth(Context context) {
        try {
            return context.getResources().getDisplayMetrics().widthPixels;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 480;
        }
    }

    public static int getScreenHeight(Context context) {
        try {
            return context.getResources().getDisplayMetrics().heightPixels;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 800;
        }
    }

    public static float getDeviceDensity(Context context) {
        try {
            if (mDensity == 0.0F) {
                mDensity = context.getResources().getDisplayMetrics().density;
            }

            return mDensity;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 480.0F;
        }
    }

    public static int getDeviceDensityValue(Context context) {
        try {
            if (mDensityDpi == 0) {
                mDensityDpi = context.getResources().getDisplayMetrics().densityDpi;
            }

            return mDensityDpi;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 480;
        }
    }

    public static int dip2px(Context context, float dipValue) {
        if (mDensity == 0.0F) {
            mDensity = context.getResources().getDisplayMetrics().density;
        }

        return (int)(dipValue * mDensity + 0.5F);
    }

    public static int px2sp(Context context, float pxValue) {
        if (mScaledDensity == 0.0F) {
            mScaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        }

        return (int)(pxValue / mScaledDensity + 0.5F);
    }

    public static int sp2px(Context context, float spValue) {
        if (mScaledDensity == 0.0F) {
            mScaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        }

        return (int)(spValue * mScaledDensity + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        if (mDensity == 0.0F) {
            mDensity = context.getResources().getDisplayMetrics().density;
        }

        return (int)(pxValue / mDensity + 0.5F);
    }


    public static String getPhoneModel() {
        if (phoneModel == null) {
            phoneModel = Build.MODEL;
        }

        return phoneModel;
    }

    public static String getSystemVersion() {
        return VERSION.RELEASE;
    }

    public static String getTimeZoneName() {
        try {
            TimeZone tz = TimeZone.getDefault();
            return tz.getDisplayName(false, 0);
        } catch (Exception var1) {
            var1.printStackTrace();
            return "";
        }
    }

}
