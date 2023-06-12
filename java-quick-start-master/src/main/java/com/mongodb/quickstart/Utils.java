package com.mongodb.quickstart;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;


public class Utils {

    public static Boolean checkDocumentExist(int id, String documentType) {
        switch (documentType) {
            case "package":
                return id >= 0 && id < 10;
            case "destination":
                return id >= 0 && id < 100;
            case "activity":
                return id >= 0 && id < 1000;
            case "passenger":
                return id >= 0 && id < 10000;
            default:
                return false;
        }
    }

    //    This method will return all the documents in a given collection
    public static MongoCursor<Document> getAllDocuments(MongoCollection<Document> collection) {
        FindIterable<Document> allDocuments = collection.find();
        return allDocuments.iterator();
    }

    //This method will return the cost for the passenger according to their subscription
    public static int getActivityCostForCurrentPassenger(int activityCost, int discountPercent) {
        return (int) (activityCost * (100 - discountPercent) * .01);
    }

    //This method will update the "doc" of the given collection "coll" according to the "query"
    public static void updateCollection(MongoCollection<Document> coll, Document doc, Bson query) {
        UpdateResult result = coll.updateOne(doc, query, new UpdateOptions().upsert(false));
    }
}
