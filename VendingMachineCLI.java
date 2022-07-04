package com.techelevator;


import com.techelevator.view.Menu;

import java.io.File;
import java.util.Scanner;

public class VendingMachineCLI {
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_EXIT };
	private static final String FEED_MONEY = "Feed Money";
	private static final String SELECT_PRODUCT = "Select Product";
	private static final String FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_OPTIONS = {FEED_MONEY, SELECT_PRODUCT, FINISH_TRANSACTION};
	private Menu menu;

	File file = new File("capstone-1/vendingmachine.csv");
	File logFile = new File("capstone-1/Log.txt");

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		System.out.println("************************************");
		System.out.println("     Welcome To Vendo-Matic 800     ");
		System.out.println("************************************");
		Scanner sc = new Scanner(System.in);
		VendingMachine v  = new VendingMachine();
		v.createVendingMachine(file);

		while (true) {

			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {

			     v.displayVendingMachine();

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				while (true) {
					String nextChoice = (String) menu.getChoiceFromOptions(PURCHASE_OPTIONS);

					if (nextChoice.equals(FEED_MONEY)) {
						// Added try since .parseDouble may crash program if user inputs invalid format
							try {
								System.out.println("Please Enter Amount");
								double amountEntered = Double.parseDouble(sc.nextLine());
								if (amountEntered == 1 || amountEntered == 2 || amountEntered == 5 || amountEntered == 10) {
									v.feedMoney(amountEntered, logFile); 
								} else {
									System.out.println("Not A Valid Input!");
								}

							} catch (NumberFormatException e) {
								System.err.println("Not A Valid Entry!");
							}

					} else if (nextChoice.equals(SELECT_PRODUCT)) {
						v.displayVendingMachine();

						System.out.println("Enter Code");
						String codeEntered = sc.nextLine();
						v.selectProduct(codeEntered, logFile);  
					} else if (nextChoice.equals(FINISH_TRANSACTION)) {
						 v.giveChange(logFile); 
                         break; 
					}
				}
			}else if (choice.equals(MAIN_MENU_EXIT)){
				System.out.println(" ***************************************************");
				System.out.println("     Thank you For Shopping At Vendo-Matic 800     ");
				System.out.println("***************************************************");
				  break;
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
