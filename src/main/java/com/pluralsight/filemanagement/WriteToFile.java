package com.pluralsight.filemanagement;

import com.pluralsight.finalmodels.LineItem;
import com.pluralsight.finalmodels.Order;
import com.pluralsight.services.TimeStamps;
import com.pluralsight.ui.TextManagement;

import java.io.File;
import java.io.FileWriter;

public class WriteToFile
{
    private FileWriter fw;
    private File receiptFile;

    private String fileName;
    TimeStamps timeStamp;

    public WriteToFile() //constructor to make file name a timestamp for every order
    {
        this.timeStamp = new TimeStamps();
        this.fileName = this.timeStamp.createReceiptFileName();
    }

    private void createFile() //used by writeToReceipt
    {
        this.receiptFile = new File("receipts/" + fileName);
    }

    public void writeToReceipt(Order order) //must create new WriteToFile each time
                                            //to use writeToReceipt, prevent same timestamp
    {
        createFile();
        try
        {
            this.fw = new FileWriter(this.receiptFile, false);

            fw.write("""
                    |*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*|
                    |-------------Le Epic Video Store-------------|
                    |*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*|
                            
                            Thank you for your patronage.
                                  """);
            String receiptStamp = timeStamp.createPrettyTimeStamp();
            fw.write(receiptStamp);

            fw.write("\n Items Purchased:\n");

            for(LineItem item : order.getItems())
            {
                fw.write(item.printLineItem());
            }

            String endLine = String.format("""
                    ....................Total: %.2f
                    ....................Tax: %.2f
                    ....................Grand Total: %.2f""", order.getTotal(), order.getTotalTax(), order.getGrandTotal());
            fw.write(endLine);

            fw.close();
        }
        catch (Exception e)
        {
            TextManagement.displayErrorForUser(3);
        }
    }
    public void showReceipt(Order order)
    {
        System.out.println("""
                    |*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*|
                    |-------------Le Epic Video Store-------------|
                    |*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*|
                            
                            Thank you for your patronage.
                                  """);

        String receiptStamp = timeStamp.createPrettyTimeStamp();
        System.out.println(receiptStamp);

        System.out.println("\n Items Purchased:\n");
        for(LineItem item : order.getItems())
        {
            System.out.print(item.printLineItem());
        }

        System.out.printf("""
                ....................Total: %.2f
                ....................Tax: %.2f
                ....................Grand Total: %.2f""\", order.getTotal(), order.getTotalTax(), order.getGrandTotal());""");
    }
}
