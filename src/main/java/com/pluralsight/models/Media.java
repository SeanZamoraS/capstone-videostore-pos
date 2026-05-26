package com.pluralsight.models;

import java.util.ArrayList;

public abstract class Media<F extends Enum<F>> //expect argument, it should be an enum
{
    private String id;
    private String title;
    private int releaseYear;
    private ArrayList<F> formats; //same enum from argument, no mixing i.e. only consoles or videoformats

    private boolean isNew;

    public Media(String id, String title, int releaseYear, ArrayList<F> formats)
    {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.formats = formats;

        setNew();
    }

    //getters and setters
    public String getId() {return id;}
    public String getTitle() {return title;}
    public int getReleaseYear() {return releaseYear;}
    public ArrayList<F> getFormats() {return formats;}

    public boolean isNew()
    {
        if(this.releaseYear >= 2006)
        {
            return true;
        }
        return false;
    }

    public void setId(String id) {this.id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setReleaseYear(int releaseYear) {this.releaseYear = releaseYear;}
    public void setFormats(ArrayList<F> formats) {this.formats = formats;}

    public void setNew()
    {
        this.isNew = this.isNew();
    }
}
