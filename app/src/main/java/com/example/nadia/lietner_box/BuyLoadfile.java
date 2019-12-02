package com.example.nadia.lietner_box;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nadia.lietner_box.bl.purchase_bl;
import com.example.nadia.lietner_box.models.purchase;
import com.example.nadia.lietner_box.util.IabHelper;
import com.example.nadia.lietner_box.util.IabResult;
import com.example.nadia.lietner_box.util.Inventory;
import com.example.nadia.lietner_box.util.Purchase;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
/**
 * Created by Nadia on 11/13/2016.
 */
public class BuyLoadfile extends AppCompatActivity implements View.OnClickListener {
    //-----------bazar
    // Debug tag, for logging
    static final String TAG = "bazaar";
    // SKUs for our products: the premium upgrade (non-consumable)
    static final String SKU = "backup";
    // واسه اینه که مشخص کنیم این محصول ما فروشی هست یا اشتراک
    boolean mIsPremium = false;
    // (arbitrary) request code for the myPurchase flow
    static final int RC_REQUEST = 10001;
    // The helper object
    IabHelper mHelper;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyloadfile);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        dialog = new SpotsDialog(this);
        dialog.show();

        TextView appName1 = (TextView) findViewById(R.id.tv_appName);
        appName1.setText(R.string.app_name);
        appName1.setTypeface(custom_font);

        TextView back = (TextView) findViewById(R.id.text_back);
        back.setOnClickListener(this);
        back.setTypeface(font);
        back.setText(R.string.icon_back);

        FrameLayout framback=(FrameLayout) findViewById(R.id.fram_Back);
        framback.setOnClickListener(this);

        TextView tvbuy= (TextView) findViewById(R.id.tv_buy);
        tvbuy.setText("برای بارگذاری اطلاعاتی که قبلا از آن پشتیبان تهیه کرده اید نیاز به خرید آن می باشد.");
        tvbuy.setTypeface(custom_font);

        TextView tvprice= (TextView) findViewById(R.id.tv_price);
        tvprice.setText("هزینه ی خرید : 500 تومان");
        tvprice.setTypeface(custom_font);

        LinearLayout btnbuy=(LinearLayout)findViewById(R.id.button_buy);
        btnbuy.setOnClickListener(this);
        TextView _buy = (TextView) findViewById(R.id.buy);
        _buy.setText(R.string.app_buy);
        _buy.setTypeface(custom_font);

        TextView _IconBuy = (TextView) findViewById(R.id.buy_icon);
        _IconBuy.setText(R.string.app_buy_icon);
        _IconBuy.setTypeface(font);

        LinearLayout btncancel=(LinearLayout)findViewById(R.id.button_cancel);
        btncancel.setOnClickListener(this);
        TextView _cancelicon = (TextView) findViewById(R.id.cancel_icon);
        _cancelicon.setText(R.string.app_cancelfont);
        _cancelicon.setTypeface(font);
        TextView _cancel = (TextView) findViewById(R.id.cancel);
        _cancel.setText(R.string.app_cancel);
        _cancel.setTypeface(custom_font);

        //------bazaar-(pardakht daron barname)-------------
        try {
            //برای اتصال به بازار
            String base64EncodedPublicKey="MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwC0d77kCTFVd2m3BlbrjicmRnPCXxmsBpgA2AuuyT8bHz+ZXow0YE5tTsDBptewmkCqV3or5f1r04FpqNx2Fh7HobIhNMkQaPqLfSY9gvVndV6Bk9EkXSRzqBhuwhgQLptn6g8jygRml3adm6v9YiYqmKuahr1x6t7q3IuaaFJHs61eXhoX90ZtGevRUn2cARAyUmc9eyw5exMdjatANe+zsMPrnYUcZd+iVfzURgcCAwEAAQ==";
            // You can find it in your Bazaar console, in the Dealers section.
            mHelper = new IabHelper(this, base64EncodedPublicKey);
            Log.d(TAG, "Starting setup.");
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    Log.d(TAG, "Setup finished.");
                    if (!result.isSuccess()) {
                        // در صورتی که نسخه ی API پشتیبانی نشود.
                        Log.d(TAG, "مشکل در راه اندازی نسخه اندروید! " + result);
                    }else{
                        //لیست خرید کاربر برمی گردونه در Inventory object ذخیره می کنه و به listener بر می گرداند
                        ArrayList<String> list_sku = new ArrayList<>();
                        list_sku.add(SKU);
                        mHelper.queryInventoryAsync(true,list_sku,mGotInventoryListener);
                    }}});
        }catch (Error e){
            Toast.makeText(getApplicationContext(),"عمل اتصال به سرویس با خطا مواجع شد!دوباره تلاش کنید", Toast.LENGTH_LONG).show();
        }
    }
    //------bazaar-(pardakht daron barname)-------------
    //بازار فهرست خریدهای مصرف نشدهٔ کاربر را باز می‌گرداند
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");
            if (result.isFailure()) {
                dialog.dismiss();
                return;
            }else {
                Log.d(TAG, "فهرست خرید با موفقعیت یافت شد.");
                // does the user have the premium upgrade?
                mIsPremium = inventory.hasPurchase(SKU);
                String my_price = inventory.getSkuDetails(SKU).getPrice();
                Log.i("@@@@",String.valueOf(mIsPremium)+" price:"+my_price);
                if (mIsPremium){
                    purchase_bl purchasebl = new purchase_bl(BuyLoadfile.this);
                    int  backup=purchasebl.countThisName(SKU);
                    if (backup==0){
                        purchase mypurchase = new purchase();
                        mypurchase.setName(SKU);
                        mypurchase.setPrice((float)500);
                        purchasebl.add(mypurchase);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"خرید قبلا انجام شده", Toast.LENGTH_SHORT).show();
                        Intent goToLoadFile = new Intent(BuyLoadfile.this, GetFileActivity.class);
                        startActivity(goToLoadFile);
                        finish();
                    }}}}};
    //زمانی که یک خرید به اتمام می‌رسد فراخوانی خواهد شد
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                Log.d(TAG, "مشکل در عملیات خرید" + result);
                Toast.makeText(getApplicationContext(),"مشکل در عملیات خرید،دوباره تلاش کنید!", Toast.LENGTH_LONG).show();
                return;
            }else if (purchase.getSku().equals(SKU)) {
                Toast.makeText(getApplicationContext(),"خرید با موفقعیت انجام شد.", Toast.LENGTH_LONG).show();
                // در صورتی که خرید با موفقعیت انجام شود backup به پایگاه داده اضافه می شود.
                purchase_bl purchasebl = new purchase_bl(BuyLoadfile.this);
                purchase mypurchase = new purchase();
                mypurchase.setName(SKU);
                mypurchase.setPrice((float)500);
                purchasebl.add(mypurchase);
                Intent goToLoadFile = new Intent(BuyLoadfile.this, GetFileActivity.class);
                startActivity(goToLoadFile);
                finish();
            }}};
    //-----------------------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_buy:
                try{
                    mHelper.launchPurchaseFlow(BuyLoadfile.this, SKU, RC_REQUEST, mPurchaseFinishedListener, "payload-backup");
                    Log.i("@@@","");
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"درخواست خرید محصول با خطا مواجع شد!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.text_back:
                finish();
                break;
            case R.id.fram_Back:
                finish();
                break;
            case R.id.button_cancel:
                finish();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }
}
