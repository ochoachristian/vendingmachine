package com.techelevator;

import com.techelevator.view.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine {

    private final Map<String, InventoryItem>vendingMachineStock = new TreeMap<>(); // <-- TreeMap sorts key by ascending order
    // hashmaps are unordered, when printed keys rows will be randomized
    private Double amount = 0.00; // storing money user inputs
    private static final double QUARTER = 0.25;
    private static final double DIME = 0.10;
    private static final double NICKEL = 0.05;
    private static final DecimalFormat df = new DecimalFormat("0.00"); //<-- using this to format double, w/o it prints 0.00
    Log log = new Log(); //<-- creating instance of log to use within methods

    public void createVendingMachine(File file){
        //created map to store .csv file, looping thru to get text
        //stored abstract class InventoryItem as value for map
        String line = "";
        try {
            Scanner sc = new Scanner(file); //<-- reading file
            while(sc.hasNextLine()){

                 line = sc.nextLine(); //<-- storing line into string
                 String[]products = line.split("\\|");//<-- using .split to turn line in file to array

                switch (products[3]) {

                    case "Chip":    // <-- if index 3 of split string = Chip
                        Double chipPrice = Double.parseDouble(products[2]); // <-- parsing string index 2 to double, this will be passed thru constructor
                        Chip chip = new Chip(products[1], chipPrice);   // <--creating instance of chip, which takes in a String name and double price
                        vendingMachineStock.put(products[0], chip);   // <-- adding product code as String key, adding chip as inventoryItem value for map
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

    public void displayVendingMachine(){ //<-- prints csv file
        //looping thru using getters in sub classes to print values
        for (Map.Entry<String, InventoryItem> m : vendingMachineStock.entrySet()) {
            if (m.getValue().getCurrentStock() == 0) { //<-- if sold out
                System.out.println(m.getKey() + "|" + m.getValue().getName() + "|" + m.getValue().getPrice() + "|Sold Out! "); // added line for if sold out
            } else {
                System.out.println(m.getKey() + "|" + m.getValue().getName() + "|" + m.getValue().getPrice() + "|Stock: " + m.getValue().getCurrentStock());
            }
        }
    }

    public void selectProduct(String productCode, File file) {
        String startAmount = df.format(amount); //<-- using this to store amount before code below executes
        String amountInDfFormat = ""; //<-- using this for stylistic purposes in method print
        // w/o df.format will print one decimal if 0 or .00001 after. Ex: 3.3 or 3.30001 instead of 3.30

            if (!(vendingMachineStock.containsKey(productCode))) { // <-- if doesn't exist
                System.out.println("Not a valid entry");

            } else {   //<-- else, productCode user inputs DOES exist

                if (vendingMachineStock.get(productCode).getCurrentStock() > 0) { // <-- if product is in stock

                    if (amount >= vendingMachineStock.get(productCode).getPrice()) { //<-- if can afford item

                        amount -= vendingMachineStock.get(productCode).getPrice(); //<-- updating amount variable
                        amountInDfFormat = df.format(Math.round((amount * 100.00)) /100.00); //<-- using Math.round to round decimals

                        vendingMachineStock.get(productCode).decreaseCurrentStock(); //<-- decreasing stock in map by 1
                        System.out.println(vendingMachineStock.get(productCode).dispense()); //<-- printing String message

                        String productName = vendingMachineStock.get(productCode).getName(); //<-- getting name for log

                       log.log(productName + " " + productCode + " $" + startAmount + " $" + amountInDfFormat, file );

                        System.out.println("Amount Remaining: $" + amountInDfFormat);

                    } else { //<-- if amount isn't enough
                        System.out.println("Not Enough Money!");
                    }
                } else {   //<-- currentStock = 0
                    System.out.println("Sold Out!");
                    System.out.println("Amount Remaining: $" + startAmount);
                }
            }

    }

    public void feedMoney(Double amountEntered, File file){ //<-- method call updates amount variable

        double startAmount = amount; //<-- amount before code below executes
        this.amount += amountEntered; //<-- updating amount

        System.out.println("Amount Remaining: $" + df.format(amount)); //<-- using df.format to print w 2 decimals
        log.log("FEED MONEY: $" + df.format(startAmount) + " $" + df.format(amount), file);

    }

    public void giveChange(File file){
        double startAmount = amount; //<-- amount before code below executes

        int quartersReturned = 0;
        int dimesReturned = 0;
        int nickelsReturned = 0;

          while ( amount > 0.00) {

              if (amount >= QUARTER) { //<-- using final variables declared above
                  amount = Math.round((amount - QUARTER) * 100.00)/100.00;
                  //^amount is first subtracted by coin, then multiplied by 100 so Math can round properly,
                  // then divided by 100 after rounding to get the correct, rounded value
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
