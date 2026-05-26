package com.pluralsight.models;

public class Fee
{
    private FeeType name;
    private Media media;

    public Fee(FeeType name, Media media)
    {
        this.name = name;
        this.media = media;
    }

    //getters and setters

    public FeeType getName() {return this.name;}
    public Media getMedia() {return this.media;}

    public void setName(FeeType name) {this.name = name;}
    public void setMedia(Media media) {this.media = media;}
}
