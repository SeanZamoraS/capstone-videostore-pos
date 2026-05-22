package com.pluralsight.models;

public enum Consoles
{
    WII(265.00),
    GAMECUBE(200.00),
    XBOX360(315.00),
    XBOX(200.00),
    PS3(615.00),
    PS2(200.00);

    final double basePrice;

    Consoles(double basePrice)
    {
        this.basePrice = basePrice;
    }

}
