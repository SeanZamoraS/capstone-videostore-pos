package com.pluralsight.application;

import com.pluralsight.ui.Colors;
import com.pluralsight.ui.TextManagement;
import org.w3c.dom.Text;

public class Main
{
    public static void main()
    {
        System.out.println("test");
        TextManagement test = new TextManagement();
        TextManagement.displayTextColor("hello", Colors.GREEN);
    }
}
