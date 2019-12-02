package com.example.nadia.lietner_box.bl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.date;
import com.example.nadia.lietner_box.models.purchase;
import com.example.nadia.lietner_box.models.purchases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nadia on 11/19/2016.
 */

public class purchase_bl {
    private Context _context;

    public purchase_bl(Context context){
        this._context = context;
    }

    public void add(purchase mypurchase)  {
        try {
            purchases model_per = new purchases(_context);
            date mydate = new date();
            Date tDate = mydate.newDate();
            mypurchase.setDate(tDate);
            int count = countThisName(mypurchase.getName());
            if (count==0){
                model_per.insertPurchase(mypurchase);
//            }else if (count==1){
//                Toast.makeText(_context,"این محصول قبلا خریداری شده", Toast.LENGTH_SHORT).show();
            }else if (count>1){
                Toast.makeText(_context,"مشکل در پایگاه داده،لطفا آن را گزارش دهید.", Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Log.i("error","adding new purchase");
        }
    }

    public void update(purchase mypurchase) throws CustomException {
        try {
            purchases model_per = new purchases(_context);
            date mydate = new date();
            Date tDate = mydate.newDate();
            mypurchase.setDate(tDate);
            model_per.updatePurchase(mypurchase);
        }catch (Exception ex){
            throw ex;
        }
    }

    public void delete(purchase mypurchase) throws CustomException {
        try {
            purchases model_per = new purchases(_context);
            model_per.deletePurchase(mypurchase);
        }catch (Exception ex){
            throw ex;
        }
    }

    public int countThisName(String name){
        List<purchase> purchaseList;
        purchases model = new purchases(_context);
        purchaseList=model.countThisName(name);
        return purchaseList.size();
    }

    public boolean ifExist(String name){
        List<purchase> purchaseList;
        purchases model = new purchases(_context);
        purchaseList=model.countThisName(name);
        if (purchaseList.size()==0) {
            return false;
        }else {return true;}
    }

    public List<purchase> getListOfPurchase(){
        List<purchase> purchaseList;
        purchases model = new purchases(_context);
        purchaseList = model.getList();
        return purchaseList;
    }

}
