package com.techelevator.view;

public abstract class InventoryItem {
    private String name;
    private Double price;
    private int currentStock = 5; // <--added stock as 5 for all classes that extend
    public InventoryItem(String name, Double price) {
        this.name = name;
        this.price = price;
    }
   // getters to be called when looping through map
    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public abstract String dispense(); //abstract method, each item prints a unique String

    public void decreaseCurrentStock(){
        currentStock--;
    }  //used for decreasing stock in map when dispensed

}
