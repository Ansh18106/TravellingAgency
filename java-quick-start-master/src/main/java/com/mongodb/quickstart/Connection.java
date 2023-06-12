package com.mongodb.quickstart;

import com.mongodb.client.*;
import org.bson.Document;

public class Connection {
    //    Connecting with MongoDatabase
    private static String connectionString = new String("mongodb+srv://anshbansal18106:c9Dpy2kixtbr1MdB@cluster0.hnswyqe.mongodb.net/?retryWrites=true&w=majority");
    private static MongoClient mongoClient = MongoClients.create(connectionString);
    private static MongoDatabase travellingAgencyDB = mongoClient.getDatabase("test");

    //    return the Activities Collection
    public MongoCollection<Document> getActivitiesCollection() {
        return travellingAgencyDB.getCollection("activities");
    }

    //    return the Destination collection
    public MongoCollection<Document> getDestinationsCollection() {
        return travellingAgencyDB.getCollection("destinations");
    }

    //    return the Package collection
    public MongoCollection<Document> getPackagesCollection() {
        return travellingAgencyDB.getCollection("packages");
    }

    //    return the passenger collection
    public MongoCollection<Document> getPassengersCollection() {
        return travellingAgencyDB.getCollection("passengers");
    }

    //    return the passenger type collection
    public MongoCollection<Document> getPassengersTypeCollection() {
        return travellingAgencyDB.getCollection("passengertypes");
    }
}

