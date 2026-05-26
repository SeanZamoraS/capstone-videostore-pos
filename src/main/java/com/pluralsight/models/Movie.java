package com.pluralsight.models;

import java.util.ArrayList;

public class Movie extends Media<VideoFormats>
{
    //constructor
    public Movie(String id, String title, int releaseYear,
                 ArrayList<VideoFormats> formats)
    {
        super(id, title, releaseYear, formats);
    }
}

