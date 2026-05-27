package com.pluralsight.models;

public enum VideoFormats
{
    VHS(-10.00),
    SD(0.00),
    BLURAY(10.00),
    HD(5.00);

    final double addOnPrice;

    VideoFormats(double addOnPrice)
    {
        this.addOnPrice = addOnPrice;
    }

    public double getAddOnPrice() {return addOnPrice;}
}
