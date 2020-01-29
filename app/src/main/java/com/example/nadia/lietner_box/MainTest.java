package com.example.nadia.lietner_box;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nadia.lietner_box.bl.cards_bl;
import com.example.nadia.lietner_box.bl.groups_bl;
import com.example.nadia.lietner_box.bl.purchase_bl;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.cardDao;
import com.example.nadia.lietner_box.models.group;
import com.example.nadia.lietner_box.models.groupWithCards;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.opencsv.CSVWriter;
import com.telerik.android.common.Util;
import com.telerik.android.primitives.widget.sidedrawer.DrawerChangeListener;
import com.telerik.android.primitives.widget.sidedrawer.DrawerLocation;
import com.telerik.android.primitives.widget.sidedrawer.RadSideDrawer;
import com.telerik.widget.list.ItemReorderBehavior;
import com.telerik.widget.list.RadListView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * Created by Nadia on 12/5/2016.
 */

public class MainTest extends AppCompatActivity implements View.OnClickListener  {

    //---Bazaar-----
    private static final int REQUEST_WRITE_STORAGE = 112;
    //_____________BAZAAR

    //===========================
    RadSideDrawer sideDrawer;
    ArrayList<DrawerListObject> drawerListItems  = new ArrayList<DrawerListObject>();
    DrawerListObject drawerListObject;
    RadListView lv_drawerList;
    DrawerListAdapter drawerListAdapter = new DrawerListAdapter(new ArrayList<>());
    RadListView lv_show_grps;
    RadListView lv_longclick;
    List<String> option_list;
    List<String> option_list_folder;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabGroup;
    FloatingActionButton fabFolder;
    TextView tvback;
    FrameLayout flback;
    Long selectedId;
    List<groupWithCards> myGroups;
    groupWithCards selected_grp;
    ShowGroupAdapter Adapter = new ShowGroupAdapter(new ArrayList<>());
    longClick_adapter longclick_adapter = new longClick_adapter(new ArrayList<>());

    RelativeLayout rel_onLongClick;

    groups_bl grpBl = new groups_bl(this);
    cards_bl crdBl = new cards_bl(this);

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        final Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");

        sideDrawer = new RadSideDrawer(this);
        sideDrawer.setDrawerContent(R.layout.activity_main_drawer);
        sideDrawer.setMainContent(R.layout.activity_main_test);
        sideDrawer.setDrawerLocation(DrawerLocation.RIGHT);
        sideDrawer.setDrawerSize((int) Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 300));
        setContentView(sideDrawer);
        //___________________listView_of_drawer________________________
        lv_drawerList = (RadListView)findViewById(R.id.lv_drawer);
        List<String> drawerTitleLines = Arrays.asList(getResources().getStringArray(R.array.title_setting_list_fa));
        List<String> accountRightIconLines = Arrays.asList(getResources().getStringArray(R.array.right_icon_setting_list_fa));
        drawerListItems.clear();
        for (int i=0; i< drawerTitleLines.size();i++){
            drawerListObject = new DrawerListObject();
            drawerListObject.setTitle(drawerTitleLines.get(i));
            drawerListObject.setRightIcon(accountRightIconLines.get(i));

            drawerListItems.add(i, drawerListObject);
        }
        drawerListAdapter = new DrawerListAdapter(drawerListItems);
        lv_drawerList.setAdapter(drawerListAdapter);

        //___drawer listview onClick___
        RadListView.ItemClickListener item_lv_account = new RadListView.ItemClickListener() {
            @Override
            public void onItemClick(int position, MotionEvent motionEvent) {
                switch (position){
                    case 0://load file
                        purchase_bl purchaseBl= new purchase_bl(MainTest.this);
                        int  buy=purchaseBl.countThisName("backup");
                        if (buy==0){
                            Intent buyIntent= new Intent(MainTest.this, BuyLoadfile.class);
                            startActivity(buyIntent);
                        }else{
                            Intent GetFileIntent3 = new Intent(MainTest.this, GetFileActivity.class);
                            GetFileIntent3.putExtra("selectedId",selectedId);
                            startActivity(GetFileIntent3);
                        }
                        Handler handler8 = new Handler();
                        handler8.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                sideDrawer.setIsOpen(false);
                            }
                        }, 200);
                        break;
                    case 1://download package
                        Intent downloadIntent = new Intent(MainTest.this, DownloadActivity.class);
                        startActivity(downloadIntent);
                        Handler handler9 = new Handler();
                        handler9.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                sideDrawer.setIsOpen(false);
                            }
                        }, 200);
                        break;
                    case 2://setting
                        Intent settingIntent = new Intent(MainTest.this,SettingActivity.class);
                        startActivity(settingIntent);
