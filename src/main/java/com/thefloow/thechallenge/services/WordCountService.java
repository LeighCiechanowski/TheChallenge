package com.thefloow.thechallenge.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCountService implements iWordCountService
{
    private Set<String> GetUniqueWords(String data)
    {
        String[] words = data.split("[^a-zA-Z'-]");
        Set<String> uniqueWords = new HashSet<>();
        uniqueWords.addAll(Arrays.asList(words));
        return uniqueWords;
    }
    
    @Override
    public Map<String, Integer> GetCounts(String data)
    {
        Map<String, Integer> counts = new TreeMap<>();
        
        if (data == null || data.length() == 0) 
        {
            return counts;
        }
        String loweredData = data.toLowerCase();
        Set<String> uniqueWords = GetUniqueWords(loweredData);
        
        uniqueWords.forEach((word) -> 
        {
            Matcher m = Pattern.compile("\\b" + word + "\\b").matcher(loweredData);

            int matches = 0;
            while(m.find())
                matches++;

            counts.put(word, matches);
            
        });
        
        return counts;
    
    }

}
