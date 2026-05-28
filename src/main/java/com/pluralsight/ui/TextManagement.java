package com.pluralsight.ui;

import com.pluralsight.models.Media;

import java.util.*;

public class TextManagement
{
    private static Scanner userInput = new Scanner(System.in);

    private String text;

    public TextManagement(String text) //constructor to use TextManagement's methods with text
    {
        this.text = text;
    }

    public TextManagement() //default constructor to use functions that don't need text
    {

    }

    //user input methods
    public String getUserInput()
    {
        String input = userInput.nextLine();
        return input;
    }

    public double getUserInputAsDouble(int errorNum) //ensures user input is a double, uses .parseDouble()
                                        //to avoid eating lines as with .nextDouble()
    {
        while (true)
        {
            String input = userInput.nextLine();
            try
            {
                Double doubleInput = Double.parseDouble(input);
                return doubleInput;
            }
            catch (NumberFormatException e)
            {
                displayErrorForUser(errorNum);
            }
        }
    }

    public int getUserInputAsInt(int errorNum) //same as getUserInputAsDouble() but for ints
    {
        while (true)
        {
            String input = userInput.nextLine();
            try
            {
                int intInput = Integer.parseInt(input);
                return intInput;
            }
            catch (NumberFormatException e)
            {
                displayErrorForUser(errorNum);
            }
        }
    }
    public int getUserInputAsInt(int errorNum, int ... options)
    {
        while (true)
        {
            String input = userInput.nextLine();
            try
            {
                int intInput = Integer.parseInt(input);
                for(int option : options)
                {
                    if(intInput == option) {return intInput;}
                }
                Exception exception = new Exception();
                throw exception;
            }
            catch (Exception e)
            {
                displayErrorForUser(errorNum);
            }
        }
    }

    public static void pressEnterToContinue()
    {
        System.out.println("Press enter to continue.");
        userInput.nextLine();
    }

    //displaying methods
    public static void displayText(String text)
    {
        System.out.println(text);
    }

    public void displayText()
    {
        System.out.println(this.text);
    }

    public static void displayTextColor(String text, Colors color) //need to use color."nameofcolor" as 2nd parameter
    {
        System.out.println(color.getCode() + text + Colors.END.getCode());
    }

    public void displayTextColor(Colors color)
    {
        System.out.println(color.getCode() + this.text + Colors.END.getCode());
    }

    public static void displayAllMedia(ArrayList<? extends Media> mediaCatalogue)//must use ? wildcard
    {
        mediaCatalogue.stream()
                .forEach(current -> System.out.println(current.getId() + "|" +
                current.getTitle()));
        //pressEnterToContinue();
    }

    public static boolean displaySearchedMedia(ArrayList<? extends Media> searchedList)
    {
        if (searchedList.isEmpty())
        {
            System.out.println("No results");
            return false;
        }

        for(int i = 0; i < searchedList.size(); i++)
        {
            String sequence = Integer.toString(i + 1);
            System.out.println(sequence + ".) " + searchedList.get(i).getTitle());
        }
        return true;
    }

    public static void displayFormatsAvailable(Media media)
    {
        media.getFormats().stream()
                .forEach(current -> System.out.println(current.toString() + "\n"));
        //pressEnterToContinue();
    }

    public static void displayErrorForUser(int errorNum) //parameter keeps which error message to
                                                        //display modular and flexible
    {
        switch (errorNum)
        {
            case 1: //wrong number in a menu screen
                System.out.println("Enter a valid menu option.");
                break;
            case 2: //filereader exception
                System.out.println("Something went wrong reading the catalogue file.");
                break;
            case 3: //general exception when creating catalogue
                System.out.println("Something may be causing the catalogue to be null.");
                break;
        }
    }
}
