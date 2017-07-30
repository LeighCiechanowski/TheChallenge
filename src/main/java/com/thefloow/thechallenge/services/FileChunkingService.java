package com.thefloow.thechallenge.services;

import Model.FileChunk;
import java.util.ArrayList;
import java.util.List;

public class FileChunkingService 
{
    private final long oneMb = 1048576;
    private final long chunkSize = oneMb * 25;
    
    /**
    * Returns an Map of all the chunked up positions in a file. 
    * @param  path the path of the file
    * @return the map of chunks
    */
    public List<FileChunk>  chunkFile(String path)
    {
        List<FileChunk> chunks = new ArrayList<>();
        
        long fileSize = oneMb * 1024;//new File(path).length();
        
        // If file is less 25mb don't bother chunking it
        if(fileSize < chunkSize)
        {
            chunks.add(new FileChunk(0l, fileSize, false, false));
        }
        else
        {
            long startPosition = 0l;
            long endPosition = chunkSize;
            
            chunks.add(new FileChunk(startPosition, endPosition, false, false));
            
            while(endPosition < fileSize)
            {
                 startPosition = endPosition + 1;
                 endPosition = endPosition + chunkSize;
                 
                 if(endPosition > fileSize)
                 {
                     endPosition = fileSize;
                     chunks.add(new FileChunk(startPosition, endPosition, false, false));
                     break;
                 }
                 
                  chunks.add(new FileChunk(startPosition, endPosition, false, false));
            }
        }
       
        return chunks;
    }
    
}
