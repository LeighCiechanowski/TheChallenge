package com.thefloow.thechallenge.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO this garbage won't fly on a big file will need to chunk it up
public class FileReaderService implements iFileReaderService
{
    @Override
    public String readFile(String file) 
    {
        try 
        {
            String content = new String(Files.readAllBytes(Paths.get(file)));
            return content;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FileReaderService.class.getName()).log(Level.SEVERE, "Cannot read file", ex);
        }
        return null;
    }
    
}
