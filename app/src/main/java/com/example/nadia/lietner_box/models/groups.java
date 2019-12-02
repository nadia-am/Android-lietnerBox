package com.example.nadia.lietner_box.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.message;
import java.util.List;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by nadia on 04/08/2016.
 */
public class groups {
    DaoSession daoSession;
    private Context _context;
    message msg;

    public groups(Context context){
        this._context = context;
    }

    public group insertGrp(group grp){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context," DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        groupDao grpDao=daoSession.getGroupDao();
        Log.i("btn_save","inserted");
        grpDao.insert(grp);
        return grp;
    }

    public group updateGrp(group grp){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context," DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        groupDao updateGrpName=daoSession.getGroupDao();
        QueryBuilder qbUpdate = updateGrpName.queryBuilder();
        List<group> groups = qbUpdate.where(groupDao.Properties.Id.eq(grp.getId())).list();
        for(group group : groups) {
            group.setGroupName(grp.getGroupName());
        updateGrpName.update(group);
        }
        return grp;
    }

    public void deleteGrp(group grp){
        DaoMaster.DevOpenHelper helper2 = new DaoMaster.DevOpenHelper(_context," DaoMaster ", null);
        SQLiteDatabase dbwrite = helper2.getWritableDatabase();
        DaoMaster daoMaster2 = new DaoMaster(dbwrite);
        daoSession = daoMaster2.newSession();
        final groupDao group_del=daoSession.getGroupDao();

        group_del.deleteByKey(grp.getId());
    }

    public void nameIsUniqe(group grp) throws CustomException {
        List <group> nameGrps;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase dbRead = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbRead);
        daoSession = daoMaster.newSession();
        groupDao readFromCard = daoSession.getGroupDao();
        QueryBuilder qb = readFromCard.queryBuilder();
        qb.where(groupDao.Properties.GroupName.eq(grp.getGroupName()));
        qb.orderAsc(groupDao.Properties.Id);
        nameGrps = qb.list();
        if ((nameGrps.isEmpty())==false ) {
            if(grp.getId()==null){
                throw new CustomException(msg.nameIsnUniqe_grp());
            }else{
                for(int i=0; i<nameGrps.size();i++){
                    if(!nameGrps.get(i).getId().equals(grp.getId())){
                        throw new CustomException(msg.nameIsnUniqe_grp());
                    }
                }
            }
        }
    }

    public void checkifNameIsNull(group grp) throws CustomException {
        if(grp.getGroupName()==null || grp.getGroupName()=="" || grp.getGroupName().length()==0){
            throw new CustomException(msg.notNullName_grp());
        }
    }

    public List<group> getAllGrp(WhereCondition whereCondition){
        List <group> allGrp;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context," DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        groupDao grpDao=daoSession.getGroupDao();
        QueryBuilder qb=grpDao.queryBuilder();
        if(whereCondition==null){
            qb.orderAsc(groupDao.Properties.Date);
            allGrp = qb.list();
            return allGrp;
        }else{
            qb.where(whereCondition);
            qb.orderAsc(groupDao.Properties.Date);
            allGrp = qb.list();
            return allGrp;
        }
    }

    public group getGroup(WhereCondition whereCondition)  {
        List<group> findGrp;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context," DaoMaster ", null);
        SQLiteDatabase dbRead = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbRead);
        daoSession = daoMaster.newSession();
        groupDao readFromCard=daoSession.getGroupDao();
        QueryBuilder qb = readFromCard.queryBuilder();
        qb.where(whereCondition);
        findGrp=qb.list();

        return findGrp.get(0);
    }

    //_____________________________________

    public List<group> read_groups(Long selectedId){
        List <group> myGroups;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context," DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        groupDao grpDao=daoSession.getGroupDao();
        QueryBuilder qb=grpDao.queryBuilder();

        qb.where(groupDao.Properties.ParentId.eq(selectedId));
        qb.orderAsc(groupDao.Properties.Date);
        myGroups = qb.list();
        return myGroups;
    }

    public group read_groups_previous(Long selectedId){
        List <group> myGroups;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context," DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        groupDao grpDao=daoSession.getGroupDao();
        QueryBuilder qb=grpDao.queryBuilder();

        qb.where(groupDao.Properties.Id.eq(selectedId));
        qb.orderAsc(groupDao.Properties.Date);
        myGroups = qb.list();
        return myGroups.get(0);
    }


}
