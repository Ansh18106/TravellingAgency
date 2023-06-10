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

public class Activity {
    public static MongoCollection<Document> getActivitiesCollection() {
        Connection conn = new Connection();
        return conn.getActivitiesCollection();
    }
    public static Document getActivity(int activityId) {
        MongoCollection<Document> activitiesCollection = getActivitiesCollection();
        return activitiesCollection.find(new Document("id", activityId)).first();
    }
    public static String getActivityName(Document activity) {
        return (String) activity.get("name");
    }
    public static String getActivityDescription(Document activity) {
        return (String) activity.get("description");
    }
    public static int getActivityDestinationId(Document activity) {
        return (int) activity.get("destinationId");
    }
    public static int getActivityCost(Document activity) {
        return (int) activity.get("cost");
    }
    public static int getActivityCapacity(Document activity) {
        return (int) activity.get("capacity");
    }
    public static List<Integer> getActivityPassengersIds(Document activity) {
        return (List<Integer>) activity.get("passengersId");
    }
    public static void printActivityName(Document activity) {
        System.out.println("Activity Name: " + getActivityName(activity));
    }
    public static void printActivityDescription(Document activity) {
        System.out.println("Activity Description: " + getActivityDescription(activity));
    }
    public static void printActivityCost(Document activity) {
        System.out.println("Activity Cost: " + getActivityCost(activity));
    }
    public static void printActivityCapacity(Document activity) {
        System.out.println("Activity Capacity: " + getActivityCapacity(activity));
    }
    public static void printActivityDestination(Document activity) {
        int destinationId = getActivityDestinationId(activity);
        // call print destination;
    }
    public static void printActivityPassengersIds(Document activity) {
        List<Integer> activityPassengersIds = getActivityPassengersIds(activity);
        for (Integer passengerId: activityPassengersIds) {
            // print passengers
        }
    }
    public void main(int activityId) {
        Document activity = getActivity(activityId);
    }
}
