package com.pluralsight.services;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TimeStampsTest
{
    @Test
    public void FileNameOutputShouldBeCurrentandFormatted()
    {
        //arrange
        DateTimeFormatter fileNameStamp = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        LocalDateTime time = null;
        time = time.now();
        String timeFormat = time.format(fileNameStamp);

        TimeStamps timeStampsTest = new TimeStamps();
        //act
        String test = timeStampsTest.createReceiptFileName();
        //asset
        assertEquals(timeFormat, test);
    }

}