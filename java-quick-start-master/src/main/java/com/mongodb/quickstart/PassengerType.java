package com.mongodb.quickstart;

import com.mongodb.client.*;
import org.bson.Document;

public class PassengerType {
    public static MongoCollection<Document> getPassengerTypeCollection() {
        Connection conn = new Connection();
        return conn.getPassengersTypeCollection();
    }

    public static Document getPassengerType(int passengerTypeId) {
        MongoCollection<Document> passengersTypeCollection = getPassengerTypeCollection();
        return passengersTypeCollection.find(new Document("id", passengerTypeId)).first();
    }

    public static String getSubscriptionType(Document passengerType) {
        return (String) passengerType.get("subscriptionType");
    }

    public static int getDiscountPercent(Document passengerType) {
        return (int) passengerType.get("discountPercent");
    }

    public static void printSubscriptionType(Document destination) {
        System.out.println("Destination Name: " + getSubscriptionType(destination));
    }

    public static void printDiscountPercent(Document destination) {
        System.out.println("Discount Percent: " + getDiscountPercent(destination));
    }
}
