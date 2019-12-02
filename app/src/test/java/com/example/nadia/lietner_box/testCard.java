package com.example.nadia.lietner_box;

import android.util.Log;

import com.example.nadia.lietner_box.bl.cards_bl;
import com.example.nadia.lietner_box.bl.groups_bl;
import com.example.nadia.lietner_box.lib.CustomException;
import com.example.nadia.lietner_box.lib.message;
import com.example.nadia.lietner_box.models.DaoSession;
import com.example.nadia.lietner_box.models.card;
import com.example.nadia.lietner_box.models.cardDao;
import com.example.nadia.lietner_box.models.cards;
import com.example.nadia.lietner_box.models.group;
import com.example.nadia.lietner_box.models.groupDao;
import com.example.nadia.lietner_box.models.groups;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by nadia on 08/08/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class testCard {
    cards_bl bl_card= new cards_bl(RuntimeEnvironment.application);
    groups_bl bl_group = new groups_bl(RuntimeEnvironment.application);

    message msg;
    cards modals_card=new cards(RuntimeEnvironment.application);
    DaoSession daoSession;

    @Test
    public void add_1_card(){
        try {
            card crd = new card();
            crd.setQuestion("first question");
            crd.setAnswer("first answer");
            bl_card.insertCrd(crd);
//            card get_card=modals_card.getCard(crd);
//            assertNotNull(get_card);
        }catch (CustomException cex){
            assertNotNull(null);
        }catch (Exception ex){
            Log.i("","");
        }

    }
    @Test
    public void add_some_card(){
        try {
            group grp = new group();
            grp.setGroupName("ttt");
            bl_group.insertGrp(grp);

            card crd1 = new card();
            crd1.setQuestion("1 question");
            crd1.setAnswer("1 answer");
            crd1.setGroupId(grp.getId());
            bl_card.insertCrd(crd1);

            card crd2 = new card();
            crd2.setQuestion("2 question");
            crd2.setAnswer("2 answer");
            crd2.setGroupId(grp.getId());
            bl_card.insertCrd(crd2);

            card crd3 = new card();
            crd3.setQuestion("3 question");
            crd3.setAnswer("3 answer");
            crd3.setGroupId(grp.getId());
            bl_card.insertCrd(crd3);

            card crd4 = new card();
            crd4.setQuestion("4 question");
            crd4.setAnswer("4 answer");
            crd4.setGroupId(grp.getId());
            bl_card.insertCrd(crd4);

            List<card> get_cards;
//            get_cards=bl_card.getCards(cardDao.Properties.GroupId.eq(grp.getId()));
//            assertEquals(4,get_cards.size());

        }catch (CustomException cex){
            assertNotNull(null);
        }catch (Exception ex){
            Log.i("","");
        }
    }
    @Test
    public void add_card_repeatedQuestion() throws CustomException {
        try{
            group grp = new group();
            grp.setGroupName("ttt");
            bl_group.insertGrp(grp);

            card crd1 = new card();
            crd1.setQuestion("question");
            crd1.setAnswer("1 answer");
            crd1.setGroupId(grp.getId());
            bl_card.insertCrd(crd1);

            card crd2 = new card();
            crd2.setQuestion("question");
            crd2.setAnswer("answer");
            crd2.setGroupId(grp.getId());
            bl_card.insertCrd(crd2);

        }catch (CustomException cex) {
            assertEquals(cex.getMessage(),msg.questionNotUniqe_crd());
        }catch (Exception ex){
            Log.i("","");
        }
    }
    @Test
    public void add_card_nullQuestion(){
        try{
            group grp = new group();
            grp.setGroupName("group");
            bl_group.insertGrp(grp);

            card crd1 = new card();
            crd1.setQuestion("");
            crd1.setAnswer("1 answer");
            crd1.setGroupId(grp.getId());
            bl_card.insertCrd(crd1);
        }catch (CustomException cex) {
            assertEquals(cex.getMessage(),msg.questionIsNull_crd());
        }catch (Exception ex){
            Log.i("","");
        }
    }
    @Test
    public void add_card_nullAnswer(){
        try{
            group grp = new group();
            grp.setGroupName("group");
            bl_group.insertGrp(grp);

            card crd1 = new card();
            crd1.setQuestion("question");
            crd1.setAnswer("");
            crd1.setGroupId(grp.getId());
            bl_card.insertCrd(crd1);


        }catch (CustomException cex) {
            assertEquals(cex.getMessage(),msg.answerIsNull_crd());
        }catch (Exception ex){
            Log.i("","");
        }
    }
    @Test
    public void update_card(){
        try{
            group grp = new group();
            grp.setGroupName("group");
            bl_group.insertGrp(grp);
            //insert*********
            card crd1 = new card();
            crd1.setQuestion("question");
            crd1.setAnswer("yes");
            crd1.setGroupId(grp.getId());
            bl_card.insertCrd(crd1);
            //update*********
            crd1.setQuestion("question2");
            crd1.setAnswer("yes");
            crd1.setCardCel(2);
            crd1.setGroupId(grp.getId());
            bl_card.updateCrd(crd1);
        }catch (CustomException cex) {
            assertEquals(cex.getMessage(),msg.answerIsNull_crd());
        }catch (Exception ex){
            Log.i("","");
        }
    }
    @Test
    public void update_card_questionExist(){
        try{
            group grp = new group();
            grp.setGroupName("group");
            bl_group.insertGrp(grp);
            //insert*********
            card crd1 = new card();
            crd1.setQuestion("question");
            crd1.setAnswer("yes");
            crd1.setGroupId(grp.getId());
            bl_card.insertCrd(crd1);
            //insert*********
            card crd2 = new card();
            crd2.setQuestion("question2");
            crd2.setAnswer("yes");
            crd2.setGroupId(grp.getId());
            bl_card.insertCrd(crd2);
            //update*********
            crd1.setQuestion("question2");
            crd1.setAnswer("yes");
            crd1.setCardCel(2);
            crd1.setGroupId(grp.getId());
            bl_card.updateCrd(crd1);


        }catch (CustomException cex) {
            assertEquals(cex.getMessage(),msg.questionNotUniqe_crd());
        }catch (Exception ex){
            Log.i("","");
        }
    }
    @Test
    public void update_card_questionNotChanged(){
        try{
            group grp = new group();
            grp.setGroupName("group");
            bl_group.insertGrp(grp);
            //insert*********
            card crd1 = new card();
            crd1.setQuestion("question");
            crd1.setAnswer("yes");
            crd1.setGroupId(grp.getId());
            bl_card.insertCrd(crd1);
            //insert*********
            card crd2 = new card();
            crd2.setQuestion("question2");
            crd2.setAnswer("yes");
            crd2.setGroupId(grp.getId());
            bl_card.insertCrd(crd2);
            //update*********
            crd1.setQuestion("question");
            crd1.setAnswer("yes");
            crd1.setCardCel(2);
            crd1.setGroupId(grp.getId());
            bl_card.updateCrd(crd1);
        }catch (CustomException cex) {
            assertEquals(cex.getMessage(),msg.questionNotUniqe_crd());
        }catch (Exception ex){
            Log.i("","");
        }
    }
    @Test
    public void update_card_nullquestion(){
        try{
            group grp = new group();
            grp.setGroupName("group");
            bl_group.insertGrp(grp);
            //insert*********
            card crd1 = new card();
            crd1.setQuestion("question");
            crd1.setAnswer("yes");
            crd1.setGroupId(grp.getId());
            bl_card.insertCrd(crd1);
            //insert*********
            card crd2 = new card();
            crd2.setQuestion("question2");
            crd2.setAnswer("yes");
            crd2.setGroupId(grp.getId());
            bl_card.insertCrd(crd2);
            //update*********
            crd1.setQuestion("");
            crd1.setAnswer("yes");
            crd1.setCardCel(2);
            crd1.setGroupId(grp.getId());
            bl_card.updateCrd(crd1);
        }catch (CustomException cex) {
            assertEquals(cex.getMessage(),msg.questionIsNull_crd());
        }catch (Exception ex){
            Log.i("","");
        }

    }
    @Test
    public void update_card_nullanswer(){
        try{
            group grp = new group();
            grp.setGroupName("group");
            bl_group.insertGrp(grp);
            //insert*********
            card crd1 = new card();
            crd1.setQuestion("question");
            crd1.setAnswer("yes");
            crd1.setGroupId(grp.getId());
            bl_card.insertCrd(crd1);
            //insert*********
            card crd2 = new card();
            crd2.setQuestion("question2");
            crd2.setAnswer("yes");
            crd2.setGroupId(grp.getId());
            bl_card.insertCrd(crd2);
            //update*********
            crd1.setAnswer("");
            crd1.setGroupId(grp.getId());
            bl_card.updateCrd(crd1);
        }catch (CustomException cex) {
            assertEquals(cex.getMessage(),msg.answerIsNull_crd());
        }catch (Exception ex){
            Log.i("","");
        }
    }
    @Test
    public void getCards(){
        try{
            group grp = new group();
            grp.setGroupName("group");
            bl_group.insertGrp(grp);
            //insert*********
            card crd1 = new card();
            crd1.setQuestion("question");
            crd1.setAnswer("yes");
            crd1.setGroupId(grp.getId());
            bl_card.insertCrd(crd1);
            //insert*********
            card crd2 = new card();
            crd2.setQuestion("question2");
            crd2.setAnswer("ye2");
            crd2.setGroupId(grp.getId());
            bl_card.insertCrd(crd2);

//            bl_card.getCards(cardDao.Properties.GroupId.eq(grp.getId()));
            bl_card.getCards(null);

        }catch (CustomException cex) {
            assertEquals(cex.getMessage(),msg.answerIsNull_crd());
        }catch (Exception ex){
            Log.i("","");
        }

    }
}
