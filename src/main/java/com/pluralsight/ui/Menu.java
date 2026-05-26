package com.pluralsight.ui;

public class Menu
{
    static TextManagement menu = new TextManagement();

    public static void startMenu()
    {
        TextManagement.displayText("""
                ---------Start Menu---------
                
                Le Epic Video Store | Point of Sales
                
                Enter a number to continue:
                
                1) Start a new transaction
                2) Show movie/game catalogue
                
                0) Exit PoS\n""");

        int choice = menu.getUserInputAsInt(1);

        switch (choice)
        {
            case 1:
                orderManagementMenu();
                break;
            case 2:
                System.exit(0);
        }
    }

    private static void orderManagementMenu()
    {
        TextManagement.displayText("""
                ---------Current Transaction---------
                
                Enter a number to continue:
                
                1) Add a movie/game for purchase
                2) Add a movie/game for rental 
                3) Add merchandise
                4) Add a fee
                
                5) Checkout
                6) Cancel transaction 
                
                9) Show movie/game catalogue""");

        int choice = menu.getUserInputAsInt(1);

        switch (choice)
        {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;

            case 5:
                break;
            case 6:
                startMenu();
                break;

            case 9:
                break;
        }
    }
}
