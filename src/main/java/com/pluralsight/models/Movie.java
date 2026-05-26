package com.pluralsight.models;

import java.util.ArrayList;

public class Movie
{
    private String id;
    private String title;
    private double basePrice;
    private int releaseYear;
    private ArrayList<VideoFormats> formats;

    private boolean isNew;

    //constructors
    public Movie() {} //remove later if not used

    public Movie(String id, String title, double basePrice, int releaseYear,
                 ArrayList<VideoFormats> formats)
    {
        this.id = id;
        this.title = title;
        this.basePrice = basePrice;
        this.releaseYear = releaseYear;
        this.formats = formats;

        setNew();
    }
    //getters and setters
    public String getId() {return id;}
    public String getTitle() {return title;}
    public double getBasePrice() {return basePrice;}
    public int getReleaseYear() {return releaseYear;}
    public ArrayList<VideoFormats> getFormats() {return formats;}

    public boolean isNew()
    {
        if(this.releaseYear > 2006)
        {
            return true;
        }
        return false;
    }

    public void setId(String id) {this.id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setBasePrice(double basePrice) {this.basePrice = basePrice;}
    public void setReleaseYear(int releaseYear) {this.releaseYear = releaseYear;}
    public void setFormats(ArrayList<VideoFormats> formats) {this.formats = formats;}

    public void setNew()
    {
        this.isNew = this.isNew();
    }
}
