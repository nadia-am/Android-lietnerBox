package com.example.nadia.lietner_box;
import android.util.Log;
import com.example.nadia.lietner_box.bl.cards_bl;
import com.example.nadia.lietner_box.bl.groups_bl;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.message;
import com.example.nadia.lietner_box.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class testGroup {
    DaoSession daoSession;
    groups_bl bl_group = new groups_bl(RuntimeEnvironment.application);
    cards_bl bl_card= new cards_bl(RuntimeEnvironment.application);
    message msg;
    groups modals_group=new groups(RuntimeEnvironment.application);
    @Test
    public void dateTest() throws ParseException {
        String strDate="Mon Oct 17 09:40:04 GMT+03:30 2016";
        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date mydate = format.parse(strDate);
        Log.i("date",String.valueOf(mydate));
    }
    @Test
    public void insertToGroup() {
        try {
            group grp = new group();
            grp.setGroupName("ttt");
            bl_group.insertGrp(grp);
//            group get_group=modals_group.getGroup(grp);
//            assertNotNull(get_group);
        }catch (CustomException cex){
            assertEquals(cex.getMessage(),msg.notNullName_grp());
            assertNotNull(null);
        }catch (Exception ex){
            Log.i("","");
        }

    }
    @Test
    public void insertGrp_Twice(){
        try {
            //1
            group grp= new group();
            grp.setGroupName("text");
            bl_group.insertGrp(grp);
//            group get_Grp= modals_group.getGroup(grp);
//            assertEquals("text",get_Grp.getGroupName());
            //2
            group grp2= new group();
            grp2.setGroupName("text");
            bl_group.insertGrp(grp2);
            assertNotNull(null);
        }catch (CustomException cex){
            assertEquals(cex.getMessage(),msg.nameIsnUniqe_grp());

        }catch (Exception ex){
            Log.i("","");
        }
    }
    @Test
    public void insertGrp_empty(){
        try {
            group grp = new group();
            grp.setGroupName("");
            bl_group.insertGrp(grp);
//            group get_group=modals_group.getGroup(grp);
//            assertNotNull(null);
//            assertNotNull(get_group);
        }catch (CustomException cex){
            assertEquals(cex.getMessage(),msg.notNullName_grp());
        }catch (Exception ex){
            Log.i("","");
        }
    }
    @Test
    public void insertGrp_null(){
        try{
            group grp = new group();
            bl_group.insertGrp(grp);
            assertNotNull(null);

        }catch (CustomException cex){
            assertEquals(cex.getMessage(),msg.notNullName_grp());

        }
        catch (Exception ex){

        }
    }
    @Test
    public void updateGroup_If_Table_empty(){

        try{
            //update
            //---0---update Without insertion before
            group grp = new group();
            grp.setGroupName("grpNameUpdate");
//            modals_group.updateGrp(grp);
            bl_group.updateGrp(grp);
            assertNotNull(null);
        }catch (CustomException cex){
            assertEquals(cex.getMessage(),msg.notNullName_grp());

        }catch (Exception ex){

        }
    }
    @Test
    public void updateGroup_if_newName_exist(){
        try{
            group grp1 = new group();
            grp1.setGroupName("name1");
            bl_group.insertGrp(grp1);

            group grp2 = new group();
            grp2.setGroupName("name2");
            bl_group.insertGrp(grp2);

            grp2.setGroupName("name1");
            bl_group.updateGrp(grp2);

        }catch (CustomException cex){
            assertEquals(cex.getMessage(),msg.nameIsnUniqe_grp());
            Log.i("","");

        }catch (Exception ex){

        }
    }
    @Test
    public void updateGroup_if_newName_isCurrent_Name(){
        try{
            group grp1 = new group();
            grp1.setGroupName("name1");
            bl_group.insertGrp(grp1);

            group grp2 = new group();
            grp2.setGroupName("name2");
            bl_group.insertGrp(grp2);

            grp2.setGroupName("name2");
            bl_group.updateGrp(grp2);

        }catch (CustomException cex){
            assertEquals(cex.getMessage(),msg.nameIsnUniqe_grp());
        }catch (Exception ex){
        }
    }
    @Test
    public void deleteGroup() throws CustomException {
        //delete
        //---1---insert
        group grp = new group();
        grp.setGroupName("group name");
        bl_group.insertGrp(grp);
        //how
        //---2---delete
        bl_group.delete(grp);
    }
    @Test
    public void get_groups(){
        try {

            //
            group grp1 = new group();
            grp1.setGroupName("aaa");
            bl_group.insertGrp(grp1);
            //
            group grp2 = new group();
            grp2.setGroupName("bbb");
            bl_group.insertGrp(grp2);
            //
            group grp3 = new group();
            grp3.setGroupName("ccc");
            bl_group.insertGrp(grp3);
            //////////////////////////
//            bl_group.getGroups(groupDao.Properties.Id.eq(1));
//            bl_group.getAllGrp(null);

        }catch (CustomException cex){
            assertNotNull(null);
        }catch (Exception ex){
            Log.i("","");
        }
    }

    @Test
    public void test_two_condition(){
        try {
            //________________
            group grp = new group();
            grp.setGroupName("grp1");
            bl_group.insertGrp(grp);
            //insert*********
            card crd1 = new card();
            crd1.setQuestion("question");
            crd1.setAnswer("yes");
            crd1.setCardCel(20);
            crd1.setGroupId(grp.getId());
            bl_card.insertCrd(crd1);
            //insert*********
            card crd2 = new card();
            crd2.setQuestion("question1");
            crd2.setAnswer("yes1");
            crd2.setCardCel(20);
            crd2.setGroupId(grp.getId());
            bl_card.insertCrd(crd2);
            //insert*********
            card crd3 = new card();
            crd3.setQuestion("question2");
            crd3.setAnswer("yes2");
            crd3.setCardCel(1);
            crd3.setGroupId(grp.getId());
            bl_card.insertCrd(crd3);
            //________________
//            List<group> findGrp;
//            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(RuntimeEnvironment.application," DaoMaster ", null);
//            SQLiteDatabase dbRead = helper.getReadableDatabase();
//            DaoMaster daoMaster = new DaoMaster(dbRead);
//            daoSession = daoMaster.newSession();
//            groupDao readFromCard=daoSession.getGroupDao();
//            QueryBuilder qb = readFromCard.queryBuilder();
//            qb.where(cardDao.Properties.CardCel.eq(20),cardDao.Properties.GroupId.eq(grp.getId()));
//            findGrp=qb.list();
//            Log.i("1111",findGrp.toString());

        }catch (CustomException cex){
            assertNotNull(null);
        }catch (Exception ex){
            Log.i("","");
        }
    }
}
