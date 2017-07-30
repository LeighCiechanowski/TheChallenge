package com.thefloow.thechallenge.engines;

import Model.FileChunk;
import com.thefloow.thechallenge.services.iFileChunkingService;
import com.thefloow.thechallenge.services.iMongoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileMappingEngine 
{
    private final iMongoService mongoService;
    private final iFileChunkingService fileChunkingService;
    private List<FileChunk> fileMap = new  ArrayList<>();
    private final String filePath;
    
    public FileMappingEngine(iMongoService mongoService, iFileChunkingService fileChunkingService, String filePath) {
        this.mongoService = mongoService;
        this.fileChunkingService = fileChunkingService;
        this.filePath = filePath;
        this.fileMap = mongoService.getFileMap();
    }
    
    private void buildMap()
    {
        fileMap = fileChunkingService.chunkFile(filePath);
        //mongoService.putFileMap(fileMap);
         mongoService.insertFileMap(fileMap);
    }
    
    /*public List<FileChunk> getFileMap()
    {
        if(fileMap.isEmpty())
        {
            buildMap();
        }
        // refresh map from db to get latest state
        this.fileMap = mongoService.getFileMap();
        return fileMap;
    }*/
    
    public FileChunk getNextFileChunk()
    {
        if(fileMap.isEmpty())
        {
            // If you are the first server there will be no file map in Mongo
            buildMap();
        }
        else
        {
            // refresh map from db to get latest state
            this.fileMap = mongoService.getFileMap();
        }
        
        FileChunk chunk = this.fileMap.stream().filter(chunks -> Objects.equals(chunks.getComplete(), false)).findFirst().orElse(null);

        return chunk;
    }
    
    public void updateFileChunk(FileChunk chunk)
    {
        mongoService.updateFileChunk(chunk);
    }
}
