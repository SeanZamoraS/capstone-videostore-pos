package com.pluralsight.application;

import com.pluralsight.filemanagement.ObjectsFromFile;
import com.pluralsight.ui.Menu;

public class Application
{
    public void startApp()
    {
        ObjectsFromFile movieFiles = new ObjectsFromFile("moviecatalogue.csv",
                "formatkey.csv");

        ObjectsFromFile videoGameFiles = new ObjectsFromFile("videogamecatalogue.csv",
                "consolekey.csv");

        Menu menu = new Menu(movieFiles.createMovieList(), videoGameFiles.createVideoGameList());
        menu.startMenu();
    }
}
