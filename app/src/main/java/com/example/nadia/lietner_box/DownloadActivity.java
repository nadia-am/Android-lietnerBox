package com.example.nadia.lietner_box;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadia.lietner_box.bl.purchase_bl;
import com.example.nadia.lietner_box.lib.errorMsg;
import com.example.nadia.lietner_box.lib.method;
import com.example.nadia.lietner_box.models.purchase;
import com.example.nadia.lietner_box.util.IabHelper;
import com.example.nadia.lietner_box.util.IabResult;
import com.example.nadia.lietner_box.util.Inventory;
import com.example.nadia.lietner_box.util.Purchase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nadia on 12/17/2016.
 */

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener{

    AlertDialog dialog;
    String path;
    errorMsg error;
    method mymethod;
    TextView tvget_b;
    TextView tvget_hundred;
    TextView tvget_first;
    TextView tvget_secound;
    TextView tvget_third;
    TextView tvfont_third;
    TextView tvget_pish;
    TextView tvfont_pish;
    TextView tvfontb;
    String url;
    String type;
    TextView tvfont_hundred;
    TextView tvfont_highSchool;
    TextView tvfont_secoundhs;
    // Debug tag, for logging
    static final String TAG = "bazaar";
    // SKUs for our products: the premium upgrade (non-consumable)
    static final String SKU_504 = "504";
    static final String SKU_1100 = "1100";
    static final String SKU_first = "first";
    static final String SKU_second = "second";
    static final String SKU_third = "third";
    static final String SKU_pish = "pish";

    // Does the user have the premium upgrade?
    boolean mIsPremium = false;
    boolean mIsPremium_504 = false;
    boolean mIsPremium_1100 = false;
    boolean mIsPremium_first = false;
    boolean mIsPremium_second = false;
    boolean mIsPremium_third = false;
    boolean mIsPremium_pish = false;

    // (arbitrary) request code for the myPurchase flow
    static final int RC_REQUEST = 10001;
    // The helper object
    IabHelper mHelper;
    purchase_bl purchasebl ;

    LinearLayout lin_downloadFof;
    LinearLayout lin_addlistFof;
    LinearLayout lin_download1100;
    LinearLayout lin_addlist1100;
    LinearLayout lin_downloadFirst;
    LinearLayout lin_addlistFirst;
    LinearLayout lin_downloadSecond;
    LinearLayout lin_addlistSecond;
    LinearLayout lin_downloadThird;
    LinearLayout lin_addlistThird;
    LinearLayout lin_downloadPish;
    LinearLayout lin_addlistPish;
    LinearLayout lin_downloadCountry;
    LinearLayout lin_addlistCountry;
    purchase_bl purchaseBl;
    ArrayList<String> mylist_sku = new ArrayList<>();
    boolean exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        error = new errorMsg();
        error.setType(false);

        purchaseBl=new purchase_bl(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        dialog = new SpotsDialog(DownloadActivity.this);
        dialog.setCancelable(false);

        TextView appname = (TextView) findViewById(R.id.tv_appName2);
        appname.setText(R.string.app_name);
        appname.setTypeface(custom_font);

        TextView back = (TextView) findViewById(R.id.tv_back);
        back.setOnClickListener(this);
        back.setTypeface(font);
        back.setText(R.string.icon_back);

        FrameLayout flback = (FrameLayout) findViewById(R.id.flBack);
        flback.setOnClickListener(this);

        //------------504----------------
        TextView tvfiveOfour = (TextView) findViewById(R.id.tv_fiveOfour);
        tvfiveOfour.setText(R.string.fof);
        tvfiveOfour.setTypeface(custom_font);

        lin_downloadFof =(LinearLayout) findViewById(R.id.lin_download_fof);
        lin_downloadFof.setOnClickListener(this);

        tvget_b = (TextView) findViewById(R.id.tv_get_b);
        tvget_b.setText(R.string.price504);
        tvget_b.setTypeface(custom_font);

        tvfontb = (TextView) findViewById(R.id.tv_font_b);
        tvfontb.setText(R.string.download);
        tvfontb.setTypeface(font);

        lin_addlistFof = (LinearLayout) findViewById(R.id.lin_addList_fof);
        lin_addlistFof.setOnClickListener(this);

        TextView addList = (TextView) findViewById(R.id.tv_add_list);
        addList.setText(R.string.add_to_list);
        addList.setTypeface(custom_font);

        TextView tvfont = (TextView) findViewById(R.id.tv_font_add);
        tvfont.setText(R.string.add_to_list_f);
        tvfont.setTypeface(font);
        //------------1100---------------
        TextView tvhundred = (TextView) findViewById(R.id.tv_hundred);
        tvhundred.setText(R.string.hundred);
        tvhundred.setTypeface(custom_font);

        lin_download1100 =(LinearLayout) findViewById(R.id.lin_download_hundred);
        lin_download1100.setOnClickListener(this);

        tvget_hundred = (TextView) findViewById(R.id.tv_get_hundred);
        tvget_hundred.setText(R.string.price1100);
        tvget_hundred.setTypeface(custom_font);

        tvfont_hundred = (TextView) findViewById(R.id.tv_font_get_hundred);
        tvfont_hundred.setText(R.string.download);
        tvfont_hundred.setTypeface(font);

        lin_addlist1100 = (LinearLayout) findViewById(R.id.lin_addList_h);
        lin_addlist1100.setOnClickListener(this);

        TextView addList_h = (TextView) findViewById(R.id.tv_addToList_hundred);
        addList_h.setText(R.string.add_to_list);
        addList_h.setTypeface(custom_font);

        TextView tvfont_listHundred = (TextView) findViewById(R.id.tv_font_List_hundred);
        tvfont_listHundred.setText(R.string.add_to_list_f);
        tvfont_listHundred.setTypeface(font);

        //------------high-school-1--------------
        TextView tv1highschool = (TextView) findViewById(R.id.tv_first);
        tv1highschool.setText(R.string.highSchool);
        tv1highschool.setTypeface(custom_font);

        lin_downloadFirst =(LinearLayout) findViewById(R.id.lin_download_first);
        lin_downloadFirst.setOnClickListener(this);

        tvget_first = (TextView) findViewById(R.id.tv_get_first);
        tvget_first.setText(R.string.pricefirst);
        tvget_first.setTypeface(custom_font);

        tvfont_highSchool = (TextView) findViewById(R.id.tv_font_get_firsthighschool);
        tvfont_highSchool.setText(R.string.download);
        tvfont_highSchool.setTypeface(font);

        lin_addlistFirst = (LinearLayout) findViewById(R.id.lin_addList_first);
        lin_addlistFirst.setOnClickListener(this);

        TextView addList_fh = (TextView) findViewById(R.id.tv_addToList_fh);
        addList_fh.setText(R.string.add_to_list);
        addList_fh.setTypeface(custom_font);

        TextView tvfont_list_fh = (TextView) findViewById(R.id.tv_font_List_fh);
        tvfont_list_fh.setText(R.string.add_to_list_f);
        tvfont_list_fh.setTypeface(font);
        //------------high-school-2--------------
        TextView tv2highschool = (TextView) findViewById(R.id.tv_secHighSchool);
        tv2highschool.setText(R.string.sechighSchool);
        tv2highschool.setTypeface(custom_font);

        lin_downloadSecond =(LinearLayout) findViewById(R.id.lin_download_secound);
        lin_downloadSecond.setOnClickListener(this);

        tvget_secound = (TextView) findViewById(R.id.tv_get_secound);
        tvget_secound.setText(R.string.pricesec);
        tvget_secound.setTypeface(custom_font);

        tvfont_secoundhs = (TextView) findViewById(R.id.tv_font_get_secoundhs);
        tvfont_secoundhs.setText(R.string.download);
        tvfont_secoundhs.setTypeface(font);

        lin_addlistSecond = (LinearLayout) findViewById(R.id.lin_addlist_second);
        lin_addlistSecond.setOnClickListener(this);

        TextView addList_secoundhs = (TextView) findViewById(R.id.tv_addToList_secoundhs);
        addList_secoundhs.setText(R.string.add_to_list);
        addList_secoundhs.setTypeface(custom_font);

        TextView tvfont_list_secoundhs = (TextView) findViewById(R.id.tv_font_List_secoundhs);
        tvfont_list_secoundhs.setText(R.string.add_to_list_f);
        tvfont_list_secoundhs.setTypeface(font);
        //------------high-school-3--------------
        TextView tv3highschool = (TextView) findViewById(R.id.tv_thirdHighSchool);
        tv3highschool.setText(R.string.thirdhighSchool);
        tv3highschool.setTypeface(custom_font);

        lin_downloadThird =(LinearLayout) findViewById(R.id.lin_download_third);
        lin_downloadThird.setOnClickListener(this);

        tvget_third = (TextView) findViewById(R.id.tv_get_third);
        tvget_third.setText(R.string.pricethird);
        tvget_third.setTypeface(custom_font);

        tvfont_third = (TextView) findViewById(R.id.tv_font_get_thirdhs);
        tvfont_third.setText(R.string.download);
        tvfont_third.setTypeface(font);

        lin_addlistThird = (LinearLayout) findViewById(R.id.lin_addList_third);
        lin_addlistThird.setOnClickListener(this);

        TextView addList_thirdhs = (TextView) findViewById(R.id.tv_addToList_thirdhs);
        addList_thirdhs.setText(R.string.add_to_list);
        addList_thirdhs.setTypeface(custom_font);

        TextView tvfont_list_thirdhs = (TextView) findViewById(R.id.tv_font_List_thirdhs);
        tvfont_list_thirdhs.setText(R.string.add_to_list_f);
        tvfont_list_thirdhs.setTypeface(font);
        //------------high-school-pish--------------
        TextView tvpish = (TextView) findViewById(R.id.tv_pish);
        tvpish.setText(R.string.forthhighSchool);
        tvpish.setTypeface(custom_font);

        lin_downloadPish =(LinearLayout) findViewById(R.id.lin_download_pish);
        lin_downloadPish.setOnClickListener(this);

        tvget_pish = (TextView) findViewById(R.id.tv_get_pish);
        tvget_pish.setText(R.string.pricepish);
        tvget_pish.setTypeface(custom_font);

        tvfont_pish = (TextView) findViewById(R.id.tv_font_get_pish);
        tvfont_pish.setText(R.string.download);
        tvfont_pish.setTypeface(font);

        lin_addlistPish = (LinearLayout) findViewById(R.id.lin_addList_pish);
        lin_addlistPish.setOnClickListener(this);

        TextView addList_pish = (TextView) findViewById(R.id.tv_addToList_pish);
        addList_pish.setText(R.string.add_to_list);
        addList_pish.setTypeface(custom_font);

        TextView tvfont_list_pish = (TextView) findViewById(R.id.tv_font_List_pish);
        tvfont_list_pish.setText(R.string.add_to_list_f);
        tvfont_list_pish.setTypeface(font);
        //---------------country-----------------
        TextView tvcountry = (TextView) findViewById(R.id.tv_country);
        tvcountry.setText(R.string.country);
        tvcountry.setTypeface(custom_font);

        lin_downloadCountry =(LinearLayout) findViewById(R.id.lin_download_country);
        lin_downloadCountry.setOnClickListener(this);

        TextView tvget_country = (TextView) findViewById(R.id.tv_get_country);
        tvget_country.setText(R.string.pricecountry);
        tvget_country.setTypeface(custom_font);

        TextView tvfont_country = (TextView) findViewById(R.id.tv_font_get_country);
        tvfont_country.setText(R.string.download);
        tvfont_country.setTypeface(font);

        lin_addlistCountry = (LinearLayout) findViewById(R.id.lin_addList_country);
        lin_addlistCountry.setOnClickListener(this);

        TextView addList_country = (TextView) findViewById(R.id.tv_addToList_country);
        addList_country.setText(R.string.add_to_list);
        addList_country.setTypeface(custom_font);

        TextView tvfont_list_country = (TextView) findViewById(R.id.tv_font_List_country);
        tvfont_list_country.setText(R.string.add_to_list_f);
        tvfont_list_country.setTypeface(font);

        exist = purchaseBl.ifExist(SKU_504);
        if (!exist){mylist_sku.add(SKU_504);}
        else {tvget_b.setText(R.string.download_file);
            tvfontb.setText(R.string.get_download);}

        exist = purchaseBl.ifExist(SKU_1100);
        if (!exist){mylist_sku.add(SKU_1100);}
        else{tvget_hundred.setText(R.string.download_file);
            tvfont_hundred.setText(R.string.get_download);}

        exist = purchaseBl.ifExist(SKU_first);
        if (!exist){mylist_sku.add(SKU_first);}
        else {tvget_first.setText(R.string.download_file);
              tvfont_highSchool.setText(R.string.get_download);}

        exist = purchaseBl.ifExist(SKU_second);
        if (!exist){mylist_sku.add(SKU_second);}
        else {tvget_secound.setText(R.string.download_file);
              tvfont_secoundhs.setText(R.string.get_download);}

        exist = purchaseBl.ifExist(SKU_third);
        if (!exist){mylist_sku.add(SKU_third);}
        else {tvget_third.setText(R.string.download_file);
            tvfont_third.setText(R.string.get_download);}

        exist = purchaseBl.ifExist(SKU_pish);
        if (!exist){mylist_sku.add(SKU_pish);}
        else {tvget_pish.setText(R.string.download_file);
            tvfont_pish.setText(R.string.get_download);}

        if (mylist_sku.size()>0){dialog.show();}
        //------bazaar-(pardakht daron barname)-------------
        try {
            //برای اتصال به بازار
            String base64EncodedPublicKey="MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwC0d77kCTFVd2m3BlbrjicmRnPCXxmsBpgA2AuuyT8bHz+ZXow0YE5tTsDBptewmkCqV3or5f1r04FpqNx2Fh7HobIhNMkQaPqLfSY9gvVndV6Bk9EkXSRzqBhuwhgQLptn6g8jygRml3adm6v9YiYqmKuahr1x6t7q3IuaaFJHs61eXhoX90ZtGevRUn2cARAyUmc9eyw5exMdjatANe+zsMPrnYUcZd+iVfzURgcCAwEAAQ==";
            mHelper = new IabHelper(this, base64EncodedPublicKey);
            Log.d(TAG, "Starting setup.");
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    Log.d(TAG, "Setup finished.");
                    if (!result.isSuccess()) {
                        dialog.dismiss();
                        // در صورتی که نسخه ی API پشتیبانی نشود.
                        Log.d(TAG, "مشکل در راه اندازی نسخه اندروید! " + result);
                    }else{
                        mymethod = new method(DownloadActivity.this);
                        if (mymethod.IsNetworkConnected()) {
                            //لیست خرید کاربر برمی گردونه در Inventory object ذخیره می کنه و به listener بر می گرداند
                            if (mylist_sku.size()>0){
                                mHelper.queryInventoryAsync(true, mylist_sku, mGotInventoryListener);
                            }
                        }else {
                            Toast.makeText(DownloadActivity.this,"اتصال به اینترنت برقرار نیست",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();}
                    }
                }});
        }catch (Error e){
            Toast.makeText(getApplicationContext(),"عمل اتصال به سرویس با خطا مواجع شد!دوباره تلاش کنید", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    }
//بازار فهرست خریدهای مصرف نشدهٔ کاربر را باز می‌گرداند
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");
            if (result.isFailure()) {//خرید انجام نشده
                dialog.dismiss();
                return;
            }else {//خرید انجام شده
                for (int i=0 ; i<mylist_sku.size() ; i++ ){
                    mIsPremium = inventory.hasPurchase(mylist_sku.get(i));//504
                    String my_price = inventory.getSkuDetails(mylist_sku.get(i)).getPrice();
                    Log.i("@@@@",String.valueOf(mIsPremium)+" price:"+my_price);
                    if (mIsPremium){
                        purchase mypurchase = new purchase();
                        mypurchase.setName(mylist_sku.get(i));
                        switch (mylist_sku.get(i)){
                            case SKU_504:
                                mypurchase.setPrice((float)1500);
                                tvget_b.setText(R.string.download_file);
                                tvfontb.setText(R.string.get_download);
                                break;
                            case SKU_1100:
                                mypurchase.setPrice((float)1900);
                                tvget_hundred.setText(R.string.download_file);
                                tvfont_hundred.setText(R.string.get_download);
                                break;
                            case SKU_first:
                                mypurchase.setPrice((float)1000);
                                tvget_first.setText(R.string.download_file);
                                tvfont_highSchool.setText(R.string.get_download);
                                break;
                            case SKU_second:
                                mypurchase.setPrice((float)1000);
                                tvget_secound.setText(R.string.download_file);
                                tvfont_secoundhs.setText(R.string.get_download);
                                break;
                            case SKU_third:
                                mypurchase.setPrice((float)1000);
                                tvget_third.setText(R.string.download_file);
                                tvfont_third.setText(R.string.get_download);
                                break;
                            case SKU_pish:
                                mypurchase.setPrice((float)1000);
                                tvget_pish.setText(R.string.download_file);
                                tvfont_pish.setText(R.string.get_download);
                                break;
                        }
                        purchase_bl purchasebl = new purchase_bl(DownloadActivity.this);
                        purchasebl.add(mypurchase);
                    }
                }
//                mIsPremium_504 = inventory.hasPurchase(SKU_504);//504
//                String my_price = inventory.getSkuDetails(SKU_504).getPrice();
//                Log.i("@@@@",String.valueOf(mIsPremium_504)+" price:"+my_price);
//                if (mIsPremium_504){
//                    purchase_bl purchasebl = new purchase_bl(DownloadActivity.this);
//                    int  backup=purchasebl.countThisName(SKU_504);
//                    tvget_b.setText(R.string.download_file);
//                    tvfontb.setText(R.string.get_download);
//                    if (backup==0){
//                        purchase mypurchase = new purchase();
//                        mypurchase.setName(SKU_504);
//                        mypurchase.setPrice((float)1500);
//                        purchasebl.add(mypurchase);
////                        Toast.makeText(getApplicationContext(),"504 خریداری شد", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                //1100
//                mIsPremium_1100 = inventory.hasPurchase(SKU_1100);
//                my_price = inventory.getSkuDetails(SKU_1100).getPrice();
//                Log.i("@@@@",String.valueOf(mIsPremium_1100)+" price:"+my_price);
//                if (mIsPremium_1100){
//                    purchase_bl purchasebl = new purchase_bl(DownloadActivity.this);
//                    int  backup=purchasebl.countThisName(SKU_1100);
//                    tvget_hundred.setText(R.string.download_file);
//                    tvfont_hundred.setText(R.string.get_download);
//                    if (backup==0){
//                        purchase mypurchase = new purchase();
//                        mypurchase.setName(SKU_1100);
//                        mypurchase.setPrice((float)1800);
//                        purchasebl.add(mypurchase);
////                        Toast.makeText(getApplicationContext(),"1100 خریداری شد", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                //first
//                mIsPremium_first = inventory.hasPurchase(SKU_first);
//                my_price = inventory.getSkuDetails(SKU_first).getPrice();
//                Log.i("@@@@",String.valueOf(mIsPremium_first)+" price:"+my_price);
//                if (mIsPremium_first){
//                    purchase_bl purchasebl = new purchase_bl(DownloadActivity.this);
//                    int  backup=purchasebl.countThisName(SKU_first);
//                    tvget_first.setText(R.string.download_file);
//                    tvfont_highSchool.setText(R.string.get_download);
//                    if (backup==0){
//                        purchase mypurchase = new purchase();
//                        mypurchase.setName(SKU_first);
//                        mypurchase.setPrice((float)1000);
//                        purchasebl.add(mypurchase);
////                        Toast.makeText(getApplicationContext(),"لغات سال اول دبیرستان خریداری شد", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                //second
//                mIsPremium_second = inventory.hasPurchase(SKU_second);
//                my_price = inventory.getSkuDetails(SKU_second).getPrice();
//                Log.i("@@@@",String.valueOf(mIsPremium_second)+" price:"+my_price);
//                if (mIsPremium_second){
//                    purchase_bl purchasebl = new purchase_bl(DownloadActivity.this);
//                    int  backup=purchasebl.countThisName(SKU_second);
//                    tvget_secound.setText(R.string.download_file);
//                    tvfont_secoundhs.setText(R.string.get_download);
//                    if (backup==0){
//                        purchase mypurchase = new purchase();
//                        mypurchase.setName(SKU_second);
//                        mypurchase.setPrice((float)1000);
//                        purchasebl.add(mypurchase);
////                        Toast.makeText(getApplicationContext(),"لغات سال دوم دبیرستان خریداری شد", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                //third
//                mIsPremium_third = inventory.hasPurchase(SKU_third);
//                my_price = inventory.getSkuDetails(SKU_third).getPrice();
//                Log.i("@@@@",String.valueOf(mIsPremium_third)+" price:"+my_price);
//                if (mIsPremium_third){
//                    purchase_bl purchasebl = new purchase_bl(DownloadActivity.this);
//                    int  backup=purchasebl.countThisName(SKU_third);
//                    tvget_third.setText(R.string.download_file);
//                    tvfont_third.setText(R.string.get_download);
//                    if (backup==0){
//                        purchase mypurchase = new purchase();
//                        mypurchase.setName(SKU_third);
//                        mypurchase.setPrice((float)1000);
//                        purchasebl.add(mypurchase);
////                        Toast.makeText(getApplicationContext(),"لغات سال سوم دبیرستان خریداری شد", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                //pish
//                mIsPremium_pish = inventory.hasPurchase(SKU_pish);
//                my_price = inventory.getSkuDetails(SKU_pish).getPrice();
//                Log.i("@@@@",String.valueOf(mIsPremium_pish)+" price:"+my_price);
//                if (mIsPremium_pish){
//                    purchase_bl purchasebl = new purchase_bl(DownloadActivity.this);
//                    int  backup=purchasebl.countThisName(SKU_pish);
//                    tvget_pish.setText(R.string.download_file);
//                    tvfont_pish.setText(R.string.get_download);
//                    if (backup==0){
//                        purchase mypurchase = new purchase();
//                        mypurchase.setName(SKU_pish);
//                        mypurchase.setPrice((float)1000);
//                        purchasebl.add(mypurchase);
////                        Toast.makeText(getApplicationContext(),"لغات پیش دانشگاهی خریداری شد", Toast.LENGTH_SHORT).show();
//                    }
//                }
                dialog.dismiss();
            }}
    };
    //زمانی که یک خرید به اتمام می‌رسد فراخوانی خواهد شد
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"عملیات خرید تکمیل نشد!", Toast.LENGTH_LONG).show();
                return;
            }else if (purchase.getSku().equals(SKU_504)) {
                Toast.makeText(getApplicationContext(),"خرید 504 با موفقعیت تمام شد.", Toast.LENGTH_LONG).show();
                // در صورتی که خرید با موفقعیت انجام شود backup به پایگاه داده اضافه می شود.
                purchasebl = new purchase_bl(DownloadActivity.this);
                purchase mypurchase = new purchase();
                mypurchase.setName(SKU_504);
                mypurchase.setPrice((float)1500);
                purchasebl.add(mypurchase);
                type = "504";
                new DownloadTask().execute(url);
            }else if (purchase.getSku().equals(SKU_1100)) {
                Toast.makeText(getApplicationContext(),"خرید 1100 با موفقعیت تمام شد.", Toast.LENGTH_LONG).show();
                // در صورتی که خرید با موفقعیت انجام شود backup به پایگاه داده اضافه می شود.
                purchasebl = new purchase_bl(DownloadActivity.this);
                purchase mypurchase = new purchase();
                mypurchase.setName(SKU_1100);
                mypurchase.setPrice((float)1800);
                purchasebl.add(mypurchase);
                type = "1100";
                new DownloadTask().execute(url);//change url
            }else if (purchase.getSku().equals(SKU_first)) {
                Toast.makeText(getApplicationContext(),"خرید سال اول دبیرستان با موفقعیت تمام شد.", Toast.LENGTH_LONG).show();
                // در صورتی که خرید با موفقعیت انجام شود backup به پایگاه داده اضافه می شود.
                purchasebl = new purchase_bl(DownloadActivity.this);
                purchase mypurchase = new purchase();
                mypurchase.setName(SKU_first);
                mypurchase.setPrice((float)1000);
                purchasebl.add(mypurchase);
                type = "first";
                new DownloadTask().execute(url);//change url
            }else if (purchase.getSku().equals(SKU_second)) {
                Toast.makeText(getApplicationContext(), "خرید سال دوم دبیرستان با موفقعیت تمام شد.", Toast.LENGTH_LONG).show();
                // در صورتی که خرید با موفقعیت انجام شود backup به پایگاه داده اضافه می شود.
                purchasebl = new purchase_bl(DownloadActivity.this);
                purchase mypurchase = new purchase();
                mypurchase.setName(SKU_second);
                mypurchase.setPrice((float) 1000);
                purchasebl.add(mypurchase);
                type = "second";
                new DownloadTask().execute(url);//change url
            }else if (purchase.getSku().equals(SKU_third)) {
                Toast.makeText(getApplicationContext(), "خرید سال سوم دبیرستان با موفقعیت تمام شد.", Toast.LENGTH_LONG).show();
                // در صورتی که خرید با موفقعیت انجام شود backup به پایگاه داده اضافه می شود.
                purchasebl = new purchase_bl(DownloadActivity.this);
                purchase mypurchase = new purchase();
                mypurchase.setName(SKU_third);
                mypurchase.setPrice((float) 1000);
                purchasebl.add(mypurchase);
                type = "third";
                new DownloadTask().execute(url);//change url
            }else if (purchase.getSku().equals(SKU_pish)) {
                Toast.makeText(getApplicationContext(), "خرید بسته پیش دانشگاهی با موفقعیت تمام شد.", Toast.LENGTH_LONG).show();
                // در صورتی که خرید با موفقعیت انجام شود backup به پایگاه داده اضافه می شود.
                purchasebl = new purchase_bl(DownloadActivity.this);
                purchase mypurchase = new purchase();
                mypurchase.setName(SKU_pish);
                mypurchase.setPrice((float) 1000);
                purchasebl.add(mypurchase);
                type = "pish";
                new DownloadTask().execute(url);//change url
            }

        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.flBack:
                finish();
                break;
            case R.id.lin_download_fof:
                mymethod = new method(DownloadActivity.this);
                if (mymethod.IsNetworkConnected()){
                    dialog = new SpotsDialog(DownloadActivity.this);
                    dialog.show();
                    url="http://favanaco.com/wp-content/uploads/2016/12/504.csv";
                    if ( mHelper!= null) mHelper.flagEndAsync();
                    mHelper.launchPurchaseFlow(DownloadActivity.this, SKU_504, RC_REQUEST, mPurchaseFinishedListener, "payload-504");
                }else {
                    Toast.makeText(DownloadActivity.this,"برای دانلود فایل باید به اینترنت متصل باشید!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lin_addList_fof:
                if (path==null){
                    Toast.makeText(DownloadActivity.this,"دانلود به درستی انجام نشد!",Toast.LENGTH_SHORT).show();
                }else{
                    Intent myIntent = new Intent(DownloadActivity.this,AddDLToListActivity.class);
                    myIntent.putExtra("selected","504");
                    myIntent.putExtra("path",path);
                    startActivity(myIntent);
                    finish();
                    }
                break;
            case R.id.lin_download_hundred:
                mymethod = new method(DownloadActivity.this);
                if (mymethod.IsNetworkConnected()){
                    dialog = new SpotsDialog(DownloadActivity.this);
                    dialog.show();
                    url="http://favanaco.com/wp-content/uploads/2017/02/1100.csv";
                    if ( mHelper!= null) mHelper.flagEndAsync();
                    mHelper.launchPurchaseFlow(DownloadActivity.this, SKU_1100, RC_REQUEST, mPurchaseFinishedListener, "payload-1100");
                }else {
                    Toast.makeText(DownloadActivity.this,"برای دانلود فایل باید به اینترنت متصل باشید!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lin_addList_h:
                if (path==null){
                    Toast.makeText(DownloadActivity.this,"دانلود به درستی انجام نشد!",Toast.LENGTH_SHORT).show();
                }else{Intent hundred_intent = new Intent(DownloadActivity.this,AddDLToListActivity.class);
                    hundred_intent.putExtra("selected","1100");
                    hundred_intent.putExtra("path",path);
                    startActivity(hundred_intent);
                    finish();
                    }
                break;
            case R.id.lin_download_first:
                mymethod = new method(DownloadActivity.this);
                if (mymethod.IsNetworkConnected()){
                    dialog = new SpotsDialog(DownloadActivity.this);
                    dialog.show();
                    url="http://favanaco.com/wp-content/uploads/2017/02/first.csv";
                    if ( mHelper!= null) mHelper.flagEndAsync();
                    mHelper.launchPurchaseFlow(DownloadActivity.this, SKU_first, RC_REQUEST, mPurchaseFinishedListener, "payload-first");
                }else {
                    Toast.makeText(DownloadActivity.this,"برای دانلود فایل باید به اینترنت متصل باشید!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lin_addList_first:
                if (path==null){
                    Toast.makeText(DownloadActivity.this,"دانلود به درستی انجام نشد!",Toast.LENGTH_SHORT).show();
                }else{Intent first_intent = new Intent(DownloadActivity.this,AddDLToListActivity.class);
                    first_intent.putExtra("selected","first");
                    first_intent.putExtra("path",path);
                    startActivity(first_intent);
                    finish();
                    }
                break;
            case R.id.lin_download_secound:
                mymethod = new method(DownloadActivity.this);
                if (mymethod.IsNetworkConnected()){
                    dialog = new SpotsDialog(DownloadActivity.this);
                    dialog.show();
                    url="http://favanaco.com/wp-content/uploads/2017/02/second.csv";
                    if ( mHelper!= null) mHelper.flagEndAsync();
                    mHelper.launchPurchaseFlow(DownloadActivity.this, SKU_second, RC_REQUEST, mPurchaseFinishedListener, "payload-second");
                }else {
                    Toast.makeText(DownloadActivity.this,"برای دانلود فایل باید به اینترنت متصل باشید!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lin_addlist_second:
                if (path==null){
                    Toast.makeText(DownloadActivity.this,"دانلود به درستی انجام نشد!",Toast.LENGTH_SHORT).show();
                }else{Intent second_intent = new Intent(DownloadActivity.this,AddDLToListActivity.class);
                    second_intent.putExtra("selected","second");
                    second_intent.putExtra("path",path);
                    startActivity(second_intent);
                    finish();
                    }
                break;
            case R.id.lin_download_third:
                mymethod = new method(DownloadActivity.this);
                if (mymethod.IsNetworkConnected()){
                    dialog = new SpotsDialog(DownloadActivity.this);
                    dialog.show();
                    url="http://favanaco.com/wp-content/uploads/2017/02/third.csv";
                    if ( mHelper!= null) mHelper.flagEndAsync();
                    mHelper.launchPurchaseFlow(DownloadActivity.this, SKU_third, RC_REQUEST, mPurchaseFinishedListener, "payload-third");
                }else {
                    Toast.makeText(DownloadActivity.this,"برای دانلود فایل باید به اینترنت متصل باشید!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lin_addList_third:
                if (path==null){
                    Toast.makeText(DownloadActivity.this,"دانلود به درستی انجام نشد!",Toast.LENGTH_SHORT).show();
                }else{Intent third_intent = new Intent(DownloadActivity.this,AddDLToListActivity.class);
                    third_intent.putExtra("selected","third");
                    third_intent.putExtra("path",path);
                    startActivity(third_intent);
                    finish();
                    }
                break;
            case R.id.lin_download_pish:
                mymethod = new method(DownloadActivity.this);
                if (mymethod.IsNetworkConnected()){
                    dialog = new SpotsDialog(DownloadActivity.this);
                    dialog.show();
                    url="http://favanaco.com/wp-content/uploads/2017/02/pish.csv";
                    if ( mHelper!= null) mHelper.flagEndAsync();
                    mHelper.launchPurchaseFlow(DownloadActivity.this, SKU_pish, RC_REQUEST, mPurchaseFinishedListener, "payload-pish");
                }else {
                    Toast.makeText(DownloadActivity.this,"برای دانلود فایل باید به اینترنت متصل باشید!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lin_addList_pish:
                if (path==null){
                    Toast.makeText(DownloadActivity.this,"دانلود به درستی انجام نشد!",Toast.LENGTH_SHORT).show();
                }else{Intent pish_intent = new Intent(DownloadActivity.this,AddDLToListActivity.class);
                    pish_intent.putExtra("selected","pish");
                    pish_intent.putExtra("path",path);
                    startActivity(pish_intent);
                    finish();
                    }
                break;
        }
    }

    public void download(final String downloadUrl, final String type) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("download","failed");
            }
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Failed to download file: " + response);
                }
                File root = android.os.Environment.getExternalStorageDirectory();
                File dir = new File(root.getAbsolutePath() + "/dolphin_download");
                dir.mkdirs();
                path=Environment.getExternalStorageDirectory() +"/dolphin_download/"+type+"download.csv";
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(response.body().bytes());
                fos.close();
            }
        });
    }

    public class DownloadTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... urls) {
                try {
                    download(urls[0],type);
                } catch (Exception e) {
                    error.setType(true);
                    error.setMsg("خطا در دانلود! لطفا دوباره تلاش کنید.");
                }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            dialog.dismiss();
            if (error.isType()){
                Toast.makeText(DownloadActivity.this,"خطا در دانلود! لطفا دوباره تلاش کنید.",Toast.LENGTH_SHORT).show();
            }else{
                switch (type){
                    case "504":
                        lin_downloadFof.setVisibility(View.INVISIBLE);
                        lin_addlistFof.setVisibility(View.VISIBLE);
                        break;
                    case "1100":
                        lin_download1100.setVisibility(View.INVISIBLE);
                        lin_addlist1100.setVisibility(View.VISIBLE);
                        break;
                    case "first":
                        lin_downloadFirst.setVisibility(View.INVISIBLE);
                        lin_addlistFirst.setVisibility(View.VISIBLE);
                        break;
                    case "second":
                        lin_downloadSecond.setVisibility(View.INVISIBLE);
                        lin_addlistSecond.setVisibility(View.VISIBLE);
                        break;
                    case "third":
                        lin_downloadThird.setVisibility(View.INVISIBLE);
                        lin_addlistThird.setVisibility(View.VISIBLE);
                        break;
                    case "pish":
                        lin_downloadPish.setVisibility(View.INVISIBLE);
                        lin_addlistPish.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
//        if (requestCode == 1) {
//
//        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            Log.d(TAG, " helper disposed");
            mHelper.dispose();
            mHelper = null;
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
