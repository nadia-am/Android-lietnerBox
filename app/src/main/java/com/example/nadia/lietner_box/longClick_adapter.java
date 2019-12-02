package com.example.nadia.lietner_box;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nadia.lietner_box.lib.method;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;

import java.util.List;

/**
 * Created by Nadia on 12/7/2016.
 */

public class longClick_adapter extends ListViewAdapter {
    public longClick_adapter(List items) {
        super(items);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.long_click, parent, false);
        return new longclick_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        longclick_viewHolder viewHolder = (longclick_viewHolder)holder;
        String settingTitle = (String)getItems().get(position);
        viewHolder.longclick.setText(settingTitle);
    }

    public static class longclick_viewHolder extends ListViewHolder {

        TextView longclick;

        public longclick_viewHolder(View itemView) {
            super(itemView);
            method _method = new method(itemView.getContext());
            longclick = (TextView)itemView.findViewById(R.id.lv_tv_longclick);
            longclick.setTypeface(_method.GetTypeFace("IRANSansWeb.ttf"));
        }
    }
}