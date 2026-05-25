package com.pluralsight.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeStamps
{
    private LocalDateTime time;
    private static final DateTimeFormatter fileNameStamp = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private static final DateTimeFormatter receiptStamp = DateTimeFormatter.ofPattern("EEEE| MMM dd, yyyy: hh:mm a");
    private static final DateTimeFormatter returnStamp = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public TimeStamps() //we have to create a new object to record the current time
    {
        this.time = LocalDateTime.now();
    }

    public void timeStuff() //test for myself
    {
        System.out.println(this.time);
        String fileName = time.format(fileNameStamp);
        String receiptPretty = time.format(receiptStamp);
        System.out.println(fileName);
        System.out.println(receiptPretty);
        System.out.println(createReturnTime(3));
    }

    public String createReceiptFileName() //for name of file for receipts, capstone req.
    {
        return this.time.format(fileNameStamp);
    }

    public String createPrettyTimeStamp() //for printing inside receipts
    {
        return this.time.format(receiptStamp);
    }

    public String createReturnTime(int days) //for making time to return rental on receipt
    {
        return this.time.plusDays(days).format(returnStamp);
    }
}
