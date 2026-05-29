package com.pluralsight.ui;

import com.pluralsight.filemanagement.WriteToFile;
import com.pluralsight.finalmodels.MediaLineItem;
import com.pluralsight.finalmodels.LineItem;
import com.pluralsight.finalmodels.Order;
import com.pluralsight.models.*;
import com.pluralsight.services.LineItemBuilder;
import com.pluralsight.services.PriceCalculator;
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
        TextManagement.displayTextColor("""
                --------------Start Menu--------------
                
                Le Epic Video Store | Point of Sales
                
                Enter a number to continue:
                
                1) Start a new transaction
                
                0) Exit PoS\n""", Colors.CYAN);

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
            TextManagement.displayTextColor("""
                    ---------Current Transaction---------
                    
                    Enter a number to continue:
                    
                    1) Add a movie/game for purchase
                    2) Add a movie/game for rental 
                    3) Add merchandise X NEXT UPDATE FEATURE
                    4) Add a fee X NEXT UPDATE FEATURE
                    
                    5) Checkout
                    6) Cancel transaction 
                    
                    8) View movie catalogue
                    9) View game catalogue\n""", Colors.PURPLE);

            int choice = menu.getUserInputAsInt(1, 1, 2, 5, 6, 8, 9);

            switch (choice)
            {
                case 1:
                    addMediaScreen(false, currentOrder);
                    break;
                case 2:
                    addMediaScreen(true, currentOrder);
                    break;
                //case 3:

                    //break;
                //case 4:
                    //break;

                case 5:
                    checkOutScreen(currentOrder);
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

    private static void checkOutScreen(Order currentOrder)
    {
        TextManagement.displayTextColor("""
                ---------Checkout---------
                
                Enter a number to continue:
                
                1) Check order
                2) Cancel order
                
                0) Back
                """, Colors.GREEN);

        int choice = menu.getUserInputAsInt(1, 1,2, 0);

        switch (choice)
        {
            case 1:
                double total = PriceCalculator.calculateTotal(currentOrder);
                double tax = PriceCalculator.calculateTax(currentOrder);
                double grandTotal = PriceCalculator.calculateGrandTotal(total, tax);

                currentOrder.setTotal(total);
                currentOrder.setTotalTax(tax);
                currentOrder.setGrandTotal(grandTotal);

                WriteToFile wtf = new WriteToFile();
                wtf.showReceipt(currentOrder);

                TextManagement.displayText("""
                        Is this receipt correct?
                        
                        Enter a number to continue: 
                        
                        1) Yes, check out now
                        2) No, go back\n""");

                int confirm = menu.getUserInputAsInt(1, 1, 2);

                switch(confirm)
                {
                    case 1:
                        wtf.writeToReceipt(currentOrder);
                        double confirmedTotal = currentOrder.getGrandTotal();
                        System.out.printf("\nConfirmed, ring customer up for $%.2f.\n", confirmedTotal);
                        TextManagement.displayText("Check receipts folder for record of transaction if desired.");

                        TextManagement.displayText("\nStarting from fresh...\n");
                        TextManagement.displayText("Returning to start menu.");
                        TextManagement.pressEnterToContinue();
                        startMenu();
                        break;
                    case 2:
                        orderManagementMenu(currentOrder);
                        break;
                }
                break;
            case 2:
                TextManagement.displayText("Returning to start menu.");
                TextManagement.pressEnterToContinue();
                startMenu();
                break;
            case 0:
                orderManagementMenu(currentOrder);
                break;
        }
    }

    private static void addFeeScreen(Order currentOrder)
    {
        TextManagement.displayText("""
                ---------Add Fee---------
                
                Enter a number to continue:
                
                1) Customer returning item late
                2) Customer lost item
                
                0) Back\n""");

        int feeChoice = menu.getUserInputAsInt(1, 1, 2, 0);

        boolean isLost = false;

        TextManagement.displayText("""
                What type of item is the customer being fined for?
                
                Enter a number to continue:
                
                1) Movie
                2) Video game
                3) Console\n""");

        int itemChoice = menu.getUserInputAsInt(1, 1, 2, 3);

        Media searchedItem;
        Consoles chosenConsoles;

        Search search = new Search();

        if (itemChoice == 1 || itemChoice == 2)
        {
            TextManagement.displayText("""
                    Search for the media's title. \n""");
            String userInput = menu.getUserInput();
        }
        else
        {
            TextManagement.displayText("Enter the name of the console. \n");
            String userInput = menu.getUserInput();
        }

        String userSearched = menu.getUserInput();

        switch (itemChoice)
        {
            case 1:
                ArrayList<? extends Media> searchedList = search.searchForMediaTitle(userSearched, movieCatalogue);

                break;
            case 2:
                search.searchForMediaTitle(userSearched, gameCatalogue);
                break;
            case 3:
                break;
        }

        switch (feeChoice)
        {
            case 1:
                TextManagement.displayText("Enter the number of days late the item is: ");
                int daysLate = menu.getUserInputAsInt(1);
                isLost = false;
                break;
            case 2:
                isLost = true;
                break;
            case 0:
                orderManagementMenu(currentOrder);
                break;
        }



    }

    private static void addMediaScreen(boolean rent, Order currentOrder)
    {
        currentOrder = currentOrder;

        while(true)
        {
            TextManagement.displayTextColor("""
                    ---------Add Item---------
                    
                    Enter a number to continue:
                    
                    1) Add a movie
                    2) Add a game
                    
                    8) View movie catalogue
                    9) View game catalogue
                    0) Back\n""", Colors.RED);

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
        TextManagement.displayTextColor("""
                
                ---------Specify Media---------
                
                Please specify which media to add.
                
                Search by title: """, Colors.BLUE);

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
            if(rent) {rememberWord = "trying to rent something.";}
            else {rememberWord = "trying to purchase something.";}

            TextManagement.displayText("Returning to Add Item screen... check catalogue and try again.");
            TextManagement.displayText("\nRemembering that you were " + rememberWord);
            TextManagement.pressEnterToContinue();
            addMediaScreen(rent, currentOrder);
            return;
        }

        Movie selectedMovie; //*+declare end movie
        VideoGame selectedGame;//*+declare end game
        Consoles selectedConsole;//*+declare end console if used
        int userTitleConf = 0; //*+declare which media user chose from list

        if (searchedList.size() == 1) //*+if one result only
        {
            TextManagement.displayText("""
                    Is this title correct?
                    Enter a number to continue:
                    
                    1) Yes
                    9) No
                    """);
            TextManagement.displayText("TITLE:");
            TextManagement.displayText(searchedList.get(0).getId().toString()+ "|" + searchedList.get(0).getTitle() + "\n");

            userTitleConf = menu.getUserInputAsInt(1, 1, 2);
        }
        else //if multiple results
        {
            TextManagement.displayText("""
                    Enter the number of the correct title:
                    (or enter 9 to cancel)""");

            //handle which inputs are valid by arraylist size... 10:50
            userTitleConf = menu.getUserInputAsInt(1);

            if(userTitleConf > searchedList.size() || userTitleConf < 0)
            {userTitleConf = 9;} //banish user to the shadow realm if number entered doesnt exist on list
        }

        switch(userTitleConf) //which option did user chose? //panic coding anti pattern arrows :)
                //THE POINT WHERE MOVIE/GAME SPLIT +*+*+*+*+*+**+*+*+**+*+**+START OF SWITCH
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

                        TextManagement.displayText("\nOnly one format available: " + selectedMovie.getChosenFormat().toString()
                        + ".");
                    }
                    else //if many formats available
                    {
                        selectedMovie = (Movie) searchedList.get(0);
                        TextManagement.displayText("Here are the available formats for this movie:");
                        TextManagement.displayFormatsAvailable(selectedMovie); //display formats
                        TextManagement.displayText("""
                                Please enter a video format for the movie.
                                For example, type VHS, SD, HD, or BLURAY (as available).\n""");

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
                        TextManagement.displayText("""
                                
                                Would you like the fullscreen or widescreen version?
                                Enter a number to continue:
                                
                                1) Fullscreen
                                2) Widescreen
                                """);
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
                    if (selectedMovie.getChosenFormat() == VideoFormats.SD || selectedMovie.getChosenFormat() == VideoFormats.VHS)
                    {
                        String word = "fullscreen";
                        if(isWS)
                        {
                            word = "widescreen";
                        }
                        TextManagement.displayText("\nSelected format: " + selectedMovie.getChosenFormat().toString() +
                                " ("+ word.toUpperCase() + ")" + "\n");
                    }
                    else
                    {
                        TextManagement.displayText("\nSelected format: " + selectedMovie.getChosenFormat().toString() + "\n");
                    }
                    if(rent) // if renting...
                    {
                        System.out.println("""
                                Enter the number of days to rent:
                                (You can rent 1 day, 3 days, or 7 days)
                                
                                Enter 1, 3, or 7.""");
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

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
                        }
                        else
                        {//if renting any other format
                            MediaLineItem orderLine =
                                    LineItemBuilder.buildRentalLine(selectedMovie, finalPrice, daysRented);
                            currentOrder.addItem(orderLine);
                            indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                            itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
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

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
                        }
                        else
                        {//if buying any other format
                            LineItem orderLine =
                                    LineItemBuilder.buildSimpleLine(selectedMovie);
                            currentOrder.addItem(orderLine);
                            indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                            itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
                        }
                    }
                }
                //diverge to game branch, copy pasting stuff +++++++++++++++++++++++++++
                if(searchedList.get(0).getId().contains("VG")) //
                {
                    Consoles chosenFormat;
                    if(searchedList.get(0).getFormats().size() == 1) //if one format available
                    {
                        chosenFormat = (Consoles) searchedList.get(0).getFormats().get(0);
                        selectedGame = (VideoGame) searchedList.get(0);
                        selectedGame.setChosenFormat(chosenFormat);

                        TextManagement.displayText("\nOnly one format available: " + selectedGame.getChosenFormat().toString()
                                + ".");
                    }
                    else //if many formats available
                    {
                        selectedGame = (VideoGame) searchedList.get(0);
                        TextManagement.displayText("Here are the available formats for this movie:");
                        TextManagement.displayFormatsAvailable(selectedGame); //display formats
                        TextManagement.displayText("""
                                Please enter a console for the game.
                                For example, type WII, GAMECUBE, XBOX360, XBOX, PS2, or PS3 (as available).\n""");

                        String consoleChoice = menu.getUserInput();

                        boolean searchHit = false;

                        for (Consoles console : selectedGame.getFormats()) // check for matching format
                        {
                            if (consoleChoice.toLowerCase().contains(console.toString().toLowerCase()))
                            {
                                selectedGame.setChosenFormat(console); //what we wanted to do
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

                    TextManagement.displayText("\nSelected format: " + selectedGame.getChosenFormat().toString() + "\n");

                    if(rent) // if renting...
                    {
                        System.out.println("""
                                Enter the number of days to rent:
                                (You can rent 1 day, 3 days, or 7 days)
                                
                                Enter 1, 3, or 7.""");
                        int daysRented = menu.getUserInputAsInt(1, 1, 3, 7);

                        double basePrice = PriceCalculator.calculateVideoGamePurchase(selectedGame);
                        double finalPrice = PriceCalculator.calculateRental(daysRented, basePrice);

                        MediaLineItem orderLine =
                                LineItemBuilder.buildRentalLine(selectedGame, finalPrice, daysRented);
                        currentOrder.addItem(orderLine);
                        indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                        itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                        TextManagement.displayText("""
                        Adding to cart: 
                        """);
                        TextManagement.displayText(itemAdded.printLineItem());

                        TextManagement.displayText("Would the customer like to check out a " + selectedGame.getChosenFormat().toString()
                        + " to go with " + selectedGame.getTitle() +"?");
                        TextManagement.displayText("""
                                
                                1) Yes
                                2) No
                                """);

                        int consoleRentChoice = menu.getUserInputAsInt(1, 1, 2);

                        switch (consoleRentChoice)
                        {
                            case 1:
                                int indexOfItemAdded1;
                                LineItem itemAdded1;

                                MediaLineItem consoleLine =
                                        LineItemBuilder.buildConsoleLine(selectedGame.getChosenFormat(), daysRented);
                                currentOrder.addItem(consoleLine);
                                indexOfItemAdded1 = currentOrder.getItems().indexOf(consoleLine);
                                itemAdded1 = currentOrder.getItems().get(indexOfItemAdded1);

                                TextManagement.displayText("""
                                        
                                        Adding to cart: 
                                        """);
                                TextManagement.displayText(itemAdded1.printLineItem());
                                break;
                            case 2:
                                TextManagement.displayText("\nNo worries.");
                                break;
                        }

                    }
                    else //purchasing
                    {
                        LineItem orderLine =
                                LineItemBuilder.buildSimpleLine(selectedGame);
                        currentOrder.addItem(orderLine);
                        indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                        itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                        TextManagement.displayText("""
                        Adding to cart: 
                        """);
                        TextManagement.displayText(itemAdded.printLineItem());

                    }
                }

                TextManagement.displayText("\nReturning to Current Transaction screen...");
                TextManagement.pressEnterToContinue();
                orderManagementMenu(currentOrder);
                break;
            case 2:
                if(searchedList.get(1).getId().contains("MV")) //if movie MOVIE BRANCH START*
                {
                    VideoFormats chosenFormat;
                    if(searchedList.get(1).getFormats().size() == 1) //if one format available
                    {
                        chosenFormat = (VideoFormats) searchedList.get(1).getFormats().get(0);
                        selectedMovie = (Movie) searchedList.get(1);
                        selectedMovie.setChosenFormat(chosenFormat);

                        TextManagement.displayText("\nOnly one format available: " + selectedMovie.getChosenFormat().toString()
                                + ".");
                    }
                    else //if many formats available
                    {
                        selectedMovie = (Movie) searchedList.get(1);
                        TextManagement.displayText("Here are the available formats for this movie:");
                        TextManagement.displayFormatsAvailable(selectedMovie); //display formats
                        TextManagement.displayText("""
                                Please enter a video format for the movie.
                                For example, type VHS, SD, HD, or BLURAY (as available).\n""");

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
                        TextManagement.displayText("""
                                
                                Would you like the fullscreen or widescreen version?
                                Enter a number to continue:
                                
                                1) Fullscreen
                                2) Widescreen
                                """);
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
                    if (selectedMovie.getChosenFormat() == VideoFormats.SD || selectedMovie.getChosenFormat() == VideoFormats.VHS)
                    {
                        String word = "fullscreen";
                        if(isWS)
                        {
                            word = "widescreen";
                        }
                        TextManagement.displayText("\nSelected format: " + selectedMovie.getChosenFormat().toString() +
                                " ("+ word.toUpperCase() + ")" + "\n");
                    }
                    else
                    {
                        TextManagement.displayText("\nSelected format: " + selectedMovie.getChosenFormat().toString() + "\n");
                    }
                    if(rent) // if renting...
                    {
                        System.out.println("""
                                Enter the number of days to rent:
                                (You can rent 1 day, 3 days, or 7 days)
                                
                                Enter 1, 3, or 7.""");
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

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
                        }
                        else
                        {//if renting any other format
                            MediaLineItem orderLine =
                                    LineItemBuilder.buildRentalLine(selectedMovie, finalPrice, daysRented);
                            currentOrder.addItem(orderLine);
                            indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                            itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
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

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
                        }
                        else
                        {//if buying any other format
                            LineItem orderLine =
                                    LineItemBuilder.buildSimpleLine(selectedMovie);
                            currentOrder.addItem(orderLine);
                            indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                            itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
                        }
                    }
                }
                //diverge to game branch, copy pasting stuff +++++++++++++++++++++++++++
                if(searchedList.get(1).getId().contains("VG")) //
                {
                    Consoles chosenFormat;
                    if(searchedList.get(1).getFormats().size() == 1) //if one format available
                    {
                        chosenFormat = (Consoles) searchedList.get(1).getFormats().get(0);
                        selectedGame = (VideoGame) searchedList.get(1);
                        selectedGame.setChosenFormat(chosenFormat);

                        TextManagement.displayText("\nOnly one format available: " + selectedGame.getChosenFormat().toString()
                                + ".");
                    }
                    else //if many formats available
                    {
                        selectedGame = (VideoGame) searchedList.get(1);
                        TextManagement.displayText("Here are the available formats for this movie:");
                        TextManagement.displayFormatsAvailable(selectedGame); //display formats
                        TextManagement.displayText("""
                                Please enter a console for the game.
                                For example, type WII, GAMECUBE, XBOX360, XBOX, PS2, or PS3 (as available).\n""");

                        String consoleChoice = menu.getUserInput();

                        boolean searchHit = false;

                        for (Consoles console : selectedGame.getFormats()) // check for matching format
                        {
                            if (consoleChoice.toLowerCase().contains(console.toString().toLowerCase()))
                            {
                                selectedGame.setChosenFormat(console); //what we wanted to do
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

                    TextManagement.displayText("\nSelected format: " + selectedGame.getChosenFormat().toString() + "\n");

                    if(rent) // if renting...
                    {
                        System.out.println("""
                                Enter the number of days to rent:
                                (You can rent 1 day, 3 days, or 7 days)
                                
                                Enter 1, 3, or 7.""");
                        int daysRented = menu.getUserInputAsInt(1, 1, 3, 7);

                        double basePrice = PriceCalculator.calculateVideoGamePurchase(selectedGame);
                        double finalPrice = PriceCalculator.calculateRental(daysRented, basePrice);

                        MediaLineItem orderLine =
                                LineItemBuilder.buildRentalLine(selectedGame, finalPrice, daysRented);
                        currentOrder.addItem(orderLine);
                        indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                        itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                        TextManagement.displayText("""
                        Adding to cart: 
                        """);
                        TextManagement.displayText(itemAdded.printLineItem());

                        TextManagement.displayText("Would the customer like to check out a " + selectedGame.getChosenFormat().toString()
                                + " to go with " + selectedGame.getTitle() +"?");
                        TextManagement.displayText("""
                                
                                1) Yes
                                2) No
                                """);

                        int consoleRentChoice = menu.getUserInputAsInt(1, 1, 2);

                        switch (consoleRentChoice)
                        {
                            case 1:
                                int indexOfItemAdded1;
                                LineItem itemAdded1;

                                MediaLineItem consoleLine =
                                        LineItemBuilder.buildConsoleLine(selectedGame.getChosenFormat(), daysRented);
                                currentOrder.addItem(consoleLine);
                                indexOfItemAdded1 = currentOrder.getItems().indexOf(consoleLine);
                                itemAdded1 = currentOrder.getItems().get(indexOfItemAdded1);

                                TextManagement.displayText("""
                                        
                                        Adding to cart: 
                                        """);
                                TextManagement.displayText(itemAdded1.printLineItem());
                                break;
                            case 2:
                                TextManagement.displayText("\nNo worries.");
                                break;
                        }

                    }
                    else //purchasing
                    {
                        LineItem orderLine =
                                LineItemBuilder.buildSimpleLine(selectedGame);
                        currentOrder.addItem(orderLine);
                        indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                        itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                        TextManagement.displayText("""
                        Adding to cart: 
                        """);
                        TextManagement.displayText(itemAdded.printLineItem());

                    }
                }

                TextManagement.displayText("\nReturning to Current Transaction screen...");
                TextManagement.pressEnterToContinue();
                orderManagementMenu(currentOrder);

                break;
            case 3:
                if(searchedList.get(2).getId().contains("MV")) //if movie MOVIE BRANCH START*
                {
                    VideoFormats chosenFormat;
                    if(searchedList.get(2).getFormats().size() == 1) //if one format available
                    {
                        chosenFormat = (VideoFormats) searchedList.get(2).getFormats().get(0);
                        selectedMovie = (Movie) searchedList.get(2);
                        selectedMovie.setChosenFormat(chosenFormat);

                        TextManagement.displayText("\nOnly one format available: " + selectedMovie.getChosenFormat().toString()
                                + ".");
                    }
                    else //if many formats available
                    {
                        selectedMovie = (Movie) searchedList.get(2);
                        TextManagement.displayText("Here are the available formats for this movie:");
                        TextManagement.displayFormatsAvailable(selectedMovie); //display formats
                        TextManagement.displayText("""
                                Please enter a video format for the movie.
                                For example, type VHS, SD, HD, or BLURAY (as available).\n""");

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
                        TextManagement.displayText("""
                                
                                Would you like the fullscreen or widescreen version?
                                Enter a number to continue:
                                
                                1) Fullscreen
                                2) Widescreen
                                """);
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
                    if (selectedMovie.getChosenFormat() == VideoFormats.SD || selectedMovie.getChosenFormat() == VideoFormats.VHS)
                    {
                        String word = "fullscreen";
                        if(isWS)
                        {
                            word = "widescreen";
                        }
                        TextManagement.displayText("\nSelected format: " + selectedMovie.getChosenFormat().toString() +
                                " ("+ word.toUpperCase() + ")" + "\n");
                    }
                    else
                    {
                        TextManagement.displayText("\nSelected format: " + selectedMovie.getChosenFormat().toString() + "\n");
                    }
                    if(rent) // if renting...
                    {
                        System.out.println("""
                                Enter the number of days to rent:
                                (You can rent 1 day, 3 days, or 7 days)
                                
                                Enter 1, 3, or 7.""");
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

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
                        }
                        else
                        {//if renting any other format
                            MediaLineItem orderLine =
                                    LineItemBuilder.buildRentalLine(selectedMovie, finalPrice, daysRented);
                            currentOrder.addItem(orderLine);
                            indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                            itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
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

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
                        }
                        else
                        {//if buying any other format
                            LineItem orderLine =
                                    LineItemBuilder.buildSimpleLine(selectedMovie);
                            currentOrder.addItem(orderLine);
                            indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                            itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                            TextManagement.displayText("""
                            Adding to cart: 
                            """);
                            TextManagement.displayText(itemAdded.printLineItem());
                        }
                    }
                }
                //diverge to game branch, copy pasting stuff +++++++++++++++++++++++++++
                if(searchedList.get(2).getId().contains("VG")) //
                {
                    Consoles chosenFormat;
                    if(searchedList.get(2).getFormats().size() == 1) //if one format available
                    {
                        chosenFormat = (Consoles) searchedList.get(2).getFormats().get(0);
                        selectedGame = (VideoGame) searchedList.get(2);
                        selectedGame.setChosenFormat(chosenFormat);

                        TextManagement.displayText("\nOnly one format available: " + selectedGame.getChosenFormat().toString()
                                + ".");
                    }
                    else //if many formats available
                    {
                        selectedGame = (VideoGame) searchedList.get(2);
                        TextManagement.displayText("Here are the available formats for this movie:");
                        TextManagement.displayFormatsAvailable(selectedGame); //display formats
                        TextManagement.displayText("""
                                Please enter a console for the game.
                                For example, type WII, GAMECUBE, XBOX360, XBOX, PS2, or PS3 (as available).\n""");

                        String consoleChoice = menu.getUserInput();

                        boolean searchHit = false;

                        for (Consoles console : selectedGame.getFormats()) // check for matching format
                        {
                            if (consoleChoice.toLowerCase().contains(console.toString().toLowerCase()))
                            {
                                selectedGame.setChosenFormat(console); //what we wanted to do
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

                    TextManagement.displayText("\nSelected format: " + selectedGame.getChosenFormat().toString() + "\n");

                    if(rent) // if renting...
                    {
                        System.out.println("""
                                Enter the number of days to rent:
                                (You can rent 1 day, 3 days, or 7 days)
                                
                                Enter 1, 3, or 7.""");
                        int daysRented = menu.getUserInputAsInt(1, 1, 3, 7);

                        double basePrice = PriceCalculator.calculateVideoGamePurchase(selectedGame);
                        double finalPrice = PriceCalculator.calculateRental(daysRented, basePrice);

                        MediaLineItem orderLine =
                                LineItemBuilder.buildRentalLine(selectedGame, finalPrice, daysRented);
                        currentOrder.addItem(orderLine);
                        indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                        itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                        TextManagement.displayText("""
                        Adding to cart: 
                        """);
                        TextManagement.displayText(itemAdded.printLineItem());

                        TextManagement.displayText("Would the customer like to check out a " + selectedGame.getChosenFormat().toString()
                                + " to go with " + selectedGame.getTitle() +"?");
                        TextManagement.displayText("""
                                
                                1) Yes
                                2) No
                                """);

                        int consoleRentChoice = menu.getUserInputAsInt(1, 1, 2);

                        switch (consoleRentChoice)
                        {
                            case 1:
                                int indexOfItemAdded1;
                                LineItem itemAdded1;

                                MediaLineItem consoleLine =
                                        LineItemBuilder.buildConsoleLine(selectedGame.getChosenFormat(), daysRented);
                                currentOrder.addItem(consoleLine);
                                indexOfItemAdded1 = currentOrder.getItems().indexOf(consoleLine);
                                itemAdded1 = currentOrder.getItems().get(indexOfItemAdded1);

                                TextManagement.displayText("""
                                        
                                        Adding to cart: 
                                        """);
                                TextManagement.displayText(itemAdded1.printLineItem());
                                break;
                            case 2:
                                TextManagement.displayText("\nNo worries.");
                                break;
                        }

                    }
                    else //purchasing
                    {
                        LineItem orderLine =
                                LineItemBuilder.buildSimpleLine(selectedGame);
                        currentOrder.addItem(orderLine);
                        indexOfItemAdded = currentOrder.getItems().indexOf(orderLine);
                        itemAdded = currentOrder.getItems().get(indexOfItemAdded);

                        TextManagement.displayText("""
                        Adding to cart: 
                        """);
                        TextManagement.displayText(itemAdded.printLineItem());

                    }
                }

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
