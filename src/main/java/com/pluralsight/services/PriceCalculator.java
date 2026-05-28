package com.pluralsight.services;

import com.pluralsight.models.*;

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

        int switchDayChoice = 0;

        switch (dayChoice)
        {
            case 1:
                switchDayChoice = 1;
                break;
            case 3:
                switchDayChoice = 2;
                break;
            case 7:
                switchDayChoice = 3;
                break;
        }

        switch(switchDayChoice)
        {
            case 1: // 1 day rental
                totalPrice *= 0.12; // 0.12 for each day at this rate (1 day)
                break;
            case 2: // 3 day rental
                totalPrice = 3 * (totalPrice * 0.1);
                break;
            case 3: // 7 day rental
                totalPrice = 7 * (totalPrice * 0.08);
                break;
        }
        double price = roundPrice(totalPrice);
        return price;
    }

    public static double calculateFee(Fee fee, int daysLate) //using SD as basePrice for simplicity
    {
        double basePrice = 0;
        if (fee.getMedia().getId().contains("MV"))
        {
            Movie movie = (Movie) fee.getMedia();
            basePrice = calculateMoviePurchase(movie, VideoFormats.SD);
        }
        else
        {
            VideoGame game = (VideoGame) fee.getMedia();
            basePrice = calculateVideoGamePurchase(game);
        }

        double maxValue = basePrice * 0.5;

        double lateFee = 5.00 * daysLate;

        if(lateFee > maxValue)
        {
            lateFee = maxValue;
        }

        if(fee.getName().toString().contains("LOST"))
        {
            lateFee = maxValue + basePrice + 4.00;
        }
        lateFee = roundPrice(lateFee);
        return lateFee;
    }

    public static double calculateFeeConsole(Consoles console, FeeType lostOrLate, int daysLate)
    {
        double basePrice = console.getBasePrice();
        double maxValue = basePrice * 0.5;

        double lateFee = 0.5 * daysLate;

        if (lateFee > maxValue)
        {
            lateFee = maxValue;
        }

        if (lostOrLate == FeeType.LOSTFEE)
        {
            lateFee = basePrice + maxValue + 4.00;
        }

        lateFee = roundPrice(lateFee);
        return lateFee;
    }

    public static double roundPrice(double unroundedPrice)
    {
        return (double) Math.round(unroundedPrice * 100) /100; //IntelliJ recommends casting to double
    }
}
