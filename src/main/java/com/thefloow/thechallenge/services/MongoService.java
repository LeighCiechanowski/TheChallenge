package com.thefloow.thechallenge.services;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
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
        return true;
    }
    
    @Override
    public void close() 
    {
        client.close();
    }
}
