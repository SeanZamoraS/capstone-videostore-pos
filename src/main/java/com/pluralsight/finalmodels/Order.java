package com.pluralsight.finalmodels;

import java.util.ArrayList;

public class Order
{
    private ArrayList<LineItem> items;
    private double total;
    private double totalTax;
    private double grandTotal;

    public Order()
    {
        this.items = new ArrayList<LineItem>();
    }
    //getters and setters
    public void setTotal(double total) {this.total = total;}
    public void setTotalTax(double totalTax) {this.totalTax = totalTax;}
    public void setGrandTotal(double grandTotal) {this.grandTotal = grandTotal;}

    public double getTotalTax() {return this.totalTax;}
    public double getGrandTotal() {return this.grandTotal;}
    public double getTotal() {return this.total;}

    public void setItems(ArrayList<LineItem> items)
    {
        this.items = items;
    }

    public ArrayList<LineItem> getItems()
    {
        return this.items;
    }

    //methods:
    public void addItem(LineItem item)
    {
        this.items.add(item);
    }


}
