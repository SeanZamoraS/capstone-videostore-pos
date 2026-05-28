package com.pluralsight.ui;

import com.pluralsight.finalmodels.Order;
import com.pluralsight.models.Media;
import com.pluralsight.models.Movie;
import com.pluralsight.models.VideoGame;
import com.pluralsight.services.Search;

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

    private static void addMediaPrompt(boolean rent, Order currentOrder, boolean movie)
    {
        TextManagement.displayText("""
                ---------Specify Media---------
                
                Please specify which media to add.
                
                Search by title: """);

        String userSearch = menu.getUserInput();
        ArrayList<? extends Media> searchedList;

        Search search = new Search();

        if(movie)
        {
            searchedList = search.searchForMediaTitle(userSearch, movieCatalogue);
        }
        else
        {
            searchedList = search.searchForMediaTitle(userSearch, gameCatalogue);
        }

        if(!TextManagement.displaySearchedMedia(searchedList))
        {
            String rememberWord = "";
            if(rent) {rememberWord = "trying to rent.";}
            else {rememberWord = "trying to purchase.";}

            TextManagement.displayText("Returning to Add Item screen... check catalogue and try again.");
            TextManagement.displayText("Remembering that you were " + rememberWord);
            TextManagement.pressEnterToContinue();
            addMediaScreen(rent, currentOrder);
        }

        Movie selectedMovie;
        VideoGame selectedGame;
        int userTitleConf = 0;

        if (searchedList.size() == 1)
        {
            TextManagement.displayText("Is this title correct? (1 = yes, 9 = no\n");
            TextManagement.displayText(searchedList.get(0).getTitle());

            userTitleConf = menu.getUserInputAsInt(1, 1, 2);
        }
        else
        {
            TextManagement.displayText("Enter the number of the correct title (or 9 to cancel): ");

            //handle which inputs are valid by arraylist size... 10:50
            userTitleConf = menu.getUserInputAsInt(1);
            switch(userTitleConf)
            {
                //?
            }
        }

        switch(userTitleConf)
        {
            case 1:
                break;
            case 9:
                String rememberWord = "";
                if (rent) {rememberWord = "trying to rent.";}
                else {rememberWord = "trying to purchase.";}

                TextManagement.displayText("Returning to Add Item screen... check catalogue and try again.");
                TextManagement.displayText("Remembering that you were " + rememberWord);
                TextManagement.pressEnterToContinue();
                addMediaScreen(rent, currentOrder);
                break;
        }

    }
}
