package com.pluralsight.filemanagement;

import com.pluralsight.models.Consoles;
import com.pluralsight.models.Movie;
import com.pluralsight.models.VideoFormats;
import com.pluralsight.models.VideoGame;
import com.pluralsight.ui.TextManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ObjectsFromFile
{
    private String fileName;
    private String fileNameKey;

    private FileReader fr;
    private FileReader frK;
    private BufferedReader br;
    private BufferedReader brK;

    public ObjectsFromFile(String fileName, String fileNameKey)
    {
        this.fileName = fileName;
        this.fileNameKey = fileNameKey;
    }
    //getters and setters
    public void setFileName(String fileName) {this.fileName = fileName;}
    public void setFileNameKey(String fileNameKey) {this.fileNameKey = fileNameKey;}
    public String getFileName() {return fileName;}
    public String getFileNameKey() {return fileNameKey;}

    public ArrayList<Movie> createMovieList() //create ArrayList of movies to use for price calculations
    {
        HashMap<String, Movie> movieMap = new HashMap<String, Movie>();
        HashMap<String, ArrayList<VideoFormats>> formatMap = new HashMap<String, ArrayList<VideoFormats>>();
        ArrayList<Movie> fullMovieCatalogue = new ArrayList<Movie>();
        try
        {
            this.fr = new FileReader(this.fileName);
            this.br = new BufferedReader(this.fr);

            br.readLine(); //eat the first line
            String line = br.readLine(); // load the 2nd line before the loop

            while(line != null)
            {
                String[] parts = line.split("\\|");

                String id = parts[0];
                String title = parts[1];
                int releaseYear = Integer.parseInt(parts[2]);

                Movie currentMovie = new Movie(id, title, releaseYear);
                movieMap.put(currentMovie.getId(), currentMovie);

                line = br.readLine();
            }
            this.br.close();
            this.fr.close();

            this.frK = new FileReader(this.fileNameKey);
            this.brK = new BufferedReader(this.frK);

            brK.readLine(); //eat the first line
            String line2 = brK.readLine(); //load first readable line

            while(line2 != null)
            {
                String[] partsPipe = line2.split("\\|");
                String id = partsPipe[0];

                String formats = partsPipe[1];
                String[] partsComma = formats.split(",");

                ArrayList<VideoFormats> finalFormats = new ArrayList<VideoFormats>();
                for(String format : partsComma)
                {
                    switch (format)
                    {
                        case "VHS":
                            finalFormats.add(VideoFormats.VHS);
                            break;
                        case "SD":
                            finalFormats.add(VideoFormats.SD);
                            break;
                        case "BLURAY":
                            finalFormats.add(VideoFormats.BLURAY);
                            break;
                        case "HD":
                            finalFormats.add(VideoFormats.HD);
                            break;
                    }
                }
                formatMap.put(id, finalFormats);

                line2 = brK.readLine();
            }
            this.frK.close();
            this.brK.close();

            for(Map.Entry<String, ArrayList<VideoFormats>> entry : formatMap.entrySet())
            {
                movieMap.get(entry.getKey()).setFormats(entry.getValue());
            }

            for(Map.Entry<String, Movie> entry : movieMap.entrySet())
            {
                fullMovieCatalogue.add(entry.getValue());
            }
        }

        catch (IOException e)
        {
            TextManagement.displayErrorForUser(2);
        }
        catch (Exception e)
        {
            TextManagement.displayErrorForUser(3);
        }
        fullMovieCatalogue.sort(Comparator.comparing(Movie::getId));
        return fullMovieCatalogue;
    }

    public ArrayList<VideoGame> createVideoGameList() //generalize this using Media?
    {
        HashMap<String, VideoGame> gameMap = new HashMap<String, VideoGame>();
        HashMap<String, ArrayList<Consoles>> formatMap = new HashMap<String, ArrayList<Consoles>>();
        ArrayList<VideoGame> fullGameCatalogue = new ArrayList<VideoGame>();
        try
        {
            this.fr = new FileReader(this.fileName);
            this.br = new BufferedReader(this.fr);

            br.readLine(); //eat the first line
            String line = br.readLine(); // load the 2nd line before the loop

            while(line != null)
            {
                String[] parts = line.split("\\|");

                String id = parts[0];
                String title = parts[1];
                int releaseYear = Integer.parseInt(parts[2]);

                VideoGame currentGame = new VideoGame(id, title, releaseYear);
                gameMap.put(currentGame.getId(), currentGame);

                line = br.readLine();
            }
            this.br.close();
            this.fr.close();

            this.frK = new FileReader(this.fileNameKey);
            this.brK = new BufferedReader(this.frK);

            brK.readLine(); //eat the first line
            String line2 = brK.readLine(); //load first readable line

            while(line2 != null)
            {
                String[] partsPipe = line2.split("\\|");
                String id = partsPipe[0];

                String formats = partsPipe[1];
                String[] partsComma = formats.split(",");

                ArrayList<Consoles> finalFormats = new ArrayList<Consoles>();
                for(String format : partsComma)
                {
                    switch (format)
                    {
                        case "WII":
                            finalFormats.add(Consoles.WII);
                            break;
                        case "GAMECUBE":
                            finalFormats.add(Consoles.GAMECUBE);
                            break;
                        case "XBOX360":
                            finalFormats.add(Consoles.XBOX360);
                            break;
                        case "XBOX":
                            finalFormats.add(Consoles.XBOX);
                            break;
                        case "PS3":
                            finalFormats.add(Consoles.PS3);
                            break;
                        case "PS2":
                            finalFormats.add(Consoles.PS2);
                            break;
                    }
                }
                formatMap.put(id, finalFormats);

                line2 = brK.readLine();
            }
            this.frK.close();
            this.brK.close();

            for(Map.Entry<String, ArrayList<Consoles>> entry : formatMap.entrySet())
            {
                gameMap.get(entry.getKey()).setFormats(entry.getValue());
            }

            for(Map.Entry<String, VideoGame> entry : gameMap.entrySet())
            {
                fullGameCatalogue.add(entry.getValue());
            }
        }

        catch (IOException e)
        {
            TextManagement.displayErrorForUser(2);
        }
        catch (Exception e)
        {
            TextManagement.displayErrorForUser(3);
        }
        fullGameCatalogue.sort(Comparator.comparing(VideoGame::getId));
        return fullGameCatalogue;
    }

}
