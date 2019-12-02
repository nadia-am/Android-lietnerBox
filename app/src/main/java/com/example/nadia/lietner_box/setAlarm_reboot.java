package com.example.nadia.lietner_box;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class setAlarm_reboot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            setAlarm set_alarm = new setAlarm(context);
            set_alarm.start_alarm();
//        }

    }
}
