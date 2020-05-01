package analysis;
import analysis.window.UserInterface;
import analysis.queries.QueryProcessor;
import analysis.queries.*;

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

        QueryProcessor qp = new QueryProcessor(collectionC, collectionA);

        UserInterface ui = new UserInterface(qp);


        // Query q = new SafetyValFutureQuery(collectionC);
        // q.process("14");
        // q.process(QNames.NUM_CRIMES);

        // // numCrimes(mongoClient, database, collection, "2010.");
        // numCrimeByArea(mongoClient, database, collection, "");


            // System.out.println(myDoc.toJson() + "\n");
        // Document query = new Document("_id", new Document("$lt", 100));
        // long count = collection.count()

    }
}
