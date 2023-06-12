package com.mongodb.quickstart;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.List;
import java.util.Scanner;

public class main {
    //    This method will enroll the passenger with passenger number = passengerNumber into activity with activity id = activityId
    private static void signupForNewActivity(int passengerNumber, int activityId) {
        Utils utilsObj = new Utils();
        if (!utilsObj.checkDocumentExist(passengerNumber, "passenger")) {
            System.out.println("Sorry passenger with this id does not exist !\nTry with in range 0 - 9999");
            return;
        }
        if (!utilsObj.checkDocumentExist(activityId, "activity")) {
            System.out.println("Sorry this activity is not available!\nTry with in range 0 - 999");
            return;
        }

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
        // actual cost of user paying for activity they select for package
        int activityCost = activityObj.getActivityCost(activity);
        int activityRemainingCapacity = activityObj.getActivityRemainingCapacity(activity);
        if (activityRemainingCapacity == 0) {
            System.out.println("Sorry! This activity has reached its maximum capacity");
            return;
        }

        PassengerType passengerTypeObj = new PassengerType();
        Document passengerType = passengerTypeObj.getPassengerType(passengerTypeId);
        String passengerSubscription = passengerTypeObj.getSubscriptionType(passengerType);
        int discountPercent = passengerTypeObj.getDiscountPercent(passengerType);

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
        } catch (MongoException error) {
            System.err.println("Unable to update due to an error: " + error);
        }
        System.out.println("Name: " + passengerName);
        System.out.println("Current balance: " + passengerBalance);
        System.out.println("Subscription type: " + passengerSubscription);
        System.out.println("Amount have to be paid by you: " + activityCostForCurrentPassenger);
        System.out.println("You have successfully signed up for " + activityName);
        System.out.println("Balance left in your account: " + passengerUpdatedBalance);
    }

    // This method will print the itinerary of the travel package
    // return
    //    Travel package name
    //    All the destination in the itinerary
    // details activities available on the destination
    private static void printItinerary(int travelPackageId) {
        Utils utilsObj = new Utils();
        if (!utilsObj.checkDocumentExist(travelPackageId, "package")) {
            System.out.println("Sorry! Travel package does not exist with this id\nTry with in range 0 - 9");
            return;
        }
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

    //    This method will print name of the package of id = packageId
    //    This method will print the capacity of the package
    //    Number of the passengers enrolled in this package
    //    Name and Number of all the enrolled passengers
    private static void printPassengerDetailsOfPackage(int packageId) {
        Utils utilsObj = new Utils();
        if (!utilsObj.checkDocumentExist(packageId, "package")) {
            System.out.println("Sorry! Travel package does not exist with this id\nTry with in range 0 - 9");
            return;
        }
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

    //This will print all the details of the passenger with number = passengerId
    // name of the passenger
    //    number of the passenger
    //    balance
    //    list of each activity they have enrolled in, including the destination the at which the activity is taking place and the price the passenger paid for the activity.
    private static void passengerDetails(int passengerId) {
        Utils utilsObj = new Utils();
        if (!utilsObj.checkDocumentExist(passengerId, "passenger")) {
            System.out.println("Sorry passenger with this id does not exist !\nTry with in range 0 - 9999");
            return;
        }
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

    // Print the details of all the activities that still have spaces available, including how many spaces are available.
    private static void printAvailableActivity() { // task 4 done
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
                    activityObj.printActivityRemainingCapacity(currentActivity);
                    activityObj.printActivityCost(currentActivity);
                }
            }
        } finally {
            cursor.close();
        }
    }

    public static void main(String[] args) {
        System.out.print("__________________________________________________________________________________________________________________________\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your unique passenger number: ");
        int passengerNumber = scanner.nextInt();
        System.out.print("Enter the activity Id you want to sign up for: ");
        int activityId = scanner.nextInt();
        signupForNewActivity(passengerNumber, activityId); // task 0

        System.out.print("__________________________________________________________________________________________________________________________\n");
        System.out.print("Enter the Id of travel package for which you want to check the itinerary: ");
        int travelPackageId = scanner.nextInt();
        printItinerary(travelPackageId); // task 1

        System.out.print("__________________________________________________________________________________________________________________________\n");
        System.out.print("Enter the Id of travel package for which you want to check the passenger details: ");
        int packageId = scanner.nextInt();
        printPassengerDetailsOfPackage(packageId); // task 2

        System.out.print("__________________________________________________________________________________________________________________________\n");
        System.out.print("Enter your unique passenger number: ");
        passengerDetails(passengerNumber); // task 3

        System.out.print("__________________________________________________________________________________________________________________________\n");
        System.out.print("Press enter to check the available activities: \n");
        scanner.nextLine();
        printAvailableActivity(); // task 4
        scanner.close();
    }
}
