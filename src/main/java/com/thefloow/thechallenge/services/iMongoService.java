package com.thefloow.thechallenge.services;

import java.util.Map;

public interface iMongoService 
{
    public boolean upsertWordCount(Map<String, Integer> wordCountMap);
    public void close();
}
