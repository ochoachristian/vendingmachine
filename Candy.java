package com.techelevator.view;

public class Candy extends InventoryItem {

    public Candy(String name, Double candyPrice) {
        super(name,candyPrice);
    }

    public String dispense(){

        return "Munch Munch, Yum!";

    }
    
}
