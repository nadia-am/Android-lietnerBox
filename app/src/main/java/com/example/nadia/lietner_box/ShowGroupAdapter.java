package com.example.nadia.lietner_box;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nadia.lietner_box.lib.method;
import com.example.nadia.lietner_box.models.group;
import com.example.nadia.lietner_box.models.groupWithCards;
import com.telerik.widget.list.ListViewAdapter;
import com.telerik.widget.list.ListViewHolder;

import java.util.List;

/**
 * Created by Nadia on 12/5/2016.
 */

public class ShowGroupAdapter extends ListViewAdapter {
    public ShowGroupAdapter(List items) {
        super(items);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mylist, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        myViewHolder viewHolder = (myViewHolder)holder;
        groupWithCards myGroup = (groupWithCards)getItems().get(position);
        String type=myGroup.getType();
        //boolean type=myGroup.getType();
        if (!(type.equals("folder"))){
            viewHolder.listIcon.setImageResource(R.drawable.file);

            viewHolder.allCard.setVisibility(View.VISIBLE);
            viewHolder.allCard.setText("تعداد کل کارتها:");

            viewHolder.allCardNum.setVisibility(View.VISIBLE);
            viewHolder.allCardNum.setText(myGroup.getAllCardNumber().toString());

            viewHolder.reviewable.setVisibility(View.VISIBLE);
            viewHolder.reviewable.setText("تعدا کارتهای قابل مرور:");

            viewHolder.reviewableNum.setVisibility(View.VISIBLE);
            viewHolder.reviewableNum.setText(myGroup.getReviewableCard().toString());
        }else {
            viewHolder.listIcon.setImageResource(R.drawable.folder);
            viewHolder.allCard.setVisibility(View.GONE);
            viewHolder.allCardNum.setVisibility(View.GONE);
            viewHolder.reviewable.setVisibility(View.GONE);
            viewHolder.reviewableNum.setVisibility(View.GONE);
        }

        viewHolder.groupName.setText(myGroup.getGroupName());

    }

    public static class myViewHolder extends ListViewHolder {
        TextView groupName;
        ImageView listIcon;
        TextView allCard;
        TextView allCardNum;
        TextView reviewable;
        TextView reviewableNum;
        public myViewHolder(View itemView) {
            super(itemView);
            method _method = new method(itemView.getContext());
            groupName = (TextView)itemView.findViewById(R.id.label);
            groupName.setTypeface(_method.GetTypeFace("IRANSansWeb.ttf"));

            listIcon = (ImageView)itemView.findViewById(R.id.icon);

            allCard=(TextView)itemView.findViewById(R.id.allCard);
            allCard.setTypeface(_method.GetTypeFace("IRANSansWeb.ttf"));

            allCardNum=(TextView)itemView.findViewById(R.id.allCardNum);
            allCardNum.setTypeface(_method.GetTypeFace("BNAZANIN.TTF"));

            reviewable=(TextView)itemView.findViewById(R.id.reviewable);
            reviewable.setTypeface(_method.GetTypeFace("IRANSansWeb.ttf"));

            reviewableNum=(TextView)itemView.findViewById(R.id.reviewableNum);
            reviewableNum.setTypeface(_method.GetTypeFace("BNAZANIN.TTF"));
        }
    }
}
