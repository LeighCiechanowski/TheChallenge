package com.thefloow.thechallenge.services;

import Model.FileChunk;
import java.util.List;

public interface iFileChunkingService  
{
    public List<FileChunk>  chunkFile(String path);
}
