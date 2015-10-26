package com.example.allen.muhanmaisi_query;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowContent extends AppCompatActivity {

    private TextView textView;
    private int curPage = 0;
    private List<String> contentList = new ArrayList<>();
    private int curFontSize = 20;

    private int getNormalBegin(int chapter){
        return (chapter-1)*25;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);

        textView = (TextView)findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String key = bundle.getString(TableShow.ChapterID);
                if (key != null) {
                    int chapter = Integer.parseInt(key);

                    String audioEnd;
                    MediaPlayer player = new MediaPlayer();
                    if (chapter < 10) {
                        audioEnd = "0" + chapter;
                    }
                    else {
                        audioEnd = "" + chapter;
                    }

                    String path = "android.resource://"+getPackageName()+"/raw/muhanmaisi" + audioEnd;
                    try {
                        player.setDataSource(getApplicationContext(), Uri.parse(path));
                        player.prepare();
                        player.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    List<String> chineseList = MainActivity.chineseList;
                    List<String> arbicList = MainActivity.abricList;

                    int begin = getNormalBegin(chapter);
                    StringBuilder builder = new StringBuilder();
                    for (int i = begin; i < begin+25 && i < chineseList.size(); ++i) {
                        contentList.add(arbicList.get(i) + "\n\n" + chineseList.get(i) + "\n");
                    }

                    curPage = 1;
                    showPageContent();
                }
            }
        }
    }

    private void showPageContent(){
        int begin = (curPage-1)*5;
        int end = begin + 5;
        StringBuilder builder = new StringBuilder();

        builder.append("第"+curPage+"换\n");
        for (int i = begin; i < end && i < contentList.size(); ++i){
            builder.append(contentList.get(i));
        }

        textView.setText(builder.toString());
    }

    private float x1,x2;
    private final float Min_Distance = 150;

    public void prePage(View view) {
        if (curPage == 1) {
            return;
        }
        --curPage;
        showPageContent();
    }

    public void nextPage(View view) {
        if (curPage == 5){
            return;
        }
        ++curPage;
        showPageContent();
    }

    public void zoomout(View view) {
        curFontSize += 2;
        textView.setTextSize(curFontSize);
    }
    public void zoomin(View view) {
        curFontSize -= 2;
        textView.setTextSize(curFontSize);
    }
}
