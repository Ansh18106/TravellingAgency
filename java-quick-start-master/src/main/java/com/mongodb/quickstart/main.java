package com.mongodb.quickstart;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import sun.security.krb5.internal.crypto.Des;

import javax.print.Doc;
import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;
public class main {
    public static void signupForNewActivity(int passengerNumber, int activityId) {
        Passenger passengerObj = new Passenger();
        Document passenger = passengerObj.getPassenger(passengerNumber);
        int passengerBalance = passengerObj.getPassengerBalance(passenger);
        int passengerType = passengerObj.getPassengerType(passenger);
        String passengerName = passengerObj.getPassengerName(passenger);
        List<Integer> activityIds = passengerObj.getPassengerActivityIds(passenger);


        Activity activityObj = new Activity();
        Document activity = activityObj.getActivity(activityId);
        String activityName = activityObj.getActivityName(activity);
        int activityCost = activityObj.getActivityCost(activity);
        int activityCapacity = activityObj.getActivityCapacity(activity);

        Utils utilsObj = new Utils();
        String passengerSubscription = utilsObj.getPassengerType(passengerType);
        int activityCostForCurrentPassenger = utilsObj.getActivityCostForCurrentPassenger(activityCost, passengerType);

        if (passengerBalance < activityCostForCurrentPassenger) {
            System.out.println( "Your don't have sufficient balance for this activity.");
            return;
        }
        if (activityIds.contains(activityId)) {
            System.out.println("You have already signed up for this activity.");
            return;
        }
        int passengerUpdatedBalance = passengerBalance - activityCostForCurrentPassenger;
        int activityUpdatedCapacity = activityCapacity - 1;
        Bson passengerUpdates = Updates.combine(Updates.set("Balance", passengerUpdatedBalance), Updates.addToSet("activityIds", activityId));
        Bson activityUpdates = Updates.combine(Updates.set("capacity", activityUpdatedCapacity), Updates.addToSet("passengersId", passengerNumber));
        UpdateOptions options = new UpdateOptions().upsert(true);
        try {
            MongoCollection<Document> passengersCollection = passengerObj.getPassengersCollection();
            MongoCollection<Document> activitiesCollection = activityObj.getActivitiesCollection();
            UpdateResult passengerResult = passengersCollection.updateOne(passenger, passengerUpdates, options);
            UpdateResult activityResult = activitiesCollection.updateOne(activity, activityUpdates, options);
            System.out.println( "Name: " + passengerName);
            System.out.println( "Current balance: " + passengerBalance);
            System.out.println( "Subscription type: " + passengerSubscription);
            System.out.println("Amount have to be paid by you: " + activityCostForCurrentPassenger);
            System.out.println("You have successfully signed up for " + activityName);
            System.out.println( "Balance left in your account: " + passengerUpdatedBalance);
        } catch (MongoException error) {
            System.err.println("Unable to update due to an error: " + error);
        }
    }
    public static void printItinerary(int travelPackageId) {
        Package packageObj = new Package();
        Destination destinationObj = new Destination();
        Document travelPackage = packageObj.getTravelPackage(travelPackageId);
        packageObj.printPackageName(travelPackage);
        List<Integer> destinationIds = packageObj.getPackageDestinationIds(travelPackage);
        for (Integer destinationId: destinationIds) {
            System.out.println("________________________________________________________________________________________");
            Document destination = destinationObj.getDestination(destinationId);
            destinationObj.printDestinationName(destination);
            destinationObj.printDestinationActivities(destination);
        }
    }
    public static void printPassengerDetailsOfPackage(int packageId) {
        Package packageObj = new Package();
        Destination destinationObj = new Destination();
        Activity activityObj = new Activity();
        Passenger passengerObj = new Passenger();
        Document travelPackage = packageObj.getTravelPackage(packageId);
        packageObj.printPackageName(travelPackage);
        int passengerCapacity = 0, numberOfPassengersEnrolled = 0;
        List<Integer> destinationIds = packageObj.getPackageDestinationIds(travelPackage);
        for (Integer destinationId: destinationIds) {
            Document destination = destinationObj.getDestination(destinationId);
            List<Integer> activityIds = destinationObj.getDestinationActivityIds(destination);
            for (Integer activityId: activityIds) {
                Document activity = activityObj.getActivity(activityId);
                passengerCapacity += activityObj.getActivityCapacity(activity);
                numberOfPassengersEnrolled += (activityObj.getActivityPassengersIds(activity)).size();
            }
        }
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Number of Passengers Enrolled: " + numberOfPassengersEnrolled);
        System.out.println("\nName\tNumber");
        for (Integer destinationId: destinationIds) {
            Document destination = destinationObj.getDestination(destinationId);
            List<Integer> activityIds = destinationObj.getDestinationActivityIds(destination);
            for (Integer activityId: activityIds) {
                Document activity = activityObj.getActivity(activityId);
                List<Integer> passengerIds = activityObj.getActivityPassengersIds(activity);
                for (Integer passengerId: passengerIds) {
                    Document passenger = passengerObj.getPassenger(passengerId);
                    String passengerName = passengerObj.getPassengerName(passenger);
                    int passengerNumber = passengerObj.getPassengerNumber(passenger);
                    System.out.println(passengerName + " " + passengerNumber);
                }
            }
        }
    }

