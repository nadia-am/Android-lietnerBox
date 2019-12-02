package com.example.nadia.lietner_box.bl;

import android.content.Context;
import android.util.Log;

import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.date;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.cards;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by nadia on 06/08/2016.
 */
public class cards_bl {
    private Context _context;

    public cards_bl(Context context){
        this._context = context;
    }
    public void insertCrd(card crd) throws CustomException {
        try{
            cards card_model=new cards(_context);
            card inserted;
            date mydate = new date();
            Date tDate=mydate.newDate();
            crd.setLDate(tDate);
            crd.setCardCel(1);

            card_model.ifQuestionIsNull(crd);
            card_model.ifQuestionIsUniqe(crd);
            card_model.ifAnswerIsNull(crd);
            inserted=card_model.insertCard(crd);
            Log.i("card1:",inserted.getQuestion());
        }catch (Exception ex){
            throw ex;
        }
    }
    public void insert_downloaded_crd(card crd) throws CustomException {
        try{
            cards card_model=new cards(_context);
            card inserted;
            date mydate = new date();
            Date tDate=mydate.yesterday_date();
            crd.setLDate(tDate);
            crd.setCardCel(1);

            card_model.ifQuestionIsNull(crd);
            card_model.ifQuestionIsUniqe(crd);
            card_model.ifAnswerIsNull(crd);
            inserted=card_model.insertCard(crd);
            Log.i("card1:",inserted.getQuestion());
        }catch (Exception ex){
            throw ex;
        }
    }

    public void bulkInsertCrd(ArrayList<card> toInsert){
        try{
            date mydate = new date();
            Date tDate=mydate.yesterday_date();
            for (int i = 0 ; i<toInsert.size() ; i++){
                toInsert.get(i).setLDate(tDate);
                toInsert.get(i).setCardCel(1);
                Log.i("card"+i+" : ",toInsert.get(i).getQuestion());
            }
            cards card_model=new cards(_context);
            card_model.bulkInsertCrd(toInsert);
        }catch (Exception ex){
            throw ex;
        }
    }

    public void insertCrd_backup(card crd) throws CustomException {
        try{
            cards card_model=new cards(_context);
            card inserted;
            date mydate = new date();
//            Date tDate=mydate.newDate();
//            crd.setLDate(tDate);

            card_model.ifQuestionIsNull(crd);
            card_model.ifQuestionIsUniqe(crd);
            card_model.ifAnswerIsNull(crd);
            inserted=card_model.insertCard(crd);
            Log.i("card1:",inserted.getQuestion());
        }catch (Exception ex){
            throw ex;
        }
    }
    public void updateCrd(card crd) throws CustomException {
        try{
            cards card_model=new cards(_context);
            date mydate = new date();
            card updated;
            Date tDate=mydate.newDate();
            crd.setLDate(tDate);

            card_model.ifQuestionIsUniqe(crd);
            card_model.ifQuestionIsNull(crd);
            card_model.ifAnswerIsNull(crd);
            updated=card_model.updateCard(crd);
        }catch (Exception ex){
            throw ex;
        }
    }

    public void updateCrd_cel(card crd) throws CustomException {
        try{
            cards card_model=new cards(_context);
            date mydate = new date();
            card updated;
            Date tDate=mydate.newDate();
            crd.setLDate(tDate);

            updated=card_model.updateCard(crd);
        }catch (Exception ex){
            throw ex;
        }
    }
    public void deleteCrd(card crd){
        cards card_model=new cards(_context);
        date mydate = new date();
        try{
            card_model.deleteCard(crd);
        }catch (Exception ex){
            throw ex;
        }
    }
    public card getCard(WhereCondition whereCondition){
        cards card_model=new cards(_context);
        card getCard;
        getCard=card_model.getCard(whereCondition);
        return getCard;
    }
    public List<card> getCards(WhereCondition whereCondition){
        try{
            cards card_model=new cards(_context);
            List<card> cards;
            cards=card_model.getCards(whereCondition);
            return cards;
        }catch(Exception ex){
            throw ex;
        }
    }
    public List<card> getCards_2con(WhereCondition whereCondition,WhereCondition whereCondition2){
        List<card> Cards_2con = null;
        Long diffDays = null;
        cards card_model=new cards(_context);
        Cards_2con=card_model.getCards_2con(whereCondition,whereCondition2);

        return Cards_2con;
    }
    public List<card> getReviewable(WhereCondition whereCondition){
        List<card> reviewable = new ArrayList<card>();
        List<card> numberOfCrd= new ArrayList<card>();
        cards card_model=new cards(_context);
        Long diffDays=Long.valueOf(0);
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
                Log.i("***","reviewable Error");

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
        return reviewable;
    }
    public List<card> getReviewable_2con(WhereCondition whereCondition,WhereCondition whereCondition2){
        List<card> reviewable = new ArrayList<card>();
        List<card> numberOfCrd= new ArrayList<card>();
        cards card_model=new cards(_context);
        Long diffDays=Long.valueOf(0);
        numberOfCrd=card_model.getCards_2con(whereCondition,whereCondition2);
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
                Log.i("***","reviewable Error");

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
        return reviewable;
    }
}
