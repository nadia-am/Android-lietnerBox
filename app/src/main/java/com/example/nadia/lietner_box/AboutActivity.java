package com.example.nadia.lietner_box;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Nadia on 10/17/2016.
 */

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");

        TextView appName1 = (TextView) findViewById(R.id.tv_appName2);
        appName1.setText(R.string.app_name);
        appName1.setTypeface(custom_font);

        FrameLayout fl_back=(FrameLayout) findViewById(R.id.flBack);
        fl_back.setOnClickListener(this);

        TextView back = (TextView) findViewById(R.id.tv_back);
        back.setOnClickListener(this);
        back.setTypeface(font);
        back.setText(R.string.icon_back);

        TextView tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setText(R.string.app_version);
        tvVersion.setTypeface(custom_font);

        TextView tvVersionNum = (TextView) findViewById(R.id.tv_version_num);
        tvVersionNum.setText(R.string.app_version_num);
        tvVersionNum.setTypeface(BNazanin);

        TextView tvReportIcon = (TextView) findViewById(R.id.tv_report_icon);
        tvReportIcon.setText(R.string.app_report_icon);
        tvReportIcon.setTypeface(font);

        TextView tvReport = (TextView) findViewById(R.id.tv_report);
        tvReport.setText(R.string.app_report);
        tvReport.setTypeface(custom_font);

        TextView tvDolphin = (TextView) findViewById(R.id.tv_dolphin);
        tvDolphin.setText(R.string.app_dolphin);
        tvDolphin.setTypeface(custom_font);

        LinearLayout report=(LinearLayout) findViewById(R.id.btn_report);
        report.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.flBack:
                finish();
                break;
            case R.id.btn_report:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "nadia.amoee@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "مشکل در برنامه جعبه لایتنر دلفین");
//                intent.putExtra(Intent.EXTRA_TEXT, "mail body");
                startActivity(Intent.createChooser(intent, ""));
                break;
        }

    }
}
