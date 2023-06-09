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

public class TravellingAgency {
    public static int totalPassengers = 0;
    public static  String connectionString = new String ("mongodb+srv://anshbansal18106:c9Dpy2kixtbr1MdB@cluster0.hnswyqe.mongodb.net/?retryWrites=true&w=majority");
    public static MongoClient mongoClient = MongoClients.create(connectionString);
    public static MongoDatabase travellingAgencyDB = mongoClient.getDatabase("test");
    public static MongoCollection<Document> activitiesCollection = travellingAgencyDB.getCollection("activities");
    public static MongoCollection<Document> destinationsCollection = travellingAgencyDB.getCollection("destinations");
    public static MongoCollection<Document> packagesCollection = travellingAgencyDB.getCollection("packages");
    public static MongoCollection<Document> passengersCollection = travellingAgencyDB.getCollection("passangers");

    public static int getActivityCostForCurrentPassenger(int activityCost, int passengerType) {
//        System.out.println(25);
        int activityCostForCurrentPassenger = activityCost;
        switch (passengerType) {
            case 1:
                activityCostForCurrentPassenger = activityCost;
                break;
            case 2:
                activityCostForCurrentPassenger = (int) (activityCost * 0.9);
                break;
            case 3:
                activityCostForCurrentPassenger = 0;
                break;
        }
        return activityCostForCurrentPassenger;
    }

    public static void printPassengerNumber(Document passenger) {
//        System.out.println(42);
        int passengerNumber = (int) passenger.get("passangerNumber");
        System.out.println("Passenger Number: " + passengerNumber);
    }
    public static void printPassengerBalance(Document passenger) {
//        System.out.println(47);
        int passengerBalance = (int) passenger.get("Balance");
        System.out.println("Passenger Balance: " + passengerBalance);
    }
    public static void printPassengerType(Document passenger) {
//        System.out.println(52);
        int passengerType = (int) passenger.get("type");
        System.out.println("Passenger Type: " + passengerType);
    }
    public static void printPassengerName(Document passenger) {
//        System.out.println(56);
        String passengerName = (String) passenger.get("name");
        System.out.println("Passenger Name: " + passengerName);
    }
    public static void printPassengerDetails(Document passenger, Boolean passengerNumber, Boolean Balance, Boolean passengerType, Boolean passengerName, Boolean printActivities) {
//        System.out.println(62);
        if (passengerName) printPassengerName(passenger);
        if (passengerNumber) printPassengerNumber(passenger);
        if (Balance) printPassengerBalance(passenger);
        if (passengerType) printPassengerType(passenger);
//        if (printActivities) printActivity();
    }
    public static void printActivityCapacity(Document activity) {
//        System.out.println(70);
        int activityCapacity = (int) activity.get("capacity");
        System.out.println("\t\tCurrent Capacity: " + activityCapacity);
    }
    public static void printActivityCost(Document activity) {
//        System.out.println(75);
        int activityCost = (int) activity.get("cost");
        System.out.println("\t\tActivity Cost: " + activityCost);
    }
    public static void printActivityDescription(Document activity) {
//        System.out.println(80);
        String activityDescription = (String) activity.get("description");
        System.out.println("\t\tActivity Description: " + activityDescription);
    }
    public static void printActivityName(Document activity) {
//        System.out.println(85);
        String activityName = (String) activity.get("name");
        System.out.println("\t\tActivity Name: " + activityName);
    }
    public static void printActivityDestination(Document activity, Boolean printDestinationId, Boolean printActivities, Boolean printPassengers, Boolean countNumberOfPassengers) {
//        System.out.println(90);
        Boolean printName = true;
        Integer destinationId = (Integer) activity.get("destinationId");
        Document destination = destinationsCollection.find(new Document("id", destinationId)).first();
        printDestinationName(destination);
//        printDestinationFromId(destinationId, printName, printDestinationId, printActivities, printPassengers, countNumberOfPassengers);
    }
    public static void printActivityPassengers(Document activity, Boolean countNumberOfPassengers, Boolean printPassengerBalance, Boolean printPassengerType) {
//        System.out.println(96);
        List<Integer> passengerIds = (List<Integer>) activity.get("passengersId");

        if (countNumberOfPassengers) {
            totalPassengers += passengerIds.size();
        }
        for (Integer passengerId: passengerIds) {
            Document passenger = passengersCollection.find(new Document("passangerNumber", passengerId)).first();
            printPassengerDetails(passenger, true, printPassengerBalance, printPassengerType, true, true);
        }
    }
    public static void printActivityDetails(Document activity, Boolean printActivityName, Boolean printDescription, Boolean printCost, Boolean printCapacity, Boolean printDestination, Boolean printDestinationId, Boolean printPassengers, Boolean countNumberOfPassengers, Boolean printActivities) {
//        System.out.println(105);
        System.out.println("________________________________________________________________________________");
        if (printActivityName) printActivityName(activity);
        if (printDescription) printActivityDescription(activity);
        if (printCost) printActivityCost(activity);
        if (printCapacity) printActivityCapacity(activity);
        if (printDestination) printActivityDestination(activity, printDestinationId, printActivities, printPassengers, countNumberOfPassengers);
        if (printPassengers || countNumberOfPassengers) printActivityPassengers(activity, countNumberOfPassengers, false, false);
    }
    public static void printDestinationName(Document destination) {
//        System.out.println(115);
        String name = (String) destination.get("name");
        System.out.println("\tDestination Name: " + name);
    }
    public static void printDestinationPackageId(Document destination) {
//        System.out.println(120);
        int packageId = (int) destination.get("packageId");
        System.out.println("\tPackage Id: " + packageId);
    }
    public static void printDestinationActivities(Document destination, Boolean printActivities, Boolean printPassengers, Boolean countNumberOfPassengers) {
//        System.out.println(125);
        Boolean printName = printActivities,
                printDescription = printActivities,
                printCost = printActivities,
                printCapacity = printActivities,
                printDestinationId = printActivities,
                printDestination = false;
        List<Integer> activitiesIds = (List<Integer>) destination.get("activityIds");
        if (printActivities) {
            if (activitiesIds.size() > 0) System.out.println("\tThe activities are available at this destination are:-");
            else System.out.println("\tNo activities are available at this destination");
        }
        for (Integer activityId: activitiesIds) {
//            System.out.println(122);
            Document activity = activitiesCollection.find(new Document("id", activityId)).first();
            printActivityDetails(activity, printName, printDescription, printCost, printCapacity, printDestination, printDestinationId, printPassengers, countNumberOfPassengers, printActivities);
        }
    }

