package com.example.nadia.lietner_box;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadia.lietner_box.bl.cards_bl;
import com.example.nadia.lietner_box.bl.groups_bl;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.message;
import com.example.nadia.lietner_box.models.DaoSession;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.cardDao;
import com.example.nadia.lietner_box.models.group;
import com.example.nadia.lietner_box.models.groupDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SelectedGroupActivity extends AppCompatActivity implements View.OnClickListener {
    public DaoSession daoSession;
    group thisGrpName;
    List<card> reviewableCard;
    List<card> thisGrpCard;
    List<card> LearnedCard;
    String GroupName;
    Date date;
    int grpId;
    int countReview = 0;
    List<card> reviewCards;

    TextView date1;
    TextView date2;

    TextView reviewCardNum;
    TextView memorizeCardNum;

    cards_bl crd_BL = new cards_bl(this);
    groups_bl grp_BL = new groups_bl(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_group);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");
        //***************receive position & GroupName  from listView of mainActivity***
        Integer position = getIntent().getIntExtra("position", 0);
        GroupName = getIntent().getStringExtra("GroupName");
        //Define textview
        TextView tvMenu = (TextView) findViewById(R.id.tv_menu);
        tvMenu.setText(GroupName);
        tvMenu.setTypeface(custom_font);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_fab);
        fab.setOnClickListener(this);

        TextView groupInfo = (TextView) findViewById(R.id.tv_groupInfo);
        groupInfo.setText(R.string.app_groupInfo);
        groupInfo.setTypeface(custom_font);

        TextView todayDate = (TextView) findViewById(R.id.tv_todayDate);
        todayDate.setText(R.string.app_todayDate);
        todayDate.setTypeface(custom_font);

        date1 = (TextView) findViewById(R.id.tv_date1);

        TextView addGroupDate = (TextView) findViewById(R.id.tv_addGroupDate);
        addGroupDate.setText(R.string.app_addGroupDate);
        addGroupDate.setTypeface(custom_font);

        date2 = (TextView) findViewById(R.id.tv_date2);

        TextView cardInfo = (TextView) findViewById(R.id.tv_cardInfo);
        cardInfo.setText(R.string.app_cardInfo);
        cardInfo.setTypeface(custom_font);

        TextView numberOfCard = (TextView) findViewById(R.id.tv_numberOfCard);
        numberOfCard.setText(R.string.app_numberOfCard);
        numberOfCard.setTypeface(custom_font);

        TextView numberOfCardNum = (TextView) findViewById(R.id.tv_numberOfCardNum);
        numberOfCardNum.setTypeface(BNazanin);

        TextView numberOfReviewCard = (TextView) findViewById(R.id.tv_reviwCard);
        numberOfReviewCard.setText(R.string.app_reviewCard);
        numberOfReviewCard.setTypeface(custom_font);

        reviewCardNum = (TextView) findViewById(R.id.tv_reviewCardNum);
        reviewCardNum.setText("0");
        reviewCardNum.setTypeface(BNazanin);

        TextView memorizeCard = (TextView) findViewById(R.id.tv_memorizeCard);
        memorizeCard.setText(R.string.app_memorizeCard);
        memorizeCard.setTypeface(custom_font);

        memorizeCardNum = (TextView) findViewById(R.id.tv_memorizeCardNum);
        memorizeCardNum.setTypeface(BNazanin);

        FloatingActionButton fabrev = (FloatingActionButton) findViewById(R.id.btn_fabrev);
        fabrev.setOnClickListener(this);

        TextView menu = (TextView) findViewById(R.id.tv_back);
        menu.setTypeface(font);
        menu.setText(R.string.icon_back);
        menu.setOnClickListener(this);

        TextView setting = (TextView) findViewById(R.id.tv_setting);
        setting.setOnClickListener(this);
        setting.setTypeface(font);
        setting.setText(R.string.icon_menu);

        FrameLayout flSetting = (FrameLayout) findViewById(R.id.fl_setting);
        flSetting.setOnClickListener(this);

        FrameLayout flback = (FrameLayout) findViewById(R.id.flBack);
        flback.setOnClickListener(this);
        //************
        // in grouptable-find exact GroupName,which was clicked in previous page
        //************
        crd_BL = new cards_bl(this);
        grp_BL = new groups_bl(this);

        new getGroup().execute();

        new getReviewable().execute();

        new getLearned().execute();
    }
    @Override
    public void onResume() {
        super.onResume();

        new getThisGrpCard().execute();

        new getReviewable().execute();

        new getLearned().execute();
    }

    public class getGroup extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute(){}

        @Override
        protected String doInBackground(Void... params) {
            thisGrpName = grp_BL.getGroup(groupDao.Properties.GroupName.eq(GroupName));
            grpId = thisGrpName.getId().intValue();
            date = thisGrpName.getDate();
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            //set text for date2
            Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");
            DateFormat df = new SimpleDateFormat("yyyy,MM,dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year1 = cal.get(Calendar.YEAR);
            int month1 = cal.get(Calendar.MONTH);
            int day1 = cal.get(Calendar.DAY_OF_MONTH);
            ConvertDate datecreate = new ConvertDate();
            datecreate.GregorianToPersian(year1, month1 + 1, day1);
            date2 = (TextView) findViewById(R.id.tv_date2);
            date2.setText(datecreate.toString());
            date2.setTypeface(BNazanin);

            // set current date1 into textview
            Calendar calendar = Calendar.getInstance();
            Date todayDate2 = calendar.getTime();
            calendar.setTime(todayDate2);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            ConvertDate dateCon = new ConvertDate();
            dateCon.GregorianToPersian(year, month + 1, day);
            date1 = (TextView) findViewById(R.id.tv_date1);
            date1.clearComposingText();
            date1.setText(dateCon.toString());
            date1.setTypeface(BNazanin);
        }
    }

    public class getReviewable extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            reviewableCard = crd_BL.getReviewable_2con(cardDao.Properties.GroupId.eq(grpId),cardDao.Properties.CardCel.notEq(20));
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");
            countReview = reviewableCard.size();
            reviewCardNum = (TextView) findViewById(R.id.tv_reviewCardNum);
            reviewCardNum.clearComposingText();
            reviewCardNum.setText(String.valueOf(countReview));
            reviewCardNum.setTypeface(BNazanin);
        }
    }

    public class getLearned extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... params) {
            LearnedCard = crd_BL.getCards_2con(cardDao.Properties.CardCel.eq(20), cardDao.Properties.GroupId.eq(grpId));
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");
            memorizeCardNum = (TextView) findViewById(R.id.tv_memorizeCardNum);
            memorizeCardNum.clearComposingText();
            memorizeCardNum.setText(String.valueOf(LearnedCard.size()));
            memorizeCardNum.setTypeface(BNazanin);
        }
    }

    public class getThisGrpCard extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            thisGrpCard = crd_BL.getCards(cardDao.Properties.GroupId.eq(grpId));
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");
            TextView numberOfCardNum = (TextView) findViewById(R.id.tv_numberOfCardNum);
            numberOfCardNum.clearComposingText();
            numberOfCardNum.setText(String.valueOf(thisGrpCard.size()));
            numberOfCardNum.setTypeface(BNazanin);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fabrev:
                if (countReview != 0) {
                    FloatingActionButton fabrev = (FloatingActionButton) findViewById(R.id.btn_fabrev);
                    Intent btnIntent = new Intent(SelectedGroupActivity.this, Review1Activity.class);
                    btnIntent.putExtra("GroupName", GroupName);
                    btnIntent.putExtra("Group_Id", grpId);
                    startActivity(btnIntent);
                } else if (countReview == 0) {
                    Toast.makeText(this, "هیچ کارتی برای مرور وجود ندارد", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_fab:
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_fab);
                Intent addCardIntent = new Intent(SelectedGroupActivity.this, AddNewCardActivity.class);
                addCardIntent.putExtra("Group_Id", grpId);
                startActivity(addCardIntent);
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.flBack:
                finish();
                break;

            case R.id.fl_setting:
                setting();
                break;
            case R.id.tv_setting:
                setting();
                break;

        }
    }
    public void setting() {
        CharSequence items[] = new CharSequence[]{"نمایش کارتها", "مرور کارتهای یادگرفته شده"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SelectedGroupActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //find out group Id base on group name
                switch (which) {
                    case 0:
                        Intent IntentGroup = new Intent(SelectedGroupActivity.this, ShowCards.class);
                        IntentGroup.putExtra("GroupName", GroupName);
                        startActivity(IntentGroup);

                        break;
                    case 1:
                        reviewCards = crd_BL.getCards(cardDao.Properties.CardCel.eq(20));
                        if (reviewCards.size() == 0) {
                            Toast.makeText(getApplicationContext(), "هیچ کارتی تا کنون یاد گرفته نشده", Toast.LENGTH_LONG).show();
                        } else {
                            Intent IntentReview = new Intent(SelectedGroupActivity.this, ReviewReviewable.class);
                            startActivity(IntentReview);
                        }
                        break;
                }
            }
        });
        builder.show();
    }
}