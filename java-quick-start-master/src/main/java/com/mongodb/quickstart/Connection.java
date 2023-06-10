package com.mongodb.quickstart;
import com.mongodb.client.*;
import org.bson.Document;

public class Connection {
    private static String connectionString = new String ("mongodb+srv://anshbansal18106:c9Dpy2kixtbr1MdB@cluster0.hnswyqe.mongodb.net/?retryWrites=true&w=majority");
    private static MongoClient mongoClient = MongoClients.create(connectionString);
    private static MongoDatabase travellingAgencyDB = mongoClient.getDatabase("test");
    public MongoCollection<Document> getActivitiesCollection () {
        return travellingAgencyDB.getCollection("activities");
    }
    public MongoCollection<Document> getDestinationsCollection () {
        return travellingAgencyDB.getCollection("destinations");
    }
    public MongoCollection<Document> getPackagesCollection () {
        return travellingAgencyDB.getCollection("packages");
    }
    public MongoCollection<Document> getPassengersCollection () {
        return travellingAgencyDB.getCollection("passangers");
    }
    public void main(String[] args) {
    }
}
