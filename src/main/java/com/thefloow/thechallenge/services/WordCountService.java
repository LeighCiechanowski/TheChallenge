package com.thefloow.thechallenge.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WordCountService 
{
    public Set<String> GetUniqueWords(String data)
    {
        String[] words = data.split("[^a-zA-Z']");
        Set<String> uniqueWords = new HashSet<>();

        uniqueWords.addAll(Arrays.asList(words));
        return uniqueWords;
    }
    
}
