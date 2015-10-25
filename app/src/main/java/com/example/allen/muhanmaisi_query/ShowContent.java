package com.example.allen.muhanmaisi_query;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.List;

public class ShowContent extends AppCompatActivity {

    private TextView textView;
    private int getNormalBegin(int chapter){
        return (chapter-1)*25;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);

        textView = (TextView)findViewById(R.id.textView);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String key = bundle.getString(TableShow.ChapterID);
                if (key != null) {
                    List<String> chineseList = MainActivity.chineseList;
                    List<String> arbicList = MainActivity.abricList;

                    int chapter = Integer.parseInt(key);
                    int begin = getNormalBegin(chapter);
                    StringBuilder builder = new StringBuilder();
                        for (int i = begin; i < begin+25 && i < chineseList.size(); ++i) {
                            builder.append(arbicList.get(i) + "\n");
                            builder.append(chineseList.get(i) + "\n");
                        }

                    textView.setText(builder.toString());
                    textView.setMovementMethod(new ScrollingMovementMethod());
                }
            }
        }
    }
}
