package com.techelevator;


import org.junit.Assert;
import org.junit.Test;
import java.io.File;

public class VendingMachineTest {

    //The file found in main is in a different format from the one found in test
    //below is the one found in my computer
    File file = new File("vendingmachine.csv");//<-- found in tests using this format
    File logFile = new File("Log.txt");

    @Test
    public void Create_Vending_Machine_Test(){
            VendingMachine sut = new VendingMachine();
            sut.createVendingMachine(file);
            int mapSize = sut.getVendingMachineStock().size();
            Assert.assertEquals(16, mapSize);
    }

    @Test
    public void Select_Product_Test() {
        VendingMachine sut = new VendingMachine();
        sut.createVendingMachine(file);
        sut.feedMoney(5.0,logFile);
        sut.selectProduct("D1",logFile);
        double vmAmount = sut.getAmount();

        Assert.assertEquals(4.15, vmAmount, 0.00); //verifying amount was subtracted by "D1" price
    }

    @Test
    public void Select_Product_With_Exact_Amount_Test() {
        VendingMachine sut = new VendingMachine();
        sut.createVendingMachine(file);
        sut.feedMoney(2.0,logFile);
        sut.feedMoney(1.0,logFile);
        sut.selectProduct("C4",logFile); //C4 = 1.5
        sut.selectProduct("C4",logFile); //amount should be exactly 1.5 here
        double vmAmount = sut.getAmount(); //amount should be 0

        Assert.assertEquals(0.0, vmAmount, 0.00);
    }

    @Test
    public void Select_Non_Existent_Product_Test(){
        VendingMachine sut = new VendingMachine();
        sut.createVendingMachine(file);
        sut.feedMoney(5.0,logFile);
        sut.selectProduct("non-existent",logFile); // code doesn't exist, amount shouldn't change
        double vmAmount = sut.getAmount();

        Assert.assertEquals(5.0, vmAmount, 0.00);//verifying amount stayed the same
    }

    @Test
    public void Select_Sold_Out_Product_Test(){
        VendingMachine sut = new VendingMachine();
        sut.createVendingMachine(file);
        sut.feedMoney(10.0,logFile);
        sut.selectProduct("B3",logFile); //10 - 1.5 = 8.5
        sut.selectProduct("B3",logFile); //8.5 - 1.5 = 7
        sut.selectProduct("B3",logFile); //7 - 1.5 = 5.5
        sut.selectProduct("B3",logFile); //5.5 - 1.5 = 4
        sut.selectProduct("B3",logFile); //dispensed last product here, 4 - 1.5 = 2.5
        sut.selectProduct("B3",logFile); //product is sold out at this point, amount should stay at 2.5
        double vmAmount = sut.getAmount();

        Assert.assertEquals(2.5, vmAmount, 0.00);
    }

    @Test
    public void Insufficient_Funds_Test(){
        VendingMachine sut = new VendingMachine();
        sut.createVendingMachine(file);
        sut.selectProduct("A1",logFile); // should not dispense since money wasn't fed
        int potatoCrispStock = sut.getVendingMachineStock().get("A1").getCurrentStock(); //currentStock should remain 5

        Assert.assertEquals(5,potatoCrispStock);
    }

    @Test
    public void Feed_Money_Test(){
        VendingMachine sut = new VendingMachine();
        sut.createVendingMachine(file);
        sut.feedMoney(5.0,logFile);
        double vmAmount = sut.getAmount();
        Assert.assertEquals(5.0, vmAmount,0.00);
    }

    @Test
    public void Give_Change_Sets_Amount_To_Zero_Test(){
        VendingMachine sut = new VendingMachine();
        sut.createVendingMachine(file);
        sut.feedMoney(5.0,logFile);
        sut.giveChange(logFile);
        double sutAmount = sut.getAmount();
        Assert.assertEquals(0.00,sutAmount,0.00);
    }
}
