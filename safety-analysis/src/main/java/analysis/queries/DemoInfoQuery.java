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

public class DemoInfoQuery implements Query{
  private MongoCollection<Document> collection;

  public DemoInfoQuery(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public Result process(String input, String option){
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
                 Aggregates.project(fields(include("Sex Code"), excludeId())),
                 Aggregates.group("$Sex Code", Accumulators.sum("count", 1)),
                 Aggregates.sort(orderBy(descending("count")))
          )
      );
    }else if(flag == 1){
          int in = Integer.parseInt(input);
          output = collection.aggregate(
              Arrays.asList(
                 Aggregates.match(Filters.eq(option, in)),
                 Aggregates.project(fields(include("Sex Code","Age"), excludeId())),
                 Aggregates.group("$Sex Code", Accumulators.sum("count", 1)),
                 Aggregates.sort(orderBy(descending("count")))
          )
      );
    }else{
      result.add("ERROR: Invalid input. Please input in a year between 2010 and 2019 or area code between 1 and 50");
      // System.out.println("ERROR HERE");
      return result;
    }
      result.add("Search by " + option + " ("+ input + ") : ");
      int i = 1;
      for(Document d : output){
         //System.out.println(d.toJson());
         result.add(i + ") " + "Gender (" + d.get("_id") + "): " + d.get("count") + " crime reports");
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

}
