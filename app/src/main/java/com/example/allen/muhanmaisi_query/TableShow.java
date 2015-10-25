package com.example.allen.muhanmaisi_query;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TableShow extends AppCompatActivity {
    static public final String ChapterID = "ChapterID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TableLayout tableLayout = new TableLayout(getApplicationContext());
        TableRow tableRow;
        Button button;

        for (int i = 0; i < 11; i++) {
            tableRow = new TableRow(getApplicationContext());
            for (int j = 0; j < 3; j++) {
                button = new Button(getApplicationContext());
                int curid = i * 3 + j + 1;
                button.setText("第" + curid + "章");
                button.setPadding(20, 20, 20, 20);
                button.setId(curid);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button curBrn = (Button)v;
                        int id = curBrn.getId();

                        Intent intent = new Intent();
                        intent.setClass(TableShow.this, ShowContent.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(ChapterID, "" + id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                tableRow.addView(button);
            }
            tableLayout.addView(tableRow);
        }
        setContentView(tableLayout);
    }

}
