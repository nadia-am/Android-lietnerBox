package com.example.nadia.lietner_box;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.nadia.lietner_box.bl.clustering;
import com.example.nadia.lietner_box.bl.groups_bl;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.eachGroup;
import com.example.nadia.lietner_box.lib.errorMsg;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.group;
import com.example.nadia.lietner_box.models.groupDao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import is.arontibo.library.ElasticDownloadView;


/**
 * Created by Nadia on 12/26/2016.
 */

public class AddDLToListActivity extends AppCompatActivity implements View.OnClickListener {
    String selectedItem;
    String name_of_folder;
    Integer amount_of_group;
    ArrayList<Integer> amountOfCards;
    Integer eachUnit;
    String path;
    group newGroup = new group();
    group folder=new group();
    groups_bl grpBl;
    cards_bl crd_BL;
    EditText etFolderName;
    EditText etGrpsName;
    String line = "";
    ArrayList<String> question =new ArrayList<>();
    ArrayList<String> answer = new ArrayList<>();
    errorMsg error = new errorMsg();
    ElasticDownloadView mElasticDownloadView;
    LinearLayout linOther;
    RelativeLayout relLoading;
    String eachGrpName;
    Integer progress;
    boolean readFile ;
    String  nameIsnUniqe="نام گروه تکراری است!";
    boolean startOperation ;
    boolean grpORfolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dl_to_list);
        startOperation = false;

        relLoading = (RelativeLayout) findViewById(R.id.rel_loading);
        relLoading.setVisibility(View.GONE);
        relLoading.setOnClickListener(this);

        mElasticDownloadView = (ElasticDownloadView) findViewById(R.id.elastic_download_view);

        readFile = true;
        progress=13;

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");

        grpBl = new groups_bl(this);
        crd_BL=new  cards_bl(this);

        selectedItem = getIntent().getStringExtra("selected");
        path = getIntent().getStringExtra("path");
        clustering clustering = new clustering();
        eachGroup eachGroup = new eachGroup();
        amountOfCards = new ArrayList<>();
        switch (selectedItem){
            case "1100":
                eachGroup = clustering.computation("1100");
                name_of_folder = "1100";
                amount_of_group = eachGroup.getAmountOfGroup();//19 cards in each group
                amountOfCards = eachGroup.getAmountOfCards();
                break;
            case "first":
                eachGroup = clustering.computation("first");
                name_of_folder = "اول دبیرستان";
                amount_of_group = eachGroup.getAmountOfGroup();//50-72-47-53-37-48-53-46-58
                amountOfCards = eachGroup.getAmountOfCards();
                break;
            case "second":
                eachGroup = clustering.computation("second");
                name_of_folder = "دوم دبیرستان";
                amount_of_group = eachGroup.getAmountOfGroup();//34-45-57-42-53-54-54
                amountOfCards = eachGroup.getAmountOfCards();
                break;
            case "third":
                eachGroup = clustering.computation("third");
                name_of_folder = "سوم دبیرستان";
                amount_of_group = eachGroup.getAmountOfGroup();
                amountOfCards = eachGroup.getAmountOfCards();
                break;
            case "pish":
                eachGroup = clustering.computation("pish");
                name_of_folder = "پیش دانشگاهی";
                amount_of_group = eachGroup.getAmountOfGroup();
                amountOfCards = eachGroup.getAmountOfCards();
                break;
            default:
                eachGroup = clustering.computation("504");
                name_of_folder = "504";
                amount_of_group = eachGroup.getAmountOfGroup();//12 cards each group
                amountOfCards = eachGroup.getAmountOfCards();
                break;
        }

        TextView appname = (TextView) findViewById(R.id.tv_appName2);
        appname.setText(R.string.app_name);
        appname.setTypeface(custom_font);

        TextView back = (TextView) findViewById(R.id.tv_back);
        back.setOnClickListener(this);
        back.setTypeface(font);
        back.setText(R.string.icon_back);

        TextView tvWait = (TextView) findViewById(R.id.tv_wait);
        tvWait.setText(R.string.wait);
        tvWait.setTypeface(custom_font);

        FrameLayout flback = (FrameLayout) findViewById(R.id.flBack);
        flback.setOnClickListener(this);

        TextView folderName = (TextView) findViewById(R.id.tv_folder_n);
        folderName.setText(R.string.folderName);
        folderName.setTypeface(custom_font);

        TextView tvamoutOfGrp = (TextView) findViewById(R.id.tv_amountOfGrp);
        tvamoutOfGrp.setText(R.string.app_amountOfGrp);
        tvamoutOfGrp.setTypeface(custom_font);

        TextView tvamoutOfGrpNum = (TextView) findViewById(R.id.tv_amountOfGrp_num);
        tvamoutOfGrpNum.setText(amount_of_group.toString());
        tvamoutOfGrpNum.setTypeface(BNazanin);

        TextView tvGrpName = (TextView) findViewById(R.id.tv_grps_n);
        tvGrpName.setText(R.string.grpsName);
        tvGrpName.setTypeface(custom_font);

        TextView tvDescript = (TextView) findViewById(R.id.tvdescript);
        tvDescript.setText(R.string.app_descript);
        tvDescript.setTypeface(custom_font);

        etGrpsName = (EditText) findViewById(R.id.et_grps_n);
        etGrpsName.setText("درس "+name_of_folder);
        etGrpsName.setTypeface(custom_font);
        etGrpsName.setSelection(etGrpsName.getText().length());//set curser at the end of group name in textview

        etFolderName = (EditText) findViewById(R.id.et_folder_n);
        etFolderName.setText(name_of_folder);
        etFolderName.setTypeface(custom_font);
        etFolderName.setSelection(etFolderName.getText().length());//set curser at the end of group name in textview

        Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setText(R.string.app_cancel);
        btnCancel.setTypeface(custom_font);
        btnCancel.setOnClickListener(this);

        Button btnSave = (Button) findViewById(R.id.btn_ok);
        btnSave.setText(R.string.app_save);
        btnSave.setTypeface(custom_font);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.flBack:
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_ok:
                startOperation =true;
                folder.setGroupName(etFolderName.getText().toString());
                folder.setParentId(-1L);
                folder.setType("folder");
                new loadFile().execute();
                eachGrpName = etGrpsName.getText().toString();
                break;
        }
    }

    public class loadFile extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute(){
            relLoading = (RelativeLayout) findViewById(R.id.rel_loading);
            relLoading.setVisibility(View.VISIBLE);
            mElasticDownloadView = (ElasticDownloadView) findViewById(R.id.elastic_download_view);
            mElasticDownloadView.startIntro();
            linOther = (LinearLayout) findViewById(R.id.lin_other);
        }
        @Override
        protected String doInBackground(Void... params) {
            question.clear();
            answer.clear();
            try {//read csv file from phone
                BufferedReader br = new BufferedReader(new FileReader(path));
                while ((line = br.readLine()) != null) {
                    String[] fof = line.split("\t");
                    question.add(fof[0].toString());
                    answer.add(fof[1].toString());
                    System.out.println( fof[0] + fof[1] );
                    Log.i("","");
                }
                //****DATA*BASE*********
                try {//add 504 folder
                    //check name of one group  before creating folder(to find out if its uniqe or not)
                    grpORfolder = true;//its true the if exception happen groups name arent uniqe
                    group grp  = new group();
                    grp.setGroupName(eachGrpName+" "+1);
                    grpBl.checkIfUniqe(grp);
                    //-----------------
                    grpORfolder = false;//if its false folder name isnt uniqe
                    grpBl.insertGrp(folder);
                    int x =0; int z=0;
                    //read-just added folder id
                    newGroup=grpBl.getGroup(groupDao.Properties.GroupName.eq(folder.getGroupName()));
                    //add groups with its card
                    for (int i=0 ; i< amount_of_group ; i++){//این حلقه باید به تعداد گروهای(درسها)باشد
                        progress=progress+1;
                        publishProgress(progress);
                        //add a grp in 504 folder
                        String nameGrp =eachGrpName+" "+(i+1);
                        group group=new group();
                        group.setGroupName(nameGrp);
                        group.setParentId(newGroup.getId());
                        group.setType("download");
                        try {
                            grpBl.insertGrp(group);
                            group addGrp = new group();//read just added group
                            addGrp=grpBl.getGroup(groupDao.Properties.GroupName.eq(nameGrp));
                        //insert card operation*****
                            if (amountOfCards.size()==1){//if its like 504 each unit 12 item then array has one value
                                eachUnit = amountOfCards.get(0);
                            }else {
                                eachUnit = amountOfCards.get(z);
                            }
                            int startOfFor = x;
                            int endOfFor =(startOfFor +eachUnit);
                            ArrayList<card> toBeInsert = new ArrayList<>();
                            card cardsToAdd;
                            for (int j = startOfFor; j<(endOfFor) ; j++){//این حلقه در هر گروه ساخته شده تعداد کارتهای لازم را (به ترتیب از پشته) اضافه می کند
                                cardsToAdd = new card();
                                cardsToAdd.setQuestion(question.get(j));
                                cardsToAdd.setAnswer(answer.get(j));
                                cardsToAdd.setGroupId(addGrp.getId());
                                toBeInsert.add(cardsToAdd) ;
                                x =j+1;
                            }
                            z++;
                            crd_BL.bulkInsertCrd(toBeInsert);//insert all 12 cards to group
                        } catch (CustomException cex) {
                            error.setType(true);
                            error.setMsg(cex.getMessage());
                        }catch (Exception ex){
                            error.setType(true);
                            error.setMsg(ex.getMessage());
                        }
                    }
                    File file = new File(path);// delete downloadedfile after add it to our app
                    boolean deleted = file.delete();
                } catch (CustomException cex) {
                    error.setType(true);
                    if (cex.getMessage().equals(nameIsnUniqe)){
                        if (!grpORfolder){
                            error.setMsg("نام پوشه تکراری است");
                        }else {error.setMsg("نام گروهها تکراری است");}

                    }else {
                        error.setMsg(cex.getMessage());
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                readFile = false;
            } catch (IOException e) {
                readFile = false;
                e.printStackTrace();
                readFile = false;
            }catch (Error e){
                e.printStackTrace();
                readFile = false;
            }
            catch (Exception e){
                e.printStackTrace();
                readFile = false;
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... progresss) {
            super.onProgressUpdate(progresss[0]);
            mElasticDownloadView.setProgress(progress);
        }
        @Override
        protected void onPostExecute(String result){
            startOperation = false;
            if (readFile){
                if (error.isType()){
                    mElasticDownloadView.fail();
                    Toast.makeText(getApplicationContext(),error.getMsg(), Toast.LENGTH_LONG).show();
                    error.setType(false);
                    Handler handler8 = new Handler();
                    handler8.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            relLoading.setVisibility(View.GONE);
                        }
                    }, 2000);
                }else {
                    mElasticDownloadView.success();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);

                }
            }else {
                mElasticDownloadView.fail();
                relLoading.setVisibility(View.GONE);
                Toast.makeText(AddDLToListActivity.this,"لطفا دوباره تلاش کنید",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (startOperation && relLoading.getVisibility()==View.VISIBLE) {
            Toast.makeText(AddDLToListActivity.this, "لطفا کمی صبر کنید", Toast.LENGTH_SHORT).show();
        }else {
            Intent backIntent = new Intent(AddDLToListActivity.this,DownloadActivity.class);
            startActivity(backIntent);
        }
    }

}
