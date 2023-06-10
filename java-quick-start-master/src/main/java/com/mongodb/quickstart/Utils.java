package com.mongodb.quickstart;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

public class Utils {
    public static MongoCursor<Document> getAllDocuments(MongoCollection<Document> collection) {
        FindIterable<Document> allDocuments = collection.find();
        return allDocuments.iterator();
    }

    public static String getPassengerType(int passengerType) {
        switch (passengerType) {
            case 1:
                return "Standard";
            case 2:
                return "Gold";
            case 3:
                return "Premium";
        }
        return "";
    }
    public static int getActivityCostForCurrentPassenger(int activityCost, int passengerType) {
        switch (getPassengerType(passengerType)) {
            case "Standard":
                return activityCost;
            case "Gold":
                return (int) (activityCost * 0.9);
            case "Premium":
                return 0;
        }
        return 0;
    }
}
