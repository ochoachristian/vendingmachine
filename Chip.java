package com.techelevator.view;

public class Chip extends InventoryItem {

    public Chip(String name, Double price) {
        super(name, price);
    }

    @Override
    public String dispense(){

        return "Crunch Crunch, Yum!";

    }


}
