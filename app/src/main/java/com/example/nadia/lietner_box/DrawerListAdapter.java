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
 * Created by Nadia on 11/5/2016.
 */

public class DrawerListAdapter extends ListViewAdapter {
    public DrawerListAdapter(List items) {
        super(items);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.drawer_lv_row, parent, false);
        return new BusinessListObjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        BusinessListObjectViewHolder viewHolder = (BusinessListObjectViewHolder)holder;
        DrawerListObject drawerListObject = (DrawerListObject)getItems().get(position);

        viewHolder.titleView.setText(drawerListObject.getTitle());
        viewHolder.tv_icon.setText(drawerListObject.getRightIcon());

    }

    public static class BusinessListObjectViewHolder extends ListViewHolder {

        public TextView titleView;
        public TextView tv_icon;

        public BusinessListObjectViewHolder(View itemView) {
            super(itemView);

            method _method = new method(itemView.getContext());

            titleView = (TextView)itemView.findViewById(R.id.tv_label);
            titleView.setTypeface(_method.GetTypeFace("IRANSansWeb.ttf"));
            tv_icon = (TextView)itemView.findViewById(R.id.tv_icon);
            tv_icon.setTypeface(_method.GetTypeFace("fontawesome-webfont.ttf"));

        }
    }

}
