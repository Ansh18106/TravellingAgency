package com.mongodb.quickstart;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bson.Document;
import org.bson.conversions.Bson;

//import org.apache.log4j.Logger;

import java.util.*;


public class Destination {
    public static MongoCollection<Document> getDestinationCollection() {
        Connection conn = new Connection();
        return conn.getDestinationsCollection();
    }
    public static Document getDestination(int packageId) {
        MongoCollection<Document> packagesCollection = getDestinationCollection();
        return packagesCollection.find(new Document("id", packageId)).first();
    }
    public static String getName(Document destination){
        return (String) destination.get("name");
    }
    public static int getPackageId(Document destination){
        return (int) destination.get("packageId");
    }
    public static List<Integer> getActivityIds(Document destination) {
        return (List<Integer>) destination.get("activityIds");
    }
    public static void printName(Document destination){
        System.out.println("Destination Name: " + getName(destination));
    }
    public static void printDestinationPackageName(Document destination){
        int packageId = getPackageId(destination);
        // call printPackageName
    }
    public static void printDestinationActivities(Document destination) {
        List<Integer> activityIds = getActivityIds(destination);
        for (Integer activityId: activityIds) {
            // call print activities
        }
    }
    public static void main(int destinationId) {
        Document destination = getDestination(destinationId);
    }
}
