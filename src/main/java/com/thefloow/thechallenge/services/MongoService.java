package com.thefloow.thechallenge.services;

import Model.FileChunk;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.bson.Document;

public class MongoService implements iMongoService
{
    public static final String DATABASE = "WordStats";
    public static final String WORD_COUNT_COLLECTION = "WordCount";
    public static final String CHUNKS_COLLECTION = "Chunks";
    private static final String COLLECTION_KEY = "word";
    private static final String COLLECTION_VAUE = "count";
    private final MongoClient client;
    private final MongoDatabase database;
    
    public MongoService(String hostname, String port) 
    {
        MongoClient mongoClient = null;
        MongoDatabase mongoDatabase = null;
        try 
        {
            mongoClient = new MongoClient(hostname, Integer.valueOf(port));
            mongoDatabase = mongoClient.getDatabase(DATABASE);
        } 
        catch (MongoException ex) 
        {
            Logger.getLogger(MongoService.class.getName()).log(Level.SEVERE, "Cannot read mongo", ex);
        }
        this.client = mongoClient;
        this.database = mongoDatabase;
    }
    
    @Override
    public boolean upsertWordCount(Map<String, Integer> wordCountMap) 
    {
        if(!wordCountMap.isEmpty())
        {
            try 
            {
                MongoCollection collection = database.getCollection(WORD_COUNT_COLLECTION);
                UpdateOptions opt = new UpdateOptions().upsert(true);
                List<UpdateOneModel> requests =
                        wordCountMap.entrySet().stream()
                                .map(entry -> {
                                    BasicDBObject filter = new BasicDBObject(COLLECTION_KEY, entry.getKey());
                                    BasicDBObject action = new BasicDBObject("$inc",
                                            new Document().append(COLLECTION_VAUE, entry.getValue()));
                                    return new UpdateOneModel<>(filter, action, opt);
                                }).collect(Collectors.toList());

                collection.bulkWrite(requests);
            } 
            catch (MongoException ex) 
            {
               Logger.getLogger(MongoService.class.getName()).log(Level.SEVERE, "Exception ocurred while upserting mongo word count", ex);
               return false;
            }
        }
        return true;
    }
    
    @Override
    public void close() 
    {
        client.close();
    }
    
    @Override
    public void insertFileMap(List<FileChunk> records) {
        MongoCollection<Document> collection = database.getCollection(CHUNKS_COLLECTION);
       
        collection.drop();
        collection.deleteMany(new Document());
        List<Document> chunks = new ArrayList<>();
   
        records.forEach((item) -> {
            chunks.add(new Document()
                    .append("start", item.getStart())
                    .append("end", item.getEnd())
                    .append("complete", item.getComplete())
                    .append("processing", item.getProcessing()));
        });

        collection.insertMany(chunks);
    }
    
    @Override
    public List<FileChunk> getFileMap() 
    {
        MongoCollection<Document> collection = database.getCollection(CHUNKS_COLLECTION);
        FindIterable<Document> iterable = collection.find();
        List<FileChunk> records = new ArrayList<>();

        iterable.forEach((Block<Document>) document -> {
           records.add(new FileChunk(document.getLong("start"),
           document.getLong("end"),
           document.getBoolean("complete"),
           document.getBoolean("processing")));
        });
      return records;
    }
    
    @Override
    public void updateFileChunk(FileChunk chunk)
    {
        BasicDBObject filter = new BasicDBObject("start", chunk.getStart());
        BasicDBObject action = new BasicDBObject("$set",
                                        new Document().append("complete", chunk.getComplete())
                                                      .append("processing", chunk.getProcessing()));
     
        MongoCollection<Document> collection = database.getCollection(CHUNKS_COLLECTION); 
        collection.updateOne(filter, action);
    }
}
