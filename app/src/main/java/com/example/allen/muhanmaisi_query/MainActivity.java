package com.example.allen.muhanmaisi_query;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int LoadAllDataFinish = 1000;
    static List<String> chineseList = new ArrayList<String>();
    static List<String> abricList = new ArrayList<String>();
    private boolean isLoadFinsh = false;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MainActivity.LoadAllDataFinish:
                {
                    isLoadFinsh = true;
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, TableShow.class);
                    startActivity(intent);
                }
                break;
            }
            super.handleMessage(msg);
        }
    };

    private void loadFile(int fileid, List<String> arr) {
        try{
            InputStream myInput = getResources().openRawResource(fileid);
            InputStreamReader reader = new InputStreamReader(myInput);
            BufferedReader breader = new BufferedReader(reader);
            String str;
            while ((str = breader.readLine()) != null) {
                if (str.matches(".*\\d+.*")) {
                    continue;
                }
                arr.add(str);
            }

            reader.close();

        }catch (Exception e) {
            e.getStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String ss = getPackageName();

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.muhanmaisi);

        Thread thread = new Thread() {
            @Override
            public void run() {
                loadFile(R.raw.chinese, chineseList);
                loadFile(R.raw.abric, abricList);

                Message msg = new Message();
                msg.what = MainActivity.LoadAllDataFinish;

                MainActivity.this.handler.sendMessageDelayed(msg, 500);
            }
        };

        thread.start();
    }

    private int count = 0;
    private long firClick = 0;
    private long secClick = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(MotionEvent.ACTION_DOWN == event.getAction()){
            count++;
            if(count == 1){
                firClick = System.currentTimeMillis();

            } else if (count == 2){
                secClick = System.currentTimeMillis();
                if(secClick - firClick < 1000){
                    //双击事件
                    if (isLoadFinsh == false) {
                        return true;
                    }
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, TableShow.class);
                    startActivity(intent);
                }
                count = 0;
                firClick = 0;
                secClick = 0;
            }
        }
        return true;
    }

}
