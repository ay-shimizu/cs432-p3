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

import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Projections.*;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Sorts.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

public class DemoInfoQuery implements Query{
  private MongoCollection<Document> collection;

  public DemoInfoQuery(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public Result process(String input, String option){
    // String result = "";
    Result result = new Result();

    if(!Helper.isValidYear(input)){
      result.add("ERROR: Invalid input. Please input in a year between 2010 and 2019");
      // System.out.println("ERROR HERE");
      return result;
    }

    Document regQuery = new Document();
      regQuery.append("$regex", input);
      regQuery.append("$options", "i");

      AggregateIterable<Document> output = collection.aggregate(
          Arrays.asList(
                 Aggregates.match(Filters.eq(option, regQuery)),
                 Aggregates.project(fields(include(""), excludeId())),
                 Aggregates.group("$Sex Code", Accumulators.sum("count", 1)),
                 Aggregates.sort(orderBy(descending("count")))
          )
      );

        result.add("Search by " + option + " ("+ input + ") : \n");
        int i = 1;
        for(Document d : output){
           //System.out.println(d.toJson());
           result.add(d.get(i) + ") " + "Sex: " + d.get("_id") + "): " + d.get("count") + " crime reports\n");
          i++;
        }
       return result;
    }

    public String toString(){
      return "";
    }
}
