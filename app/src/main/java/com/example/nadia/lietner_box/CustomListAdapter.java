package com.example.nadia.lietner_box;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nadia on 03/06/2016.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> grpName= new ArrayList<String>();
    private ArrayList<Integer> allCard = new ArrayList<Integer>();
    private ArrayList<Integer> reviewable = new ArrayList<Integer>();
    Typeface custom_font;
    Typeface Bnazanin;

    public CustomListAdapter(Activity context, ArrayList<String> grpName, ArrayList<Integer> allCard, ArrayList<Integer> reviewable,Typeface custom_font,Typeface Bnazanin) {
        super(context, R.layout.mylist, grpName);
        this.context = context;
        this.grpName = grpName;
        this.allCard = allCard;
        this.reviewable = reviewable;
        this.custom_font=custom_font;
        this.Bnazanin=Bnazanin;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist,null,true);


        TextView grpname=(TextView) rowView.findViewById(R.id.label);
        grpname.setTypeface(custom_font);

        TextView allcard=(TextView) rowView.findViewById(R.id.allCard);
        allcard.setTypeface(custom_font);
        allcard.clearComposingText();
        TextView allcardNum=(TextView) rowView.findViewById(R.id.allCardNum);
        allcardNum.setTypeface(Bnazanin);
        allcardNum.clearComposingText();

        TextView reviewablecard=(TextView) rowView.findViewById(R.id.reviewable);
        reviewablecard.setTypeface(custom_font);
        reviewablecard.clearComposingText();
        TextView reviewablecardNum=(TextView) rowView.findViewById(R.id.reviewableNum);
        reviewablecardNum.setTypeface(Bnazanin);
        reviewablecardNum.clearComposingText();

        grpname.setText(grpName.get(position));

        allcard.setText(" تعداد کل کارتها ");
        allcardNum.setText(allCard.get(position)+":");
        reviewablecard.setText(" کارتهای قابل مرور ");
        reviewablecardNum.setText(reviewable.get(position)+":");

        return rowView;
    }
}
