package com.pluralsight.filemanagement;

import com.pluralsight.models.Media;
import com.pluralsight.models.Movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ObjectsFromFile
{
    private String fileName;

    private FileReader fr;
    private BufferedReader br;

    public ObjectsFromFile(String fileName)
    {
        this.fileName = fileName;
    }
    //getters and setters
    public void setFileName(String fileName) {this.fileName = fileName;}
    public String getFileName() {return fileName;}

    public ArrayList<Movie> createMovieList()
    {
        try
        {
            this.fr = new FileReader(this.fileName);
            this.br = new BufferedReader(this.fr);

        }
        catch (Exception e)
        {

        }
        return null;
    }


}
