package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.CommonDialog1;
import com.example.meetyou.myapplication.view.ZoomImageView;

public class FullScreenDialogActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen_dialog);


        Button ssss = findViewById(R.id.btn);
        ssss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog1 commonDialog1 = new CommonDialog1(FullScreenDialogActivity.this);
//                commonDialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                commonDialog1.show();


//                Intent intent = new Intent(FullScreenDialogActivity.this, DoubleActivity.class);
//
//                startActivity(intent);
            }
        });



    }

}
