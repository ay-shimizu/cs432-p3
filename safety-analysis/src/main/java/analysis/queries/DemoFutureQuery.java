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

public class DemoFutureQuery implements Query{
  private MongoCollection<Document> collection;

  public DemoFutureQuery(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public Result process(String input, String option){
    Result result = new Result();

    // if(!Helper.isValidYear(input)){
    //   result.add("ERROR: Invalid input. Please input in a year between 2010 and 2019");
    //   // System.out.println("ERROR HERE");
    //   return result;
    // }
    // //String returnVal_M = processHelperGender(input, "M");
    // //String returnVal_F = processHelperGender(input, "F");
    // //String returnVal_I = processHelperGender(input, "I");
    // //String returnVal_O = processHelperGender(input, "O");
    // //String returnVal_D = processHelperGender(input, "D");
    //
    // result.add("Year in (" + input + ") Arrest Type (M) percentage order by Age: \n" + processHelperGender(input, "M") "\n");
    // result.add("Year in (" + input + ") Arrest Type (F) percentage order by Age: \n" + processHelperGender(input, "F") "\n");
    // result.add("Year in (" + input + ") Arrest Type (I) percentage order by Age: \n" + processHelperGender(input, "I") "\n");
    // result.add("Year in (" + input + ") Arrest Type (O) percentage order by Age: \n" + processHelperGender(input, "O") "\n");
    // result.add("Year in (" + input + ") Arrest Type (D) percentage order by Age: \n" + processHelperGender(input, "D") "\n");

    //String processHelperAge(input, "M");
    return result;
  }
 //
 //  public String processHelperGender(String input, String code){
 //    String result = "";
 //    AggregateIterable<Document> genderDocuments = processByGender(input, code);
 //    List<ArrayList<String>> listGenderData = processByAge(genderDocuments);
 //    for(int i = 0; i < listGenderData.size(); i++){
 //        result += "Gender: (" + listGenderData.get(0) + "): "+ listGenderData.get(1) + " crime reports\n";
 //    }
 //    return result;
 //  }
 //
 //  public Stirng processHelperAge(String input, String code){
 //    String reult = "";
 //    AggregateIterable<Document> ageDocuments = processByAge(input, code);
 //    List<ArrayList<String>> listAgeData = processByAge(ageDocuments);
 //    List<Double> range = calculateRange(listAgeData);
 //    result += "0 ~ 10: " + (String) new DecimalFormat("#.##").format(range.get(0));
 //    for(int i = 1; i < range.size()-1; i++){
 //        result += i + "1 ~ " + (i+1) + "0: " + range.get(i);
 //    }
 //    result += "80+ : " + range.get(range.size()-1);
 //    return result;
 //  }
 //
 //  public List<ArrayList<String>> intoList(AggregateIterable<Document> documents){
 //      List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
 //      for(Document d : documents){
 //        ArrayList<String> temp = new ArrayList<String>();
 //        temp.add(d.get("_id").toString());
 //        temp.add(d.get("count").toString());
 //        list.add(temp);
 //        //System.out.println(d.get("Sex Code").toString());
 //      }
 //      return list;
 //  }
 //
 //  public AggregateIterable<Document> processByGender(String input, String Arrestcode){
 //    Document regQuery = new Document();
 //      regQuery.append("$regex", input);
 //      regQuery.append("$options", "i");
 //
 //    AggregateIterable<Document> documents = collection.aggregate(
 //        Arrays.asList(
 //                Aggregates.match(Filters.eq("Arrest Date", regQuery)),
 //                Aggregates.match(Filters.eq("Arrest Type Code", Arrestcode)),
 //                Aggregates.project(fields(include("Arrest Type Code", "Sex Code"), excludeId())),
 //                Aggregates.group("$Sex Code", Accumulators.sum("count", 1)),
 //                Aggregates.sort(orderBy(descending("count"))),
 //        )
 //    );
 //    return documents;
 //  }
 //
 //  public AggregateIterable<Document> processByAge(String input, String Arrestcode){
 //    Document regQuery = new Document();
 //      regQuery.append("$regex", input);
 //      regQuery.append("$options", "i");
 //
 //    AggregateIterable<Document> documents = collection.aggregate(
 //        Arrays.asList(
 //                Aggregates.match(Filters.eq("Arrest Date", regQuery)),
 //                Aggregates.match(Filters.eq("Arrest Type Code", Arrestcode)),
 //                Aggregates.project(fields(include("Arrest Type Code", "Age"), excludeId())),
 //                Aggregates.group("$Age", Accumulators.sum("count", 1)),
 //                Aggregates.sort(orderBy(ascending("_id")))
 //        )
 //    );
 //    return documents;
 //  }
 //
 //  // return a list of percentage value of each age range
 //  public List<Double> calculateRange(List<ArrayList<String>> list){
 //    List<Integer> sum = new ArrayList<Integer>();
 //    for(int i = 0; i < 9; i++){
 //      sum.add(0);
 //    }
 //
 //    for(int i = 0; i < list.size(); i++){
 //      int value = Integer.parseInt(list.get(i).get(0));
 //      if(value < 11){
 //        //System.out.println(value + ": "+list.get(i).get(1));
 //        int temp = sum.get(0) + Integer.parseInt(list.get(i).get(1));
 //        sum.set(0, temp);
 //      }
 //      else if(value < 21){
 //      //  System.out.println(value + ": " +list.get(i).get(1));
 //        int temp = sum.get(1) + Integer.parseInt(list.get(i).get(1));
 //        sum.set(1, temp);
 //      }
 //      else if(value < 31){
 //      //  System.out.println(value + ": " +list.get(i).get(1));
 //        int temp = sum.get(2) + Integer.parseInt(list.get(i).get(1));
 //        sum.set(2, temp);
 //      }
 //      else if(value < 41){
 //      //  System.out.println(value + ": " +list.get(i).get(1));
 //        int temp = sum.get(3) + Integer.parseInt(list.get(i).get(1));
 //        sum.set(3, temp);
 //      }
 //      else if(value < 51){
 //      //  System.out.println(value + ": " +list.get(i).get(1));
 //        int temp = sum.get(4) + Integer.parseInt(list.get(i).get(1));
 //        sum.set(4, temp);
 //      }
 //      else if(value < 61){
 //      //  System.out.println(value + ": " +list.get(i).get(1));
 //        int temp = sum.get(5) + Integer.parseInt(list.get(i).get(1));
 //        sum.set(5, temp);
 //      }
 //      else if(value < 71){
 //        //System.out.println(value + ": " +list.get(i).get(1));
 //        int temp = sum.get(6) + Integer.parseInt(list.get(i).get(1));
 //        sum.set(6, temp);
 //      }
 //      else{
 //        //System.out.println(value + ": " +list.get(i).get(1));
 //        int temp = sum.get(7) + Integer.parseInt(list.get(i).get(1));
 //        sum.set(7, temp);
 //      }
 //    }
 //      int total = 0;
 //      for(int i = 0; i < sum.size(); i++){
 //        total += sum.get(i);
 //        System.out.println(i + ": " + sum.get(i));
 //      }
 //      System.out.println(total);
 //
 //      List<Double> result = new ArrayList<Double>();
 //      for(int i = 0; i < sum.size(); i++){
 //        result.add((double) sum.get(i)/total * 100);
 //        System.out.println(i + ": " + result.get(i));
 //      }
 //      return result;
 // }
  public String toString(){
    return "";
  }
}
