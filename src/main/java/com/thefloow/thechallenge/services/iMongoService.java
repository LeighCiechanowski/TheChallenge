package com.thefloow.thechallenge.services;

import Model.FileChunk;
import java.util.List;
import java.util.Map;

public interface iMongoService 
{
    public boolean upsertWordCount(Map<String, Integer> wordCountMap);
    public void insertFileMap(List<FileChunk> records);
    public List<FileChunk> getFileMap();
    public void updateFileChunk(FileChunk chunk);
    public void close();
}
