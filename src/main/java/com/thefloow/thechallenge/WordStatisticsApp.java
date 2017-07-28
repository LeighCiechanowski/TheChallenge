package com.thefloow.thechallenge;

import com.thefloow.thechallenge.services.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WordStatisticsApp 
{
    public static final String SOURCE_KEY = "Source";
    public static final String MONGO_HOST_KEY = "MongoHost";
    public static final String MONGO_PORT_KEY = "MongoPort";
    
    public static void main( String[] args )
    {
        // TODO remove hardcoded args
        args = new String[]{"-source","testdata.txt","-mongo", "localhost:27017"};
        
        System.out.println("And so it begins");
        ParameterService parameterService = new ParameterService();
        Map<String, String> parameters = parameterService.buildParameters(args);
        
        FileReaderService fileReader = new FileReaderService();
        String data = fileReader.readFile(parameters.get(WordStatisticsApp.SOURCE_KEY));
        
        WordCountService countService = new WordCountService();
        HashMap<String, Integer>  uniqueWords = countService.GetCounts(data);
    }
   
}
