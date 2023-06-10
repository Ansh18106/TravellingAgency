package com.mongodb.quickstart;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.List;

public class Passenger {
    public static MongoCollection<Document> getPassengersCollection() {
        Connection conn = new Connection();
        return conn.getPassengersCollection();
    }
    public static Document getPassenger(int passengerNumber) {
        MongoCollection<Document> passengersCollection = getPassengersCollection();
        return passengersCollection.find(new Document("passangerNumber", passengerNumber)).first();
    }
    public static String getPassengerName(Document passenger) {
        return (String) passenger.get("name");
    }
    public static int getPassengerNumber(Document passenger) {
        return (int) passenger.get("passangerNumber");
    }
    public static int getPassengerType(Document passenger) {
        return (int) passenger.get("type");
    }
    public static int getPassengerBalance(Document passenger) {
        return (int) passenger.get("Balance");
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
        System.out.println("Passenger Type: " + getPassengerType(passenger));
    }
    public static void printPassengerBalance(Document passenger) {
        System.out.println("Passenger Balance: " + getPassengerBalance(passenger));
    }
    public static void printPassengersAllActivities(Document passenger) {
        List<Integer> activityIds =  getPassengerActivityIds(passenger);
        for (Integer activityId: activityIds) {
            // call print activities
        }
    }
    public void main(int passengerNumber) {
        Document passenger = getPassenger(passengerNumber);
    }
}
