package com.example.allen.muhanmaisi_query;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int LoadAllDataFinish = 1000;
    static List<String> chineseList = new ArrayList<String>();
    static List<String> abricList = new ArrayList<String>();
    static List<String> beginPrayList = new ArrayList<>();
    static List<String> explainList = new ArrayList<>();
    static MyHttpAsynClient httpAsynClient = new MyHttpAsynClient();

    private boolean isLoadFinsh = false;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MainActivity.LoadAllDataFinish:
                {
                    isLoadFinsh = true;
                }
                break;
            }
            super.handleMessage(msg);
        }
    };

    private void loadFirstFile() {
        for (int i = 1; i <= 33; ++i) {
            try {
                InputStream instream = getResources().openRawResource(
                        getResources().getIdentifier("raw/m" + i,
                                "raw", getPackageName()));
                InputStreamReader reader = new InputStreamReader(instream);
                BufferedReader breader = new BufferedReader(reader);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = breader.readLine()) != null) {
                    builder.append(str);
                }
                explainList.add(builder.toString());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
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
        MyHttpAsynClient.url = getResources().getString(R.string.ipstr);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        httpAsynClient.login("allen", "123456");

        String ss = getPackageName();

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.conver);

        Thread thread = new Thread() {
            @Override
            public void run() {
                loadFile(R.raw.chinese, chineseList);
                loadFile(R.raw.abric, abricList);
                loadFile(R.raw.begin, beginPrayList);
                loadFirstFile();
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
            if (isLoadFinsh == false) {
                return true;
            }

            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TableShow.class);
            startActivity(intent);
        }
        return true;
    }

}
