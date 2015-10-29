package com.example.allen.muhanmaisi_query;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TableShow extends AppCompatActivity {
    static public final String ChapterID = "ChapterID";
    static String dateString = "2015-10-25";
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static java.util.Date date;
    static final int lastChapter = 31;
    static public int curChapter;
    PopupWindow popupWindow;
    private int selectedChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_show);
        try {
            date = sdf.parse(dateString);
        }
        catch (java.text.ParseException e){

        }

        java.util.Date curDate = Calendar.getInstance().getTime();
        int diffInDays = (int) ((curDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));

        diffInDays %= 33;

        curChapter = lastChapter + diffInDays;
        curChapter %= 33;
        if (curChapter == 0) {
            curChapter = 33;
        }

        GridView gridView = (GridView)findViewById(R.id.gridview);
        try{
            gridView.setAdapter(new GridAdapter(this));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedChapter = position + 1;
                    if (selectedChapter == curChapter) {
                        showPopupWindow(view);
                    }
                    else {
                        toNextActivity(selectedChapter);
                    }
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void toNextActivity(int chapter) {
        Intent intent = new Intent();
        intent.setClass(TableShow.this, ShowContent.class);
        Bundle bundle = new Bundle();
        bundle.putString(ChapterID, "" + chapter);
        intent.putExtras(bundle);

        startActivity(intent);
    }
    private void showPopupWindow(View view) {
        if (popupWindow == null) {
            // 一个自定义的布局，作为显示的内容
            View contentView = LayoutInflater.from(this).inflate(
                    R.layout.pop_window1, null);
            // 设置按钮的点击事件
            popupWindow = new PopupWindow(contentView,
                    android.widget.AbsListView.LayoutParams.WRAP_CONTENT, android.widget.AbsListView.LayoutParams.WRAP_CONTENT, true);

            popupWindow.setTouchable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());

            ListView listView = (ListView)contentView.findViewById(R.id.poplistview);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            if (popupWindow.isShowing()) {
                                popupWindow.dismiss();
                            }
                            else {
                                popupWindow.showAsDropDown(view);
                            }
                            toNextActivity(selectedChapter);
                            break;
                        case 1:
                            break;
                    }
                }
            });
        }

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }
}
