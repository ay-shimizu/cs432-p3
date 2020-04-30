package analysis.queries;
import analysis.util.Helper;
import analysis.util.Result;

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

public class NumCrimesQuery implements Query{
  private MongoCollection<Document> collection;

  public NumCrimesQuery(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public Result process(String input){
    // String result = "";
    Result result = new Result();

    if(!Helper.isValidYear(input)){
      result.add("ERROR: Invalid input. Please input in a year between 2010 and 2019");
      // System.out.println("ERROR HERE");
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
    result.add("The total number of crime reported in " + input + " is: " + count);
    return result;
  }

  public String toString(){
    return "";
  }
}
