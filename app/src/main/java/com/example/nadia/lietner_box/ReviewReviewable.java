package com.example.nadia.lietner_box;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadia.lietner_box.bl.cards_bl;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.models.DaoSession;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.cardDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadia on 28/06/2016.
 */
public class ReviewReviewable extends AppCompatActivity implements View.OnClickListener {
    public DaoSession daoSession;
    List <card> reviewCards;
    ArrayList<Integer> list = new ArrayList<Integer>();
    int counter=0;
    int counterAnswer=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_reviewable);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");

        Button btnSaveE=(Button) findViewById(R.id.btn_saveE);
        btnSaveE.setText((R.string.app_save));
        btnSaveE.setTypeface(custom_font);
        btnSaveE.setOnClickListener(this);

        Button btnCancelE=(Button) findViewById(R.id.btn_canelE);
        btnCancelE.setText((R.string.app_cancel));
        btnCancelE.setTypeface(custom_font);
        btnCancelE.setOnClickListener(this);

        TextView tv_edit = (TextView) findViewById(R.id.tv_edit);
        tv_edit.setTypeface(font);
        tv_edit.setText(R.string.icon_edit);
        tv_edit.setOnClickListener(this);

        FrameLayout fledit= (FrameLayout) findViewById(R.id.fl_edit);
        fledit.setOnClickListener(this);

        TextView tv_menu = (TextView) findViewById(R.id.tv_appName4);
        tv_menu.setText(R.string.app_name);
        tv_menu.setTypeface(custom_font);

        TextView question = (TextView) findViewById(R.id.tv_questionR);
        question.setTypeface(font);
        question.setText(R.string.app_question_mark);

        TextView questionRR = (TextView) findViewById(R.id.tv_questionRR);
        questionRR.setTypeface(custom_font);
        questionRR.setText(R.string.app_question);

        Button answer = (Button) findViewById(R.id.btn_answer);
        answer.setText(R.string.app_answer);
        answer.setTypeface(custom_font);
        answer.setOnClickListener(this);

        Button refuse = (Button) findViewById(R.id.btn_refuse);
        refuse.setText(R.string.app_refuse);
        refuse.setOnClickListener(this);
        refuse.setTypeface(custom_font);

        TextView answermark = (TextView) findViewById(R.id.tv_answerR);
        answermark.setTypeface(font);
        answermark.setText(R.string.app_answer_mark);

        TextView answerRR =(TextView) findViewById(R.id.tv_answerRR);
        answerRR.setTypeface(custom_font);
        answerRR.setText(R.string.app_answer);

        cards_bl crd_BL=new cards_bl(this);

        reviewCards=crd_BL.getCards(cardDao.Properties.CardCel.eq(20));

        if (reviewCards.size()!=0) {
            list.clear();
            for (int i=0; i>=0 && i<reviewCards.size() ; i++)
            {
                list.add(reviewCards.get(i).getId().intValue());
            }
            TextView questionR2 = (TextView) findViewById(R.id.tv_questionR2);
            questionR2.setText(reviewCards.get(0).getQuestion());
            questionR2.setTypeface(custom_font);
            questionR2.setOnClickListener(this);

            TextView answerR2 = (TextView) findViewById(R.id.tv_answerR2);
            answerR2.setTypeface(custom_font);
            answerR2.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_answer:
                answerBtnClicked();
                break;
            case (R.id.btn_refuse):
                TextView tvEdite12=(TextView)findViewById(R.id.tv_edit);
                tvEdite12.setVisibility(View.INVISIBLE);
                refuseBtnClicked();
                break;
            case R.id.tv_edit:
                //make text box editable
                editCard();
                break;
            case R.id.fl_edit:
                //make text box editable
                editCard();
                break;
            case R.id.btn_saveE:
                LinearLayout lin1 = (LinearLayout) findViewById(R.id.lin_noti);
                lin1.setVisibility(View.VISIBLE);
                LinearLayout lin3 = (LinearLayout) findViewById(R.id.lin3);
                lin3.setVisibility(View.INVISIBLE);

                //db update new card if its changes!
                saveEdite();
                break;
            case R.id.btn_canelE:
                Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
                LinearLayout lin11 = (LinearLayout) findViewById(R.id.lin_noti);
                lin11.setVisibility(View.VISIBLE);
                LinearLayout lin33 = (LinearLayout) findViewById(R.id.lin3);
                lin33.setVisibility(View.INVISIBLE);

                EditText et_question = (EditText) findViewById(R.id.et_questionR2);
                et_question.setTypeface(custom_font);
                EditText et_answer = (EditText) findViewById(R.id.et_answerR2);
                et_answer.setTypeface(custom_font);
                et_question.setVisibility(View.INVISIBLE);
                et_answer.setVisibility(View.INVISIBLE);

                TextView tv_question = (TextView) findViewById(R.id.tv_questionR2);
                TextView tv_answer = (TextView) findViewById(R.id.tv_answerR2);
                tv_answer.setTypeface(custom_font);
                tv_question.setVisibility(View.VISIBLE);
                tv_answer.setVisibility(View.VISIBLE);
                break;
        }

    }
    public void answerBtnClicked() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        RelativeLayout relayout2 = (RelativeLayout) findViewById(R.id.rel2);
        relayout2.setVisibility(View.VISIBLE);
        TextView tvEdite=(TextView)findViewById(R.id.tv_edit);
        tvEdite.setVisibility(View.VISIBLE);

        counterAnswer=counter;
        TextView answerR2 = (TextView) findViewById(R.id.tv_answerR2);
        answerR2.setText(reviewCards.get(counterAnswer).getAnswer());
        answerR2.setTypeface(custom_font);
    }
    public void refuseBtnClicked(){
        counter++;
        if (counter< list.size()){
            RelativeLayout relayout21 = (RelativeLayout) findViewById(R.id.rel2);
            relayout21.setVisibility(View.INVISIBLE);
            TextView questionR2 = (TextView) findViewById(R.id.tv_questionR2);
            questionR2.setText(reviewCards.get(counter).getQuestion());
        }else{
            finish();
        }

    }
    public void editCard(){
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        LinearLayout lin11 = (LinearLayout) findViewById(R.id.lin_noti);
        lin11.setVisibility(View.INVISIBLE);
        LinearLayout lin3= (LinearLayout) findViewById(R.id.lin3);
        lin3.setVisibility(View.VISIBLE);
        //set Text view and edit text
        String ques,ans;
        TextView tv_question = (TextView) findViewById(R.id.tv_questionR2);
        ques=tv_question.getText().toString();
        TextView tv_answer = (TextView) findViewById(R.id.tv_answerR2);
        ans= tv_answer.getText().toString();

        tv_question.setVisibility(View.INVISIBLE);
        tv_answer.setVisibility(View.INVISIBLE);

        EditText et_question = (EditText) findViewById(R.id.et_questionR2);
        et_question.setText(ques);
        et_question.setTypeface(custom_font);
        et_question.setSelection(et_question.getText().length());

        EditText et_answer = (EditText) findViewById(R.id.et_answerR2);
        et_answer.setText(ans);
        et_answer.setTypeface(custom_font);
        et_answer.setSelection(et_answer.getText().length());
        et_question.setVisibility(View.VISIBLE);
        et_answer.setVisibility(View.VISIBLE);
    }
    public void saveEdite(){
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        EditText etAnswer= (EditText) findViewById(R.id.et_answerR2);
        EditText etQuestion=(EditText) findViewById(R.id.et_questionR2);
        String answer,question;
        answer=etAnswer.getText().toString();
        question=etQuestion.getText().toString();
        cards_bl crd_BL=new cards_bl(this);
        //update table question and answer
        card updateCrd;
        updateCrd=crd_BL.getCard(cardDao.Properties.Id.eq(list.get(counter)));
        updateCrd.setAnswer(answer);
        updateCrd.setQuestion(question);

        try{
            crd_BL.updateCrd(updateCrd);
        }catch (CustomException cex) {
            Toast.makeText(getApplicationContext(),cex.getMessage(), Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"خطایی رخ داده!", Toast.LENGTH_LONG).show();
        }
        etAnswer.setVisibility(View.INVISIBLE);
        etQuestion.setVisibility(View.INVISIBLE);

        TextView answer_ch=(TextView) findViewById(R.id.tv_answerR2);
        answer_ch.setText(updateCrd.getAnswer());
        answer_ch.setTypeface(custom_font);
        answer_ch.setVisibility(View.VISIBLE);

        TextView question_ch=(TextView) findViewById(R.id.tv_questionR2);
        question_ch.setText(updateCrd.getQuestion());
        question_ch.setVisibility(View.VISIBLE);
    }
}
