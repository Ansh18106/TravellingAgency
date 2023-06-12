package com.mongodb.quickstart;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;

public class Package {

    //    This method will return the package collection
    public static MongoCollection<Document> getTravelPackageCollection() {
        Connection conn = new Connection();
        return conn.getPackagesCollection();
    }

    //This method will return the package document with package id = packageId
    public static Document getTravelPackage(int packageId) {
        MongoCollection<Document> packagesCollection = getTravelPackageCollection();
        return packagesCollection.find(new Document("id", packageId)).first();
    }

    //This method will return the package name
    public static String getPackageName(Document travelPackage) {
        return (String) travelPackage.get("name");
    }

    //This method will return all the destination ids which will be cover by this travel package
    public static List<Integer> getPackageDestinationIds(Document travelPackage) {
        return (List<Integer>) travelPackage.get("destinationIds");
    }

    //    This method will print the package name of the travel package
    public static void printPackageName(Document travelPackage) {
        System.out.println("Package Name: " + getPackageName(travelPackage));
    }

    //    This method will print all the details of the destinations which will be cover by this travel package
    public static void printAllPackageDestinations(Document travelPackage) {
        List<Integer> destinationIds = getPackageDestinationIds(travelPackage);
        for (Integer destinationId : destinationIds) {
            // call print destination
        }
    }
}
