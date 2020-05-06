package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.ZoomImageView;

public class DoubleActivity extends Activity {

    ImageView imageview;
    ImageView imageview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_double);

        ZoomImageView viewById = findViewById(R.id.ssss);

        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.start_logo);
        viewById.setImageBitmap(decodeResource);

    }

}
