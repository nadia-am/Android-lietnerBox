package com.example.nadia.lietner_box.lib;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;

/**
 * Created by Nadia on 11/5/2016.
 */

public class method {
    public Context _context;
    public method(Context context){
        this._context = context;
    }

    public boolean IsNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public Typeface GetTypeFace(String fontName){
        Typeface face = Typeface.createFromAsset(_context.getAssets(),fontName);
        return face;
    }
}
