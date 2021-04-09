package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;

import java.util.Arrays;


/**
 * 取图片上的主色
 */
public class MainColorActivity extends Activity {

    private static Intent getIntent(Context context, String content, String title) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra("content", content);
        intent.putExtra("dialog_title", title);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_color);
        getMainColor();
    }

    TextView t0;
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    TextView t6;

    public void getMainColor() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a111);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            //发生主线程    Palette调色板   总共六种颜色
            @Override
            public void onGenerated(Palette palette) {
                t0 = (TextView) findViewById(R.id.t0);
                t1 = (TextView) findViewById(R.id.t1);
                t2 = (TextView) findViewById(R.id.t2);
                t3 = (TextView) findViewById(R.id.t3);
                t4 = (TextView) findViewById(R.id.t4);
                t5 = (TextView) findViewById(R.id.t5);
                t6 = (TextView) findViewById(R.id.t6);

//                //柔和而暗的颜色
//                int darkMutedColor = palette.getDarkMutedColor(Color.RED);
//                //鲜艳和暗的颜色
//                int darkVibrantColor = palette.getDarkVibrantColor(Color.BLUE);
//                //亮和鲜艳的颜色
//                int lightVibrantColor = palette.getLightVibrantColor(Color.BLUE);
//                //亮和柔和的颜色
//                int lightMutedColor = palette.getLightMutedColor(Color.BLUE);
//                //柔和颜色
//                int mutedColor = palette.getMutedColor(Color.BLUE);
//                int vibrantColor = palette.getVibrantColor(Color.BLUE);
//
//
//
//
//                int dominantColor = palette.getDominantColor(0x000000);//主颜色
//                int alphaComponent = ColorUtils.setAlphaComponent(dominantColor, 180);//70%不透明
//                t0.setBackgroundColor(dominantColor);
//                //t1.setBackgroundColor(alphaComponent);
//
//                t1.setBackgroundColor(darkMutedColor);
//                t2.setBackgroundColor(darkVibrantColor);
//                t3.setBackgroundColor(lightVibrantColor);
//                t4.setBackgroundColor(lightMutedColor);
//                t5.setBackgroundColor(mutedColor);
//                t6.setBackgroundColor(vibrantColor);


                int dominantColor = palette.getDominantColor(Color.parseColor("#D9FFFFFF"));//主颜色

                int red = (dominantColor & 0xff0000) >> 16;
                int green = (dominantColor & 0x00ff00) >> 8;
                int blue = (dominantColor & 0x0000ff);

                float[] floats = rgb2hsb(red, green, blue);//hsb
                floats[1] = 0.9f;
                floats[2] = 0.5f;//明度设置为50

                //再转回rgb
                int[] ints = hsb2rgb(floats[0], floats[1], floats[2]);


                int  color = Color.argb(180,ints[0], ints[1], ints[2]);

                t1.setBackgroundColor(color);


            }
        });
    }


    public static float[] rgb2hsb(int rgbR, int rgbG, int rgbB) {
        assert 0 <= rgbR && rgbR <= 255;
        assert 0 <= rgbG && rgbG <= 255;
        assert 0 <= rgbB && rgbB <= 255;
        int[] rgb = new int[] { rgbR, rgbG, rgbB };
        Arrays.sort(rgb);
        int max = rgb[2];
        int min = rgb[0];

        float hsbB = max / 255.0f;
        float hsbS = max == 0 ? 0 : (max - min) / (float) max;

        float hsbH = 0;
        if (max == rgbR && rgbG >= rgbB) {
            hsbH = (rgbG - rgbB) * 60f / (max - min) + 0;
        } else if (max == rgbR && rgbG < rgbB) {
            hsbH = (rgbG - rgbB) * 60f / (max - min) + 360;
        } else if (max == rgbG) {
            hsbH = (rgbB - rgbR) * 60f / (max - min) + 120;
        } else if (max == rgbB) {
            hsbH = (rgbR - rgbG) * 60f / (max - min) + 240;
        }

        return new float[] { hsbH, hsbS, hsbB };
    }

    public static int[] hsb2rgb(float h, float s, float v) {
        assert Float.compare(h, 0.0f) >= 0 && Float.compare(h, 360.0f) <= 0;
        assert Float.compare(s, 0.0f) >= 0 && Float.compare(s, 1.0f) <= 0;
        assert Float.compare(v, 0.0f) >= 0 && Float.compare(v, 1.0f) <= 0;

        float r = 0, g = 0, b = 0;
        int i = (int) ((h / 60) % 6);
        float f = (h / 60) - i;
        float p = v * (1 - s);
        float q = v * (1 - f * s);
        float t = v * (1 - (1 - f) * s);
        switch (i) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            case 5:
                r = v;
                g = p;
                b = q;
                break;
            default:
                break;
        }
        return new int[] { (int) (r * 255.0), (int) (g * 255.0),
                (int) (b * 255.0) };
    }


}
