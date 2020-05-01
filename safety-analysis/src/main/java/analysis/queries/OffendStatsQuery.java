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

public class OffendStatsQuery implements Query{
  private MongoCollection<Document> collection;

  public OffendStatsQuery(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public Result process(String input){
    Result result = new Result();

    // if(!Helper.isValidYear(input)){
    //   result.add("ERROR: Invalid input. Please input in a year between 2010 and 2019");
    //   // System.out.println("ERROR HERE");
    //   return result;
    // }
    //
    // //Age statistic of each Arrest code "MFIOD" depends on the year user input
    // //String returnVal_M = processHelper(input, "M");
    // //String returnVal_F = processHelper(input, "F");
    // //String returnVal_I = processHelper(input, "I");
    // //String returnVal_D = processHelper(input, "D");
    // //String returnVal_O = processHelper(input, "O");
    //
    // result.add("Year in" + input + "Arrest Type(M): " + processHelper(input, "M"));
    // result.add("Year in" + input + "Arrest Type(F): " + processHelper(input, "F"));
    // result.add("Year in" + input + "Arrest Type(I): " + processHelper(input, "I"));
    // result.add("Year in" + input + "Arrest Type(O): " + processHelper(input, "O"));
    // result.add("Year in" + input + "Arrest Type(D): " + processHelper(input, "D"));
    return result;
  }

  // public String processHelper(String input, String option){
  //     AggregateIterable<Document> documents = processData(input, option);
  //     List<ArrayList<String>> listData = intoList(documents_M);
  //     String result = getResult(listData);
  //     return result;
  // }
  //
  // public String getResult(List<ArrayList<String>> staticPoint){
  //   String data = "";
  //   data += "Max age: " + staticPoint.get(0).get(0) + "  ";
  //   data += "Min age: " + staticPoint.get(staticPoint.size()-1).get(0) + "  ";
  //
  //   double averageAge = 0.0;
  //   int total = 0;
  //   int ageSum = 0;
  //   for(int i = 0; i < staticPoint.size(); i++){
  //     int age = Integer.parseInt(staticPoint.get(i).get(0));
  //     int num = Integer.parseInt(staticPoint.get(i).get(1));
  //     ageSum += age * num;
  //     total += num;
  //   }
  //   averageAge = (double) ageSum/total;
  //   data += "Average age: " + String.valueOf((averageAge)) + "\n";
  //
  //   System.out.println("Max: " + maxAge + " Min: " +  minAge);
  //   System.out.println("Total: " + total + " Agesum: " +  ageSum + " Average:" + averageAge);
  //
  //   return data;
  // }
  //
  // public static List<ArrayList<String>> intoList(AggregateIterable<Document> documents){
  //     List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
  //     for(Document d : documents){
  //       ArrayList<String> temp = new ArrayList<String>();
  //       temp.add(d.get("_id").toString());
  //       temp.add(d.get("count").toString());
  //       list.add(temp);
  //       //System.out.println(d.get("Sex Code").toString());
  //     }
  //     return list;
  // }
  //
  // public AggregateIterable<Document> processData(String input, String Arrestcode){
  //   Document regQuery = new Document();
  //     regQuery.append("$regex", input);
  //     regQuery.append("$options", "i");
  //
  //   AggregateIterable<Document> documents = collection.aggregate(
  //       Arrays.asList(
  //               Aggregates.match(Filters.eq("Arrest Date", regQuery)),
  //               Aggregates.match(Filters.eq("Arrest Type Code", Arrestcode)),
  //               Aggregates.project(fields(include("Arrest Type Code", "Age"), excludeId())),
  //               Aggregates.group("$Age", Accumulators.sum("count", 1)),
  //               Aggregates.sort(orderBy(descending("_id")))
  //       )
  //   );
  //   return documents;
  // }


  public String toString(){
    return "";
  }
}