//                        startActivityForResult(settingIntent,1);
                        Handler handler4 = new Handler();
                        handler4.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                sideDrawer.setIsOpen(false);
                            }
                        }, 200);
                        break;
                    case 3://help
                        Intent intentHelp = new Intent(MainTest.this,HelpActivity.class);
                        startActivity(intentHelp);
//                        startActivityForResult(intentHelp,1);
                        Handler handler6 = new Handler();
                        handler6.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                sideDrawer.setIsOpen(false);
                            }
                        }, 200);
                        break;
                    case 4://about us
                        Intent linAbout_Intent2 = new Intent(MainTest.this, AboutActivity.class);
                        startActivity(linAbout_Intent2);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                sideDrawer.setIsOpen(false);
                            }
                        }, 200);
                        break;
                }
            }

            @Override
            public void onItemLongClick(int position, MotionEvent motionEvent) {
            }
        };
        lv_drawerList.addItemClickListener(item_lv_account);
        //______________Drawer_Change_Listener_Help_________________________________
        sideDrawer.addChangeListener(new DrawerChangeListener() {
            @Override
            public boolean onDrawerOpening(RadSideDrawer radSideDrawer) {
                return false;
            }
            @Override
            public void onDrawerOpened(RadSideDrawer radSideDrawer) {
                String text = "پس از پشتیبان گیری از گروه، از این قسمت می توانید آن را بارگذاری کنید.";
                TextPaint tpaint = new TextPaint();
                tpaint.setAntiAlias(true);
                tpaint.setTypeface(custom_font);
                tpaint.setTextSize(30);
                tpaint.setColor(Color.WHITE);

                Button customButton = (Button) getLayoutInflater().inflate(R.layout.view_custom_button, null);
                customButton.setText("باشه");
                customButton.setTypeface(custom_font);
                customButton.setTextSize(28);
                customButton.setTextColor(Color.parseColor("#00B8D4"));
            }
            @Override
            public boolean onDrawerClosing(RadSideDrawer radSideDrawer) {
                return false;
            }
            @Override
            public void onDrawerClosed(RadSideDrawer radSideDrawer) {
            }
        });

        //______________premission android 6___________________________
        boolean hasPermission = (ContextCompat.checkSelfPermission(MainTest.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(MainTest.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }

        TextView appName = (TextView) findViewById(R.id.tv_appName);
        appName.setText(R.string.app_name);
        appName.setTypeface(custom_font);

        TextView drawer = (TextView) findViewById(R.id.tv_drawer);
        drawer.setText(R.string.app_drawer);
        drawer.setTypeface(font);
        drawer.setOnClickListener(this);

        FrameLayout fldrawer=(FrameLayout) findViewById(R.id.fl_drawer);
        fldrawer.setOnClickListener(this);

        tvback = (TextView) findViewById(R.id.tvback);
        tvback.setOnClickListener(this);
        tvback.setTypeface(font);
        tvback.setText(R.string.icon_back);

        flback = (FrameLayout) findViewById(R.id.flback);
        flback.setOnClickListener(this);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);

        fabGroup = (FloatingActionButton) findViewById(R.id.fab_group);
        fabGroup.setOnClickListener(this);


        fabFolder = (FloatingActionButton) findViewById(R.id.fab_folder);
        fabFolder.setOnClickListener(this);

        rel_onLongClick = (RelativeLayout) findViewById(R.id.rel_onLongClick);
        rel_onLongClick.setOnClickListener(this);

        //---listview----rel_onLongClick
        option_list = Arrays.asList(getResources().getStringArray(R.array.option_list));
        option_list_folder = Arrays.asList(getResources().getStringArray(R.array.option_list_folder));
        lv_longclick = (RadListView)findViewById(R.id.lv_longclick);
        RadListView.ItemClickListener RadList_longClick = new RadListView.ItemClickListener() {
            @Override
            public void onItemClick(int position, MotionEvent motionEvent) {
                if (selected_grp.getType().equals("group")){
                    //selected_grp.getType().equals(true)
                    switch (position){//backup
                        case 0:
                            List<card>crds;
                            crds=crdBl.getCards(cardDao.Properties.GroupId.eq(selected_grp.getId()));
                            //make dir
                            File root = android.os.Environment.getExternalStorageDirectory();
                            File dir = new File(root.getAbsolutePath() + "/dolphin_Backup");
                            dir.mkdirs();
                            //TODO
                            try {
                                CSVWriter writer = new CSVWriter(
                                        new OutputStreamWriter(new FileOutputStream(getExternalFilesDir(null) +"/dolphin_backup/" + selected_grp.getGroupName()+".csv"), StandardCharsets.UTF_8),
                                        '\t',
                                        CSVWriter.DEFAULT_QUOTE_CHARACTER,
                                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                        CSVWriter.DEFAULT_LINE_END
                                );


//                                CSVWriter writer = new CSVWriter(new FileWriter(Environment.getExternalStorageDirectory() +"/dolphin_backup/"+selected_grp.getGroupName()+".csv"),'\t');
                                for(int i=0;i<crds.size();i++) {
                                    String crd_grpId=String.valueOf(crds.get(i).getGroupId());
                                    String grp_name = String.valueOf(String.valueOf(selected_grp.getGroupName()));
                                    String crd_question=String.valueOf(crds.get(i).getQuestion());
                                    String crd_answer=String.valueOf(crds.get(i).getAnswer());
                                    String crd_cardcel=String.valueOf(crds.get(i).getCardCel());
                                    String crd_Ldate=String.valueOf(crds.get(i).getLDate());
                                    String[] crd_value={crd_grpId,grp_name,crd_question,crd_answer,crd_cardcel,crd_Ldate};
                                    writer.writeNext(crd_value);
                                }
                                writer.close();
                                showMsg("پشتیبان گیری","فایل در پوشه ی dolphin_backup ذخیره شد.");
                                rel_onLongClick.setVisibility(View.INVISIBLE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            break;
                        case 1://edit
                            Intent editeGrpName = new Intent(MainTest.this, AddNewGroupActivity.class);
                            editeGrpName.putExtra("Group_Id", selected_grp.getId());
                            startActivityForResult(editeGrpName, 1);
                            rel_onLongClick.setVisibility(View.INVISIBLE);
                            break;
                        case 2://delete group
                            new deletegrp().execute();
                            rel_onLongClick.setVisibility(View.INVISIBLE);
                            break;
                    }
                }else {//selected group type are: folder Or download
                    switch (position){
                        case 0:
                            Intent editeGrpName = new Intent(MainTest.this, AddNewGroupActivity.class);
                            editeGrpName.putExtra("Group_Id", selected_grp.getId());
                            startActivityForResult(editeGrpName, 1);
                            rel_onLongClick.setVisibility(View.INVISIBLE);
                            break;
                        case 1://delete folder
                            new deleteWithSubGrp().execute();
                            rel_onLongClick.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
            }
            @Override
            public void onItemLongClick(int i, MotionEvent motionEvent) {}
        };
        lv_longclick.addItemClickListener(RadList_longClick);

        longclick_adapter = new longClick_adapter(option_list);
        lv_longclick.setAdapter(longclick_adapter);

        //MainActivity listview
        lv_show_grps = (RadListView)findViewById(R.id.lv_show_grps);
        RadListView.ItemClickListener showOnItemClickListener = new RadListView.ItemClickListener() {
            @Override
            public void onItemClick(int position, MotionEvent motionEvent) {
                selected_grp = myGroups.get(position);
                if (!(selected_grp.getType().equals("folder"))){
                    //selected_grp.getType().equals(true)
                    Intent IntentGroup = new Intent(MainTest.this, SelectedGroupActivity.class);
                    IntentGroup.putExtra("position", position);
                    IntentGroup.putExtra("GroupName", selected_grp.getGroupName());
                    startActivity(IntentGroup);
                }else{
                    selectedId = selected_grp.getId();
                    new LoadingPage().execute();
                }
                if(fabMenu.isExpanded()) {
                    fabMenu.collapse();
                }
            }
            @Override
            public void onItemLongClick(int position, MotionEvent motionEvent) {

                selected_grp = myGroups.get(position);
                //if folder longClick then just show delete and edit
                if (selected_grp.getType().equals("group")){
                    //selected_grp.getType().equals(true)
                    longclick_adapter = new longClick_adapter(option_list);
                    lv_longclick.setAdapter(longclick_adapter);
                }else{
                    longclick_adapter = new longClick_adapter(option_list_folder);
                    lv_longclick.setAdapter(longclick_adapter);
                }
                rel_onLongClick.setVisibility(View.VISIBLE);
                if(fabMenu.isExpanded()) {
                    fabMenu.collapse();
                }
            }
        };
        lv_show_grps.addItemClickListener(showOnItemClickListener);

//_________________HELP_layout_Main_page_LIST_VIEW_________________________
        String text= "از این قسمت می توانید به تنظیمات برنامه دسترسی داشته باشید";
        TextPaint tpaint = new TextPaint();
        tpaint.setAntiAlias(true);
        tpaint.setTypeface(custom_font);
        tpaint.setTextSize(30);
        tpaint.setColor(Color.WHITE);

        Button customButton = (Button) getLayoutInflater().inflate(R.layout.view_custom_button, null);
        customButton.setText("باشه");
        customButton.setTypeface(custom_font);
        customButton.setTextSize(28);
        customButton.setTextColor(Color.parseColor("#00B8D4"));
    }

    //wriable premission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted or use the features what required the permission
                } else
                {
                    Toast.makeText(MainTest.this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private class LoadingPage extends AsyncTask<Void,Void, String[]>{
        @Override
        protected void onPreExecute(){
            dialog = new SpotsDialog(MainTest.this);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(Void... voids) {//read from db
            if (selectedId ==null){
                selectedId = -1L;
            }
            myGroups = grpBl.readGroupsWithCards(selectedId);
            return new String[0];
        }
        @Override
        protected void onPostExecute(String[] result) {//show read files in listview
            reload_main_listView();
            if (selectedId!=-1L){
                flback.setVisibility(View.VISIBLE);
            }else{
                flback.setVisibility(View.INVISIBLE);
            }
            dialog.dismiss();
            super.onPostExecute(result);
        }

    }

    private class deletegrp extends AsyncTask<Void,Void, String[]>{
        @Override
        protected void onPreExecute(){
            dialog = new SpotsDialog(MainTest.this);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(Void... voids) {//read from db
            del_listView_item();
            myGroups = grpBl.readGroupsWithCards(selectedId);
           return new String[0];
        }
        @Override
        protected void onPostExecute(String[] result) {//show read files in listview
            dialog.dismiss();
            reload_main_listView();
            super.onPostExecute(result);
        }
    }

    private class deleteWithSubGrp extends AsyncTask<Void,Void, String[]>{
        @Override
        protected void onPreExecute(){
            dialog = new SpotsDialog(MainTest.this);
            dialog.show();
        }

        @Override
        protected String[] doInBackground(Void... voids) {//read from db
            grpBl.delete_with_child(selected_grp.getId());
            myGroups = grpBl.readGroupsWithCards(selectedId);
            return new String[0];
        }
        @Override
        protected void onPostExecute(String[] result) {//show read files in listview
            dialog.dismiss();
            reload_main_listView();
            super.onPostExecute(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_drawer:
                sideDrawer.setIsOpen(true);
                setContentView(sideDrawer);
                break;
            case R.id.tv_drawer:
                sideDrawer.setIsOpen(true);
                setContentView(sideDrawer);
                break;
            case R.id.fab_group:
                Intent addgrp_intent = new Intent(MainTest.this,AddNewGroupActivity.class);
                addgrp_intent.putExtra("GroupTitle", "نام گروه");
                addgrp_intent.putExtra("selectedId",selectedId);
                addgrp_intent.putExtra("type","group");
                startActivity(addgrp_intent);
                break;
            case R.id.fab_folder:
                Intent addfld_intent = new Intent(MainTest.this,AddNewGroupActivity.class);
                addfld_intent.putExtra("GroupTitle", "نام پوشه");
                addfld_intent.putExtra("selectedId",selectedId);
                addfld_intent.putExtra("type","folder");
                startActivity(addfld_intent);
                break;
            case R.id.rel_onLongClick:
                rel_onLongClick.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_ok:
                RelativeLayout relhelp=(RelativeLayout) findViewById(R.id.rel_help);
                relhelp.setVisibility(View.INVISIBLE);
                break;
            case R.id.fl_ok:
                RelativeLayout relHelp = (RelativeLayout) findViewById(R.id.rel_help);
                relHelp.setVisibility(View.INVISIBLE);
                break;
            case R.id.flback:
                group back_group = grpBl.read_groups_previous(selectedId);
                selectedId = back_group.getParentId();
                new LoadingPage().execute();
                break;
            case R.id.tvback:
                group back_group2 = grpBl.read_groups_previous(selectedId);
                selectedId = back_group2.getParentId();
                new LoadingPage().execute();
                break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (rel_onLongClick.getVisibility()==View.VISIBLE){
            rel_onLongClick.setVisibility(View.INVISIBLE);
        }else{
            if (selectedId==-1){//if drawer is open close it
                if(this.sideDrawer.getIsOpen()){
                    this.sideDrawer.setCloseOnBackPress(true);
                    if(fabMenu.isExpanded()) {
                        fabMenu.collapse();
                    }
                }else if(fabMenu.isExpanded()) {
                    fabMenu.collapse();
                }else{//2 times click back to exit
                    if (doubleBackToExitPressedOnce) {
                        super.onBackPressed();
                        return;
                    }
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, "لطفا برای خروج دوباره بر روی برگشت کلیک کنید", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce=false;
                        }
                    }, 2000);}
            }else if(fabMenu.isExpanded()) {
                fabMenu.collapse();
            }else{//else go back to previous folder
                group back_group = grpBl.read_groups_previous(selectedId);
                selectedId = back_group.getParentId();
                new LoadingPage().execute();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        new LoadingPage().execute();
        if (fabMenu.isExpanded()){
            fabMenu.collapse();
        }
        if(dialog.isShowing()){
            //if its visibility is showing then dismiss here
            dialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                //reload listview
                new LoadingPage().execute();
                break;
        }
    }

    public void reload_main_listView (){

        Adapter = new ShowGroupAdapter(myGroups);
        lv_show_grps.setAdapter(Adapter);

    }

    public void del_listView_item(){//delete selected group and its cards
        group delGrp = new group();
        delGrp.setId(selected_grp.getId());
        delGrp.setGroupName(selected_grp.getGroupName());
        grpBl.delete(delGrp);
    }

    public void showMsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("OK",null);
        Log.d("inja", "msgdialog fun");
        builder.show();
    }
}
