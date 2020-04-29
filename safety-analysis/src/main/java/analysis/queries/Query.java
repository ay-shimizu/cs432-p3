package analysis.queries;

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

public class Query{
  private MongoClient mongoClient;
  private MongoDatabase database;
  private MongoCollection<Document> collection;

  public Query(MongoClient mongoClientIn, MongoDatabase databaseIn, MongoCollection<Document> collectionIn){
    mongoClient = mongoClientIn;
    database = databaseIn;
    collection = collectionIn;
  }

  public void setCollection(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public String process(QNames type, String input){
    String result = "";

    switch(type){
      case NUM_CRIMES:
        //query 1
        result = numCrimes(input);
        break;
      case HIGHEST_CRIMES:
        result = highestCrimes(input);
        break;
      default:
        System.out.println("DEFAULT");
    }

    return result;
  }

  public boolean validYear(String input){
    try{
      int year = Integer.parseInt(input);

      if(year < 2010 || year > 2019){
        System.out.println("ERROR: Invalid year inputted.");
        return false;
      }
    }catch(Exception e){
      System.err.println("Exception caught... \n" + e);
      return false;
    }

    return true;
  }

  public String highestCrimes(String input){
    String result = "";

    if(!validYear(input)){
      result = "ERROR: Invalid input. Please input in a year between 2010 and 2019";
      return result;
    }

    //ref: https://stackoverflow.com/questions/31643109/mongodb-aggregation-with-java-driver
    //ref: https://mongodb.github.io/mongo-java-driver/3.6/driver/tutorials/aggregation/
    AggregateIterable<Document> output = collection.aggregate(
        Arrays.asList(
                Aggregates.project(fields(include("Area ID", "Area Name"), excludeId())),
                Aggregates.group("$Area ID", Accumulators.sum("count", 1), Accumulators.first("Area Name", "$Area Name")),
                Aggregates.sort(orderBy(descending("count")))
        )
      );

      result = "<html>";
      int i = 1;
      for(Document d : output){
        // System.out.println(d.toJson());
        //oof, inefficient
        result = result + i + ")" + d.get("Area Name") + " (id." + d.get("_id") + "): " + d.get("count") + " crime reports <br><br>";
        i++;
      }

      return result + " </html>";
  }

  public String numCrimes(String input){
    String result = "";

    if(!validYear(input)){
      result = "ERROR: Invalid input. Please input in a year between 2010 and 2019";
      return result;
    }

    String regString = input + ".";

    Document regQuery = new Document();
      regQuery.append("$regex", regString);
      regQuery.append("$options", "i");

    Document findQuery = new Document();
      findQuery.append("Date Occurred", regQuery);

    int count =  (int) collection.countDocuments(findQuery);

    // System.out.println("COUNT: " + count);
    result = "The total number of crime reported in " + input + " is: " + count;
    return result;
  }
}
