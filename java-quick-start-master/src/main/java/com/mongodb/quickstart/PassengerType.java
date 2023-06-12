package com.mongodb.quickstart;

import com.mongodb.client.*;
import org.bson.Document;

public class PassengerType {
    //    This method will return the passenger type collection
    public static MongoCollection<Document> getPassengerTypeCollection() {
        Connection conn = new Connection();
        return conn.getPassengersTypeCollection();
    }

    //This method will return the passengerType document with passenger type id = passengerTypeId
    public static Document getPassengerType(int passengerTypeId) {
        MongoCollection<Document> passengersTypeCollection = getPassengerTypeCollection();
        return passengersTypeCollection.find(new Document("id", passengerTypeId)).first();
    }

    //    This method will return the Passenger Type of the passenger:
//    Standard
//    Gold
//    Platinum
    public static String getSubscriptionType(Document passengerType) {
        return (String) passengerType.get("subscriptionType");
    }

    //    This will return the discount to be provided to the passenger of passenger type = passengerType
    public static int getDiscountPercent(Document passengerType) {
        return (int) passengerType.get("discountPercent");
    }

//    This will print the passenger type

    public static void printSubscriptionType(Document destination) {
        System.out.println("Destination Name: " + getSubscriptionType(destination));
    }
//    This will print the discount percent to be provided to the passenger of passenger type = passengerType

    public static void printDiscountPercent(Document destination) {
        System.out.println("Discount Percent: " + getDiscountPercent(destination));
    }
}
