package com.pluralsight.ui;

import java.util.*;

public class TextManagement
{
    static Scanner userInput = new Scanner(System.in);

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

    //displaying methods
    public static void displayText(String text)
    {
        System.out.println(text);
    }

    public void displayText()
    {
        System.out.println(this.text);
    }

    public static void displayTextColor(String text, Colors color) //need to use Color."nameofcolor" as 2nd parameter
    {
        System.out.println(color.getCode() + text + Colors.END.getCode());
    }

    public void displayTextColor(Colors color)
    {
        System.out.println(color.getCode() + this.text + Colors.END.getCode());
    }

    private static void displayErrorForUser(int errorNum) //parameter keeps which error message to
                                                        //display modular and flexible
    {
        switch (errorNum)
        {
            case 1: //wrong number in a menu screen
                System.out.println("Enter a valid menu option.");
        }
    }
}
