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
        return price;
    }

    public static double calculateRentalMovie()
    {

        return 0;
    }
}
