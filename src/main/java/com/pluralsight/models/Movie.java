package com.pluralsight.models;

import java.util.ArrayList;

public class Movie extends Media {
    private String id;
    private String title;
    private int releaseYear;
    private ArrayList<VideoFormats> formats;

    private boolean isNew;

    //constructors
    public Movie(String id, String title, int releaseYear,
                 ArrayList<VideoFormats> formats) {
        super(id, title, releaseYear, formats);
    }

    //getters and setters

}

