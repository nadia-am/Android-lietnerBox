package com.example.nadia.lietner_box.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by nadia on 13/08/2016.
 */
public class groupWithCards {

    public Long id;
    public String GroupName;
    public java.util.Date date;
    public Integer allCardNumber;
    public Integer reviewableCard;
    public String  type;
    public Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAllCardNumber() {
        return allCardNumber;
    }

    public void setAllCardNumber(Integer allCardNumber) {
        this.allCardNumber = allCardNumber;
    }

    public Integer getReviewableCard() {
        return reviewableCard;
    }

    public void setReviewableCard(Integer reviewableCard) {
        this.reviewableCard = reviewableCard;
    }

    public String  getType() {
        try{
            return type;
        }catch (Error e){
            return "";
        }

    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
