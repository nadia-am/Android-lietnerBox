package com.example.nadia.lietner_box;

import android.app.AlertDialog;
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

public class Review1Activity extends AppCompatActivity implements View.OnClickListener {
    public DaoSession daoSession;
    String GroupName;
    int Group_Id;
    int counter=0;
    int counterAnswer=0;
    List<card> reviewable = new ArrayList<card>();
    cards_bl crd_BL;
    TextView tvnumber;
    int number;
    Typeface BNazanin;
    Button btnBefore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review1);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");

        number=1;
        Button btnSaveE=(Button) findViewById(R.id.btn_saveE);
        btnSaveE.setText((R.string.app_save));
        btnSaveE.setTypeface(custom_font);
        btnSaveE.setOnClickListener(this);

        Button btnCancelE=(Button) findViewById(R.id.btn_canelE);
        btnCancelE.setText((R.string.app_cancel));
        btnCancelE.setTypeface(custom_font);
        btnCancelE.setOnClickListener(this);

        tvnumber = (TextView) findViewById(R.id.tv_number);

        TextView tv_edit = (TextView) findViewById(R.id.tv_edit);
        tv_edit.setTypeface(font);
        tv_edit.setText(R.string.icon_edit);
        tv_edit.setOnClickListener(this);

        FrameLayout frame_layout=(FrameLayout) findViewById(R.id.fl_finish_r);
        frame_layout.setOnClickListener(this);

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
        refuse.setText(R.string.next);
        refuse.setOnClickListener(this);
        refuse.setTypeface(font);

        btnBefore = (Button) findViewById(R.id.btn_before);
        btnBefore.setText(R.string.previous);
        btnBefore.setTypeface(font);
        btnBefore.setOnClickListener(this);

        Button refuse2 = (Button) findViewById(R.id.btn_refuse2);
        refuse2.setText(R.string.app_refuse);
        refuse2.setTypeface(custom_font);
        refuse2.setOnClickListener(this);

        TextView answermark = (TextView) findViewById(R.id.tv_answerR);
        answermark.setTypeface(font);
        answermark.setText(R.string.app_answer_mark);

        TextView answerRR =(TextView) findViewById(R.id.tv_answerRR);
        answerRR.setTypeface(custom_font);
        answerRR.setText(R.string.app_answer);

        Button correct = (Button) findViewById(R.id.btn_correct);
        correct.setTypeface(font);
        correct.setText(R.string.app_correcrt);
        correct.setOnClickListener(this);

        Button incorrect = (Button) findViewById(R.id.btn_incorrect);
        incorrect.setTypeface(font);
        incorrect.setText(R.string.app_incorrect);
        incorrect.setOnClickListener(this);
        //recive groupName & last date of update
        GroupName = getIntent().getStringExtra("GroupName");
        Group_Id = getIntent().getIntExtra("Group_Id",0);

        crd_BL=new cards_bl(this);
        reviewable.clear();
        reviewable=crd_BL.getReviewable_2con(cardDao.Properties.GroupId.eq(Group_Id),cardDao.Properties.CardCel.notEq(20));
        if (reviewable.size()!= 0)
        {
            tvnumber.setText(String.valueOf(number));
            tvnumber.setTypeface(BNazanin);
            number++;
            TextView questionR2 = (TextView) findViewById(R.id.tv_questionR2);
            questionR2.setText(reviewable.get(0).getQuestion());
            questionR2.setTypeface(custom_font);
            questionR2.setOnClickListener(this);

            TextView answerR2 = (TextView) findViewById(R.id.tv_answerR2);
            answerR2.setTypeface(custom_font);
            answerR2.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        switch (v.getId())
        {
            case (R.id.btn_refuse):
                refuseBtnClicked();
                break;
            case R.id.btn_before:
                previousBtnClicked();
                break;
            case R.id.btn_refuse2:
                FrameLayout fll=(FrameLayout) findViewById(R.id.fl_finish_r);
                TextView tvEdite12=(TextView)findViewById(R.id.tv_edit);
                tvEdite12.setVisibility(View.INVISIBLE);
                fll.setVisibility(View.INVISIBLE);
                refuseBtnClicked();
                break;
            case R.id.btn_answer:
                FrameLayout fram_layout=(FrameLayout) findViewById(R.id.fl_finish_r);
                fram_layout.setVisibility(View.VISIBLE);
                TextView edit_tv= (TextView) findViewById(R.id.tv_edit);
                edit_tv.setVisibility(View.VISIBLE);
                answerBtnClicked();
                break;
            case R.id.btn_incorrect:
                FrameLayout fram1=(FrameLayout) findViewById(R.id.fl_finish_r);
                TextView tview1=(TextView)findViewById(R.id.tv_edit);
                tview1.setVisibility(View.INVISIBLE);
                fram1.setVisibility(View.INVISIBLE);
                incorrect();
                refuseBtnClicked();
                break;
            case R.id.btn_correct:
                FrameLayout fram2=(FrameLayout) findViewById(R.id.fl_finish_r);
                TextView tview2=(TextView)findViewById(R.id.tv_edit);
                tview2.setVisibility(View.INVISIBLE);
                fram2.setVisibility(View.INVISIBLE);
                correct();
                refuseBtnClicked();
                break;
            case R.id.tv_edit:
                //make text box editable
                editCard();
                break;
            case R.id.fl_finish_r:
                editCard();
                break;
            case R.id.btn_saveE:
                //db update new card if its changes!
                saveEdite();
                LinearLayout linEdite = (LinearLayout) findViewById(R.id.linEdite);
                linEdite.setVisibility(View.INVISIBLE);
                LinearLayout lin22_2 = (LinearLayout) findViewById(R.id.lin_second);
                lin22_2.setVisibility(View.VISIBLE);

                EditText et_question1 = (EditText) findViewById(R.id.et_questionR2);
                et_question1.setTypeface(custom_font);
                EditText et_answer1 = (EditText) findViewById(R.id.et_answerR2);
                et_answer1.setTypeface(custom_font);
                et_question1.setVisibility(View.INVISIBLE);
                et_answer1.setVisibility(View.INVISIBLE);

                TextView tv_question1 = (TextView) findViewById(R.id.tv_questionR2);
                TextView tv_answer1 = (TextView) findViewById(R.id.tv_answerR2);
                tv_answer1.setTypeface(custom_font);
                tv_question1.setVisibility(View.VISIBLE);
                tv_question1.setText(et_question1.getText());
                tv_answer1.setVisibility(View.VISIBLE);
                tv_answer1.setText(et_answer1.getText());
                break;
            case R.id.btn_canelE:
                LinearLayout linEdite1 = (LinearLayout) findViewById(R.id.linEdite);
                linEdite1.setVisibility(View.INVISIBLE);
                LinearLayout lin22 = (LinearLayout) findViewById(R.id.lin_second);
                lin22.setVisibility(View.VISIBLE);

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

    public void showMsg(String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
//        AlertDialog dlg = builder.create();
        Log.d("inja", "msgdialog fun");
        builder.show();
    }

    public void refuseBtnClicked(){
        counter++;
        if (counter< reviewable.size())
        {
            tvnumber.setText(String.valueOf(number));
            tvnumber.setTypeface(BNazanin);
            number++;
            RelativeLayout relayout21 = (RelativeLayout) findViewById(R.id.rel2);
            relayout21.setVisibility(View.INVISIBLE);
            LinearLayout lin22 = (LinearLayout) findViewById(R.id.lin_second);
            lin22.setVisibility(View.INVISIBLE);
            LinearLayout lin11 = (LinearLayout) findViewById(R.id.lin_first);
            lin11.setVisibility(View.VISIBLE);


            TextView questionR2 = (TextView) findViewById(R.id.tv_questionR2);
            questionR2.setText(reviewable.get(counter).getQuestion());
        }else
        {
            finish();
        }
    }

    public void previousBtnClicked(){
        if (counter!=0){
            counter--;
            if (counter>= 0)
            {
                number--;
                tvnumber.setText(String.valueOf(number-1));
                tvnumber.setTypeface(BNazanin);
                RelativeLayout relayout21 = (RelativeLayout) findViewById(R.id.rel2);
                relayout21.setVisibility(View.INVISIBLE);
                LinearLayout lin22 = (LinearLayout) findViewById(R.id.lin_second);
                lin22.setVisibility(View.INVISIBLE);
                LinearLayout lin11 = (LinearLayout) findViewById(R.id.lin_first);
                lin11.setVisibility(View.VISIBLE);

                TextView questionR2 = (TextView) findViewById(R.id.tv_questionR2);
                questionR2.setText(reviewable.get(counter).getQuestion());
            }
        }

    }
    public void answerBtnClicked(){
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        RelativeLayout relayout2 = (RelativeLayout) findViewById(R.id.rel2);
        relayout2.setVisibility(View.VISIBLE);
        LinearLayout lin2 = (LinearLayout) findViewById(R.id.lin_second);
        lin2.setVisibility(View.VISIBLE);
        LinearLayout lin1 = (LinearLayout) findViewById(R.id.lin_first);
        lin1.setVisibility(View.INVISIBLE);
        counterAnswer=counter;
        TextView answerR2 = (TextView) findViewById(R.id.tv_answerR2);
        answerR2.setText(reviewable.get(counterAnswer).getAnswer());
        answerR2.setTypeface(custom_font);
    }

    public void incorrect() {
        int  id_card;
        card  updateCrd;
        id_card = reviewable.get(counter).getId().intValue();
        cards_bl crd_Bl=new cards_bl(this);
        updateCrd=crd_Bl.getCard(cardDao.Properties.Id.eq(id_card));
        updateCrd.setCardCel(1);
        try{
            crd_Bl.updateCrd(updateCrd);
        }catch (CustomException cex) {
            Toast.makeText(getApplicationContext(),cex.getMessage(), Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"خطایی رخ داده!", Toast.LENGTH_LONG).show();
        }
    }

    public void correct(){
        int  id_card;
        cards_bl crd_BL=new cards_bl(this);
        id_card = reviewable.get(counter).getId().intValue();
        card getCard;
        getCard=crd_BL.getCard(cardDao.Properties.Id.eq(id_card));
        int cell=getCard.getCardCel().intValue();
        switch (cell){
            case 1:
                getCard.setCardCel(2);
                break;
            case 2:
                getCard.setCardCel(4);
                break;
            case 4:
                getCard.setCardCel(8);
                break;
            case 8:
                getCard.setCardCel(15);
                break;
            case 15:
                showMsg("**کارت یادگرفته شد**","تبریک می گم، شما این کارت را حفظ کردید");
                getCard.setCardCel(20);
        }
        //************
        // update card
        try{
            crd_BL.updateCrd(getCard);
        }catch (CustomException cex) {
            Toast.makeText(getApplicationContext(),cex.getMessage(), Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"خطایی رخ داد!", Toast.LENGTH_LONG).show();
        }
    }

    public void editCard(){
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        LinearLayout lin22 = (LinearLayout) findViewById(R.id.lin_second);
        lin22.setVisibility(View.INVISIBLE);
        LinearLayout lin11 = (LinearLayout) findViewById(R.id.lin_first);
        lin11.setVisibility(View.INVISIBLE);
        LinearLayout lin_edite= (LinearLayout) findViewById(R.id.linEdite);
        lin_edite.setVisibility(View.VISIBLE);
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
        TextView tvQuestion=(TextView) findViewById(R.id.tv_questionR2);
        String answer,question;
        answer=etAnswer.getText().toString();
        question=etQuestion.getText().toString();
        //update table question and answer
        cards_bl crd_BL=new cards_bl(this);
        card updatedCrd;
        updatedCrd=crd_BL.getCard(cardDao.Properties.Id.eq(reviewable.get(counter).getId()));
        updatedCrd.setAnswer(answer);
        updatedCrd.setQuestion(question);
        try{
            crd_BL.updateCrd(updatedCrd);
        }catch (CustomException cex) {
            Toast.makeText(getApplicationContext(),cex.getMessage(), Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"خطایی رخ داده!", Toast.LENGTH_LONG).show();
        }
        TextView answer_ch=(TextView) findViewById(R.id.tv_answerR2);
        answer_ch.setText(reviewable.get(counterAnswer).getAnswer());
        answer_ch.setTypeface(custom_font);
        answer_ch.setVisibility(View.VISIBLE);
        TextView question_ch=(TextView) findViewById(R.id.tv_questionR2);
        question_ch.setText(reviewable.get(counter).getQuestion());
        question_ch.setVisibility(View.VISIBLE);
        etAnswer.setVisibility(View.INVISIBLE);
        etQuestion.setVisibility(View.INVISIBLE);

        LinearLayout lin1 = (LinearLayout) findViewById(R.id.lin_first);
        lin1.setVisibility(View.INVISIBLE);
        LinearLayout lin_edit = (LinearLayout) findViewById(R.id.linEdite);
        lin_edit.setVisibility(View.INVISIBLE);
        LinearLayout lin2 = (LinearLayout) findViewById(R.id.lin_second);
        lin2.setVisibility(View.VISIBLE);
    }
}

