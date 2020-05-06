package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.CommonDialog1;

public class FullScreenDialogActivity2 extends Activity {


    EditText edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen_layout);

        edittext = findViewById(R.id.edittext);

    }

}
