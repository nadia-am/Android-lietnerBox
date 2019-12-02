package com.example.nadia.lietner_box.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nadia.lietner_box.lib.message;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Nadia on 11/19/2016.
 */

public class purchases {

    DaoSession daoSession;
    private Context _context;
    message msg;

    public purchases(Context context){
        this._context = context;
    }

    public void insertPurchase(purchase purchase){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context," DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        purchaseDao purchaseDao=daoSession.getPurchaseDao();
        Log.i("btn_save","inserted");
        purchaseDao.insert(purchase);
    }

    public void updatePurchase(purchase mypurchase){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context," DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        purchaseDao updateGrpName=daoSession.getPurchaseDao();
        QueryBuilder qbUpdate = updateGrpName.queryBuilder();
        List<purchase> purchases = qbUpdate.where(purchaseDao.Properties.Id.eq(mypurchase.getId())).list();
        for(purchase purchase : purchases) {
            purchase.setName(mypurchase.getName());
            purchase.setPrice(mypurchase.getPrice());
            purchase.setDate(mypurchase.getDate());
            updateGrpName.update(purchase);
        }
    }

    public void deletePurchase(purchase mypurchase){
        DaoMaster.DevOpenHelper helper2 = new DaoMaster.DevOpenHelper(_context," DaoMaster ", null);
        SQLiteDatabase dbwrite = helper2.getWritableDatabase();
        DaoMaster daoMaster2 = new DaoMaster(dbwrite);
        daoSession = daoMaster2.newSession();
        final purchaseDao purchase_del=daoSession.getPurchaseDao();

        purchase_del.deleteByKey(mypurchase.getId());
    }

    public List<purchase> countThisName(String name){
        List<purchase> names;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        purchaseDao Dao = daoSession.getPurchaseDao();
        QueryBuilder qb = Dao.queryBuilder();
        qb.where(purchaseDao.Properties.Name.eq(name));
        names = qb.list();
        return names;
    }

    public List<purchase> getList(){
        List<purchase> purchase;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        purchaseDao Dao = daoSession.getPurchaseDao();
        QueryBuilder qb = Dao.queryBuilder();
        qb.orderAsc(purchaseDao.Properties.Date);
        purchase = qb.list();
        return purchase;
    }

}
