package com.pluralsight.ui;

import com.pluralsight.finalmodels.MediaLineItem;
import com.pluralsight.finalmodels.LineItem;
import com.pluralsight.finalmodels.Order;
import com.pluralsight.models.Media;
import com.pluralsight.models.Movie;
import com.pluralsight.models.VideoFormats;
import com.pluralsight.models.VideoGame;
import com.pluralsight.services.LineItemBuilder;
import com.pluralsight.services.PriceCalculator;
import com.pluralsight.services.Search;
import org.w3c.dom.Text;

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
                
                0) Exit PoS\n""");

        int choice = menu.getUserInputAsInt(1, 1, 0);

        switch (choice)
        {
            case 1:
                orderManagementMenu(null);
                break;
            case 0:
                System.exit(0);
                break;
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
                    addMediaPrompt(rent, currentOrder, true);
                    break;
                case 2:
                    addMediaPrompt(rent, currentOrder, false);
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
        int indexOfItemAdded;
        LineItem itemAdded = null;
        TextManagement.displayText("""
                ---------Specify Media---------
                
                Please specify which media to add.
                
                Search by title: """);

        String userSearch = menu.getUserInput();
        ArrayList<? extends Media> searchedList;

        Search search = new Search();

        if(movie) //IF MOVIE SETUP BEGIN* set searchlist to movie
        {
            searchedList = search.searchForMediaTitle(userSearch, movieCatalogue);
        }
        else //IF VIDEO GAME SETUP BEGIN+ set searchlist to videogame
        {
            searchedList = search.searchForMediaTitle(userSearch, gameCatalogue);
        }

        if(!TextManagement.displaySearchedMedia(searchedList)) //if no results
        {
            String rememberWord = "";
            if(rent) {rememberWord = "trying to rent.";}
            else {rememberWord = "trying to purchase.";}

            TextManagement.displayText("Returning to Add Item screen... check catalogue and try again.");
            TextManagement.displayText("Remembering that you were " + rememberWord);
            TextManagement.pressEnterToContinue();
            addMediaScreen(rent, currentOrder);
        }

        Movie selectedMovie; //*+declare end movie
        VideoGame selectedGame; //*+declare end game
        int userTitleConf = 0; //*+declare which media user chose from list

        if (searchedList.size() == 1) //*+if one result only
        {
            TextManagement.displayText("Is this title correct?: (1 = yes, 9 = no)");
            TextManagement.displayText(searchedList.get(0).getId().toString()+ "|" + searchedList.get(0).getTitle() + "\n");

            userTitleConf = menu.getUserInputAsInt(1, 1, 2);
        }
        else //if multiple results
        {
            TextManagement.displayText("Enter the number of the correct title (or 9 to cancel): ");

            //handle which inputs are valid by arraylist size... 10:50
            userTitleConf = menu.getUserInputAsInt(1);

            if(userTitleConf > searchedList.size() || userTitleConf < 0)
            {userTitleConf = 9;} //banish user to the shadow realm if number entered doesnt exist on list
        }

        switch(userTitleConf) //which option did user chose? //panic coding anti pattern arrows :)
                //THE POINT WHERE MOVIE/GAME SPLIT
        {
            case 1: //item on the list #1 or only 1 item + confirmed
                if(searchedList.get(0).getId().contains("MV")) //if movie MOVIE BRANCH START*
                {
                    VideoFormats chosenFormat;
                    if(searchedList.get(0).getFormats().size() == 1) //if one format available
                    {
                        chosenFormat = (VideoFormats) searchedList.get(0).getFormats().get(0);
                        selectedMovie = (Movie) searchedList.get(0);
                        selectedMovie.setChosenFormat(chosenFormat);

                        TextManagement.displayText("Only one format available: " + selectedMovie.getChosenFormat().toString()
                        + ".");
                    }
                    else //if many formats available
                    {
                        selectedMovie = (Movie) searchedList.get(0);
                        TextManagement.displayText("\nHere are the available formats for this movie:");
                        TextManagement.displayFormatsAvailable(selectedMovie); //display formats
                        TextManagement.displayText("Please enter a video format for the movie.\n");

                        String videoFChoice = menu.getUserInput();

                        boolean searchHit = false;

                        for (VideoFormats format : selectedMovie.getFormats()) // check for matching format
                        {
                            if (videoFChoice.toLowerCase().contains(format.toString().toLowerCase()))
                            {
                                selectedMovie.setChosenFormat(format); //what we wanted to do
                                searchHit = true;
                            }
                        }
                        if(!searchHit) //no matching format? go back
                        {
                            String rememberWord = "";
                            if (rent) {rememberWord = "trying to rent.";}
                            else {rememberWord = "trying to purchase.";}

                            TextManagement.displayText("Returning to Add Item screen... check catalogue, choose valid selection, and try again.");
                            TextManagement.displayText("Remembering that you were " + rememberWord);
                            TextManagement.pressEnterToContinue();
                            addMediaScreen(rent, currentOrder);
                            break;
                        }
                    }
                    boolean isWS = false;
                    if(selectedMovie.getChosenFormat() == VideoFormats.SD || selectedMovie.getChosenFormat() == VideoFormats.VHS)
                    {//if format is VHS or SD
                        TextManagement.displayText("Would you like fullscreen or widescreen? Enter 1 for fullscreen; enter 2 for widescreen. \n");
                        int screenChoice = menu.getUserInputAsInt(1, 1, 2);

                        switch(screenChoice)
                        {
                            case 1:
                                isWS = false; //chose fullscreen
                                break;
                            case 2:
                                isWS = true; //chose widescreen
                                break;
                        }
                    }
                    TextManagement.displayText("Selected format: " + selectedMovie.getChosenFormat().toString() + "\n");
                    if(rent) // if renting...
                    {
                        System.out.println("Enter the number of days to rent (1 days, 3 days, or 7 days): ");
                        int daysRented = menu.getUserInputAsInt(1, 1, 3, 7);

                        double basePrice = PriceCalculator.calculateMoviePurchase(selectedMovie, selectedMovie.getChosenFormat());
                        double finalPrice = PriceCalculator.calculateRental(daysRented, basePrice);

                        if(selectedMovie.getChosenFormat() == VideoFormats.SD || selectedMovie.getChosenFormat() == VideoFormats.VHS)
                        {//if renting and VHS/SD
                            MediaLineItem orderLine =
                                    LineItemBuilder.buildRentalLineWSFS(selectedMovie, finalPrice, daysRented, isWS);
                            currentOrder.addItem(orderLine);
                            indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                            itemAdded = currentOrder.getItems().get(indexOfItemAdded);
                        }
                        else
                        {//if renting any other format
                            MediaLineItem orderLine =
                                    LineItemBuilder.buildRentalLine(selectedMovie, finalPrice, daysRented);
                            currentOrder.addItem(orderLine);
                            indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                            itemAdded = currentOrder.getItems().get(indexOfItemAdded);
                        }

                    }
                    else //purchasing
                    {
                        if(selectedMovie.getChosenFormat() == VideoFormats.SD || selectedMovie.getChosenFormat() == VideoFormats.VHS)
                        {//if buying an VHS/SD
                            LineItem orderLine =
                                    LineItemBuilder.buildSimpleLineWSFS(selectedMovie, isWS);
                            currentOrder.addItem(orderLine);
                            indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                            itemAdded = currentOrder.getItems().get(indexOfItemAdded);
                        }
                        else
                        {//if buying any other format
                            LineItem orderLine =
                                    LineItemBuilder.buildSimpleLine(selectedMovie);
                            currentOrder.addItem(orderLine);
                            indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                            itemAdded = currentOrder.getItems().get(indexOfItemAdded);
                        }
                    }
                }
                //diverge to game branch, copy pasting stuff
                if(searchedList.get(0).getId().contains("VG"))
                {

                }
                TextManagement.displayText("""
                        Adding to cart: 
                        
                        """);
                TextManagement.displayText(itemAdded.printLineItem());

                TextManagement.displayText("\nReturning to Current Transaction screen...");
                TextManagement.pressEnterToContinue();
                orderManagementMenu(currentOrder);
                break;
            case 9:
                String rememberWord = "";
                if (rent) {rememberWord = "trying to rent.";}
                else {rememberWord = "trying to purchase.";}

                TextManagement.displayText("Returning to Add Item screen... check catalogue, choose valid selection, and try again.");
                TextManagement.displayText("Remembering that you were " + rememberWord);
                TextManagement.pressEnterToContinue();
                addMediaScreen(rent, currentOrder);
                break;
        }

    }
}
