package com.mongodb.quickstart;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;


public class Utils {
    public static MongoCursor<Document> getAllDocuments(MongoCollection<Document> collection) {
        FindIterable<Document> allDocuments = collection.find();
        return allDocuments.iterator();
    }

    public static int getActivityCostForCurrentPassenger(int activityCost, int discountPercent) {
        return (int) (activityCost * (100 - discountPercent) * .01);
    }

    public static void updateCollection(MongoCollection<Document> coll, Document doc, Bson query) {
        System.out.println(doc + "updated" + query);
        UpdateResult result = coll.updateOne(doc, query, new UpdateOptions().upsert(false));
        System.out.println(result);
    }
}
