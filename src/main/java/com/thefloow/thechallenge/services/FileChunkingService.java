package com.thefloow.thechallenge.services;

import Model.FileChunk;
import java.io.File;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileChunkingService implements iFileChunkingService
{
    private final long oneMb = 1048576;
    private final long chunkSize = 8;//oneMb * 25;
    
    /**
    * Returns an Map of all the chunked up positions in a file. 
    * @param  path the path of the file
    * @return the map of chunks
    */
    @Override
    public List<FileChunk>  chunkFile(String path)
    {
        List<FileChunk> chunks = new ArrayList<>();
        
        long fileSize = new File(path).length();
        
        // If file is less 25mb don't bother chunking it
        if(fileSize < chunkSize)
        {
            chunks.add(new FileChunk(0l, fileSize, false, false));
        }
        else
        {
            long startPosition = 0l;
            long endPosition = chunkSize;
            
            long breakableEndPosition = findNearestSpace(endPosition, path);
            
            chunks.add(new FileChunk(startPosition, breakableEndPosition, false, false));
            
            while(breakableEndPosition < fileSize)
            {
                 startPosition = breakableEndPosition + 1;
                 endPosition = breakableEndPosition + chunkSize;
                 
                 if(endPosition > fileSize)
                 {
                     endPosition = fileSize;
                     chunks.add(new FileChunk(startPosition, endPosition, false, false));
                     break;
                 }
                 breakableEndPosition = findNearestSpace(endPosition, path);
                 chunks.add(new FileChunk(startPosition, breakableEndPosition, false, false));
            }
        }
       
        return chunks;
    }
    
    private long findNearestSpace(long position, String file)
    {
        Path path = FileSystems.getDefault().getPath(file);
        try 
        {
            String currentCharacter = "";
            FileChannel fileChannel = (FileChannel)Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ));
            MappedByteBuffer mappedByteBuffer;
            
            while(!" ".equals(currentCharacter))
            {
                mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, position, 1);
                CharBuffer charBuffer = Charset.forName("utf-8").decode(mappedByteBuffer);
                currentCharacter = charBuffer.toString();
                position = position + 1;
            }
            return position - 1;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FileChunkingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return position;
    }
    
}