    public static void printDestination (Document destination, Boolean name, Boolean packageId, Boolean printActivities, Boolean printPassengers, Boolean countNumberOfPassengers) {
//        System.out.println(145);
        if (name) printDestinationName(destination);
        if (packageId) printDestinationPackageId(destination);
        if(printActivities || printPassengers || countNumberOfPassengers) printDestinationActivities(destination, printActivities, printPassengers, countNumberOfPassengers);
    }
    public static void printPackageId(Document travelPackage) {
//        System.out.println(151);
        int packageId = (int) travelPackage.get("id");
        System.out.println(packageId);
    }
    public static void printPackageName(Document travelPackage) {
//        System.out.println(156);
        String packageName = (String) travelPackage.get("name");
        System.out.println("Package Name: " + packageName);
    }
    public static void printPackageCapacity(Document travelPackage) {
//        System.out.println(161);
        int packageCapacity = (int) travelPackage.get("capacity");
        System.out.println("Package Capacity: " + packageCapacity);
    }
    public static void printDestinationFromId(int destinationId, Boolean printName, Boolean printPackageId, Boolean printActivities, Boolean printPassengers, Boolean countNumberOfPassengers) {
        Document destination = destinationsCollection.find(new Document("id", destinationId)).first();
        printDestination(destination, printName, false, printActivities, printPassengers, countNumberOfPassengers);
    }
    public static void printPackageItineraries(Document travelPackage, Boolean printDestination, Boolean printPassengers, Boolean countNumberOfPassengers) {
//        System.out.println(166);
        Boolean printName = printDestination,
                printActivities = printDestination;
        List<Integer> destinationIds = (List<Integer>) travelPackage.get("destinationIds");
        for (Integer destinationId: destinationIds) {
//            System.out.println(147);
            printDestinationFromId(destinationId, printName, false, printActivities, printPassengers, countNumberOfPassengers);
//            Document destination = destinationsCollection.find(new Document("id", destinationId)).first();
//            printDestination(destination, printName, false, printActivities, printPassengers, countNumberOfPassengers);
        }
//        System.out.println(destinationIds);
    }
    public static void printPackagePassengers(Document travelPackage, Boolean countNumberOfPassengers) {
//        System.out.println(178);
        printPackageItineraries(travelPackage, false, true, countNumberOfPassengers);
    }

