package analysis;
import analysis.window.UserInterface;
import analysis.queries.QueryProcessor;
import analysis.queries.QNames;

//ref: http://mongodb.github.io/mongo-java-driver/4.0/driver/getting-started/quick-start/
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Sorts.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class App
{
    public static void numCrimes(MongoClient mongoClient, MongoDatabase database, MongoCollection<Document> collection, String userInput){

      Document regQuery = new Document();
        regQuery.append("$regex", userInput);
        regQuery.append("$options", "i");

      Document findQuery = new Document();
        findQuery.append("Date Occurred", regQuery);

      int count =  (int) collection.countDocuments(findQuery);
      System.out.println("COUNT: " + count);
    }

    public static void numCrimeByArea(MongoClient mongoClient, MongoDatabase database, MongoCollection<Document> collection, String userInput){

      //ref: https://stackoverflow.com/questions/31643109/mongodb-aggregation-with-java-driver
      //ref: https://mongodb.github.io/mongo-java-driver/3.6/driver/tutorials/aggregation/
      AggregateIterable<Document> output = collection.aggregate(
          Arrays.asList(
                  Aggregates.project(fields(include("Area ID", "Area Name"), excludeId())),
                  Aggregates.group("$Area ID", Accumulators.sum("count", 1), Accumulators.first("Area Name", "$Area Name")),
                  Aggregates.sort(orderBy(descending("count")))
          )
        );

        int i = 1;
        for(Document d : output){
          // System.out.println(d.toJson());
          System.out.println(i + ")" + d.get("Area Name") + " (id." + d.get("_id") + "): " + d.get("count") + " crime reports\n");
          i++;
        }

    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        //Do I need to do this: https://stackoverflow.com/questions/29454916/how-to-prevent-logging-on-console-when-connected-to-mongodb-from-java
        //disable logging??

        // Connect to a single MongoDB instance
        MongoClient mongoClient = MongoClients.create(); //default, runs on localhost on port 27017
        //Access a database of specified name
        MongoDatabase database = mongoClient.getDatabase("CrimeArrestLA");
        //Access a collection
        MongoCollection<Document> collectionC = database.getCollection("Crime");
        //MongoCollection instances are immutable
        MongoCollection<Document> collectionA = database.getCollection("Arrest");

        QueryProcessor qp = new QueryProcessor(mongoClient, database, collectionC);

        UserInterface ui = new UserInterface(qp);
        // q.process(QNames.NUM_CRIMES);

        // // numCrimes(mongoClient, database, collection, "2010.");
        // numCrimeByArea(mongoClient, database, collection, "");


            // System.out.println(myDoc.toJson() + "\n");
        // Document query = new Document("_id", new Document("$lt", 100));
        // long count = collection.count()

        // mostCommonTypeOfCrime(collectionC, "Date Occurred", "2010");
        // mostCommonTypeOfCrime(collectionC, "Area Name", "Newton");
        // demographicInfo(collectionA, "Date Occurred", "2010");
        // demographicInfo(collectionA, "Area Name", "Newton");
    }
}
