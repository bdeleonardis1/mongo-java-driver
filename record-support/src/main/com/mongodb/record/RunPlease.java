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

        CodecRegistry recordCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));

//        MongoCollection<School> collection = database.getCollection("schools", School.class).withCodecRegistry(recordCodecRegistry);
//
//        System.out.println("Document: " + collection.find().first());
//        System.out.println(new School(new ObjectId(), "Berkeley"));
//
        School s = new School(new ObjectId(), "Berkeley");

        try {
            Constructor<? extends School> cons = s.getClass().getConstructor(new Class[] {ObjectId.class, String.class});

            Object[] arguments = new Object[]{new ObjectId(), "created on da fly"};

            for (RecordComponent comp : School.class.getRecordComponents()) {
                System.out.println("Component name: " + comp.getName());
                System.out.println("Component type: " + comp.getType());
            }
        } catch (Exception e) {
            System.out.println("e: " + e);
        }
    }
}
