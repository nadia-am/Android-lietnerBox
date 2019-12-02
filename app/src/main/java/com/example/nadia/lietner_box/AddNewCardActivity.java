package com.example.nadia.lietner_box;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadia.lietner_box.bl.cards_bl;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.errorMsg;
import com.example.nadia.lietner_box.models.DaoSession;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.cardDao;

public class AddNewCardActivity extends AppCompatActivity implements View.OnClickListener{
    public DaoSession daoSession;
    long Group_Id;
    int Card_Id;
    card crdEdite;

    card card = new card();
    cards_bl crd_BL;

    errorMsg error = new errorMsg();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        FrameLayout fl_back=(FrameLayout) findViewById(R.id.flBack);
        fl_back.setOnClickListener(this);
        //set header
        TextView tv_menue3 = (TextView) findViewById(R.id.tv_appName3);
        tv_menue3.setText(R.string.app_name);
        tv_menue3.setTypeface(custom_font);
        //set content  text view
        TextView question1 = (TextView) findViewById(R.id.tv_question);
        question1.setText(R.string.app_question);
        question1.setTypeface(custom_font);

        TextView answer1 = (TextView) findViewById(R.id.tv_answer1);
        answer1.setText(R.string.app_answer);
        answer1.setTypeface(custom_font);

        Button save = (Button) findViewById(R.id.btn_save);
        save.setText(R.string.app_save);
        save.setTypeface(custom_font);
        save.setOnClickListener(this);

        Button finish = (Button) findViewById(R.id.btn_finish);
        finish.setText(R.string.app_finish);
        finish.setTypeface(custom_font);
        finish.setOnClickListener(this);

        TextView back = (TextView) findViewById(R.id.tv_back);
        back.setOnClickListener(this);
        back.setTypeface(font);
        back.setText(R.string.icon_back);

        cards_bl crd_BL=new  cards_bl(this);
        //*************GET*Intent*ID*of*GroupName*For*This*Card***
        Group_Id = getIntent().getIntExtra("Group_Id", 0);
        Card_Id = getIntent().getIntExtra("Card_Id", -1);
        if (Card_Id != -1){
            crdEdite=crd_BL.getCard(cardDao.Properties.Id.eq(Card_Id));
            Button btnSave = (Button) findViewById(R.id.btn_save);
            btnSave.clearComposingText();
            btnSave.setText(R.string.app_updateGroup);
            //set group name which wants to be updated
            EditText etquestion=(EditText) findViewById(R.id.et_question);
            etquestion.setTypeface(custom_font);
            etquestion.setText(crdEdite.getQuestion());
            etquestion.setSelection(etquestion.getText().length());//set curser at the end of group name in textview
            //set group name which wants to be updated
            EditText etanswer=(EditText) findViewById(R.id.et_answer);
            etanswer.setTypeface(custom_font);
            etanswer.setText(crdEdite.getAnswer());
            etanswer.setSelection(etanswer.getText().length());//set curser at the end of group name in textview
        }
    }
    @Override
    public void onClick(View v) {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        crd_BL=new  cards_bl(this);
        switch (v.getId()){
            case R.id.btn_save:
                EditText et_question = (EditText) findViewById(R.id.et_question);
                et_question.setTypeface(custom_font);
                EditText et_answer = (EditText) findViewById(R.id.et_answer);
                et_answer.setTypeface(custom_font);
                if (Card_Id == -1){//add card
                    card.setQuestion(et_question.getText().toString());
                    card.setAnswer(et_answer.getText().toString());
                    card.setGroupId(Group_Id);
                    new addCard().execute();
                }else{//update card
                    crdEdite.setQuestion(et_question.getText().toString());
                    crdEdite.setAnswer(et_answer.getText().toString());
                    new editCard().execute();
                }
                break;
            case R.id.btn_finish:
                finish();
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.flBack:
                finish();
                break;
        }
    }

    public class addCard extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute(){}

        @Override
        protected String doInBackground(Void... params) {
            try {
                crd_BL.insertCrd(card);
            }catch (CustomException cex){
                error.setType(true);
                error.setMsg(cex.getMessage());
            }catch (Exception ex){
                error.setType(true);
                error.setMsg("خطایی رخ داده!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            if (error.isType()){
                Toast.makeText(getApplicationContext(),error.getMsg(), Toast.LENGTH_LONG).show();
                error.setType(false);
            }else {
                Intent IntentSubmit = new Intent();
                setResult(1, IntentSubmit);
                finish();
            }
        }
    }

    public class editCard extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute(){}

        @Override
        protected String doInBackground(Void... params) {
            try {
                crd_BL.updateCrd(crdEdite);
            } catch (CustomException cex){
                error.setType(true);
                error.setMsg(cex.getMessage());
            }catch (Exception ex){
                error.setType(true);
                error.setMsg("خطایی رخ داده!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            if (error.isType()){
                Toast.makeText(getApplicationContext(),error.getMsg(), Toast.LENGTH_LONG).show();
                error.setType(false);
            }else {
                Intent IntentSubmit = new Intent();
                setResult(1, IntentSubmit);
                finish();
            }
        }
    }
}

