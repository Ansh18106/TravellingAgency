package com.mongodb.quickstart;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.List;

public class main {
    public static void signupForNewActivity(int passengerNumber, int activityId) {
        Passenger passengerObj = new Passenger();
        Document passenger = passengerObj.getPassenger(passengerNumber);
        int passengerTypeId = passengerObj.getPassengerTypeId(passenger);
        int passengerBalance = passengerObj.getPassengerBalance(passenger);
        String passengerName = passengerObj.getPassengerName(passenger);
        List<Integer> activityIds = passengerObj.getPassengerActivityIds(passenger);
        if (activityIds.contains(activityId)) {
            System.out.println("You have already signed up for this activity.");
            return;
        }

        Activity activityObj = new Activity();
        Document activity = activityObj.getActivity(activityId);
        String activityName = activityObj.getActivityName(activity);
        // actual cost of user paying for activity they seelct for package
        int activityCost = activityObj.getActivityCost(activity);
        int seatsFilled = (activityObj.getActivityPassengersIds(activity)).size();
        int activityCapacity = activityObj.getActivityCapacity(activity);
        //
        int activityRemainingCapacity = activityCapacity - seatsFilled;
        if (activityRemainingCapacity == 0) {
            System.out.println("Sorry! This activity has reached its capacity");
            return;
        }

        PassengerType passengerTypeObj = new PassengerType();
        Document passengerType = passengerTypeObj.getPassengerType(passengerTypeId);
        String passengerSubscription = passengerTypeObj.getSubscriptionType(passengerType);
        int discountPercent = passengerTypeObj.getDiscountPercent(passengerType);


        Utils utilsObj = new Utils();
        int activityCostForCurrentPassenger = utilsObj.getActivityCostForCurrentPassenger(activityCost, discountPercent);

        if (passengerBalance < activityCostForCurrentPassenger) {
            //throw err with correct message
            System.out.println("Your don't have sufficient balance for this activity.");
            return;
        }

        int passengerUpdatedBalance = passengerBalance - activityCostForCurrentPassenger;
        try {
            passengerObj.setPassengerBalance(passengerObj.getPassenger(passengerNumber), passengerUpdatedBalance);
            passengerObj.setActivityIds(passengerObj.getPassenger(passengerNumber), activityId);
            activityObj.setPassengerIds(activity, passengerNumber);
            System.out.println("Name: " + passengerName);
            System.out.println("Current balance: " + passengerBalance);
            System.out.println("Subscription type: " + passengerSubscription);
            System.out.println("Amount have to be paid by you: " + activityCostForCurrentPassenger);
            System.out.println("You have successfully signed up for " + activityName);
            System.out.println("Balance left in your account: " + passengerUpdatedBalance);
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
        for (Integer destinationId : destinationIds) {
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
        for (Integer destinationId : destinationIds) {
            Document destination = destinationObj.getDestination(destinationId);
            List<Integer> activityIds = destinationObj.getDestinationActivityIds(destination);
            for (Integer activityId : activityIds) {
                Document activity = activityObj.getActivity(activityId);
                passengerCapacity += activityObj.getActivityCapacity(activity);
                numberOfPassengersEnrolled += (activityObj.getActivityPassengersIds(activity)).size();
            }
        }
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Number of Passengers Enrolled: " + numberOfPassengersEnrolled);
        System.out.println("\nName\tNumber");
        for (Integer destinationId : destinationIds) {
            Document destination = destinationObj.getDestination(destinationId);
            List<Integer> activityIds = destinationObj.getDestinationActivityIds(destination);
            for (Integer activityId : activityIds) {
                Document activity = activityObj.getActivity(activityId);
                List<Integer> passengerIds = activityObj.getActivityPassengersIds(activity);
                for (Integer passengerId : passengerIds) {
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
        PassengerType passengerTypeObj = new PassengerType();
        Utils utilObj = new Utils();
        Document passenger = passengerObj.getPassenger(passengerId);
        String passengerName = passengerObj.getPassengerName(passenger);
        int passengerTypeId = passengerObj.getPassengerTypeId(passenger);
        Document passengerType = passengerTypeObj.getPassengerType(passengerTypeId);
        String passengerSubscriptionType = passengerTypeObj.getSubscriptionType(passengerType);
        int discountPercent = passengerTypeObj.getDiscountPercent(passengerType);
        passengerObj.printPassengerName(passenger);
        passengerObj.printPassengerNumber(passenger);
        passengerObj.printPassengerBalance(passenger);
        List<Integer> activityIds = passengerObj.getPassengerActivityIds(passenger);
        for (Integer activityId : activityIds) {
            System.out.println("________________________________________________________________________________________");
            Document currentActivity = activityObj.getActivity(activityId);
            int activityDestinationId = activityObj.getActivityDestinationId(currentActivity);
            Document activityDestination = destinationObj.getDestination(activityDestinationId);
            String activityName = activityObj.getActivityName(currentActivity);
            int activityCost = activityObj.getActivityCost(currentActivity);
            String activityDestinationName = destinationObj.getDestinationName(activityDestination);
            System.out.println(activityName + " is available on " + activityDestinationName);
            System.out.println(passengerName + " is a " + passengerSubscriptionType + " subscriber. They have paid " + utilObj.getActivityCostForCurrentPassenger(activityCost, discountPercent) + " for " + activityName);
        }
    }

    public static void printAvailableActivity() { // task 4 done
        Utils allActivitiesObj = new Utils();
        Activity activityObj = new Activity();
        MongoCursor<Document> cursor = allActivitiesObj.getAllDocuments(activityObj.getActivitiesCollection());
        try {
            while (cursor.hasNext()) {
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
        int travelPackageId = 0, passengerId = 74, packageId = 0;
        signupForNewActivity(0, 1); // task 0 done
        printItinerary(travelPackageId); // task 1 done
        printPassengerDetailsOfPackage(packageId); // task 2 done
        passengerDetails(passengerId); // task 3 done
        printAvailableActivity(); // task 4 done
    }
}
