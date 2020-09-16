package com.mongodb.record;

import com.mongodb.client.MongoClient;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoCollection;


public class RunPlease {
    public static void main(String[] args) {
        System.out.println(new School(new ObjectId(), "Cal"));
        System.out.println("one time");

        MongoClient client;
    }
}