    public static void printPackageDetails(Document travelPackage, Boolean printDestination, Boolean printId, Boolean printName, Boolean printItineraries, Boolean printPassengers, Boolean countNumberOfPassengers) {
//        System.out.println(183);
        if (printId) printPackageId(travelPackage);
        if (printName) printPackageName(travelPackage);
        if (printItineraries) printPackageItineraries(travelPackage, printDestination, printPassengers, countNumberOfPassengers);
        if (printPassengers || countNumberOfPassengers) printPackagePassengers(travelPackage, countNumberOfPassengers);
    }
    private static void getAllDocuments(MongoCollection<Document> col) {
//        System.out.println(190);
        System.out.println("Fetching all documents from the collection");
        // Performing a read operation on the collection.
        FindIterable<Document> fi = col.find();
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while(cursor.hasNext()) {
                Document currentActivity = cursor.next();
                if ((int) currentActivity.get("capacity") > 0) {
                    printActivityDetails(currentActivity, true, true, true,true, false, false, false, false, false);
                }
            }
        } finally {
            cursor.close();
        }
    }
    public static void signupForNewActivity(Integer PassengerNumber, Integer ActivityId) { // task 0
//        System.out.println(207);
        Document passenger = passengersCollection.find(new Document("passangerNumber", PassengerNumber)).first();
        int passengerBalance = (int) passenger.get("Balance");
        int passengerType = (int) passenger.get("type");
        String passengerName = (String) passenger.get("name");


        Document activity = activitiesCollection.find(new Document("id", ActivityId)).first();
        String activityName = (String) activity.get("name");
        String activityDescription = (String) activity.get("description");
        int destinationId = (int) activity.get("destinationId");
        int activityCost = (int) activity.get("cost");
        int activityCapacity = (int) activity.get("capacity");
        int activityCostForCurrentPassenger = getActivityCostForCurrentPassenger(activityCost, passengerType);

        System.out.println(passenger);
        System.out.println(activity);
        System.out.println( "Your current balance: " + passengerBalance);
        System.out.println( "Your cost for this activity: " + activityCostForCurrentPassenger);
        if (passengerBalance < activityCostForCurrentPassenger) {
            System.out.println( "Your don't have sufficient balance");
            return;
        }
        passengerBalance -= activityCostForCurrentPassenger;
        System.out.println( "Balance left in your account: " + passengerBalance);
//        Document query = activitiesCollection.find(new Document("id", ActivityId)).first();
        Bson updates = Updates.combine(Updates.set("Balance", passengerBalance));
        UpdateOptions options = new UpdateOptions().upsert(true);
        try {
            UpdateResult result = passengersCollection.updateOne(passenger, updates, options);
            System.out.println(result);
        } catch (MongoException error) {
            System.err.println("Unable to update due to an error: " + error);
        }
    }
    public static void printItinerary(int travelPackageId) { // task 1
//        System.out.println(243);
        try {
            Document travelPackage = packagesCollection.find(new Document("id", travelPackageId)).first();
            printPackageDetails(travelPackage, true, false, true,true, false, false);
        } catch (MongoException error) {
            System.err.println("Unable to print due to an error: " + error);
        }
    }
    public  static void passengerDetails(int passangerId) { // task 2
//        System.out.println(266);
        Document passenger = passengersCollection.find(new Document("passangerNumber", passangerId)).first();
        printPassengerDetails(passenger, true, true, true, true, true);
        int passengerType = (int) passenger.get("type");
        List<Integer> activityIds = (List<Integer>) passenger.get("activityIds");
        if (activityIds.size() > 0) {
            for (Integer activityId: activityIds) {
                Document activity = activitiesCollection.find(new Document("id", activityId)).first();
                printActivityDetails(activity, true, false, false, false, true, false, false, false, false);
                int activityCost = (int) activity.get("cost");
                int activityCostForCurrentPassenger = getActivityCostForCurrentPassenger(activityCost, passengerType);
                System.out.println("This passenger has paid " + activityCostForCurrentPassenger + " for this activity.");
            }
        } else System.out.println("This passenger has not signed up for any activities");
    }
    public static void printTotalPassengers() {
        System.out.println("Total number of passangers signed up in this package: " + totalPassengers);
        totalPassengers = 0;
    }
    public static void printPassengerDetailsOfPackage(int ItineraryId) { //    task 3
//        System.out.println(282);
        Document currentPackage = packagesCollection.find(new Document("id", ItineraryId)).first();
        printPackageDetails(currentPackage, false, false, true, false, true,true);
        printTotalPassengers();
    }

    public static void printAvailableActivity() { // task 4.
//        System.out.println(288);
        getAllDocuments(activitiesCollection);
    }

    public static void main(String[] args) {
        int travelPackageId = 0, passengerId = 3, packageId = 3;

//        signupForNewActivity(2, 0); // not complete yet __ task 0

//        printItinerary(travelPackageId); // task 1 done
//        printPassengerDetailsOfPackage(packageId); // task 2 done
//        passengerDetails(passengerId); // task 3 done
//        printAvailableActivity(); // task 4 done
    }
}
