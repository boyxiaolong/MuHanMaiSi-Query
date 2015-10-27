package com.example.allen.muhanmaisi_query;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TableShow extends AppCompatActivity {
    static public final String ChapterID = "ChapterID";
    static String dateString = "2015-10-25";
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static java.util.Date date;
    static final int lastChapter = 31;
    static public int curChapter;

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
                    Intent intent = new Intent();
                    intent.setClass(TableShow.this, ShowContent.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ChapterID, "" + (position+1));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
