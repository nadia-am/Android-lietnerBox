package com.example.nadia.lietner_box;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.nadia.lietner_box.bl.setting_bl;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.setting;
import java.util.Calendar;
import java.util.List;

public class setAlarm {
    private Context _context;
    PendingIntent pendingIntent;
    List<setting> read_setting;

    public setAlarm(Context context) {
        this._context = context;
    }

    public void start_alarm() {
        setting_bl seting_bl = new setting_bl(_context);
        read_setting = seting_bl.readSetting();
        if (read_setting.get(0).getStatus()) {
            int saveHour = read_setting.get(0).getHour();
            int saveMinute = read_setting.get(0).getMinute();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, saveHour);
            calendar.set(Calendar.MINUTE, saveMinute);
            calendar.set(Calendar.SECOND, 00);
//            int interval = (1000*9);
            int interval = (1000 * 60 * 60 * 24);
            Intent alarmIntent = new Intent(_context, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(_context, 0, alarmIntent, 0);
            boolean alarmUp = (PendingIntent.getBroadcast(_context, 0, alarmIntent, PendingIntent.FLAG_NO_CREATE)) != null;
            if (alarmUp) {
                AlarmManager manager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
                manager.cancel(pendingIntent);
            }
            AlarmManager manager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
//            manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
            Log.i("**************", "system time::" + String.valueOf(System.currentTimeMillis() + " calaender::" + String.valueOf(calendar.getTimeInMillis())));
        } else {
            Intent alarmIntent = new Intent(_context, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(_context, 0, alarmIntent, 0);
            AlarmManager manager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);
        }
    }
}
