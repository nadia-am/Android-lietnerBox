package com.example.nadia.lietner_box;
import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nadia.lietner_box.bl.cards_bl;
import com.example.nadia.lietner_box.bl.groups_bl;
import com.example.nadia.lietner_box.bl.purchase_bl;
import com.example.nadia.lietner_box.models.DaoSession;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.cardDao;
import com.example.nadia.lietner_box.models.group;
import com.example.nadia.lietner_box.models.groupDao;
import com.example.nadia.lietner_box.models.groupWithCards;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.opencsv.CSVWriter;
import com.telerik.android.common.Util;
import com.telerik.android.primitives.widget.sidedrawer.DrawerChangeListener;
import com.telerik.android.primitives.widget.sidedrawer.DrawerLocation;
import com.telerik.android.primitives.widget.sidedrawer.RadSideDrawer;
import com.telerik.widget.list.RadListView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //_____________BAZAAR
    IUpdateCheckService service;
    UpdateServiceConnection connection;
    private static final String TAG = "UpdateCheck";
    class UpdateServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IUpdateCheckService.Stub
                    .asInterface((IBinder) boundService);
            try {
                long vCode = service.getVersionCode("com.example.nadia.lietner_box");
                Toast.makeText(MainActivity.this, "Version Code:" + vCode,Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e(TAG, "onServiceConnected(): Connected");
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Log.e(TAG, "onServiceDisconnected(): Disconnected");
        }
    }
    private void initService() {
        Log.i(TAG, "initService()");
        connection = new UpdateServiceConnection();
        Intent i = new Intent(
                "com.farsitel.bazaar.service.UpdateCheckService.BIND");
        i.setPackage("com.farsitel.bazaar");
        boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
        Log.e(TAG, "initService() bound value: " + ret);
    }
    /** This is our function to un-binds this activity from our service. */
    private void releaseService() {
        unbindService(connection);
        connection = null;
        Log.d(TAG, "releaseService(): unbound.");
    }
    //_____________
    public ListView list;
    private ArrayList<String> arrResult = new ArrayList<String>();
    private ArrayList<Integer> allCard = new ArrayList<Integer>();
    private ArrayList<Integer> reviewable = new ArrayList<Integer>();
    //---DB------
    public DaoSession daoSession;
    List<group> allGrpName;
    //---Bazaar-----
    private GoogleApiClient client;
    private static final int REQUEST_WRITE_STORAGE = 112;

    RadSideDrawer sideDrawer;

    //-----------------------
    ArrayList<DrawerListObject> drawerListItems  = new ArrayList<DrawerListObject>();
    DrawerListObject drawerListObject;
    RadListView lv_drawerList;
    DrawerListAdapter drawerListAdapter = new DrawerListAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        final Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        super.onCreate(savedInstanceState);
        sideDrawer = new RadSideDrawer(this);
        sideDrawer.setDrawerContent(R.layout.activity_main_drawer);
        sideDrawer.setMainContent(R.layout.activity_main);
        sideDrawer.setDrawerLocation(DrawerLocation.RIGHT);
        sideDrawer.setDrawerSize((int) Util.getDimen(TypedValue.COMPLEX_UNIT_DIP, 300));
        setContentView(sideDrawer);

        initService();
        //___________________listView_of_drawer________________________
        lv_drawerList = (RadListView)findViewById(R.id.lv_drawer);
        List<String> drawerTitleLines = Arrays.asList(
                getResources().getStringArray(R.array.title_setting_list_fa));
        List<String> accountRightIconLines = Arrays.asList(
                getResources().getStringArray(R.array.right_icon_setting_list_fa));
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
                    case 0:
                        purchase_bl purchaseBl= new purchase_bl(MainActivity.this);
                        int  buy=purchaseBl.countThisName("backup");
                        if (buy==0){
                            Intent buyIntent= new Intent(MainActivity.this, BuyLoadfile.class);
                            startActivity(buyIntent);
                        }else{
                            Intent GetFileIntent3 = new Intent(MainActivity.this, GetFileActivity.class);
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
                    case 1:
                        Intent settingIntent = new Intent(MainActivity.this,SettingActivity.class);
                        startActivityForResult(settingIntent,1);
                        Handler handler4 = new Handler();
                        handler4.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                sideDrawer.setIsOpen(false);
                            }
                        }, 200);
                        break;
                    case 2:
                        Intent intentHelp = new Intent(MainActivity.this,HelpActivity.class);
                        startActivityForResult(intentHelp,1);
                        Handler handler6 = new Handler();
                        handler6.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                sideDrawer.setIsOpen(false);
                            }
                        }, 200);
                        break;
                    case 3:
                        Intent linAbout_Intent2 = new Intent(MainActivity.this, AboutActivity.class);
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
                if (isFirstTime1()) {
                    new ShowcaseView.Builder(MainActivity.this)
                            .withMaterialShowcase()
                            .setStyle(R.style.CustomShowcaseTheme3)
                            .setTarget(new ViewTarget(lv_drawerList.getChildAt(0).findViewById(R.id.tv_icon)))
                            .setContentText(text)
                            .setContentTextPaint(tpaint)
                            .replaceEndButton(customButton)
                            .build();
                }
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
        boolean hasPermission = (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
        //define textView
//        FloatingActionButton fabGroup = (FloatingActionButton) findViewById(R.id.fab_group);
//        fabGroup.setOnClickListener(this);
//
//        FloatingActionButton fabFolder = (FloatingActionButton) findViewById(R.id.fab_folder);
//        fabFolder.setOnClickListener(this);

        TextView dolphin=(TextView) findViewById(R.id.tv_dolphin) ;
        dolphin.setText(R.string.app_dolphin);
        dolphin.setTypeface(custom_font);

        TextView appName = (TextView) findViewById(R.id.tv_appName);
        appName.setText(R.string.app_name);
        appName.setTypeface(custom_font);

        TextView drawer = (TextView) findViewById(R.id.tv_drawer);
        drawer.setText(R.string.app_drawer);
        drawer.setTypeface(font);
        drawer.setOnClickListener(this);

        FrameLayout fldrawer=(FrameLayout) findViewById(R.id.fl_drawer);
        fldrawer.setOnClickListener(this);

//        final FloatingActionButton creatGroup = (FloatingActionButton) findViewById(R.id.btn_creatGroup);
//        creatGroup.setOnClickListener(this);

        ListView listview = (ListView) findViewById(R.id.listView);
//**************LiST*View*****
        groups_bl grp_BL = new groups_bl(this);
        List<groupWithCards> groupCards;

        groupCards = grp_BL.getGroupsWithCards(null);
        if (groupCards!=null){
            listUpdate(groupCards);
            //hid FABbtn on scroll down&up
            listview.setOnScrollListener(new AbsListView.OnScrollListener() {
                private int mLastFirstVisibleItem;
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }
                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (mLastFirstVisibleItem < firstVisibleItem) {
                        Log.i("SCROLLING DOWN", "TRUE");
//                        creatGroup.hide();
                    }
                    if (mLastFirstVisibleItem > firstVisibleItem) {
                        Log.i("SCROLLING UP", "TRUE");
//                        creatGroup.show();
                    }
                    mLastFirstVisibleItem = firstVisibleItem;
                }
            });
        }
       client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

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
            if (isFirstTime2()){
                new ShowcaseView.Builder(this)
                        .withMaterialShowcase()
                        .setStyle(R.style.CustomShowcaseTheme3)
                        .setTarget(new ViewTarget(R.id.fl_drawer, this))
                        .setContentText(text)
                        .setContentTextPaint(tpaint)
                        .replaceEndButton(customButton)
                        .build();
            }
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
                    Toast.makeText(MainActivity.this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_drawer:
                sideDrawer.setIsOpen(true);
                setContentView(sideDrawer);
                break;
            case R.id.tv_drawer:
                sideDrawer.setIsOpen(true);
                setContentView(sideDrawer);
                break;
            case R.id.tv_ok:
                RelativeLayout relhelp=(RelativeLayout) findViewById(R.id.rel_help);
                relhelp.setVisibility(View.INVISIBLE);
                break;
            case R.id.fl_ok:
                RelativeLayout relHelp = (RelativeLayout) findViewById(R.id.rel_help);
                relHelp.setVisibility(View.INVISIBLE);
        }
    }

    private void listUpdate(List<groupWithCards> groupCards) {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");
        final ListView list = (ListView) findViewById(R.id.listView);
        //___________________________________________________________
        final groups_bl grp_BL = new groups_bl(this);
        final cards_bl crd_BL = new cards_bl(this);
        allGrpName=grp_BL.getGroups(null);
        //___________________________________________________________
        arrResult.clear();
        //get Group Name FRom arrList & put it in listVIew*
        for (int i = 0; allGrpName.size() > i; i++) {
            arrResult.add(allGrpName.get(i).getGroupName());
        }
        allCard.clear();
        reviewable.clear();
        for (int i = 0; i < groupCards.size(); i++) {
            allCard.add(groupCards.get(i).allCardNumber);
            reviewable.add(groupCards.get(i).reviewableCard);
        }
        CustomListAdapter adapter = new CustomListAdapter(this, arrResult, allCard, reviewable, custom_font, BNazanin);
        list.setAdapter(adapter);

        //****************************************

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // ListView Clicked item value
                String itemValue = (String) list.getItemAtPosition(position);
                Intent IntentGroup = new Intent(MainActivity.this, SelectedGroupActivity.class);
                IntentGroup.putExtra("position", position);
                IntentGroup.putExtra("GroupName", itemValue);
                startActivity(IntentGroup);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
                final String G_Name = (String) list.getItemAtPosition(position);
                CharSequence items[] = new CharSequence[]{"ویرایش","حذف","پشتیبان گیری"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //find out group Id base on group name
                        //___________________________________________________________
                        group getGrp = null;
                        getGrp=grp_BL.getGroup(groupDao.Properties.GroupName.eq(G_Name));
                        switch (which) {
                            case 0:
                                Intent editeGrpName = new Intent(MainActivity.this, AddNewGroupActivity.class);
                                editeGrpName.putExtra("Group_Id", getGrp.getId());
                                startActivityForResult(editeGrpName, 1);
                                break;
                            case 1:
                                //delete selected group base on id
                                grp_BL.delete(getGrp);
                                List<groupWithCards> groupCards;
                                groupCards = grp_BL.getGroupsWithCards(null);
                                if (groupCards!=null){
                                    listUpdate(groupCards);
                                }
                                break;
                            case 2:
                                List<card>crds;
                                crds=crd_BL.getCards(cardDao.Properties.GroupId.eq(getGrp.getId()));
                                //make dir
                                File root = android.os.Environment.getExternalStorageDirectory();
                                File dir = new File(root.getAbsolutePath() + "/dolphin_Backup");
                                dir.mkdirs();
//                                try {
//                                    CSVWriter writer = new CSVWriter(new FileWriter(Environment.getExternalStorageDirectory() +"/dolphin_backup/"+getGrp.getGroupName()+".csv"),'\t');
//                                    for(int i=0;i<crds.size();i++) {
//                                        String crd_grpId=String.valueOf(crds.get(i).getGroupId());
//                                        String grp_name = String.valueOf(String.valueOf(getGrp.getGroupName()));
//                                        String crd_question=String.valueOf(crds.get(i).getQuestion());
//                                        String crd_answer=String.valueOf(crds.get(i).getAnswer());
//                                        String crd_cardcel=String.valueOf(crds.get(i).getCardCel());
//                                        String crd_Ldate=String.valueOf(crds.get(i).getLDate());
//                                        String[] crd_value={crd_grpId,grp_name,crd_question,crd_answer,crd_cardcel,crd_Ldate};
//                                        writer.writeNext(crd_value);
//                                    }
//                                    writer.close();
//                                    showMsg("پشتیبان گیری","فایل در پوشه ی dolphin_backup ذخیره شد.");
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        switch (requestCode) {
            case 1:
                groups_bl grp_BL = new groups_bl(this);
                List<groupWithCards> groupCards;
                groupCards = grp_BL.getGroupsWithCards(null);
                if (groupCards!=null){
                    listUpdate(groupCards);
                }
                List<group> allGrpList;
                allGrpList=grp_BL.getGroups(null);
                if (allGrpList.size()>=1){
                    if (isFirstTime3()) {
                        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
                        RelativeLayout relHelp = (RelativeLayout) findViewById(R.id.rel_help);
                        relHelp.setVisibility(View.VISIBLE);
                        TextView tvtxt = (TextView) findViewById(R.id.tv_txt);
                        tvtxt.setText("در صورتی که هر کدام از گروه ها را نگه دارید، لیستی نمایش داده می شود که امکان حذف، ویرایش و پشتیبان گیری از گروه فراهم می شود.");
                        tvtxt.setTypeface(custom_font);

                        TextView tvok = (TextView) findViewById(R.id.tv_ok);
                        tvok.setText("باشه");
                        tvok.setTypeface(custom_font);

                        FrameLayout flok = (FrameLayout) findViewById(R.id.fl_ok);
                        flok.setOnClickListener(this);
                    }
                }
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        groups_bl grp_BL = new groups_bl(this);
        List<groupWithCards> groupCards;
        groupCards = grp_BL.getGroupsWithCards(null);
        if (groupCards!=null){
            listUpdate(groupCards);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.nadia.lietner_box/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }
    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.nadia.lietner_box/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if(this.sideDrawer.getIsOpen()){
            this.sideDrawer.setCloseOnBackPress(true);
        }else{
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
            }, 2000);
        }
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

    private boolean isFirstTime1()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore1", false);

        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore1", true);
            editor.commit();
        }
        return !ranBefore;
    }
    private boolean isFirstTime2()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore2", false);

        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore2", true);
            editor.commit();
        }
        return !ranBefore;
    }
    private boolean isFirstTime3()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore3", false);

        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore3", true);
            editor.commit();
        }
        return !ranBefore;
    }

}
