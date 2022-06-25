package com.techelevator.view;

public class Gum extends InventoryItem {

    public Gum(String name, Double gumPrice) {
        super(name, gumPrice);
    }

    public String dispense(){
        return "Chew Chew, Yum!";
    }

}
