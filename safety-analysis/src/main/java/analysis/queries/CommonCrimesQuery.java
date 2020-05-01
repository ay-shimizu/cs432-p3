package analysis.queries;

import analysis.util.Helper;
import analysis.util.Result;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.AggregateIterable;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Accumulators;
import org.bson.Document;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class CommonCrimesQuery implements Query{
  private MongoCollection<Document> collection;

  public CommonCrimesQuery(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public Result process(String input, String option){
    // String result = "";
    Result result = new Result();
    int flag = check(input);
    AggregateIterable<Document> output;
    if(flag == 0){
          Document regQuery = new Document();
          regQuery.append("$regex", input);
          regQuery.append("$options", "i");
          output = collection.aggregate(
              Arrays.asList(
                      Aggregates.match(Filters.eq(option, regQuery)),
                      Aggregates.project(fields(include("Crime Code", "Crime Code Description"), excludeId())),
                      Aggregates.group("$Crime Code", Accumulators.sum("count", 1),
                      Accumulators.first("Crime Code Description", "$Crime Code Description")),
                      Aggregates.sort(orderBy(descending("count"))),
                      Aggregates.limit(10)
              )
            );
    }else if(flag == 1){
          int in = Integer.parseInt(input);
          output = collection.aggregate(
              Arrays.asList(
                      Aggregates.match(Filters.eq(option, in)),
                      Aggregates.project(fields(include("Crime Code", "Crime Code Description"), excludeId())),
                      Aggregates.group("$Crime Code", Accumulators.sum("count", 1),
                      Accumulators.first("Crime Code Description", "$Crime Code Description")),
                      Aggregates.sort(orderBy(descending("count"))),
                      Aggregates.limit(10)
              )
            );
    }else{
      result.add("ERROR: Invalid input. Please input in a year between 2010 and 2019 or area code between 1 and 50");
      // System.out.println("ERROR HERE");
      return result;
    }
      result.add("Search by " + option + " (" + input + ") TOP 10 LIST: \n");
      int i = 1;
      for(Document d : output){
           //System.out.println(d.toJson());
          result.add(i + ") " + d.get("Crime Code Description") + " (Crime Code." + d.get("_id") + "): \n" + d.get("count") + " crime reports\n");
          i++;
      }
      return result;
   }
   public int check(String input){
     if(Helper.isValidYear(input)){
       return 0;
     }else if(Helper.isValidArea(input)){
       return 1;
     }else{
       return -1;
     }
   }


     public String toString(){
       return "";
     }

}
