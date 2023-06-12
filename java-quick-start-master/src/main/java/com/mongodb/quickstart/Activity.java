package com.mongodb.quickstart;

import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

public class Activity {
    //    This method will return the activity collection
    public static MongoCollection<Document> getActivitiesCollection() {
        Connection conn = new Connection();
        return conn.getActivitiesCollection();
    }

    //This method will return the activity document with activity id = activityId
    public static Document getActivity(int activityId) {
        MongoCollection<Document> activitiesCollection = getActivitiesCollection();
        return activitiesCollection.find(new Document("id", activityId)).first();
    }

    //    This method will return the name of the activity
    public static String getActivityName(Document activity) {
        return (String) activity.get("name");
    }

    //This method will return the description of the activity
    public static String getActivityDescription(Document activity) {
        return (String) activity.get("description");
    }

    //This method will return the destination id which will supervise this activity
    public static int getActivityDestinationId(Document activity) {
        return (int) activity.get("destinationId");
    }

    //This method will return the activity cost according to the standard passenger
    public static int getActivityCost(Document activity) {
        return (int) activity.get("cost");
    }

    //This method will return the total capacity of the activity
    public static int getActivityCapacity(Document activity) {
        return (int) activity.get("capacity");
    }

    //This method will return the remaining capacity of the activity
    public static int getActivityRemainingCapacity(Document activity) {
        return ((int) activity.get("capacity") - getActivityPassengersIds(activity).size());
    }

    //This method will return all the passenger ids who have enrolled in this activity
    public static List<Integer> getActivityPassengersIds(Document activity) {
        return (List<Integer>) activity.get("passengersId");
    }

    //    This method will add the passenger to the activity
    public static void setPassengerIds(Document activity, int passengerNumber) {
        Utils updateCollObj = new Utils();
        Bson updatedPassengerIdsQuery = Updates.combine(Updates.addToSet("passengersId", passengerNumber));
        MongoCollection<Document> activityCollection = getActivitiesCollection();
        updateCollObj.updateCollection(activityCollection, activity, updatedPassengerIdsQuery);
    }

    //    This method will print the activity name of the activity
    public static void printActivityName(Document activity) {
        System.out.println("Activity Name: " + getActivityName(activity));
    }

    //    This method will print the acitivity description
    public static void printActivityDescription(Document activity) {
        System.out.println("Activity Description: " + getActivityDescription(activity));
    }

    //This method will print the activity cost according to the standard passenger
    public static void printActivityCost(Document activity) {
        System.out.println("Activity Cost: " + getActivityCost(activity));
    }

    //This method will print the capacity of the activity
    public static void printActivityCapacity(Document activity) {
        System.out.println("Activity Capacity: " + getActivityCapacity(activity));
    }

    //This method will print the remaining capacity of the activity
    public static void printActivityRemainingCapacity(Document activity) {
        System.out.println("Activity Capacity: " + getActivityRemainingCapacity(activity));
    }

    //This method will print the details of the destination which will supervise this activity
    public static void printActivityDestination(Document activity) {
        int destinationId = getActivityDestinationId(activity);
        // call print destination;
    }

    //This method will print all the details of the passengers enrolled in this activity
    public static void printActivityPassengersIds(Document activity) {
        List<Integer> activityPassengersIds = getActivityPassengersIds(activity);
        for (Integer passengerId : activityPassengersIds) {
            // print passengers
        }
    }
}
