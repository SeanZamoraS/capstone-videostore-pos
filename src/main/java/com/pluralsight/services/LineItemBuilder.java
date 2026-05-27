package com.pluralsight.services;

import com.pluralsight.finalmodels.LineItem;
import com.pluralsight.finalmodels.MediaLineItem;
import com.pluralsight.finalmodels.Order;
import com.pluralsight.models.Consoles;
import com.pluralsight.models.Media;

public class LineItemBuilder
{
    public static LineItem buildSimpleLine(String name, double price) //pretty useless but just in case
    {
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

        String newName = " ";

        if(isWS)
        {
            String name = line.getName() + " WIDESCREEN";
            line.setName(name);
        }
        else
        {
            String name = line.getName() + " FULLSCREEN";
            line.setName(name);
        }
        return line;
    }

    public static MediaLineItem buildConsoleLine(Consoles console, int dayChoice)
    {
        String name = console.toString();
        String comment = makeReturnTimeComment(dayChoice);
        double price = PriceCalculator.calculateRental(dayChoice, console.getBasePrice());

        MediaLineItem line = new MediaLineItem(name, price, comment);
        return line;
    }

    private static String makeReturnTimeComment(int dayChoice)
    {
        TimeStamps timeStamp = new TimeStamps();

        String comment = " ";

        switch (dayChoice)
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
}
