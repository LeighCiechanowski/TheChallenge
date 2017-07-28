package com.thefloow.thechallenge.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public class WordCountService 
{
    private Set<String> GetUniqueWords(String data)
    {
        String[] words = data.split("[^a-zA-Z']");
        Set<String> uniqueWords = new HashSet<>();

        uniqueWords.addAll(Arrays.asList(words));
        return uniqueWords;
    }
    
    public HashMap<String, Integer> GetCounts(String data)
    {
        HashMap<String, Integer> counts = new HashMap<>();
        Set<String> uniqueWords = GetUniqueWords(data);
        
        uniqueWords.forEach((word) -> 
        {
            int count = StringUtils.countMatches(data, word);
            counts.put(word, count);
        });
        
        return counts;
    
    }

}
