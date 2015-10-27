package com.example.allen.muhanmaisi_query;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allen on 15/10/27.
 */
public class GridAdapter extends BaseAdapter {
    private List<ChapterItem> items = new ArrayList<ChapterItem>();
    private Context context;
    public GridAdapter(Context context) {
        for (int i = 1; i <= 33; ++i) {
            ChapterItem item = new ChapterItem();
            item.chapterid = i;
            item.chapterName = "第" + i + "章";
            items.add(item);
        }

        this.context = context;
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int pos, View contentView, ViewGroup parent) {
        if (contentView == null) {
            contentView = LayoutInflater.from(context).inflate(R.layout.item, null);
        }

        TextView textView = (TextView)contentView.findViewById(R.id.squaretext);
        ChapterItem item = (ChapterItem)getItem(pos);
        textView.setText(item.chapterName);
        if (item.chapterid == TableShow.curChapter) {
            textView.setBackgroundColor(Color.GREEN);
        }
        return contentView;
    }
}
