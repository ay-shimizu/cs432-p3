package analysis.queries;
import analysis.util.Helper;
import analysis.util.Result;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Projections.*;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Sorts.*;
import com.mongodb.client.model.Accumulators;

public class SafetyValFutureQuery implements Query{
  private MongoCollection<Document> collection;
  private String year = "2019";

  public SafetyValFutureQuery(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public void setYear(String s){
    year = s;
  }

  public Result process(String input, String option){
      Result result = new Result();

      if(!Helper.isValidAreaID(input)){
        System.out.println("ERROR");
        result.add("ERROR: Invalid input. Please input in an AreaID between 1 and 21");
        return result;
      }

      SafetyValQuery baseQuery = new SafetyValQuery(collection);

      Result y2019 = baseQuery.process(input, null);
      int value2019 = baseQuery.getValue();

      baseQuery.setYear("2018");
      Result y2018 = baseQuery.process(input, null);
      int value2018 = baseQuery.getValue();

      baseQuery.setYear("2017");
      Result y2017 = baseQuery.process(input, null);
      int value2017 = baseQuery.getValue();

      baseQuery.setYear("2016");
      Result y2016 = baseQuery.process(input, null);
      int value2016 = baseQuery.getValue();

      baseQuery.setYear("2015");
      Result y2015 = baseQuery.process(input, null);
      int value2015 = baseQuery.getValue();

      System.out.println(value2015 + " --> " + value2016 + " --> " +value2017 + " --> " + value2018 + " --> " + value2019);

      int diffTotal = (value2019-value2018) + (value2018 - value2017) + (value2017-value2016) + (value2016-value2015);
      int change = diffTotal/4;
      System.out.println("Diff Total: " + diffTotal + " || " + diffTotal/4);

      int predicted = value2019 + change;
      String grade = baseQuery.getGrade(predicted);
      System.out.println("The predicted score and value of Area " + input + ": " + predicted + " (" + grade + ")");
      result.add("The predicted score and value of Area " + input + ": " + predicted + " (" + grade + ")");

      // System.out.println("Total in LA: " + totalCrimes + "\nTotal in area: " + total + "\nViolent: " + violent + "\nProperty: " + property);
      // System.out.println("Value: " + value + " = " + grade);
      return result;
  }

}
