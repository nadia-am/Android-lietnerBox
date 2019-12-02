package com.example.nadia.lietner_box;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadia.lietner_box.bl.groups_bl;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.errorMsg;
import com.example.nadia.lietner_box.models.DaoMaster;
import com.example.nadia.lietner_box.models.DaoSession;
import com.example.nadia.lietner_box.models.group;
import com.example.nadia.lietner_box.models.groupDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

public class AddNewGroupActivity extends AppCompatActivity implements View.OnClickListener {
    public DaoSession daoSession;
    long GrpId_edit;
    group grpEdit;
    String GroupTitle;
    Long selectedId;
    String type;

    groups_bl grp_Bl;
    group group;

    errorMsg error = new errorMsg();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        setTitle("اضافه کردن گروه جدید");

        FrameLayout fl_back=(FrameLayout) findViewById(R.id.flBack);
        fl_back.setOnClickListener(this);
        //define textView in header
        TextView appName1 = (TextView) findViewById(R.id.tv_appName2);
        appName1.setText(R.string.app_name);
        appName1.setTypeface(custom_font);
        //set content text view
        TextView groupName = (TextView) findViewById(R.id.tv_groupName);
        groupName.setTypeface(custom_font);

        Button submitGroup = (Button) findViewById(R.id.btn_submitGroup);
        submitGroup.setTypeface(font);
        submitGroup.setText(R.string.app_save);
        submitGroup.setTypeface(custom_font);
        submitGroup.setOnClickListener(this);

        TextView back = (TextView) findViewById(R.id.tv_back);
        back.setOnClickListener(this);
        back.setTypeface(font);
        back.setText(R.string.icon_back);

        grp_Bl=new groups_bl(this);
        //get groupId to edit groupName,if edit doesnt press the default value is "-1"
        GrpId_edit = getIntent().getLongExtra("Group_Id",-1);
        selectedId = getIntent().getLongExtra("selectedId",-1);
        GroupTitle = getIntent().getStringExtra("GroupTitle");
        type = getIntent().getStringExtra("type");
        groupName.setText(GroupTitle);
        //===========================================
        //      primary DB Definition    read
        //===========================================
        if (GrpId_edit != -1){//edit group
            grpEdit=grp_Bl.getGroup(groupDao.Properties.Id.eq(GrpId_edit));
            //set button name "Update"
            submitGroup.clearComposingText();
            submitGroup.setText(R.string.app_updateGroup);
            //set group name which wants to be updated
            EditText etname=(EditText) findViewById(R.id.et_name);
            etname.setTypeface(custom_font);
            etname.setText(grpEdit.getGroupName());
            //set curser at the end of group name in textview
            etname.setSelection(etname.getText().length());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submitGroup:
                grp_Bl= new groups_bl(this);
                EditText etgroup_name = (EditText) findViewById(R.id.et_name);
                //show error if there is no name for group
                if(GrpId_edit == -1){//add new group
                    group=new group();
                    group.setGroupName(etgroup_name.getText().toString());
                    group.setParentId(selectedId);
                    if (type.equals("group")){group.setType("group");}else{group.setType("folder");}
                    //if (type.equals("true")){group.setType(true);}else{group.setType(false);}
                    new addGroup().execute();
                }
                else{//else update
                    grpEdit.setGroupName(etgroup_name.getText().toString());
                    new editGroup().execute();
                }
            break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.flBack:
                finish();
                break;
        }
    }

    public class addGroup extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute(){}

        @Override
        protected String doInBackground(Void... params) {
            try{
                grp_Bl.insertGrp(group);
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
                setResult(1,IntentSubmit);
                finish();
            }
        }
    }

    public class editGroup extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute(){}

        @Override
        protected String doInBackground(Void... params) {
            try{
                grp_Bl.updateGrp(grpEdit);
                Intent IntentSubmit = new Intent();
                setResult(1,IntentSubmit);
                finish();

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
                setResult(1,IntentSubmit);
                finish();
            }
        }
    }
}
