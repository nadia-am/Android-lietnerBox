package com.example.nadia.lietner_box.models;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.List;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

public class settings {
    DaoSession daoSession;
    private Context _context;

    public settings(Context context){
        this._context = context;
    }

    public setting insertSetting(setting seting ){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        settingDao settingDao = daoSession.getSettingDao();

        settingDao.insert(seting);
        return seting;
    }

    public setting updatesetting(setting seting ) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        settingDao update = daoSession.getSettingDao();
        QueryBuilder qbUpdate = update.queryBuilder();

        final List<setting> update_s = qbUpdate.where(settingDao.Properties.Id.isNotNull()).list();
        for (setting s : update_s) {
            s.setDate(seting.getDate());
            s.setHour(seting.getHour());
            s.setMinute(seting.getMinute());
            s.setStatus(seting.getStatus());
            update.update(s);
        }
        return seting;
    }

    public void deleteSetting(setting seting) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        settingDao updatecard = daoSession.getSettingDao();        updatecard.deleteByKey(seting.getId());
    }

    public List<setting> readSetting() {
        List<setting> read;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        settingDao stingDao = daoSession.getSettingDao();
        QueryBuilder qb = stingDao.queryBuilder();
        qb.orderAsc(settingDao.Properties.Id);
        read = qb.list();
        return read;

    }

}
