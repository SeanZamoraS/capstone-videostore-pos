package com.pluralsight.ui;

import com.pluralsight.finalmodels.Order;
import com.pluralsight.models.Media;
import com.pluralsight.models.Movie;
import com.pluralsight.models.VideoGame;

import java.util.ArrayList;

public class Menu
{
    private static TextManagement menu = new TextManagement();

    static ArrayList<Movie> movieCatalogue;
    static ArrayList<VideoGame> gameCatalogue;

    public Menu(ArrayList<Movie> movieCatalogue, ArrayList<VideoGame> gameCatalogue)
    {
        this.movieCatalogue = movieCatalogue;
        this.gameCatalogue = gameCatalogue;
    }

    public static void startMenu()
    {
        TextManagement.displayText("""
                ---------Start Menu---------
                
                Le Epic Video Store | Point of Sales
                
                Enter a number to continue:
                
                1) Start a new transaction
                2) Show movie/game catalogue
                
                0) Exit PoS\n""");

        int choice = menu.getUserInputAsInt(1, 1, 2, 0);

        switch (choice)
        {
            case 1:
                orderManagementMenu(null);
                break;
            case 2:
                System.exit(0);
        }
    }

    private static void orderManagementMenu(Order sameOrder)
    {
        Order currentOrder;
        if(sameOrder == null)
        {currentOrder = new Order();}
        else {currentOrder = sameOrder;}


        while (true)
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
                    
                    8) View movie catalogue
                    9) View game catalogue\n""");

            int choice = menu.getUserInputAsInt(1, 1, 2, 3, 4, 5, 6, 8, 9);

            switch (choice)
            {
                case 1:
                    addMediaScreen(false, currentOrder);
                    break;
                case 2:
                    addMediaScreen(true, currentOrder);
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

                case 8:
                    TextManagement.displayAllMedia(movieCatalogue);
                    TextManagement.pressEnterToContinue();
                    break;
                case 9:
                    TextManagement.displayAllMedia(gameCatalogue);
                    TextManagement.pressEnterToContinue();
                    break;
            }
        }
    }

    private static void addMediaScreen(boolean rent, Order currentOrder)
    {
        currentOrder = currentOrder;

        while(true)
        {
            TextManagement.displayText("""
                    ---------Add Item---------
                    
                    Enter a number to continue:
                    
                    1) Add a movie
                    2) Add a game
                    
                    8) View movie catalogue
                    9) View game catalogue
                    0) Back\n""");

            int choice = menu.getUserInputAsInt(1, 1, 2, 8, 9, 0);

            switch(choice)
            {
                case 1:
                    break;
                case 2:
                    break;


                case 8:
                    TextManagement.displayAllMedia(movieCatalogue);
                    TextManagement.pressEnterToContinue();
                    break;
                case 9:
                    TextManagement.displayAllMedia(gameCatalogue);
                    TextManagement.pressEnterToContinue();
                    break;
                case 0:
                    orderManagementMenu(currentOrder);
                    break;
            }
        }


    }
}
