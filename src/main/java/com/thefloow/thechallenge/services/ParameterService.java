
package com.thefloow.thechallenge.services;

import java.util.HashMap;

public class ParameterService implements iParameterService
{
    @Override
    public HashMap<String, String> buildParameters(String[] args) 
    {
        HashMap<String, String> parameterMap = new HashMap<>();
        
        for (int i=0; i<args.length; i++) 
        {
            String arg = args[i];
            if("-source".equals(arg)) 
            {
                String value = args[i +1];
                parameterMap.put("Source", value);
            }
            if("-mongo".equals(arg)) 
            {
                String mongo = args[i +1];
                String[] output = mongo.split(":");
                String mongoHost = output[0];
                String mongoPort = output[1];
                parameterMap.put("MongoHostname", mongoHost);
                parameterMap.put("MongoPort", mongoPort);
            }
            
        }
       return parameterMap;
    }
}