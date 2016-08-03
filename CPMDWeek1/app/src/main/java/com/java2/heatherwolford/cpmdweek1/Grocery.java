//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Grocery implements Serializable {
    //Member variables
    private String item;
    private int amount;


    //Class Constructors
    public Grocery(ArrayList<JSONObject> groceryList){
        item = "";
        amount = 0;
    }

    public Grocery(String _item, int _amount){
        item = _item;
        amount = _amount;
    }

    //Getter Method
    public String getItem(){
        return item;
    }
    public int getAmount() {
        return amount;
    }

    //Setter Method
    public void setitem(String _item){
        item = _item;
    }
    public void setAmount(int _amount) {
        amount = _amount;
    }
}
