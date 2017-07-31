package com.thefloow.thechallenge.services;

public interface iFileReaderService 
{
    public String readFile(long start, long end);
    public boolean close();
}
