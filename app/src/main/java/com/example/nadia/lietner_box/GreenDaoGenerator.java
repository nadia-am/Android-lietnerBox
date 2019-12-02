package com.example.nadia.lietner_box;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {

    private static final String project_dir=System.getProperty("user.dir");
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(8, "com.example.nadia.lietner_box.models");//7

        Entity group = schema.addEntity("group");
        group.addIdProperty().primaryKey().autoincrement();
        group.addStringProperty("GroupName");
        group.addDateProperty("date");
        group.addStringProperty("type");// group,folder or download
        group.addLongProperty("parentId"); //-1 if its in the root

        Entity card = schema.addEntity("card");
        card.addIdProperty().primaryKey().autoincrement();
        card.addIntProperty("cardCel");
        card.addLongProperty("groupId");
        card.addStringProperty("question");
        card.addStringProperty("answer");
        card.addDateProperty("LDate");

        Entity setting = schema.addEntity("setting");
        setting.addIdProperty().primaryKey().autoincrement();
        setting.addDateProperty("date");
        setting.addIntProperty("hour");
        setting.addIntProperty("minute");
        setting.addBooleanProperty("status");

        Entity purchase = schema.addEntity("myPurchase");
        purchase.addIdProperty().primaryKey().autoincrement();
        purchase.addStringProperty("name");
        purchase.addFloatProperty("price");
        purchase.addDateProperty("date");

        new DaoGenerator().generateAll(schema, project_dir+"\\app\\src\\main\\java");

    }
}
