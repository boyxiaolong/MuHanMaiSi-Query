package com.example.allen.muhanmaisi_query;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowContent extends AppCompatActivity {

    private TextView textView;
    private int curPage = 0;
    private List<String> contentList = new ArrayList<>();
    private float curFontSize;
    static private SharedPreferences sharedPreferences;
    private final String fontKeyStr = "fontKeyStr";
    private int chapter = 0;

    enum Mode {
        NONE, DRAG, ZOOM
    }

    Mode mode = Mode.NONE;
    static final int MIN_FONT_SIZE = 20;
    static final int MAX_FONT_SIZE = 70;
    PointF start = new PointF();

    Point tvPos0 = new Point();
    Point tvPos1 = new Point();
    Point tvPosSave = new Point();

    float oldDist = 1f;
    int scrWidth;
    int scrHeight;

    private int getNormalBegin(int chapter){
        return (chapter-1)*25;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);

        textView = (TextView)findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        sharedPreferences = getSharedPreferences(fontKeyStr, Context.MODE_PRIVATE);

        curFontSize = sharedPreferences.getFloat(fontKeyStr, 20.0f);
        textView.setTextSize(curFontSize);
        textView.setOnTouchListener(new android.view.View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        start.set(event.getX(), event.getY());
                        tvPos0.set((int) event.getX(), (int) event.getY());
                        mode = Mode.DRAG;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                        if (oldDist > 10f) {
                            mode = Mode.ZOOM;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = Mode.NONE;
                        tvPos1.set(tvPosSave.x, tvPosSave.y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == Mode.ZOOM) {
                            doZoom(event);
                        }
                        break;
                }
                return true;
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String key = bundle.getString(TableShow.ChapterID);
                if (key != null) {
                    chapter = Integer.parseInt(key);

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

                    curPage = 0;
                    if (chapter == 1) {
                        curPage = -1;
                    }
                    showPageContent();
                }
            }
        }
    }

    private void showPageContent(){
        if (curPage == -1) {
            if (chapter == 1) {
                StringBuilder builder = new StringBuilder();
                for (int i = 6; i < MainActivity.beginPrayList.size(); ++i) {
                    builder.append(MainActivity.beginPrayList.get(i));
                }

                textView.setText(builder.toString() + "\n");
                return;
            }
            else {
                return;
            }
        }
        else if (curPage == 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 6; ++i) {
                builder.append(MainActivity.beginPrayList.get(i) + "\n");
            }

            textView.setText(builder.toString());
            return;
        }
        else if (curPage == 6){
            String res = MainActivity.explainList.get(chapter-1);

            textView.setText(res);
            return;
        }

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
        if (chapter == 1) {
            if (curPage == -1)
                return;
        }
        else if (curPage == 0) {
            return;
        }

        --curPage;
        showPageContent();
    }

    public void nextPage(View view) {
        if (curPage == 6){
            return;
        }
        ++curPage;
        showPageContent();
    }

    private void updateTextSize() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(fontKeyStr, curFontSize);
        editor.commit();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, curFontSize);
    }

    private void doZoom(MotionEvent event) {
        float newDist = spacing(event);
        if (newDist > 10f) {
            float scale = newDist / oldDist;
            if (scale > 1) {
                scale = 1.05f;
            } else if (scale < 1) {
                scale = 0.95f;
            }
            curFontSize = textView.getTextSize() * scale;
            if ((curFontSize < MAX_FONT_SIZE && curFontSize > MIN_FONT_SIZE)
                    || (curFontSize >= MAX_FONT_SIZE && scale < 1)
                    || (curFontSize <= MIN_FONT_SIZE && scale > 1)) {
                updateTextSize();
            }
        }
    }

    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        float res = x*x + y *y;
        return (float)Math.sqrt(res);
    }
}
