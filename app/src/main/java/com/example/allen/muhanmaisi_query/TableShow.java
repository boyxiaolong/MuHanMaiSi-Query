package com.example.allen.muhanmaisi_query;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TableShow extends AppCompatActivity {
    static public final String ChapterID = "ChapterID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_show);

    }

    public void onChickFirst(View view){
        Intent intent = new Intent();
        intent.setClass(TableShow.this, ShowContent.class);
        Bundle bundle = new Bundle();
        bundle.putString(ChapterID, "1");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
