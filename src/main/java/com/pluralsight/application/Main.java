package com.pluralsight.application;

import com.pluralsight.services.TimeStamps;
import com.pluralsight.ui.Colors;
import com.pluralsight.ui.TextManagement;

public class Main
{
    public static void main()
    {
        System.out.println("test");
        TextManagement test = new TextManagement();
        TextManagement.displayTextColor("hello", Colors.GREEN);
        TimeStamps test2 = new TimeStamps();
        test2.timeStuff();

        Application app = new Application();
        app.startApp();

    }
}
