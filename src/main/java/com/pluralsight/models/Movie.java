package com.pluralsight.models;

import java.util.ArrayList;

public class Movie extends Media<VideoFormats>
{
    //private VideoFormats chosenFormat;

    //constructor
    public Movie(String id, String title, int releaseYear)
    {
        super(id, title, releaseYear);
    }
    public Movie(String id, String title, int releaseYear,
                 ArrayList<VideoFormats> formats)
    {
        super(id, title, releaseYear);
        super.setFormats(formats);
    }

    //public void setChosenFormat(VideoFormats videoFormat) {this.chosenFormat = videoFormat;}
    //public VideoFormats getChosenFormat() {return this.chosenFormat;}
}

