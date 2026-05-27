package com.pluralsight.models;

import java.util.ArrayList;

public class VideoGame extends Media<Consoles>
{
    //private Consoles chosenFormat;

    public VideoGame(String id, String title, int releaseYear)
    {
        super(id, title, releaseYear);
    }
    public VideoGame(String id, String title, int releaseYear,
                 ArrayList<Consoles> formats) //possible will be unused
    {
        super(id, title, releaseYear);
        super.setFormats(formats);
    }

    //public void setChosenFormat(Consoles console) {this.chosenFormat = console;}
    //public Consoles getChosenFormat() {return this.chosenFormat;}
}
