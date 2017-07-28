package com.thefloow.thechallenge;

import com.thefloow.thechallenge.services.*;
import java.util.Map;



public class WordStatisticsApp 
{
    
    public static void main( String[] args )
    {
         String[] myStringArray = new String[]{"-source","test.text","-mongo", "localhost:27017"};
         System.out.println("And so it begins");
         ParameterService parameterService = new ParameterService();
         Map<String, String> parameters = parameterService.buildParameters(myStringArray);
    }
   
}
