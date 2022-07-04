package com.techelevator;

import com.techelevator.view.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine {

    private final Map<String, InventoryItem>vendingMachineStock = new TreeMap<>();
    private Double amount = 0.00; 
    private static final double QUARTER = 0.25;
    private static final double DIME = 0.10;
    private static final double NICKEL = 0.05;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    Log log = new Log();

    public void createVendingMachine(File file){
        
        String line = "";
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){

                 line = sc.nextLine();
                 String[]products = line.split("\\|");

                switch (products[3]) {

                    case "Chip": 
                        Double chipPrice = Double.parseDouble(products[2]);
                        Chip chip = new Chip(products[1], chipPrice); 
                        vendingMachineStock.put(products[0], chip); 
                        break;

                    case "Drink":
                        Double drinkPrice = Double.parseDouble(products[2]);
                        Drink drink = new Drink(products[1], drinkPrice);
                        vendingMachineStock.put(products[0], drink);
                        break;

                    case "Candy":
                        Double candyPrice = Double.parseDouble(products[2]);
                        Candy candy = new Candy(products[1], candyPrice);
                        vendingMachineStock.put(products[0], candy);
                        break;

                    case "Gum":
                        Double gumPrice = Double.parseDouble(products[2]);
                        Gum gum = new Gum(products[1], gumPrice);
                        vendingMachineStock.put(products[0], gum);
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayVendingMachine(){
        //looping thru using getters in sub classes to print values
        for (Map.Entry<String, InventoryItem> m : vendingMachineStock.entrySet()) {
            if (m.getValue().getCurrentStock() == 0) { //<-- if sold out
                System.out.println(m.getKey() + "|" + m.getValue().getName() + "|" + m.getValue().getPrice() + "|Sold Out! ");
            } else {
                System.out.println(m.getKey() + "|" + m.getValue().getName() + "|" + m.getValue().getPrice() + "|Stock: " + m.getValue().getCurrentStock());
            }
        }
    }

    public void selectProduct(String productCode, File file) {
        String startAmount = df.format(amount); //<-- using this to store amount before code below executes
        String amountInDfFormat = ""; //<-- using this for stylistic purposes in method print
        // w/o df.format will print one decimal if 0 or .00001 after. Ex: 3.3 or 3.30001 instead of 3.30

            if (!(vendingMachineStock.containsKey(productCode))) {
                System.out.println("Not a valid entry");

            } else {   //<-- else, productCode user inputs DOES exist

                if (vendingMachineStock.get(productCode).getCurrentStock() > 0) { 

                    if (amount >= vendingMachineStock.get(productCode).getPrice()) { 
                        amount -= vendingMachineStock.get(productCode).getPrice(); 
                        amountInDfFormat = df.format(Math.round((amount * 100.00)) /100.00);
                        vendingMachineStock.get(productCode).decreaseCurrentStock();
                        System.out.println(vendingMachineStock.get(productCode).dispense());

                        String productName = vendingMachineStock.get(productCode).getName();

                       log.log(productName + " " + productCode + " $" + startAmount + " $" + amountInDfFormat, file );

                        System.out.println("Amount Remaining: $" + amountInDfFormat);

                    } else { 
                        System.out.println("Not Enough Money!");
                    }
                } else {  
                    System.out.println("Sold Out!");
                    System.out.println("Amount Remaining: $" + startAmount);
                }
            }

    }

    public void feedMoney(Double amountEntered, File file){
        double startAmount = amount;
        this.amount += amountEntered;
        System.out.println("Amount Remaining: $" + df.format(amount));
        log.log("FEED MONEY: $" + df.format(startAmount) + " $" + df.format(amount), file);

    }

    public void giveChange(File file){
        double startAmount = amount; 

        int quartersReturned = 0;
        int dimesReturned = 0;
        int nickelsReturned = 0;

          while ( amount > 0.00) {

              if (amount >= QUARTER) {
                  amount = Math.round((amount - QUARTER) * 100.00)/100.00;
             
                  quartersReturned++;

              } else if (amount >= DIME) {
                  amount = Math.round((amount - DIME) * 100.00)/100.00;
                  dimesReturned++;

              } else if (amount >= NICKEL) {
                  amount = Math.round((amount - NICKEL) * 100.00)/100.00;
                  nickelsReturned++;
              }
          }
          System.out.println("Your change is: " + quartersReturned + " Quarters, " + dimesReturned + " Dimes, " + nickelsReturned + " Nickels.");
          log.log("GIVE CHANGE: $" + df.format(startAmount) + " $" + df.format(amount),file);
    }

    public Map<String, InventoryItem> getVendingMachineStock() {
        return vendingMachineStock;
    }

    public Double getAmount() {
        return amount;
    }
}
