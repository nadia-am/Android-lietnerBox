package com.example.nadia.lietner_box;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.nadia.lietner_box.bl.cards_bl;
import com.example.nadia.lietner_box.bl.groups_bl;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.errorMsg;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.group;
import com.example.nadia.lietner_box.models.groupDao;
//import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class SortFileActivity  extends AppCompatActivity implements View.OnClickListener{
    String path;
    ArrayList<String> Question = new ArrayList<String>();
    ArrayList<String> Answer = new ArrayList<String>();
    ArrayList<Integer> cardCel = new ArrayList<Integer>();
    ArrayList<String> Ldate = new ArrayList<String>();

    boolean check;
    Long selectedId;

    String grp_name;
    groups_bl grp;
    group group=new group();

    errorMsg error = new errorMsg();

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_file);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");

        selectedId = getIntent().getLongExtra("selectedId",-1);
        TextView groupInfo = (TextView) findViewById(R.id.tv_group_name);
        groupInfo.setText(R.string.app_backup_info);
        groupInfo.setTypeface(custom_font);

        TextView appName = (TextView) findViewById(R.id.tv_appName);
        appName.setText(R.string.app_name);
        appName.setTypeface(custom_font);

        TextView menu = (TextView) findViewById(R.id.tv_back);
        menu.setTypeface(font);
        menu.setText(R.string.icon_back);
        menu.setOnClickListener(this);

        FrameLayout flback=(FrameLayout)findViewById(R.id.fl_Back);
        flback.setOnClickListener(this);

        TextView tvGrpName=(TextView) findViewById(R.id.tv_grpName);
        tvGrpName.setText(R.string.app_groupName);
        tvGrpName.setTypeface(custom_font);

        TextView tvCrdNumTxt=(TextView) findViewById(R.id.tv_crdNumTxt);
        tvCrdNumTxt.setText(R.string.app_crdNum);
        tvCrdNumTxt.setTypeface(custom_font);

        Button btnSave=(Button) findViewById(R.id.btn_save);
        btnSave.setText(R.string.app_save);
        btnSave.setTypeface(custom_font);
        btnSave.setOnClickListener(this);

        Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setText(R.string.app_finish);
        btnCancel.setTypeface(custom_font);
        btnCancel.setOnClickListener(this);

        TextView tvSwitch = (TextView) findViewById(R.id.tv_switch);
        tvSwitch.setText(R.string.app_protectInfo);
        tvSwitch.setTypeface(custom_font);

        path=getIntent().getStringExtra("path");
        //read file and save grpName,crds
        String grpName = null;
        Question.clear();
        Answer.clear();
        cardCel.clear();
        Ldate.clear();
//        try {
//            CSVReader reader = new CSVReader(new FileReader(path), '\t');
//            String[] read;
//            read = reader.readNext();
//            if (read==null){
//                String[] bits = path.split("/");
//                String lastOne = bits[bits.length-1];
//                grpName=lastOne;
//
//            }else{
//                grpName = read[1];
//                for (int i = 0; read != null; i++) {
//                    Question.add(read[2]);
//                    Answer.add(read[3]);
//                    cardCel.add(Integer.valueOf(read[4]));
//                    Ldate.add((read[5]));
//                    read = reader.readNext();
//                }
//            }
//        }catch (Error e){
//            Toast.makeText(getApplicationContext(),getResources().getText(R.string.error), Toast.LENGTH_LONG).show();
//            finish();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(),getResources().getText(R.string.error), Toast.LENGTH_LONG).show();
//            finish();
//        }
        //___________________________________________

        EditText etGrpName=(EditText) findViewById(R.id.et_grpName);
        etGrpName.setText(grpName);
        etGrpName.setTypeface(custom_font);
        etGrpName.setSelection(etGrpName.getText().length());//set curser at the end of group name in textview

        TextView tvCrdNumTxtNum=(TextView) findViewById(R.id.tv_crdNumNumber);
        tvCrdNumTxtNum.setText(String.valueOf(Answer.size()));
        tvCrdNumTxtNum.setTypeface(BNazanin);

        SwitchCompat sw_btn = (SwitchCompat) findViewById(R.id.switch1);
        check=false;
        sw_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    check=true;
                }else {
                    check=false;
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.fl_Back:
                finish();
                break;
            case R.id.btn_save:
                grp=new groups_bl(this);
                EditText etGrpName=(EditText) findViewById(R.id.et_grpName);
                grp_name=String.valueOf(etGrpName.getText());
                //get grpName from edit text and add it to grp
                new saveGrpCrd().execute();

                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    public class saveGrpCrd extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute(){
            dialog = new SpotsDialog(SortFileActivity.this);
            dialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            try{
                group.setGroupName(grp_name);
                group.setParentId(selectedId);
                group.setType("group");
                //group.setType(true);
                grp.insertGrp(group);
                //add cards
                group getGrp= new group();
                getGrp=grp.getGroup(groupDao.Properties.GroupName.eq(grp_name));
                if(check==true){
                    cards_bl crd=new cards_bl(SortFileActivity.this);
                    for (int i=0;i<Question.size();i++){
                        card card = new card();
                        card.setQuestion(Question.get(i));
                        card.setAnswer(Answer.get(i));
                        card.setGroupId(getGrp.getId());
                        card.setCardCel(cardCel.get(i));
                        //convert str to date
                        DateFormat df  = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                        Date convertedDate = new Date();
                        convertedDate = df.parse(Ldate.get(i));
                        card.setLDate(convertedDate);
                        crd.insertCrd_backup(card);
                    }
                }else{
                    cards_bl crd=new cards_bl(SortFileActivity.this);
                    for (int i=0;i<Question.size();i++){
                        card card = new card();
                        card.setQuestion(Question.get(i));
                        card.setAnswer(Answer.get(i));
                        card.setGroupId(getGrp.getId());
                        crd.insertCrd(card);
                    }
                }
            }catch (CustomException cex){
                error.setType(true);
                error.setMsg(cex.getMessage());
            }catch (Exception ex){
                error.setType(true);
                error.setMsg("خطایی رخ داده! ");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            if (error.isType()){
                Toast.makeText(getApplicationContext(),error.getMsg(), Toast.LENGTH_LONG).show();
                error.setType(false);
            }else {
                dialog.dismiss();
                finish();
            }
        }
    }
}
