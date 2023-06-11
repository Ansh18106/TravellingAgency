package com.mongodb.quickstart;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public class Passenger {
    public static MongoCollection<Document> getPassengersCollection() {
        Connection conn = new Connection();
        return conn.getPassengersCollection();
    }

    public static Document getPassenger(int passengerNumber) {
        MongoCollection<Document> passengersCollection = getPassengersCollection();
        return passengersCollection.find(new Document("passengerNumber", passengerNumber)).first();
    }

    public static String getPassengerName(Document passenger) {
        return (String) passenger.get("name");
    }

    public static int getPassengerNumber(Document passenger) {
        return (int) passenger.get("passengerNumber");
    }

    public static int getPassengerTypeId(Document passenger) {
        return (int) passenger.get("type");
    }

    public static int getPassengerBalance(Document passenger) {
        return (int) passenger.get("balance");
    }

    public static void setPassengerBalance(Document passenger, int balance) {
        Utils updateCollObj = new Utils();
        MongoCollection<Document> passengersCollection = getPassengersCollection();
        Bson updatedBalanceQuery = Updates.combine(Updates.set("balance", balance));
        updateCollObj.updateCollection(passengersCollection, passenger, updatedBalanceQuery);
    }

    public static void setActivityIds(Document passenger, int activityId) {
        Utils updateCollObj = new Utils();
        MongoCollection<Document> passengersCollection = getPassengersCollection();
        Bson updatedActivityIdsQuery = Updates.combine(Updates.addToSet("activityIds", activityId));
        updateCollObj.updateCollection(passengersCollection, passenger, updatedActivityIdsQuery);
    }

    public static List<Integer> getPassengerActivityIds(Document passenger) {
        return (List<Integer>) passenger.get("activityIds");
    }

    public static void printPassengerName(Document passenger) {
        System.out.println("Passenger Name: " + getPassengerName(passenger));
    }

    public static void printPassengerNumber(Document passenger) {
        System.out.println("Passenger Number: " + getPassengerNumber(passenger));
    }

    public static void printPassengerType(Document passenger) {
        System.out.println("Passenger Type: " + getPassengerTypeId(passenger));
    }

    public static void printPassengerBalance(Document passenger) {
        System.out.println("Passenger Balance: " + getPassengerBalance(passenger));
    }

    public static void printPassengersAllActivities(Document passenger) {
        List<Integer> activityIds = getPassengerActivityIds(passenger);
        for (Integer activityId : activityIds) {
            // call print activities
        }
    }
}
