package com.example.nadia.lietner_box;

/**
 * Created by Nadia on 11/5/2016.
 */

public class DrawerListObject {
    private String title;
    private String rightIcon;
    private String leftIcon;
    private Integer notification;

    public DrawerListObject() {
    }

    public DrawerListObject(String title, String rightIcon, String leftIcon, Integer notification) {
        this.title = title;
        this.rightIcon = rightIcon;
        this.leftIcon = leftIcon;
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "DrawerListObject{" +
                "title='" + title + '\'' +
                ", rightIcon='" + rightIcon + '\'' +
                ", leftIcon='" + leftIcon + '\'' +
                ", notification=" + notification +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRightIcon() {
        return rightIcon;
    }

    public void setRightIcon(String rightIcon) {
        this.rightIcon = rightIcon;
    }

}
