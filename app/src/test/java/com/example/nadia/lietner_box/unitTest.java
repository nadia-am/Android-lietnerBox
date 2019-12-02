package com.example.nadia.lietner_box;

import android.util.Log;

import com.example.nadia.lietner_box.bl.cards_bl;
import com.example.nadia.lietner_box.bl.setting_bl;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.date;
import com.example.nadia.lietner_box.models.setting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by nadia on 07/09/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class unitTest {
    setting_bl seting_bl=new setting_bl(RuntimeEnvironment.application);
    @Test
    public void date(){
        List<setting> read_setting;
        List<setting> read_setting2;
        read_setting=seting_bl.readSetting();

        if (read_setting.size()==0){
            //insert into setting table
            date mydate = new date();
            Date tDate=mydate.newDate();
            try {
                setting first_setting=new setting();
                first_setting.setStatus(false);
                first_setting.setDate(tDate);
                first_setting.setHour(11);
                first_setting.setMinute(47);
                //inserting
                seting_bl.insertSeting(first_setting);
            } catch (CustomException e) {
                e.printStackTrace();
            }
        }
        read_setting2=seting_bl.readSetting();
        date mydate = new date();
        Date tDate = mydate.newDate();
        Date saveDate = read_setting2.get(0).getDate();
        int save_H = read_setting2.get(0).getHour();
        int save_M = read_setting2.get(0).getMinute();
        ///////////////////////
        Calendar cal = Calendar.getInstance();
        cal.setTime(saveDate);
        int minute = cal.get(Calendar.MINUTE);
        int hour = cal.get(Calendar.HOUR);
        ///////////////////////
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(tDate);

//        if(saveDate.compareTo(tDate) > 0){
//            Log.i("******","saveDate is after tDate");
//        }
        if(saveDate.compareTo(tDate) < 0){
            if(hour < save_H  ){
                Log.i("******","saveDate is befor tDate");
            }else if(hour == save_H){
                if(minute < save_M){
                    Log.i("******","notification");
                }
            }

        }
//        if(saveDate.compareTo(tDate) == 0){
//            Log.i("******","saveDate is the same as tDate");
//        }

    }
}
