package com.thefloow.thechallenge;

import Model.FileChunk;
import com.thefloow.thechallenge.services.FileChunkingService;
import com.thefloow.thechallenge.services.*;
import java.util.List;
import java.util.Map;

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
        
        FileChunkingService test = new FileChunkingService();
        List<FileChunk> data = test.chunkFile("test.xml");
        
        mongoService.putFileMap(data);
        mongoService.close();
        //FileChunkingService test = new FileChunkingService();
        //test.chunkFile(SOURCE_KEY);
        // TODO remove hardcoded args
        /*args = new String[]{"-source","testdata.txt","-mongo", "localhost:27017"};
        
        System.out.println("And so it begins");
        ParameterService parameterService = new ParameterService();
        Map<String, String> parameters = parameterService.buildParameters(args);
        
        MongoService mongoService = new MongoService(
            parameters.get(WordStatisticsApp.MONGO_HOST_KEY), 
            parameters.get(WordStatisticsApp.MONGO_PORT_KEY));
         
        WordCountService countService = new WordCountService();
        
        WordCountEngine engine = new WordCountEngine(mongoService, countService);
        engine.Run(parameters.get(WordStatisticsApp.SOURCE_KEY));
*/
    }
   
}
