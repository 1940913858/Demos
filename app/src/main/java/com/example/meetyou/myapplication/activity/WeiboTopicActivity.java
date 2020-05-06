package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.view.weibotopic.BookEntity;
import com.example.meetyou.myapplication.view.weibotopic.TEditText;
import com.example.meetyou.myapplication.view.weibotopic.TObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeiboTopicActivity extends Activity implements View.OnClickListener {
    /** 执行增加字符串的操作 */
    private Button button;
    private ArrayList<BookEntity> mList = new ArrayList<BookEntity>();
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weibo_topic);


        editText = (EditText) findViewById(R.id.edittext);
        editText.addTextChangedListener(new MyTextWatcher());

        /** 监听删除按键，执行删除动作 */
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) { //当为删除键并且是按下动作时执行
                    int selectionStart = editText.getSelectionStart();
                    int lastPos = 0;
                    for (int i = 0; i < mList.size(); i++) { //循环遍历整个输入框的所有字符
                        if ((lastPos = editText.getText().toString().indexOf(mList.get(i).getBookName(), lastPos)) != -1) {
                            if (selectionStart != 0 && selectionStart >= lastPos && selectionStart <= (lastPos + mList.get(i).getBookName().length())) {
                                String sss = editText.getText().toString();
                                editText.setText(sss.substring(0, lastPos) + sss.substring(lastPos + mList.get(i).getBookName().length())); //字符串替换，删掉符合条件的字符串
                                mList.remove(i); //删除对应实体
                                editText.setSelection(lastPos); //设置光标位置
                                return true;
                            }
                        } else {
                            lastPos += ("#" + mList.get(i).getBookName() + "#").length();
                        }
                    }
                }
                return false;
            }
        });



        button = (Button) findViewById(R.id.topic_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextInt = new Random().nextInt(100);
                String str = "#测试测试" + nextInt + "# ";
                editText.setText(editText.getText());
                editText.append(str);
                editText.setSelection(editText.getText().toString().length());
                mList.add(new BookEntity(str, nextInt+""));
            }
        });
    }

    class MyTextWatcher implements TextWatcher {
        @Override
        public synchronized void afterTextChanged(Editable s) {
            editText.removeTextChangedListener(this);
            int findPos = 0;
            int copyPos = 0;
            String sText = s.toString();
            List<Integer> spanIndexes = new ArrayList<Integer>();
            s.clear();
            for (int i = 0; i < mList.size(); i++) {
                String tempBookName = "#" + mList.get(i).getBookName() + "#";
                if ((findPos = sText.indexOf(tempBookName, findPos)) != -1) {
                    spanIndexes.add(findPos);//bookName 的开始索引，键值为偶数，从0开始
                    spanIndexes.add(findPos + tempBookName.length()); //bookName 的结束索引，键值为奇数，从1开始
                }
            }
            if (spanIndexes != null && spanIndexes.size() != 0) {
                for (int i = 0; i < spanIndexes.size(); i++) {
                    if (i % 2 == 0) {
                        s.append(sText.substring(copyPos, spanIndexes.get(i)));
                    } else {
                        Spanned htmlText = Html.fromHtml("<font color='blue'>" + sText.substring(copyPos, spanIndexes.get(i)) + "</font>");
                        s.append(htmlText);
                    }
                    copyPos = spanIndexes.get(i);
                }
                s.append(sText.substring(copyPos));
            } else {
                s.append(sText);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }

    @Override
    public void onClick(View v) {
    }
}
