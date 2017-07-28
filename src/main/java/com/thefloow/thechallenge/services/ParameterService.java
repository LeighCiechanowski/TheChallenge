package com.thefloow.thechallenge.services;

import com.thefloow.thechallenge.WordStatisticsApp;
import java.util.HashMap;

public class ParameterService implements iParameterService
{
    private static final String SOURCE_PARAMETER_KEY = "-source";
    private static final String MONGO_PARAMETER_KEY = "-mongo";
    
    @Override
    public HashMap<String, String> buildParameters(String[] args) 
    {
        HashMap<String, String> parameterMap = new HashMap<>();
        
        for (int i=0; i<args.length; i++) 
        {
            String arg = args[i];
            if(SOURCE_PARAMETER_KEY.equals(arg)) 
            {
                String value = args[i +1];
                parameterMap.put(WordStatisticsApp.SOURCE_KEY, value);
            }
            if(MONGO_PARAMETER_KEY.equals(arg)) 
            {
                String mongo = args[i +1];
                String[] output = mongo.split(":");
                String mongoHost = output[0];
                String mongoPort = output[1];
                parameterMap.put(WordStatisticsApp.MONGO_HOST_KEY, mongoHost);
                parameterMap.put(WordStatisticsApp.MONGO_PORT_KEY, mongoPort);
            }
            
        }
       return parameterMap;
    }
}