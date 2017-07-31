package com.thefloow.thechallenge.engines;

import com.thefloow.thechallenge.services.*;
import java.util.Map;

public class WordCountEngine 
{
    private final iMongoService mongoService;
    private final iWordCountService wordCountService;
    private final iFileReaderService fileReaderService;

    public WordCountEngine(iMongoService mongoService, iWordCountService wordCountService, iFileReaderService fileReaderService) {
        this.mongoService = mongoService;
        this.wordCountService = wordCountService;
        this.fileReaderService = fileReaderService;
    }
    
    public void Run(long start, long end)
    {
        String chunk = fileReaderService.readFile(start, end);
        processLine(chunk);
    }
    
    private void processLine(String line)
    {
        Map<String, Integer> wordCounts = wordCountService.GetCounts(line);
        saveWordCounts(wordCounts);
    }
    
    private void saveWordCounts(Map<String, Integer> wordCounts)
    { 
        mongoService.upsertWordCount(wordCounts);
    }
    
}