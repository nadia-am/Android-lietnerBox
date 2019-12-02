package com.example.nadia.lietner_box.lib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nadia on 02/08/2016.
 */
public class date {

    public static Date newDate(){
        Date Tdate = new Date();
//        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//        String todayDate = df.format(Tdate);
        return Tdate;
    }
    public static Date yesterday_date(){
        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        return yesterday;
    }
    public Date convertStringToDate(String dateString)
    {
        Date date = null;
        Date formatteddate = null;
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        try{
            date = df.parse(dateString);
//            formatteddate = df.format(date);
        }
        catch ( Exception ex ){
            System.out.println(ex);
        }
        return formatteddate;
    }

}
