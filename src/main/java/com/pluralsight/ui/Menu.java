package com.pluralsight.ui;

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
