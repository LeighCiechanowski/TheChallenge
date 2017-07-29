package com.thefloow.thechallenge;

import com.thefloow.thechallenge.services.*;
import java.util.HashMap;
import java.util.Map;

public class WordStatisticsApp 
{
    public static final String SOURCE_KEY = "Source";
    public static final String MONGO_HOST_KEY = "MongoHost";
    public static final String MONGO_PORT_KEY = "MongoPort";
    
    public static void main( String[] args )
    {
        // TODO remove hardcoded args
        args = new String[]{"-source","testdata.txt","-mongo", "localhost:27017"};
        
        ParameterService parameterService = new ParameterService();
        Map<String, String> parameters = parameterService.buildParameters(args);
        
        FileReaderService fileReader = new FileReaderService(parameters.get(WordStatisticsApp.SOURCE_KEY));
        
        long fileSize = fileReader.getFileSize();
        long readSize = 2;
        long position = 0;
        //long lastStartPosition = fileSize - readSize;
        
        WordCountService countService = new WordCountService();
        MongoService mongoService = new MongoService(
        parameters.get(WordStatisticsApp.MONGO_HOST_KEY), 
        parameters.get(WordStatisticsApp.MONGO_PORT_KEY));
        
        while(position <= fileSize)
        {
            String data = fileReader.readFile(position, readSize);
            HashMap<String, Integer> wordCounts = countService.GetCounts(data);
            mongoService.upsertWordCount(wordCounts);
            position = position + readSize;
        }
        mongoService.close();
        /*
        String data = fileReader.readFile(0, 2);
        
        WordCountService countService = new WordCountService();
        HashMap<String, Integer> wordCounts = countService.GetCounts(data);
        
        MongoService mongoService = new MongoService(
                parameters.get(WordStatisticsApp.MONGO_HOST_KEY), 
                parameters.get(WordStatisticsApp.MONGO_PORT_KEY));
        
        mongoService.upsertWordCount(wordCounts);
        mongoService.close();
*/
    }
   
}
