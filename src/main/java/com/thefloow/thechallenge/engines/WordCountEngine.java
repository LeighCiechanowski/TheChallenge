package com.thefloow.thechallenge.engines;

import com.thefloow.thechallenge.services.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class WordCountEngine 
{
    private final iMongoService mongoService;
    private final iWordCountService wordCountService;
    private final FileReaderService fileReaderService;

    public WordCountEngine(iMongoService mongoService, iWordCountService wordCountService, FileReaderService fileReaderService) {
        this.mongoService = mongoService;
        this.wordCountService = wordCountService;
        this.fileReaderService = fileReaderService;
    }
    
    public void Run(long start, long end)
    {
        String chunk = fileReaderService.readFile(start, end);
        processLine(chunk);
        /*
        try (Stream<String> lines = Files.lines(Paths.get(path), Charset.defaultCharset())) 
        {
            lines.forEach(line -> processLine(line));
        }       
        catch (IOException ex) 
        {
            Logger.getLogger(WordCountEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            mongoService.close();
        }
*/
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