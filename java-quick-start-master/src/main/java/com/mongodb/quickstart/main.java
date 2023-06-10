package com.mongodb.quickstart;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

public class main {
    public static void printItinerary(int travelPackageId) {
        try {
//            Document travelPackage = packagesCollection.find(new Document("id", travelPackageId)).first();
//            printPackageDetails(travelPackageId, true, false, true,true, false, false);
        } catch (MongoException error) {
            System.err.println("Unable to print due to an error: " + error);
        }
    }

    public static void printAvailableActivity() {
        Utils allActivities = new Utils();
        Activity activity = new Activity();
        MongoCursor<Document> cursor = allActivities.getAllDocuments(activity.getActivitiesCollection());
        try {
            while(cursor.hasNext()) {
                Document currentActivity = cursor.next();
                if ((int) currentActivity.get("capacity") > 0) {
                    activity.printActivityName(currentActivity);
                    activity.printActivityDescription(currentActivity);
                    activity.printActivityCapacity(currentActivity);
                    activity.printActivityCost(currentActivity);
                }
            }
        } finally {
            cursor.close();
        }
    }
    public static void main() {
        int travelPackageId = 0;
//            signupForNewActivity(2, 0); // task 0 done
//
//            printItinerary(travelPackageId); // task 1 done
//        printPassengerDetailsOfPackage(packageId); // task 2 done
//        passengerDetails(passengerId); // task 3 done
        printAvailableActivity(); // task 4 done
    }
}
