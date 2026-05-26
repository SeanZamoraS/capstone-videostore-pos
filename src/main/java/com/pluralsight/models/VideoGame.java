package com.pluralsight.models;

import java.util.ArrayList;

public class VideoGame extends Media<Consoles>
{
    public VideoGame(String id, String title, int releaseYear,
                 ArrayList<Consoles> formats)
    {
        super(id, title, releaseYear, formats);
    }
}
