package com.mongodb.quickstart;
import com.mongodb.client.*;
import org.bson.Document;
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
    public static String getDestinationName(Document destination){
        return (String) destination.get("name");
    }
    public static int getDestinationPackageId(Document destination){
        return (int) destination.get("packageId");
    }
    public static List<Integer> getDestinationActivityIds(Document destination) {
        return (List<Integer>) destination.get("activityIds");
    }
    public static void printDestinationName(Document destination){
        System.out.println("Destination Name: " + getDestinationName(destination));
    }
    public static void printDestinationPackageName(Document destination){
        int packageId = getDestinationPackageId(destination);
        // call printPackageName
    }
    public static void printDestinationActivities(Document destination) {
        Activity activityObj = new Activity();
        List<Integer> activityIds = getDestinationActivityIds(destination);
        for (Integer activityId: activityIds) {
            System.out.println("___________________________________________________");
            Document activity = activityObj.getActivity(activityId);
            activityObj.printActivityName(activity);
            activityObj.printActivityDescription(activity);
            activityObj.printActivityCost(activity);
            activityObj.printActivityCapacity(activity);
        }
    }
    public static void main(int destinationId) {
        Document destination = getDestination(destinationId);
    }
}
