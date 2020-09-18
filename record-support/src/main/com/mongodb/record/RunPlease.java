package com.mongodb.record;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import com.mongodb.client.MongoCollection;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class RunPlease {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("mydb");

        CodecRegistry recordCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(RecordCodecProvider.builder().build()));

        MongoCollection<School> collection = database.getCollection("schools", School.class).withCodecRegistry(recordCodecRegistry);

        collection.insertOne(new School(new ObjectId(), "Hahaha university"));
        //System.out.println("The created school object: " + collection.find().first());
//

    }
}
