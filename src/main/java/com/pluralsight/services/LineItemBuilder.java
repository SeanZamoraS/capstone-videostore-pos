package com.pluralsight.services;

import com.pluralsight.finalmodels.LineItem;
import com.pluralsight.finalmodels.MediaLineItem;
import com.pluralsight.finalmodels.Order;
import com.pluralsight.models.*;

public class LineItemBuilder
{
    public static LineItem buildSimpleLine(String name, double price) //non-specific charges
    {
        LineItem line = new LineItem(name, price);
        return line;
    }

    public static LineItem buildSimpleLine(Media media) //purchase game/movie, non-VHS/SD
    {
        String name = media.getTitle() + ": " + media.getChosenFormat();

        double price = 0;

        if (media.getId().contains("MV"))
        {
            Movie movie = (Movie) media;

            price = PriceCalculator.calculateMoviePurchase(movie, movie.getChosenFormat());
        }
        else
        {
            VideoGame game = (VideoGame) media;

            price = PriceCalculator.calculateVideoGamePurchase(game);
        }

        LineItem line = new LineItem(name, price);
        return line;
    }

    public static LineItem buildSimpleLineWSFS(Media media, boolean isWS)
    {
        LineItem line = buildSimpleLine(media);

        String newName = makeWSFSName(line, isWS);
        line.setName(newName);

        return line;
    }

    public static LineItem buildSimpleLine(Merchandise merch) //purchase from Merchandise enum
    {
        String name = merch.toString();
        double price = merch.getBasePrice();
        price = PriceCalculator.roundPrice(price);

        LineItem line = new LineItem(name, price);
        return line;
    }

    public static MediaLineItem buildRentalLine(Media media, double price, int dayChoice)
    {
        String comment = "";

        String name = media.getTitle() + ": " + media.getChosenFormat();

        comment = makeReturnTimeComment(dayChoice);

        MediaLineItem line = new MediaLineItem(name, price, comment);

        return line;
    }

    public static MediaLineItem buildRentalLineWSFS(Media media, double price, int dayChoice, boolean isWS)
    { //only use this method for VHS or SD format choice
        MediaLineItem line = buildRentalLine(media, price, dayChoice);

        String newName = makeWSFSName(line, isWS);
        line.setName(newName);

        return line;
    }

    public static MediaLineItem buildConsoleLine(Consoles console, int dayChoice) //console rentals
    {
        String name = console.toString();
        String comment = makeReturnTimeComment(dayChoice);
        double price = PriceCalculator.calculateRental(dayChoice, console.getBasePrice());

        MediaLineItem line = new MediaLineItem(name, price, comment);
        return line;
    }

    public static MediaLineItem buildFeeLine(Fee fee) //meow
    {
        String name = fee.getName().toString();
        String comment = "Fined for: " + fee.getMedia().getTitle();

        return null;
    }

    //private methods, space savers for methods above
    private static String makeReturnTimeComment(int dayChoice)
    {
        TimeStamps timeStamp = new TimeStamps();

        String comment = " ";

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

        switch (switchDayChoice)
        {
            case 1: //1 day rental
                comment = "Return on: " + timeStamp.createReturnTime(1);
                break;
            case 2: //3 day rental
                comment = "Return on: " + timeStamp.createReturnTime(3);
                break;
            case 3: //7 day rental
                comment = "Return on: " + timeStamp.createReturnTime(7);
                break;
        }
        return comment;
    }

    private static String makeWSFSName(LineItem movie, boolean isWS)
    {
        String newName = " ";

        if(isWS)
        {
            newName = movie.getName() + " WIDESCREEN";
        }
        else
        {
            newName = movie.getName() + " FULLSCREEN";
        }

        return newName;
    }
}