    public static void passengerDetails(int passengerId) {
        Passenger passengerObj = new Passenger();
        Activity activityObj = new Activity();
        Destination destinationObj = new Destination();
        Utils util = new Utils();
        Document passenger = passengerObj.getPassenger(passengerId);
        String passengerName = passengerObj.getPassengerName(passenger);
        int passengerType = passengerObj.getPassengerType(passenger);
        passengerObj.printPassengerName(passenger);
        passengerObj.printPassengerNumber(passenger);
        passengerObj.printPassengerBalance(passenger);
        List<Integer> activityIds =  passengerObj.getPassengerActivityIds(passenger);
        for (Integer activityId: activityIds) {
            System.out.println("________________________________________________________________________________________");
            Document currentActivity =  activityObj.getActivity(activityId);
            int activityDestinationId = activityObj.getActivityDestinationId(currentActivity);
            Document activityDestination =  destinationObj.getDestination(activityDestinationId);
            String activityName = activityObj.getActivityName(currentActivity);
            int activityCost = activityObj.getActivityCost(currentActivity);
            String activityDestinationName = destinationObj.getDestinationName(activityDestination);
            System.out.println(activityName + " is available on " + activityDestinationName);
            System.out.println(passengerName + " is a " + util.getPassengerType(passengerType) + " subscriber. They have paid " +  util.getActivityCostForCurrentPassenger(activityCost, passengerType)+ " for " + activityName);
        }
    }

    public static void printAvailableActivity() { // task 4 done
        Utils allActivitiesObj = new Utils();
        Activity activityObj = new Activity();
        MongoCursor<Document> cursor = allActivitiesObj.getAllDocuments(activityObj.getActivitiesCollection());
        try {
            while(cursor.hasNext()) {
                Document currentActivity = cursor.next();
                if ((int) currentActivity.get("capacity") > 0) {
                    System.out.println("________________________________________________________________________________");
                    activityObj.printActivityName(currentActivity);
                    activityObj.printActivityDescription(currentActivity);
                    activityObj.printActivityCapacity(currentActivity);
                    activityObj.printActivityCost(currentActivity);
                }
            }
        } finally {
            cursor.close();
        }
    }

    public static void main(String[] args) {
        int travelPackageId = 0, passengerId = 3, packageId = 0;
            signupForNewActivity(836, 81); // task 0 done
//            printItinerary(travelPackageId); // task 1 done
//            printPassengerDetailsOfPackage(packageId); // task 2 done
//            passengerDetails(passengerId); // task 3 done
//            printAvailableActivity(); // task 4 done
    }
}
