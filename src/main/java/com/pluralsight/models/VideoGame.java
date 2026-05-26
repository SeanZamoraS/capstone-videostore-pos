package com.pluralsight.models;

import java.util.ArrayList;

public class VideoGame extends Media<Consoles>
{
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
}
