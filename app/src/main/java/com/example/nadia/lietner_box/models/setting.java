package com.example.nadia.lietner_box.models;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "SETTING".
 */
public class setting {

    private Long id;
    private java.util.Date date;
    private Integer hour;
    private Integer minute;
    private Boolean status;

    public setting() {
    }

    public setting(Long id) {
        this.id = id;
    }

    public setting(Long id, java.util.Date date, Integer hour, Integer minute, Boolean status) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
