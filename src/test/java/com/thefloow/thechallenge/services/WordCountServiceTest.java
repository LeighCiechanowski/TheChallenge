package com.thefloow.thechallenge.services;

import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class WordCountServiceTest 
{
    WordCountService wordCountService;
    
    public WordCountServiceTest() 
    {
        wordCountService = new WordCountService();
    }
    
    @Test
    public void testMapIsNotEmpty() 
    {
        String testData = "The old old Old old! quick brown ";
        Map<String, Integer> result = wordCountService.GetCounts(testData);
        assertThat(result).isNotEmpty();
        assertThat(result).containsOnlyKeys("The", "old", "quick", "brown");
    }
    
}
