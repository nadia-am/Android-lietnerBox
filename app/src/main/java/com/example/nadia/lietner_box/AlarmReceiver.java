package com.example.nadia.lietner_box;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import com.example.nadia.lietner_box.bl.cards_bl;
import com.example.nadia.lietner_box.bl.setting_bl;
import com.example.nadia.lietner_box.lib.date;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.cardDao;
import com.example.nadia.lietner_box.models.setting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        List<setting> read_setting;
        setting_bl seting_bl=new setting_bl(context);
        cards_bl crd_bl=new cards_bl(context);
        List<card> reviewable;
        reviewable = crd_bl.getReviewable(cardDao.Properties.CardCel.notEq(20));
        if (reviewable.size() != 0) {
            read_setting = seting_bl.readSetting();
            Date saveDate = read_setting.get(0).getDate();
            int set_hour = read_setting.get(0).getHour();
            int set_minute = read_setting.get(0).getMinute();
            date mydate = new date();
            DateFormat df = new SimpleDateFormat("yyyy,MM,dd");
            String set_date = df.format(saveDate);
            //***Now Date***
            Date nowDate = mydate.newDate();
            String now_date = df.format(nowDate);
            Calendar cal_n = Calendar.getInstance();
            cal_n.setTime(nowDate);
            int now_h = cal_n.get(Calendar.HOUR_OF_DAY);
            int now_m = cal_n.get(Calendar.MINUTE);

            int first = set_date.compareTo(now_date);
            boolean first_con = first < 0;
            boolean sec_con = (((first == 0) && (now_h == set_hour) && (now_m <= set_minute)));
            if (first_con || sec_con) {
                ///////////--------------notification----------
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                mBuilder.setSmallIcon(R.drawable.dolphin_noti);
                mBuilder.setContentTitle("مرور کارتها");
                mBuilder.setContentText("تعدادی کارت برای مرور وجود دارد");
                //        mBuilder.setContentText(String.valueOf(x)+" کارت برای مرور وجود دارد");
                mBuilder.setAutoCancel(true);
                mBuilder.setDefaults(-1);
                Intent resultIntent = new Intent(context, MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
                ///////////------------------------------------
            }
        }//reviewable
    }
}
