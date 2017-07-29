package com.thefloow.thechallenge.services;

import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import org.junit.Test;

public class WordCountServiceTest 
{
    WordCountService wordCountService;
    String testString =  "The the old old quick brown brown it's check-in";
    
    public WordCountServiceTest() 
    {
        wordCountService = new WordCountService();
    }
    
    @Test
    public void testGetCountsReturnsCorrectSizeMap() 
    {
        Map<String, Integer> result = wordCountService.GetCounts(testString);
        assertThat(result).hasSize(6);
    }
    
    @Test
    public void testGetCountsReturnsCorrectExtries() 
    {
        Map<String, Integer> result = wordCountService.GetCounts(testString);
        assertThat(result).contains(
            entry("the", 2),
            entry("old", 2),
            entry("quick", 1),
            entry("brown", 2),
            entry("it's", 1),
            entry("check-in", 1));
    }
    
    @Test
    public void testGetCountsReturnsEmptyIfNullProvided() {
       Map<String, Integer> result = wordCountService.GetCounts(null);
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetCountsReturnsEmptyIfEmptyStringProvided() {
      Map<String, Integer> result = wordCountService.GetCounts("");
        assertThat(result).isEmpty();
    }  
}
