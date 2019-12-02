package com.example.nadia.lietner_box.bl;

import android.content.Context;
import android.util.Log;

import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.date;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.cardDao;
import com.example.nadia.lietner_box.models.cards;
import com.example.nadia.lietner_box.models.group;
import com.example.nadia.lietner_box.models.groupDao;
import com.example.nadia.lietner_box.models.groupWithCards;
import com.example.nadia.lietner_box.models.groups;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by nadia on 06/08/2016.
 */
public class groups_bl {
    private Context _context;

    public groups_bl(Context context){
        this._context = context;
    }

    public void insertGrp(group grp) throws CustomException {
        try{
            groups group_models = new groups(_context);
            date mydate = new date();
            group inserted;
            Date tDate=mydate.newDate();
            grp.setDate(tDate);

            group_models.checkifNameIsNull(grp);
            group_models.nameIsUniqe(grp);
            inserted=group_models.insertGrp(grp);
        }catch (Exception ex){
            throw ex;
        }
    }

    public void checkIfUniqe(group grp) throws CustomException {
        groups group_models = new groups(_context);
        group_models.nameIsUniqe(grp);
    }

    public void updateGrp(group grp) throws CustomException{
        try {
            groups group_models = new groups(_context);
            date mydate = new date();
            group updatedGrp;
            Date tDate=mydate.newDate();
            grp.setDate(tDate);

            group_models.checkifNameIsNull(grp);
            group_models.nameIsUniqe(grp);
            updatedGrp=group_models.updateGrp(grp);

        }catch (Exception ex){
            throw ex;
        }
    }

    public void delete(group grp){
        try{
            groups group_models = new groups(_context);
            group_models.deleteGrp(grp);
        }catch(Exception ex){
            throw ex;
        }

    }

    public List<group> getGroups(WhereCondition whereCondition){
        try{
            List<group> allGrp;
            groups group_models = new groups(_context);
            allGrp=group_models.getAllGrp(whereCondition);
            return allGrp;

        }catch(Exception ex){
            throw ex;
        }

    }

    public Integer countReviewable(WhereCondition whereCondition) {
        List<card> reviewable = new ArrayList<card>();
        List<card> numberOfCrd;
        Long diffDays = null;
        int Count_reviewable=0;

        cards card_model=new cards(_context);

        numberOfCrd=card_model.getCards(whereCondition);
        for (int i = 0; i < numberOfCrd.size(); i++) {
            try {
                //date of today
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Date todayDate2 = new Date();
                String Tdate = df.format(todayDate2);
                //date of UpdatedCard
                Date LDate = numberOfCrd.get(i).getLDate();
                String LDateGrp = df.format(LDate);
                Date d1, d2;
                d1 = df.parse(Tdate);//current date
                d2 = df.parse(LDateGrp);//date of last update
                long diff = d1.getTime() - d2.getTime();
                //find out subtraction of 2 date
                diffDays = diff / (24 * 60 * 60 * 1000);
                String b = String.valueOf(diffDays);
            } catch (Exception e) {
            }
            switch (numberOfCrd.get(i).getCardCel()) {
                case 1:
                    if (diffDays >= 1)
                        reviewable.add(numberOfCrd.get(i));
                    break;
                case 2:
                    if (diffDays >= 2)
                        reviewable.add(numberOfCrd.get(i));
                    break;
                case 4:
                    if (diffDays >= 4)
                        reviewable.add(numberOfCrd.get(i));
                    break;
                case 8:
                    if (diffDays >= 8)
                        reviewable.add(numberOfCrd.get(i));
                    break;
                case 15:
                    if (diffDays >= 15)
                        reviewable.add(numberOfCrd.get(i));
                    break;
            }
        }
        if (reviewable!=null){
            Count_reviewable=reviewable.size();
        }
        return Count_reviewable;
    }

    public List<groupWithCards> getGroupsWithCards(WhereCondition whereCondition){
        List<group> grp;
        List<groupWithCards> allGrpWithCrd = new ArrayList<groupWithCards>();
        groups group_models = new groups(_context);
        cards card_models = new cards(_context);
        grp=group_models.getAllGrp(whereCondition);

        for (int i=0;i<grp.size();i++) {
            groupWithCards _groupWithCards = new groupWithCards();
            _groupWithCards.id = grp.get(i).getId();
            _groupWithCards.GroupName = grp.get(i).getGroupName();
            _groupWithCards.date = grp.get(i).getDate();
            _groupWithCards.type = grp.get(i).getType();
            _groupWithCards.parentId = grp.get(i).getParentId();
            _groupWithCards.allCardNumber=card_models.countCards(cardDao.Properties.GroupId.eq(grp.get(i).getId()));
            _groupWithCards.reviewableCard=countReviewable(cardDao.Properties.GroupId.eq(grp.get(i).getId()));
           allGrpWithCrd.add(_groupWithCards);
        }
        return allGrpWithCrd;
    }

    public group getGroup(WhereCondition whereCondition)  {
        group getGrp;
        groups group_models = new groups(_context);
        getGrp=group_models.getGroup(whereCondition);
        return getGrp;
    }
    //_______________________________________

    public List<groupWithCards> readGroupsWithCards(Long selectedId){
        List<group> grp;
        List<group> grptest;
        List<groupWithCards> allGrpWithCrd = new ArrayList<groupWithCards>();
        groups group_models = new groups(_context);
        cards card_models = new cards(_context);
        grp=group_models.read_groups(selectedId);


        for (int i=0;i<grp.size();i++) {
            groupWithCards _groupWithCards = new groupWithCards();
            _groupWithCards.setId( grp.get(i).getId());
            _groupWithCards.setGroupName(grp.get(i).getGroupName());
            _groupWithCards.setDate(grp.get(i).getDate());
            _groupWithCards.setType(grp.get(i).getType());
            _groupWithCards.setParentId(grp.get(i).getParentId());
            _groupWithCards.setAllCardNumber(card_models.countCards(cardDao.Properties.GroupId.eq(grp.get(i).getId())));
            _groupWithCards.setReviewableCard(countReviewable(cardDao.Properties.GroupId.eq(grp.get(i).getId())));
            allGrpWithCrd.add(_groupWithCards);
        }
        return allGrpWithCrd;
    }

    public List<group> read_groups(Long selectedId){
        try{
            List<group> myGroups;
            groups group_models = new groups(_context);
            myGroups=group_models.read_groups(selectedId);
            return myGroups;
        }catch(Exception ex){
            throw ex;
        }
    }

    public group read_groups_previous(Long selectedId){
        try{
            group myGroups;
            groups group_models = new groups(_context);
            myGroups=group_models.read_groups_previous(selectedId);
            return myGroups;
        }catch(Exception ex){
            throw ex;
        }
    }

    public void delete_with_child(Long id){
        group selectedGrp = new group();
        List<group> getGroups = null;
        ArrayList<Long> listOfIds = new ArrayList<>();
        int j=0;
        listOfIds.add(id);
        while (j != (listOfIds.size())){
            selectedGrp = new group();
            selectedGrp = getGroup(groupDao.Properties.Id.eq(listOfIds.get(j)));
            if (selectedGrp.getType().equals("folder")){
            //if (!(selectedGrp.getType()))
                getGroups = getGroups(groupDao.Properties.ParentId.eq(listOfIds.get(j)));
                if (getGroups.size()>0){
                    for (int i=0 ; i<getGroups.size() ; i++){
                        listOfIds.add(getGroups.get(i).getId());
                    }
                }
            }
            delete(selectedGrp);
            j++;
        }

    }
}
