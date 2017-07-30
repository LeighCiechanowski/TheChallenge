package com.thefloow.thechallenge;

import Model.FileChunk;
import com.thefloow.thechallenge.engines.FileMappingEngine;
import com.thefloow.thechallenge.engines.WordCountEngine;
import com.thefloow.thechallenge.services.FileChunkingService;
import com.thefloow.thechallenge.services.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WordStatisticsApp 
{
    public static final String SOURCE_KEY = "Source";
    public static final String MONGO_HOST_KEY = "MongoHost";
    public static final String MONGO_PORT_KEY = "MongoPort";
    
    public static void main( String[] args )
    {
        args = new String[]{"-source","testdata.txt","-mongo", "localhost:27017"};
        ParameterService parameterService = new ParameterService();
        Map<String, String> parameters = parameterService.buildParameters(args);
        
        MongoService mongoService = new MongoService(
            parameters.get(WordStatisticsApp.MONGO_HOST_KEY), 
            parameters.get(WordStatisticsApp.MONGO_PORT_KEY));
        
        List<FileChunk> test = mongoService.getFileMap();
        
        FileChunkingService fileChunkingService = new FileChunkingService();
  
        FileMappingEngine fileMappingEngine = new FileMappingEngine(mongoService, fileChunkingService, parameters.get(WordStatisticsApp.SOURCE_KEY));      
        FileChunk chunk = fileMappingEngine.getNextFileChunk();
        
        while(chunk != null)
        {
            chunk.setProcessing(true);
            fileMappingEngine.updateFileChunk(chunk);
            // read chunk and update word count
            
            FileReaderService fileReaderService = new FileReaderService("testdata.txt");
            WordCountService countService = new WordCountService();
            WordCountEngine engine = new WordCountEngine(mongoService, countService, fileReaderService);
            //fileReaderService.close();
            engine.Run(chunk.getStart(), chunk.getEnd());
            
            chunk.setComplete(true);
            fileMappingEngine.updateFileChunk(chunk);
            chunk = fileMappingEngine.getNextFileChunk();
        }
    }
   
}
