package com.thefloow.thechallenge.services;

import com.thefloow.thechallenge.model.FileChunk;
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
    private final long oneHundredKiloBytes = 1048576;
    private final long chunkSize = oneHundredKiloBytes;
    private FileChannel fileChannel;
    private long fileSize;
    
    public FileChunkingService(String file) 
    {
        Path path = FileSystems.getDefault().getPath(file);
        try 
        {
            fileChannel = (FileChannel)Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ));
            fileSize = fileChannel.size();
        } 
        catch (IOException ex) {
            Logger.getLogger(FileChunkingService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
    * Returns an Map of all the chunked up positions in a file. 
    * @param  path the path of the file
    * @return the map of chunks
    */
    @Override
    public List<FileChunk>  chunkFile(String path)
    {
        List<FileChunk> chunks = new ArrayList<>();
        
        // If file is less chunk size don't bother chunking it
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
        try 
        {
            String currentCharacter = "";
            MappedByteBuffer mappedByteBuffer;
            
            while((!" ".equals(currentCharacter)) && position < fileSize)
            {
                mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, position, 1);
                CharBuffer charBuffer = Charset.forName("utf-8").decode(mappedByteBuffer);
                currentCharacter = charBuffer.toString();
                position = position + 1;
            }
            
            if(position == fileSize)
            {
                return fileSize;
            }
            return position - 1;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FileChunkingService.class.getName()).log(Level.SEVERE, "Exception happened in find nearest space", ex);
        }
        return position;
    }
    
}
