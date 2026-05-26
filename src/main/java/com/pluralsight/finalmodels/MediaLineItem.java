package com.pluralsight.finalmodels;

public class MediaLineItem extends LineItem
{
    private String comment;

    public MediaLineItem(String name, double price, String comment)
    {
        super(name, price);
        this.comment = comment;
    }

    //getters and setters
    public void setComment(String comment) {this.comment = comment;}
    public String getComment() {return this.comment;}

    @Override
    public void printLineItem()
    {
        System.out.printf("""
                %s
                %s
                ........%.2f\n""", super.getName(), this.comment, super.getPrice());
    }
}
