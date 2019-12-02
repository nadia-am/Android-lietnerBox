package com.example.nadia.lietner_box;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nadia on 27/06/2016.
 */
public class CustomListAdapterCard extends ArrayAdapter<String> {
    private final Activity context;
    private ArrayList<String> Question= new ArrayList<String>();
    private ArrayList<String> Answer = new ArrayList<String>();
    private ArrayList<Integer> Cardcel = new ArrayList<Integer>();
    Typeface custom_font;
    Typeface BNazanin;

    public CustomListAdapterCard(Activity context, ArrayList<String> Question, ArrayList<String> Answer, ArrayList<Integer> Cardcel, Typeface custom_font,Typeface BNazanin) {
        super(context, R.layout.list_card, Question);
        this.context = context;
        this.Question = Question;
        this.Answer = Answer;
        this.custom_font=custom_font;
        this.BNazanin=BNazanin;
        this.Cardcel=Cardcel;
    }
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_card,null,true);

        TextView question=(TextView) rowView.findViewById(R.id.question);
        question.setTypeface(custom_font);
        question.setText("سوال : "+Question.get(position));

        TextView answer=(TextView) rowView.findViewById(R.id.answer);
        answer.setTypeface(custom_font);
        answer.setText(Answer.get(position)+":");
        TextView answerQ=(TextView) rowView.findViewById(R.id.answer_q);
        answerQ.setTypeface(custom_font);
        answerQ.setText(" پاسخ ");

        TextView cardcell=(TextView) rowView.findViewById(R.id.cardcel);
        cardcell.setTypeface(BNazanin);
        cardcell.setText(Cardcel.get(position)+":");
        TextView cardcel_q=(TextView) rowView.findViewById(R.id.cardcel_Q);
        cardcel_q.setTypeface(custom_font);
        cardcel_q.setText(" شماره خانه ");



        return rowView;
    }
}
