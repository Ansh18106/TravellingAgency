package com.mongodb.quickstart;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

public class Package {
    public static MongoCollection<Document> getTravelPackageCollection() {
        Connection conn = new Connection();
        return conn.getPackagesCollection();
    }
    public static Document getTravelPackage(int packageId) {
        MongoCollection<Document> packagesCollection = getTravelPackageCollection();
        return packagesCollection.find(new Document("id", packageId)).first();
    }
    public static String getPackageName(Document travelPackage) {
        return (String) travelPackage.get("name");
    }
    public static List<Integer> getPackageDestinationIds(Document travelPackage) {
        return (List<Integer>) travelPackage.get("destinationIds");
    }

    public static void printPackageName(Document travelPackage) {
        System.out.println("Package Name: " + getPackageName(travelPackage));
    }

    public static void printAllPackageDestinations(Document travelPackage) {
        List<Integer> destinationIds = getPackageDestinationIds(travelPackage);
        for (Integer destinationId: destinationIds) {
            // call print destination
        }
    }
    public void main(int packageId) {
        Document travelPackage = getTravelPackage(packageId);
    }
}
