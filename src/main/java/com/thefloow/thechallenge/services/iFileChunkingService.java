package com.thefloow.thechallenge.services;

import com.thefloow.thechallenge.model.FileChunk;
import java.util.List;

public interface iFileChunkingService  
{
    public List<FileChunk>  chunkFile(String path);
}
