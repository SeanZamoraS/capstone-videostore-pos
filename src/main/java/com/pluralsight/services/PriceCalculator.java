package com.pluralsight.services;

import com.pluralsight.models.Media;
import com.pluralsight.models.Movie;
import com.pluralsight.models.VideoFormats;
import com.pluralsight.models.VideoGame;

public class PriceCalculator
{
    //PriceCalculator is just meant to calculate, adding to a LineItem/Order is another class's responsibility
    //therefore, we will return doubles to be used
    public static double calculateMoviePurchase(Movie movie, VideoFormats videoFormat)
    {
        double price = 0;
        if (movie.isNew())
        {
            price = 85.00 + videoFormat.getAddOnPrice();
        }
        else
        {
            price = 45.00 + videoFormat.getAddOnPrice();
        }
        price = roundPrice(price);
        return price;
    }

    public static double calculateVideoGamePurchase(VideoGame videoGame)
    {
        double price = 0;
        if(videoGame.isNew())
        {
            price = 65.00;
        }
        else
        {
            price = 35.00;
        }
        price = roundPrice(price);
        return price;
    }

    public static double calculateRental(int dayChoice, double totalPrice) //calculate totalPrice w/ above methods
                                                                                //may also use Console basePrice
    {
        totalPrice = totalPrice; //making sure it is outside switch brackets, for my own sanity

        switch(dayChoice)
        {
            case 1: // 1 day rental
                totalPrice *= 0.12; // 0.12 for each day at this rate (1 day)
                break;
            case 2: // 3 day rental
                totalPrice = 3 * (totalPrice * 0.1);
                break;
            case 3: // 7 day rental
                totalPrice = 7 * (totalPrice * 0.8);
                break;
        }
        double price = roundPrice(totalPrice);
        return price;
    }

    private static double roundPrice(double unroundedPrice)
    {
        return (double) Math.round(unroundedPrice * 100) /100; //IntelliJ recommends casting to double
    }
}
