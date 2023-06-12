package com.mongodb.quickstart;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public class Passenger {
    // This method will return the passenger collection
    public static MongoCollection<Document> getPassengersCollection() {
        Connection conn = new Connection();
        return conn.getPassengersCollection();
    }

    //This method will return the passenger document with passenger id = passengerNumber
    public static Document getPassenger(int passengerNumber) {
        MongoCollection<Document> passengersCollection = getPassengersCollection();
        return passengersCollection.find(new Document("passengerNumber", passengerNumber)).first();
    }
//    This method  will return the name of the passenger

    public static String getPassengerName(Document passenger) {
        return (String) passenger.get("name");
    }

    //This method  will return the Number of the passenger
    public static int getPassengerNumber(Document passenger) {
        return (int) passenger.get("passengerNumber");
    }

    //    This method  will return the subscription type id of the passenger
    public static int getPassengerTypeId(Document passenger) {
        return (int) passenger.get("type");
    }

    //This method  will return the balance of the passenger
    public static int getPassengerBalance(Document passenger) {
        return (int) passenger.get("balance");
    }

    //This method will return the ids of all the activities present in the itinerary of the passenger

    public static List<Integer> getPassengerActivityIds(Document passenger) {
        return (List<Integer>) passenger.get("activityIds");
    }

    //This method  will update the balance of the passenger
    public static void setPassengerBalance(Document passenger, int balance) {
        Utils updateCollObj = new Utils();
        MongoCollection<Document> passengersCollection = getPassengersCollection();
        Bson updatedBalanceQuery = Updates.combine(Updates.set("balance", balance));
        updateCollObj.updateCollection(passengersCollection, passenger, updatedBalanceQuery);
    }

    //    This method will add the activity to the itinerary of the passenger
    public static void setActivityIds(Document passenger, int activityId) {
        Utils updateCollObj = new Utils();
        MongoCollection<Document> passengersCollection = getPassengersCollection();
        Bson updatedActivityIdsQuery = Updates.combine(Updates.addToSet("activityIds", activityId));
        updateCollObj.updateCollection(passengersCollection, passenger, updatedActivityIdsQuery);
    }

    //    This method will print the name of passenger
    public static void printPassengerName(Document passenger) {
        System.out.println("Passenger Name: " + getPassengerName(passenger));
    }

    //This method will print the number of the passenger
    public static void printPassengerNumber(Document passenger) {
        System.out.println("Passenger Number: " + getPassengerNumber(passenger));
    }

    //This method  will print the type Id of the passenger
    public static void printPassengerType(Document passenger) {
        System.out.println("Passenger Type: " + getPassengerTypeId(passenger));
    }

    //This method will print the balance of the passenger
    public static void printPassengerBalance(Document passenger) {
        System.out.println("Passenger Balance: " + getPassengerBalance(passenger));
    }

    //    This method will print the details of all the activities present in the itinerary of the passenger
    public static void printPassengersAllActivities(Document passenger) {
        List<Integer> activityIds = getPassengerActivityIds(passenger);
        for (Integer activityId : activityIds) {
            // call print activities
        }
    }
}
