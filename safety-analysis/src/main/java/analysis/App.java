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

import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        // Connect to a single MongoDB instance
        MongoClient mongoClient = MongoClients.create(); //default, runs on localhost on port 27017
        //Access a database of specified name
        MongoDatabase database = mongoClient.getDatabase("CrimeArrestLA");
        //Access a collection
        MongoCollection<Document> collectionC = database.getCollection("Crime"); //MongoCollection instances are immutable
        MongoCollection<Document> collectionA = database.getCollection("Arrest");

        QueryProcessor qp = new QueryProcessor(collectionC, collectionA);

        UserInterface ui = new UserInterface(qp);

    }
}
