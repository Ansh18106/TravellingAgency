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
    Connection conn = new Connection();
//    private static MongoDatabase travellingAgencyDB = conn.travellingAgencyDB;
    private static int totalPassengers = 0;
    private static  String connectionString = new String ("mongodb+srv://anshbansal18106:c9Dpy2kixtbr1MdB@cluster0.hnswyqe.mongodb.net/?retryWrites=true&w=majority");
    private static MongoClient mongoClient = MongoClients.create(connectionString);
    private static MongoDatabase travellingAgencyDB = mongoClient.getDatabase("test");
    private static MongoCollection<Document> activitiesCollection = travellingAgencyDB.getCollection("activities");
    private static MongoCollection<Document> destinationsCollection = travellingAgencyDB.getCollection("destinations");
    private static MongoCollection<Document> packagesCollection = travellingAgencyDB.getCollection("packages");
    private static MongoCollection<Document> passengersCollection = travellingAgencyDB.getCollection("passangers");

    //
    private static int getActivityCostForCurrentPassenger(int activityCost, int passengerType) {
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

    private static void printPassengerNumber(Document passenger) {
//        System.out.println(42);
        int passengerNumber = (int) passenger.get("passangerNumber");
        System.out.println("Passenger Number: " + passengerNumber);
    }
    private static void printPassengerBalance(Document passenger) {
//        System.out.println(47);
        int passengerBalance = (int) passenger.get("Balance");
        System.out.println("Passenger Balance: " + passengerBalance);
    }
    private static void printPassengerType(Document passenger) {
//        System.out.println(52);
        int passengerType = (int) passenger.get("type");
        System.out.println("Passenger Type: " + passengerType);
    }
    private static void printPassengerName(Document passenger) {
//        System.out.println(56);
        String passengerName = (String) passenger.get("name");
        System.out.println("Passenger Name: " + passengerName);
    }
    private static void printPassengerDetails(Document passenger, Boolean passengerNumber, Boolean Balance, Boolean passengerType, Boolean passengerName, Boolean printActivities) {
//        System.out.println(62);
        if (passengerName) printPassengerName(passenger);
        if (passengerNumber) printPassengerNumber(passenger);
        if (Balance) printPassengerBalance(passenger);
        if (passengerType) printPassengerType(passenger);
//        if (printActivities) printActivity();
    }
    private static void printActivityCapacity(Document activity) {
//        System.out.println(70);
        int activityCapacity = (int) activity.get("capacity");
        System.out.println("\t\tCurrent Capacity: " + activityCapacity);
    }
    private static void printActivityCost(Document activity) {
//        System.out.println(75);
        int activityCost = (int) activity.get("cost");
        System.out.println("\t\tActivity Cost: " + activityCost);
    }
    private static void printActivityDescription(Document activity) {
//        System.out.println(80);
        String activityDescription = (String) activity.get("description");
        System.out.println("\t\tActivity Description: " + activityDescription);
    }
    private static void printActivityName(Document activity) {
//        System.out.println(85);
        String activityName = (String) activity.get("name");
        System.out.println("\t\tActivity Name: " + activityName);
    }
    private static void printActivityDestination(Document activity, Boolean printDestinationId, Boolean printActivities, Boolean printPassengers, Boolean countNumberOfPassengers) {
//        System.out.println(90);
        Boolean printName = true;
        Integer destinationId = (Integer) activity.get("destinationId");
        Document destination = destinationsCollection.find(new Document("id", destinationId)).first();
        printDestinationName(destination);
//        printDestinationFromId(destinationId, printName, printDestinationId, printActivities, printPassengers, countNumberOfPassengers);
    }
    private static void printActivityPassengers(Document activity, Boolean countNumberOfPassengers, Boolean printPassengerBalance, Boolean printPassengerType) {
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
    private static void printActivityDetails(Document activity, Boolean printActivityName, Boolean printDescription, Boolean printCost, Boolean printCapacity, Boolean printDestination, Boolean printDestinationId, Boolean printPassengers, Boolean countNumberOfPassengers, Boolean printActivities) {
//        System.out.println(105);
        System.out.println("________________________________________________________________________________");
        if (printActivityName) printActivityName(activity);
        if (printDescription) printActivityDescription(activity);
        if (printCost) printActivityCost(activity);
        if (printCapacity) printActivityCapacity(activity);
        if (printDestination) printActivityDestination(activity, printDestinationId, printActivities, printPassengers, countNumberOfPassengers);
        if (printPassengers || countNumberOfPassengers) printActivityPassengers(activity, countNumberOfPassengers, false, false);
    }
    private static void printDestinationName(Document destination) {
//        System.out.println(115);
        String name = (String) destination.get("name");
        System.out.println("\tDestination Name: " + name);
    }
    private static void printDestinationPackageId(Document destination) {
//        System.out.println(120);
        int packageId = (int) destination.get("packageId");
        System.out.println("\tPackage Id: " + packageId);
    }
    private static void printDestinationActivities(Document destination, Boolean printActivities, Boolean printPassengers, Boolean countNumberOfPassengers) {
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

    private static void printDestination (Document destination, Boolean name, Boolean packageId, Boolean printActivities, Boolean printPassengers, Boolean countNumberOfPassengers) {
//        System.out.println(145);
        if (name) printDestinationName(destination);
        if (packageId) printDestinationPackageId(destination);
        if(printActivities || printPassengers || countNumberOfPassengers) printDestinationActivities(destination, printActivities, printPassengers, countNumberOfPassengers);
    }
    private static void printPackageId(Document travelPackage) {
//        System.out.println(151);
        int packageId = (int) travelPackage.get("id");
        System.out.println(packageId);
    }
    private static void printPackageName(Document travelPackage) {
//        System.out.println(156);
        String packageName = (String) travelPackage.get("name");
        System.out.println("Package Name: " + packageName);
    }
    private static void printPackageCapacity(Document travelPackage) {
//        System.out.println(161);
        int packageCapacity = (int) travelPackage.get("capacity");
        System.out.println("Package Capacity: " + packageCapacity);
    }
    private static void printDestinationFromId(int destinationId, Boolean printName, Boolean printPackageId, Boolean printActivities, Boolean printPassengers, Boolean countNumberOfPassengers) {
        Document destination = destinationsCollection.find(new Document("id", destinationId)).first();
        printDestination(destination, printName, false, printActivities, printPassengers, countNumberOfPassengers);
    }
    private static void printPackageItineraries(Document travelPackage, Boolean printDestination, Boolean printPassengers, Boolean countNumberOfPassengers) {
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
    private static void printPackagePassengers(Document travelPackage, Boolean countNumberOfPassengers) {
//        System.out.println(178);
        printPackageItineraries(travelPackage, false, true, countNumberOfPassengers);
    }

    private static void printPackageDetails(Document travelPackage, Boolean printDestination, Boolean printId, Boolean printName, Boolean printItineraries, Boolean printPassengers, Boolean countNumberOfPassengers) {
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
    private static void signupForNewActivity(Integer PassengerNumber, Integer activityId) { // task 0
//        System.out.println(207);
        Document passenger = passengersCollection.find(new Document("passangerNumber", PassengerNumber)).first();
        int passengerBalance = (int) passenger.get("Balance");
        int passengerType = (int) passenger.get("type");
        String passengerName = (String) passenger.get("name");
        List<Integer> activityIds = (List<Integer>) passenger.get("activityIds");


        Document activity = activitiesCollection.find(new Document("id", activityId)).first();
        String activityName = (String) activity.get("name");
        int activityCost = (int) activity.get("cost");
        int activityCapacity = (int) activity.get("capacity");
        int activityCostForCurrentPassenger = getActivityCostForCurrentPassenger(activityCost, passengerType);

//        System.out.println(passenger);
//        System.out.println(activity);
        if (passengerBalance < activityCostForCurrentPassenger) {
            System.out.println( "Your don't have sufficient balance");
            return;
        }
        if (activityIds.contains(activityId)) {
            System.out.println("You have already signed up for this activity");
            return;
        }
        int passengerUpdatedBalance = passengerBalance - activityCostForCurrentPassenger;
        int activityUpdatedCapacity = activityCapacity - 1;
        Bson passengerUpdates = Updates.combine(Updates.set("Balance", passengerUpdatedBalance), Updates.addToSet("activityIds", activityId));
        Bson activityUpdates = Updates.combine(Updates.set("capacity", activityUpdatedCapacity), Updates.addToSet("passengersId", PassengerNumber));
        UpdateOptions options = new UpdateOptions().upsert(true);
        try {
            UpdateResult passengerResult = passengersCollection.updateOne(passenger, passengerUpdates, options);
            UpdateResult activityResult = activitiesCollection.updateOne(activity, activityUpdates, options);
//            System.out.println(passengerResult);
//            System.out.println(activityResult);
            System.out.println( "Your current balance: " + passengerBalance);
            System.out.println( "Your cost for this activity: " + activityCostForCurrentPassenger);
            System.out.println("You have successfully signed up for " + activityName);
            System.out.println( "Balance left in your account: " + passengerBalance);
        } catch (MongoException error) {
            System.err.println("Unable to update due to an error: " + error);
        }
    }
    private static void printItinerary(int travelPackageId) { // task 1
//        System.out.println(243);
        try {
            Document travelPackage = packagesCollection.find(new Document("id", travelPackageId)).first();
            printPackageDetails(travelPackage, true, false, true,true, false, false);
        } catch (MongoException error) {
            System.err.println("Unable to print due to an error: " + error);
        }
    }
    private  static void passengerDetails(int passangerId) { // task 2
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
    private static void printTotalPassengers() {
        System.out.println("Total number of passangers signed up in this package: " + totalPassengers);
        totalPassengers = 0;
    }
    private static void printPassengerDetailsOfPackage(int ItineraryId) { //    task 3
//        System.out.println(282);
        Document currentPackage = packagesCollection.find(new Document("id", ItineraryId)).first();
        printPackageDetails(currentPackage, false, false, true, false, true,true);
        printTotalPassengers();
    }

    private static void printAvailableActivity() { // task 4.
//        System.out.println(288);
        getAllDocuments(activitiesCollection);
    }

    public static void main(String[] args) {
        Destination d = new Destination();
//        d.getName();
//        d.getPackageId();
        int travelPackageId = 0, passengerId = 3, packageId = 3;

        signupForNewActivity(2, 0); // task 0 done
//        printItinerary(travelPackageId); // task 1 done
//        printPassengerDetailsOfPackage(packageId); // task 2 done
        passengerDetails(passengerId); // task 3 done
//        printAvailableActivity(); // task 4 done
    }
}
