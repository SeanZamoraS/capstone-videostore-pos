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

    public WriteToFile() //constructor to make file name a timestamp
    {
        this.timeStamp = new TimeStamps();
        this.fileName = this.timeStamp.createReceiptFileName();
    }

    private void createFile()
    {
        this.receiptFile = new File(fileName);
    }

    public void writeToReceipt(Order order)
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

            String.format("""
                    ....................Tax: %.2f
                    ....................Total: %.2f""", order.getTotalTax(), order.getGrandTotal());
        }
        catch (Exception e)
        {
            TextManagement.displayErrorForUser(3);
        }
    }
}
