package com.example.nadia.lietner_box.lib;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by nadia on 05/10/2016.
 */
public class getPath {

    public ArrayList<String> back(ArrayList<String> path_arr){
        int size=path_arr.size();
        Log.i("path_arr size",String.valueOf(size));
        ArrayList<String> back_arr=new ArrayList<String>();
        for (int i=0;i<size-1;i++){
            back_arr.add(path_arr.get(i));
            Log.i("path_arr.("+i+")",path_arr.get(i));
        }
        return back_arr;
    }

    public ArrayList<String> forward(String str,ArrayList<String> path_arr){
        path_arr.add(str);
        return path_arr;
    }

    public String convert(ArrayList<String> path_arry){
        String path="";
        for(int i=0;i<path_arry.size();i++){
            path=path+"/"+path_arry.get(i);
        }
        return path;
    }

}
