package com.techelevator.view;

public class Drink extends InventoryItem {

    public Drink(String name, Double price) {
        super(name, price);
    }

    public String dispense(){
        return "Glug Glug, Yum!";
    }

}
