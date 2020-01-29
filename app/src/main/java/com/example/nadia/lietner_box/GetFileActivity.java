package com.example.nadia.lietner_box;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadia.lietner_box.lib.getPath;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nadia on 05/10/2016.
 */
public class GetFileActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> path_arr = new ArrayList<String>();
    ArrayList<String> directory= new ArrayList<String>();
    ArrayList<Long> fileSize= new ArrayList<Long>();
    ArrayList<Long>  lastModified= new ArrayList<Long>();
    int x=0;
    Long selectedId;

    getPath get_path=new getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_file);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");

        selectedId = getIntent().getLongExtra("selectedId",-1);

        TextView tvback=(TextView)findViewById(R.id.tv_back);
        tvback.setTypeface(font);
        tvback.setText(R.string.icon_back);
        tvback.setOnClickListener(this);

        FrameLayout flback=(FrameLayout)findViewById(R.id.fl_Back);
        flback.setOnClickListener(this);

        TextView appName = (TextView) findViewById(R.id.tv_appName);
        appName.setText(R.string.app_name);
        appName.setTypeface(custom_font);

        new showfiles().execute();
    }

    private void Listview_show(final ArrayList<String> directory,final  ArrayList<Long> fileSize,final ArrayList<Long>  lastModified){
        final ListView list = (ListView) findViewById(R.id.listView_dir);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "IRANSansWeb.ttf");
        Typeface BNazanin = Typeface.createFromAsset(getAssets(), "BNAZANIN.TTF");
        Collections.sort(directory,String.CASE_INSENSITIVE_ORDER);
        CustomListAdapterDir adapter = new CustomListAdapterDir(this,directory, custom_font,BNazanin , fileSize,lastModified);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //save selected folder or file
                getPath get_path=new getPath();
                String itemValue = (String) list.getItemAtPosition(position);
                File ifSelected = new File(itemValue);
                Uri uri_select = Uri.fromFile(ifSelected);
                String Extension= MimeTypeMap.getFileExtensionFromUrl(uri_select.toString());
                ArrayList<Long> size_f = new ArrayList<Long>();
                ArrayList<Long>  last_modified= new ArrayList<Long>();
                //if the selected is a file add it to our path else is csv file sent its full path to next activity to read it
                if (Extension.equals("csv")) {//send file full-path to SortFileActivity activity
                    path_arr = get_path.forward(itemValue, path_arr);
//                    String path = Environment.getExternalStorageDirectory() + get_path.convert(path_arr);
                    String path = getExternalFilesDir(null) + get_path.convert(path_arr);

                    Intent myIntent = new Intent(GetFileActivity.this, SortFileActivity.class);
                    myIntent.putExtra("path", path);
                    myIntent.putExtra("selectedId",selectedId);
                    startActivity(myIntent);
                    finish();
                } else {//if its a directory add it to our path and find all file in it
                    path_arr = get_path.forward(itemValue, path_arr);
                    String path2 = get_path.convert(path_arr);
                    String path = Environment.getExternalStorageDirectory() + path2;

                    File f = new File(path);
                    File[] files = f.listFiles();
                    directory.clear();
                    for (File inFile : files) {
                        if (inFile.isDirectory() && !(inFile.isHidden())) {
                            directory.add(inFile.getName());
                        } else {//show file with .csv extension
                            Uri selectedUri = Uri.fromFile(inFile);
                            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                            Log.i("fileExtension", fileExtension);
                            if (fileExtension.equals("csv")) {
                                directory.add(inFile.getName());
                                size_f.add(inFile.length());
                                last_modified.add(inFile.lastModified());
                                Log.i("name added", inFile.getName());
                            }
                        }
                    }
                    Listview_show(directory,size_f,last_modified);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_back:
                new showfile_backClick().execute();
                break;
            case R.id.fl_Back:
                new showfile_backClick().execute();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        //your code when back button pressed
        getPath get_path2=new getPath();
        String check_path22=get_path2.convert(path_arr);
        if (check_path22==""){
            finish();
        }
        path_arr=get_path2.back(path_arr);
        String path22=get_path2.convert(path_arr);
        String path1 = Environment.getExternalStorageDirectory()+path22;
        File f1 = new File(path1);
        File[] files1 = f1.listFiles();
        directory.clear();
        for (File inFile : files1) {
            if ( inFile.isDirectory() && !(inFile.isHidden()) ) {
                directory.add(inFile.getName());
            }else{
                Uri selectedUri = Uri.fromFile(inFile);
                String fileExtension= MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                Log.i("fileExtension",fileExtension);
                if (fileExtension.equals("csv")){
                    directory.add(inFile.getName());
                    fileSize.add(inFile.length());
                    lastModified.add(inFile.lastModified());
                    Log.i("name added",inFile.getName());
                }
            }
        }
        Listview_show(directory,fileSize,lastModified);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class showfiles extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute(){}
        @Override
        protected String doInBackground(Void... params) {
            //first get the loacal path
            String path = Environment.getExternalStorageDirectory().getPath();
            Log.i("first path",path);
            //get all folder and file in local path
            File f = new File(path);
            File[] files = f.listFiles();
            directory.clear();
            fileSize.clear();
            lastModified.clear();
            for (File inFile : files) {//if the files are directory save them
                if (inFile.isDirectory() && !(inFile.isHidden()) ) {
                    directory.add(inFile.getName());
                }else{//if the file is not a foler
                    Uri selectedUri = Uri.fromFile(inFile);
                    String fileExtension= MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());//get the file extension
                    if (fileExtension.equals("csv")){//if its "csv" save it to directory
                        directory.add(inFile.getName());
                        fileSize.add(inFile.length());
                        lastModified.add(inFile.lastModified());
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            Listview_show(directory,fileSize,lastModified);//call listview_show with all file and folder that should be shown in it
        }
    }

    public class showfile_backClick extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... params) {
            get_path=new getPath();
            String check_path2=get_path.convert(path_arr);
            if (check_path2==""){
                finish();
            }
            path_arr=get_path.back(path_arr);
            String path2=get_path.convert(path_arr);
            String path = Environment.getExternalStorageDirectory()+path2;
            File f = new File(path);
            File[] files = f.listFiles();
            directory.clear();
            for (File inFile : files) {
                if (inFile.isDirectory()) {
                    directory.add(inFile.getName());
                }else{
                    Uri selectedUri = Uri.fromFile(inFile);
                    String fileExtension= MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
                    Log.i("fileExtension",fileExtension);
                    if (fileExtension.equals("csv")){
                        directory.add(inFile.getName());
                        Log.i("name added",inFile.getName());
                        fileSize.add(inFile.length());
                        lastModified.add(inFile.lastModified());
                    }
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            Listview_show(directory,fileSize,lastModified);//call listview_show with all file and folder that should be shown in it
        }
    }
}
