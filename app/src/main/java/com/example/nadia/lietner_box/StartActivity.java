package com.example.nadia.lietner_box;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.widget.Toast;

import com.example.nadia.lietner_box.bl.setting_bl;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.date;
import com.example.nadia.lietner_box.models.setting;

import java.util.Date;
import java.util.List;

public class StartActivity extends AppCompatActivity  {
    List<setting> read_setting_tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        setting_bl set_bl = new setting_bl(this);

        TextView dolphin=(TextView) findViewById(R.id.tv_dolphin);
        dolphin.setText(R.string.app_dolphin);
        dolphin.setTypeface(custom_font);
        TextView version = (TextView) findViewById(R.id.version);
        version.setTypeface(BNazanin);
        version.setText("نسخه 1.6");
        //****check setting table*insert*false*value*if*its*first*time********************
        read_setting_tb = set_bl.readSetting();
        if (read_setting_tb.size() == 0) {
            //set hour
            date mydate = new date();
            Date tDate = mydate.newDate();
            try {
                setting first_setting = new setting();
                first_setting.setStatus(false);
                first_setting.setDate(tDate);
                first_setting.setHour(9);
                first_setting.setMinute(0);
                //inserting
                set_bl.insertSeting(first_setting);
            } catch (CustomException e) {
                Toast.makeText(getApplicationContext(), "خطایی رخ داده!", Toast.LENGTH_LONG).show();
            }
        }
        read_setting_tb = set_bl.readSetting();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(StartActivity.this, MainPageActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 1000);

    }
}
