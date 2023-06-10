package com.mongodb.quickstart;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

public class Utils {
    public static MongoCursor<Document> getAllDocuments(MongoCollection<Document> collection) {
//        System.out.println(190);
        System.out.println("Fetching all documents from the collection");
        // Performing a read operation on the collection.
        FindIterable<Document> fi = collection.find();
        MongoCursor<Document> cursor = fi.iterator();
        return fi.iterator();
    }
}
