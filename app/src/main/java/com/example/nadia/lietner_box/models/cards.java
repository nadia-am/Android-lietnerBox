package com.example.nadia.lietner_box.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.message;
import com.example.nadia.lietner_box.models.DaoMaster;
import com.example.nadia.lietner_box.models.DaoSession;
import com.example.nadia.lietner_box.models.cardDao;
import com.example.nadia.lietner_box.models.card;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
/**
 * Created by nadia on 02/08/2016.
 */
public class cards {
    DaoSession daoSession;
    private Context _context;
    message msg;

    public cards(Context context) {
        this._context = context;
    }
    public card insertCard(card crd) {
        try{
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            cardDao cardao = daoSession.getCardDao();
            cardao.insert(crd);

        }catch (Exception ex){
            Log.i("fgcbc","dfgdvg");
        }
        return crd;
    }
    public void bulkInsertCrd(ArrayList<card> toInsert){
        try{
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            cardDao cardao = daoSession.getCardDao();
            cardao.insertInTx(toInsert);
        }catch (Exception ex){
            Log.i("fgcbc","dfgdvg");
        }
    }
    public card updateCard(card crd) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        cardDao cardao = daoSession.getCardDao();
        cardDao updatecard = daoSession.getCardDao();
        QueryBuilder qbUpdate = updatecard.queryBuilder();
        final List<card> cards = qbUpdate.where(cardDao.Properties.Id.eq(crd.getId())).list();
        for (card card : cards) {
            card.setLDate(crd.getLDate());
            card.setQuestion(crd.getQuestion());
            card.setAnswer(crd.getAnswer());
            card.setCardCel(crd.getCardCel());
            cardao.update(card);
//            Toast.makeText(_context,"بروز رسانی کارت انجام شد", Toast.LENGTH_LONG).show();
            Log.i("***btn_save", "card update");
        }
        return crd;
    }
    public void deleteCard(card crd) {
        DaoMaster.DevOpenHelper helperr = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase dbReadd = helperr.getWritableDatabase();
        DaoMaster daoMasterr = new DaoMaster(dbReadd);
        daoSession = daoMasterr.newSession();
        cardDao readFromCard = daoSession.getCardDao();
        readFromCard.deleteByKey(crd.getId());
    }
    public void ifQuestionIsUniqe(card crd) throws CustomException {
        List<card> nameQuestion;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase dbRead = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbRead);
        daoSession = daoMaster.newSession();
        cardDao readFromCard = daoSession.getCardDao();
        QueryBuilder qb = readFromCard.queryBuilder();
        qb.where(cardDao.Properties.Question.eq(crd.getQuestion()),cardDao.Properties.GroupId.eq(crd.getGroupId()));
        qb.orderAsc(cardDao.Properties.Id);
        nameQuestion = qb.list();
        if ((nameQuestion.isEmpty()) == false) {
            if (crd.getId() == null) {
                throw new CustomException(msg.questionNotUniqe_crd());
            } else {
                for (int i = 0; i < nameQuestion.size(); i++) {
                    if (!nameQuestion.get(i).getId().equals(crd.getId())) {
                        Toast.makeText(_context,"type_QuestionID::"+nameQuestion.get(i).getId().getClass()+" nameQuestionID::"+nameQuestion.get(i).getId()+" Type_crdID::"+crd.getId().getClass()+" crdID:"+crd.getId(), Toast.LENGTH_LONG).show();
                        throw new CustomException(msg.questionNotUniqe_crd());
                    }
                }
            }
        }
    }
    public void ifAnswerIsUniqe(card crd) throws CustomException {
        List<card> nameAnswer;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase dbRead = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbRead);
        daoSession = daoMaster.newSession();
        cardDao readFromCard = daoSession.getCardDao();
        QueryBuilder qb = readFromCard.queryBuilder();
        qb.where(cardDao.Properties.Answer.eq(crd.getAnswer()));
        qb.orderAsc(cardDao.Properties.Id);
        nameAnswer = qb.list();
        if ((nameAnswer.isEmpty()) == false) {
            if (crd.getId()== null) {
                throw new CustomException(msg.answerNotUniqe_crd());
            } else {
                for (int i = 0; i < nameAnswer.size(); i++) {
                    if (!nameAnswer.get(i).getId().equals(crd.getId()) ) {
                        throw new CustomException(msg.answerNotUniqe_crd());
                    }
                }
            }
        }
    }
    public void ifQuestionIsNull(card crd) throws CustomException {
        if (crd.getQuestion() == null || crd.getQuestion() == ""  || crd.getQuestion().length()==0) {
            throw new CustomException(msg.questionIsNull_crd());
        }
    }
    public void ifAnswerIsNull(card crd) throws CustomException {
        if (crd.getAnswer() == null || crd.getAnswer() == "" || crd.getAnswer().length()==0) {
            throw new CustomException(msg.answerIsNull_crd());
        }
    }
    public void idIsUniqe(card crd) throws CustomException {
        List<card> idGrps;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase dbRead = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbRead);
        daoSession = daoMaster.newSession();
        cardDao readFromCard = daoSession.getCardDao();
        QueryBuilder qb = readFromCard.queryBuilder();
        qb.where(cardDao.Properties.Id.eq(crd.getId()));
        qb.orderAsc(cardDao.Properties.Id);
        idGrps = qb.list();
        if (idGrps.isEmpty() == false) {
            throw new CustomException(msg.idIsnUniqe_crd());
        }
    }
    public List<card> getCards(WhereCondition whereCondition) {
        List<card> cardsbyGrpName;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase db = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        cardDao crdDao = daoSession.getCardDao();
        QueryBuilder qb = crdDao.queryBuilder();
        if (whereCondition == null) {
            qb.orderAsc(cardDao.Properties.Id);
            cardsbyGrpName = qb.list();
            return cardsbyGrpName;
        } else {
            qb.where(whereCondition);
            qb.orderAsc(cardDao.Properties.Id);
            cardsbyGrpName = qb.list();
            return cardsbyGrpName;
        }
    }
    public int countCards(WhereCondition whereCondition) {
        List<card> cardsbyGrpName;
        int Count_cardsbyGrpName;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase db = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        cardDao crdDao = daoSession.getCardDao();
        QueryBuilder qb = crdDao.queryBuilder();
        if (whereCondition == null) {
            qb.orderAsc(cardDao.Properties.Id);
            cardsbyGrpName = qb.list();
            Count_cardsbyGrpName = cardsbyGrpName.size();
            return Count_cardsbyGrpName;
        } else {
            qb.where(whereCondition);
            qb.orderAsc(cardDao.Properties.Id);
            cardsbyGrpName = qb.list();
            Count_cardsbyGrpName = cardsbyGrpName.size();
            return Count_cardsbyGrpName;
        }

    }
    public List<card> getCards_2con(WhereCondition whereCondition , WhereCondition whereCondition2){
        List<card> memorized;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase db = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        cardDao readFromCard=daoSession.getCardDao();
        QueryBuilder qb_learned = readFromCard.queryBuilder();
        qb_learned.where(whereCondition,whereCondition2);
        qb_learned.orderAsc(cardDao.Properties.Id);
        memorized=qb_learned.list();
        return memorized;
    }
    public card getCard(WhereCondition whereCondition) {
        List<card> findCard;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(_context, " DaoMaster ", null);
        SQLiteDatabase dbRead = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbRead);
        daoSession = daoMaster.newSession();
        cardDao readFromCard = daoSession.getCardDao();
        QueryBuilder qb = readFromCard.queryBuilder();
        if (whereCondition == null) {
            findCard = qb.list();
            if (findCard.size() == 0) {
                return null;
            } else {
                return findCard.get(0);
            }

        }else {
            qb.where(whereCondition);
            findCard = qb.list();
            if (findCard.size() == 0) {
                return null;
            } else {
                return findCard.get(0);
            }
        }

    }
}
