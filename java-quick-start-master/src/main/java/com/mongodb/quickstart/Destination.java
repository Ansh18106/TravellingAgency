package com.mongodb.quickstart;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.*;

public class Destination {
    //    This method will return the destination collection
    public static MongoCollection<Document> getDestinationCollection() {
        Connection conn = new Connection();
        return conn.getDestinationsCollection();
    }

    //This method will return the destination document with pacakge id = packageId
    public static Document getDestination(int packageId) {
        MongoCollection<Document> packagesCollection = getDestinationCollection();
        return packagesCollection.find(new Document("id", packageId)).first();
    }

    //    This method will return the destination of the destination
    public static String getDestinationName(Document destination) {
        return (String) destination.get("name");
    }

    //This method will return the packageId which will take the passenger to this destination
    public static int getDestinationPackageId(Document destination) {
        return (int) destination.get("packageId");
    }

    //This method will return the activity ids of the available activities on this destination
    public static List<Integer> getDestinationActivityIds(Document destination) {
        return (List<Integer>) destination.get("activityIds");
    }

    //This method will return the name of the destination
    public static void printDestinationName(Document destination) {
        System.out.println("Destination Name: " + getDestinationName(destination));
    }

    //This will print the name of the package which will take the passenger to this destination
    public static void printDestinationPackageName(Document destination) {
        int packageId = getDestinationPackageId(destination);
        // call printPackageName
    }

    //This will print all the details of the activities available on this destination
    public static void printDestinationActivities(Document destination) {
        Activity activityObj = new Activity();
        List<Integer> activityIds = getDestinationActivityIds(destination);
        for (Integer activityId : activityIds) {
            System.out.println("___________________________________________________");
            Document activity = activityObj.getActivity(activityId);
            activityObj.printActivityName(activity);
            activityObj.printActivityDescription(activity);
            activityObj.printActivityCost(activity);
            activityObj.printActivityCapacity(activity);
        }
    }
}
