package com.example.nadia.lietner_box;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nadia.lietner_box.bl.setting_bl;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.date;
import com.example.nadia.lietner_box.models.setting;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    List<setting> read_setting;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        final setting_bl seting_bl = new setting_bl(this);

//        TextView menu = (TextView) findViewById(R.id.tv_appName);
//        menu.setTypeface(custom_font);
//        menu.setText(R.string.app_name);



        TextView back = (TextView) findViewById(R.id.tv_back);
        back.setOnClickListener(this);
        back.setTypeface(font);
        back.setText(R.string.icon_back);

        TextView fnoti = (TextView) findViewById(R.id.fnotification);
        fnoti.setTypeface(font);
        fnoti.setText(R.string.fnoti);

        TextView noti = (TextView) findViewById(R.id.notification);
        noti.setTypeface(custom_font);
        noti.setText(R.string.noti);

        TextView fsetTime = (TextView) findViewById(R.id.fsetTime);
        fsetTime.setTypeface(font);
        fsetTime.setText(R.string.fsettime);
        fsetTime.setOnClickListener(this);

        FrameLayout flSetTime = (FrameLayout) findViewById(R.id.fl_setTime);
        flSetTime.setOnClickListener(this);

        TextView setTime = (TextView) findViewById(R.id.setTime);
        setTime.setTypeface(custom_font);
        setTime.setText(R.string.settime);

        TextView fbackup = (TextView) findViewById(R.id.fbackup);
        fbackup.setTypeface(font);
        fbackup.setText(R.string.fbackup);

        TextView backup = (TextView) findViewById(R.id.backup);
        backup.setTypeface(custom_font);
        backup.setText(R.string.backup);

        final LinearLayout linSetTime = (LinearLayout) findViewById(R.id.lin_setTime);
        linSetTime.setVisibility(View.GONE);
        linSetTime.setOnClickListener(this);

        Switch sw_btn =  findViewById(R.id.switch1);
        //find  status value and set it to our switch
        read_setting = seting_bl.readSetting();
        final boolean setting_status = read_setting.get(0).getStatus();
        sw_btn.setChecked(setting_status);

        final int h = read_setting.get(0).getHour();
        final int m = read_setting.get(0).getMinute();
        TextView etTime = (TextView) findViewById(R.id.et_time);
        etTime.setTypeface(custom_font);
        NumberFormat f = new DecimalFormat("00");
        etTime.setText(String.valueOf(h) + ":" + f.format(m));
        /////////////////////////////////////////////
        //pre-define for alarm manager
        sw_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    date mydate = new date();
                    Date tDate = mydate.newDate();
                    read_setting = seting_bl.readSetting();
                    int old_hour = read_setting.get(0).getHour();
                    int old_minute = read_setting.get(0).getMinute();
                    //update status
                    try {
                        linSetTime.setVisibility(View.VISIBLE);
                        setting seting = new setting();
                        seting.setDate(tDate);
                        seting.setHour(old_hour);
                        seting.setMinute(old_minute);
                        seting.setStatus(true);

                        seting_bl.updateSeting(seting);
                    } catch (CustomException e) {
                        Toast.makeText(getApplicationContext(),"خطایی رخ داده!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    read_setting = seting_bl.readSetting();
                    int old_hour = read_setting.get(0).getHour();
                    int old_minute = read_setting.get(0).getMinute();
                    Date old_date = read_setting.get(0).getDate();
                    try {
                        linSetTime.setVisibility(View.GONE);
                        setting seting = new setting();
                        seting.setDate(old_date);
                        seting.setHour(old_hour);
                        seting.setMinute(old_minute);
                        seting.setStatus(false);

                        seting_bl.updateSeting(seting);
                    } catch (CustomException e) {
                        Toast.makeText(getApplicationContext(),"خطایی رخ داده!", Toast.LENGTH_LONG).show();
                    }
//                    setAlarm set_alarm = new setAlarm(SettingActivity.this);
//                    set_alarm.stop_alarm();
                }
                setAlarm set_alarm = new setAlarm(SettingActivity.this);
                set_alarm.start_alarm();
            }
        });
        if (sw_btn.isChecked()) {
            linSetTime.setVisibility(View.VISIBLE);
        } else {
            linSetTime.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.flBack:
                finish();
                break;
            case R.id.lin_setTime:
                timePicker();
                break;
            case R.id.fl_setTime:
                timePicker();
                break;
            case R.id.setTime:
                timePicker();
                break;
        }
    }

    public void timePicker() {
        final setting_bl seting_bl = new setting_bl(this);
        read_setting = seting_bl.readSetting();
        final int mHour = read_setting.get(0).getHour();
        final int mMinute = read_setting.get(0).getMinute();

        final TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(SettingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                TextView etTime = (TextView) findViewById(R.id.et_time);
                NumberFormat f = new DecimalFormat("00");
                etTime.setText(String.valueOf(hourOfDay) + ":" + f.format(minute));
                //update db time-just change time
                read_setting = seting_bl.readSetting();
                boolean setting_status = read_setting.get(0).getStatus();
                date mydate = new date();
                Date tDate = mydate.newDate();
//                Date setting_date = read_setting.get(0).getDate();
                try {
                    setting seting = new setting();
                    seting.setDate(tDate);
                    seting.setHour(hourOfDay);
                    seting.setMinute(minute);
                    seting.setStatus(true);
                    seting_bl.updateSeting(seting);
                } catch (CustomException e) {
                    Toast.makeText(getApplicationContext(),"خطایی رخ داده", Toast.LENGTH_LONG).show();
                }
//                Toast.makeText(SettingActivity.this, "تنظیمات ست شد", Toast.LENGTH_SHORT).show();
                setAlarm set_alarm = new setAlarm(SettingActivity.this);
                set_alarm.start_alarm();

            }

        }, mHour, mMinute, true);
        mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "لغو", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(SettingActivity.this, "تنظیمات لغو شد", Toast.LENGTH_SHORT).show();
            }
        });
        mTimePicker.setButton(DialogInterface.BUTTON_POSITIVE, "تنظیم", mTimePicker);
        mTimePicker.setTitle("تنظیم زمان");
        mTimePicker.show();
    }
}