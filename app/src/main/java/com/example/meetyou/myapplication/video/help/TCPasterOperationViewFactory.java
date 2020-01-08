package com.example.meetyou.myapplication.video.help;

import android.content.Context;
import android.view.View;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.video.view.PasterOperationView;

/**
 * Created by hanszhli on 2017/6/21.
 * <p>
 * 创建 OperationView的工厂
 */

public class TCPasterOperationViewFactory {

    public static PasterOperationView newOperationView(Context context) {
        return (PasterOperationView) View.inflate(context, R.layout.layout_paster_operation_view,
                null);
    }
}
