package com.pluralsight.services;

import com.pluralsight.models.Media;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Search
{
    public ArrayList<? extends Media> searchForMediaTitle(String input, ArrayList<? extends Media> mediaList)
    {
        ArrayList<? extends Media> firstThree = mediaList.stream()
                .filter(media -> media.getTitle().toLowerCase().contains(input.toLowerCase()))
                .limit(3)
                .collect(Collectors.toCollection(ArrayList::new));
        return firstThree;
    }
}
