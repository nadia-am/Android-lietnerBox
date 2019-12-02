package com.example.nadia.lietner_box.lib;

import java.util.ArrayList;

/**
 * Created by Nadia on 2/14/2017.
 */

public class eachGroup {
    String name;
    Integer amountOfGroup;
    ArrayList<Integer> amountOfCards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmountOfGroup() {
        return amountOfGroup;
    }

    public void setAmountOfGroup(Integer amountOfGroup) {
        this.amountOfGroup = amountOfGroup;
    }

    public ArrayList<Integer> getAmountOfCards() {
        return amountOfCards;
    }

    public void setAmountOfCards(ArrayList<Integer> amountOfCards) {
        this.amountOfCards = amountOfCards;
    }
}
