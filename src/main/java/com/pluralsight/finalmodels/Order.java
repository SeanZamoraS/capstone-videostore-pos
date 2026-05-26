package com.pluralsight.finalmodels;

import java.util.ArrayList;

public class Order
{
    private ArrayList<LineItem> items;

    public Order()
    {
        this.items = new ArrayList<LineItem>();
    }
}
