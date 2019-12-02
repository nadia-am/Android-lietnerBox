package com.example.nadia.lietner_box.bl;

import android.widget.Switch;

import com.example.nadia.lietner_box.lib.eachGroup;

import java.util.ArrayList;

/**
 * Created by Nadia on 2/14/2017.
 */

public class clustering {

    public eachGroup computation(String name){
        ArrayList<Integer> cards = new ArrayList<>();
        eachGroup eachGroup = new eachGroup();
        eachGroup.setName(name);
        switch (name){
            case "1100":
                eachGroup.setAmountOfGroup(46);//19 cards in each group
                cards.add(19);
                eachGroup.setAmountOfCards(cards);
                break;
            case "first":
                eachGroup.setAmountOfGroup(9);//50-72-47-53-37-48-53-46-58
                cards.add(50);
                cards.add(72);
                cards.add(47);
                cards.add(53);
                cards.add(37);
                cards.add(48);
                cards.add(53);
                cards.add(46);
                cards.add(58);
                eachGroup.setAmountOfCards(cards);
                break;
            case "second":
                eachGroup.setAmountOfGroup(7);//34-45-57-42-53-54-53
                cards.add(34);
                cards.add(45);
                cards.add(57);
                cards.add(42);
                cards.add(53);
                cards.add(53);
                cards.add(54);
                eachGroup.setAmountOfCards(cards);
                break;
            case "third":
                eachGroup.setAmountOfGroup(6);
                cards.add(63);
                cards.add(57);
                cards.add(54);
                cards.add(63);
                cards.add(48);
                cards.add(52);
                eachGroup.setAmountOfCards(cards);
                break;
            case "pish":
                eachGroup.setAmountOfGroup(8);
                cards.add(41);
                cards.add(41);
                cards.add(41);
                cards.add(41);
                cards.add(41);
                cards.add(41);
                cards.add(41);
                cards.add(40);
                eachGroup.setAmountOfCards(cards);
                break;
            default:
                eachGroup.setAmountOfGroup(42);
                cards.add(12);
                eachGroup.setAmountOfCards(cards);
                break;

        }
        return eachGroup;
    }

}
