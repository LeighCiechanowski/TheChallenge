package com.thefloow.thechallenge.services;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileReaderService implements iFileReaderService
{
    private FileChannel fileChannel;
    private long fileSize;

    public long getFileSize() 
    {
        return fileSize;
    }
    
    public FileReaderService(String file) 
    {
        try 
        {
            Path path = FileSystems.getDefault().getPath(file);
            fileChannel = (FileChannel)Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ));
            fileSize = fileChannel.size();
        } catch (IOException ex) 
        {
            Logger.getLogger(FileReaderService.class.getName()).log(Level.SEVERE, "Cannot open file", ex);
        }
    }
    
    @Override
    public String readFile(long start, long end) 
    {
        
        if(end > fileSize)
        {
            end = fileSize;
        }

        MappedByteBuffer mappedByteBuffer;
        try 
        {
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, start, end - start);
            CharBuffer charBuffer = Charset.forName("utf-8").decode(mappedByteBuffer);
            
            return charBuffer.toString();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FileReaderService.class.getName()).log(Level.SEVERE, "Failed to read file", ex);
        }
        return null;    
    }
    
    @Override
    public boolean close()
    {
        try 
        {
            fileChannel.close();
            return true;
        } 
        catch (IOException ex) 
        {
           Logger.getLogger(FileReaderService.class.getName()).log(Level.SEVERE, "Failed to close file", ex);
           return false;
        }
    }
}
