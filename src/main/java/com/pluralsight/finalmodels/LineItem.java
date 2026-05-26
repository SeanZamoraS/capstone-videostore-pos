package com.pluralsight.finalmodels;

public class LineItem
{
    private String name;
    private double price;

    public LineItem(String name, double price)
    {
        this.name = name;
        this.price = price;
    }

    //getters and setters
    public void setName(String name) {this.name = name;}
    public void setPrice(double price) {this.price = price;}

    public String getName() {return this.name;}
    public double getPrice() {return this.price;}

    //methods:
    public void printLineItem()
    {
        System.out.printf("""
                %s
                ........%.2f\n""", this.name, this.price);
    }
}
