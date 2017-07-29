package com.thefloow.thechallenge.engines;

import com.thefloow.thechallenge.services.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class WordCountEngine 
{
    private final MongoService mongoService;
    private final WordCountService wordCountService;

    public WordCountEngine(MongoService mongoService, WordCountService wordCountService) {
        this.mongoService = mongoService;
        this.wordCountService = wordCountService;
    }
    
    public void Run(String path)
    {
        try (Stream<String> lines = Files.lines(Paths.get(path), Charset.defaultCharset())) 
        {
            lines.forEachOrdered(line -> processLine(line));
        }       
        catch (IOException ex) 
        {
            Logger.getLogger(WordCountEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            mongoService.close();
        }
    }
    
    private void processLine(String line)
    {
        HashMap<String, Integer> wordCounts = wordCountService.GetCounts(line);
        saveWordCounts(wordCounts);
    }
    
    private void saveWordCounts(HashMap<String, Integer> wordCounts)
    { 
        mongoService.upsertWordCount(wordCounts);
    }
    
}