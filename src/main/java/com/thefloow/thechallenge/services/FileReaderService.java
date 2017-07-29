package com.thefloow.thechallenge.services;

import java.io.File;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

// TODO this garbage won't fly on a big file will need to chunk it up
public class FileReaderService implements iFileReaderService
{
    private FileChannel fileChannel;
    private long fileSize;

    public long getFileSize() {
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
    public String readFile(long position, long readLength) 
    {
        
        if(readLength > fileSize)
        {
            readLength = fileSize;
        }
        
        if((position + readLength) > fileSize)
        {
            readLength = fileSize - position;
        }

        MappedByteBuffer mappedByteBuffer;
        try 
        {
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, position, readLength);
            CharBuffer charBuffer = Charset.forName("utf-8").decode(mappedByteBuffer);
            
            return charBuffer.toString();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FileReaderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;    
       
        
      
        /*try 
        {
            String content = new String(Files.readAllBytes(Paths.get(file)));
            return content;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FileReaderService.class.getName()).log(Level.SEVERE, "Cannot read file", ex);
        }
        return null;
*/
    }
}
