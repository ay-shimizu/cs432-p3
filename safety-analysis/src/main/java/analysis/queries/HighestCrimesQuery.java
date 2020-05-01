package analysis.queries;
import analysis.util.Helper;
import analysis.util.Result;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.AggregateIterable;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Projections.*;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Sorts.*;
import com.mongodb.client.model.Accumulators;


public class HighestCrimesQuery implements Query{
  private MongoCollection<Document> collection;

  public HighestCrimesQuery(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public Result process(String input){
      Result result = new Result();

      if(!input.equals("") && !Helper.isValidYear(input)){
        result.add("ERROR: Invalid input. Please input in a year between 2010 and 2019");
        return result;
      }

      //ref: https://stackoverflow.com/questions/31643109/mongodb-aggregation-with-java-driver
      //ref: https://mongodb.github.io/mongo-java-driver/3.6/driver/tutorials/aggregation/

      AggregateIterable<Document> output = null;
      if(input.equals("")){
        //info from 2010-2019
         output = collection.aggregate(
            Arrays.asList(
                    Aggregates.project(fields(include("Area ID", "Area Name"), excludeId())),
                    Aggregates.group("$Area ID", Accumulators.sum("count", 1), Accumulators.first("Area Name", "$Area Name")),
                    Aggregates.sort(orderBy(descending("count")))
            )
          );
      }else{
        //info from input year
        output = collection.aggregate(
            Arrays.asList(
                    Aggregates.match(Filters.regex("Date Occurred", input+".")),
                    Aggregates.project(fields(include("Area ID", "Area Name"), excludeId())),
                    Aggregates.group("$Area ID", Accumulators.sum("count", 1), Accumulators.first("Area Name", "$Area Name")),
                    Aggregates.sort(orderBy(descending("count")))
            )
          );

      }

      int i = 1;
      for(Document d : output){
        // System.out.println(d.toJson());
        String s = i + ")" + d.get("Area Name") + " (id." + d.get("_id") + "): " + d.get("count") + " crime reports";
        result.add(s);
        i++;
      }
      return result;
  }

  public String toString(){
    return "";
  }
}
