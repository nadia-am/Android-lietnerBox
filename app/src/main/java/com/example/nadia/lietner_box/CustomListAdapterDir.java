package com.example.nadia.lietner_box;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomListAdapterDir extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> directory= new ArrayList<String>();
    Typeface custom_font;
    Typeface BNazanin;
    ArrayList<Long> fileSize;
    ArrayList<Long> lastModifed;

    public CustomListAdapterDir (Activity context, ArrayList<String> directory,Typeface custom_font,Typeface BNazanin,ArrayList<Long>  fileSize,ArrayList<Long> lastModifed) {
        super(context, R.layout.dirlist, directory);
        this.context = context;
        this.directory = directory;
        this.custom_font=custom_font;
        this.BNazanin=BNazanin;
        this.fileSize=fileSize;
        this.lastModifed=lastModifed;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.dirlist,null,true);

        ImageView imgPic=(ImageView)rowView.findViewById(R.id.img_pic);
        TextView tvsize = (TextView) rowView.findViewById(R.id.tv_size);
        TextView tvdate = (TextView) rowView.findViewById(R.id.tv_date);

        int size=directory.size();
        File ifSelected = new File(directory.get(size-1));
        Uri uri_select = Uri.fromFile(ifSelected);
        String Extension= MimeTypeMap.getFileExtensionFromUrl(uri_select.toString());
        if (Extension.equals("csv")){
            imgPic.setBackgroundResource(R.drawable.dolphin_csv);

            String sizeF=convert_to_kb(fileSize.get(position));
            tvsize.setText(sizeF);
            tvsize.setTypeface(custom_font);
            tvdate.setText(convert_date(lastModifed.get(position)));
            tvdate.setTypeface(BNazanin);
        }else{
            imgPic.setBackgroundResource(R.drawable.folderload);
        }

        TextView txtdirectory=(TextView) rowView.findViewById(R.id.dir);
        txtdirectory.setTypeface(custom_font);
        txtdirectory.setText(directory.get(position));

        return rowView;
    }
    public  String convert_to_kb(Long fileSize) {
        String x;
        Long bytes = fileSize;
        if (bytes>1024){
            Long kilobytes = (bytes / 1024);
            if (kilobytes>1024){
                Long megabytes = (kilobytes / 1024);
                x=String.valueOf(megabytes)+" MB";
                return x;
            }else{
                x=String.valueOf(kilobytes)+" KB";
                return x;
            }

        }else {
            x=String.valueOf(bytes) +" byte";
            return x;
        }

//
//        double gigabytes = (megabytes / 1024);

    }

    public  String convert_date(Long date){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat dayFormat_y = new SimpleDateFormat("yyyy");
        String y = dayFormat_y.format(date);
        int year=Integer.valueOf(y);

        SimpleDateFormat dayFormat_m = new SimpleDateFormat("MM");
        String m = dayFormat_m.format(date);
        int month=Integer.valueOf(m);

        SimpleDateFormat dayFormat_d = new SimpleDateFormat("dd");
        String d = dayFormat_d.format(date);
        int day=Integer.valueOf(d);

        ConvertDate dateCon = new ConvertDate();
        dateCon.GregorianToPersian(year, month + 1, day);

        return dateCon.toString();


    }



}
