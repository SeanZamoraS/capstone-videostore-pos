package com.pluralsight.models;

public enum Merchandise
{
    MEMBERSHIP(10.00),
    BUNCHA(3.50),
    MIKEIKE(3.00),
    JRMINT(3.00),
    POPCORN(3.25);

    final double basePrice;

    Merchandise(double basePrice)
    {
        this.basePrice = basePrice;
    }

    public double getBasePrice() {return basePrice;}
    @Override
    public String toString() {return super.toString();}

}
