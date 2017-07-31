package com.thefloow.thechallenge;

import Model.FileChunk;
import com.thefloow.thechallenge.engines.FileMappingEngine;
import com.thefloow.thechallenge.engines.WordCountEngine;
import com.thefloow.thechallenge.services.FileChunkingService;
import com.thefloow.thechallenge.services.*;
import java.util.Date;
import java.util.Map;

public class WordStatisticsApp 
{
    public static final String SOURCE_KEY = "Source";
    public static final String MONGO_HOST_KEY = "MongoHost";
    public static final String MONGO_PORT_KEY = "MongoPort";
    
    public static void main( String[] args )
    {
        System.out.println("Word Stats app started: " + new Date());

        if(args.length == 0)
        {
            args = new String[]{"-source","testdata.txt","-mongo", "localhost:27017"};
        }
        
        ParameterService parameterService = new ParameterService();
        Map<String, String> parameters = parameterService.buildParameters(args);
        
        MongoService mongoService = new MongoService(
            parameters.get(WordStatisticsApp.MONGO_HOST_KEY), 
            parameters.get(WordStatisticsApp.MONGO_PORT_KEY));
        
        FileChunkingService fileChunkingService = new FileChunkingService(parameters.get(WordStatisticsApp.SOURCE_KEY));
  
        FileMappingEngine fileMappingEngine = new FileMappingEngine(mongoService, fileChunkingService, parameters.get(WordStatisticsApp.SOURCE_KEY));      
        FileChunk chunk = fileMappingEngine.getNextFileChunk();
        
        FileReaderService fileReaderService = new FileReaderService(parameters.get(WordStatisticsApp.SOURCE_KEY));
        WordCountService countService = new WordCountService();
        WordCountEngine engine = new WordCountEngine(mongoService, countService, fileReaderService);
        
        while(chunk != null)
        {
            chunk.setProcessing(true);
            System.out.println("Updating filechunk " + chunk.getStart());
            fileMappingEngine.updateFileChunk(chunk);
          
            engine.Run(chunk.getStart(), chunk.getEnd());
            
            chunk.setComplete(true);
            fileMappingEngine.updateFileChunk(chunk);
            chunk = fileMappingEngine.getNextFileChunk();
        }
        
         System.out.println("Word Stats app finished: " + new Date());
    }
   
}
