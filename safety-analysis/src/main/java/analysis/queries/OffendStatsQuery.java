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

public class OffendStatsQuery implements Query{
  private MongoCollection<Document> collection;
  private static DecimalFormat df2 = new DecimalFormat("#.##");

  public OffendStatsQuery(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public Result process(String input, String option){
    Result result = new Result();

    if(!Helper.isValidYear(input)){
      result.add("ERROR: Invalid input. Please input in a year between 2010 and 2019");
      // System.out.println("ERROR HERE");
      return result;
    }

    //Age statistic of each Arrest code "MFIOD" depends on the year user input
    result.add("Year in (" + input + ") Arrest Type(M): \n" + processHelper(input, "M"));
    result.add("Year in (" + input + ") Arrest Type(F): \n" + processHelper(input, "F"));
    result.add("Year in (" + input + ") Arrest Type(I): \n" + processHelper(input, "I"));
    result.add("Year in (" + input + ") Arrest Type(O): \n" + processHelper(input, "O"));
    result.add("Year in (" + input + ") Arrest Type(D): \n" + processHelper(input, "D"));
    return result;
  }

  public String processHelper(String input, String option){
      AggregateIterable<Document> documents = processData(input, option);
      List<ArrayList<String>> listData = intoList(documents);
      String result = getResult(listData);
      return result;
  }

  public String getResult(List<ArrayList<String>> staticPoint){
    String data = "";

    data += "Max age: " + staticPoint.get(0).get(0) + "  \n";
    data += "Min age: " + staticPoint.get(staticPoint.size()-1).get(0) + "  \n";

    double averageAge = 0.0;
    int total = 0;
    int ageSum = 0;
    for(int i = 0; i < staticPoint.size(); i++){
      int age = Integer.parseInt(staticPoint.get(i).get(0));
      int num = Integer.parseInt(staticPoint.get(i).get(1));
      ageSum += age * num;
      total += num;
    }
    averageAge = (double) ageSum/total;
    data += "Average age: " + String.valueOf(df2.format(averageAge)) + "\n";

    //System.out.println("Max: " + maxAge + " Min: " +  minAge);
    //System.out.println("Total: " + total + " Agesum: " +  ageSum + " Average:" + averageAge);

    return data;
  }

  public static List<ArrayList<String>> intoList(AggregateIterable<Document> documents){
      List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
      for(Document d : documents){
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(d.get("_id").toString());
        temp.add(d.get("count").toString());
        list.add(temp);
        //System.out.println(d.get("Sex Code").toString());
      }
      return list;
  }

  public AggregateIterable<Document> processData(String input, String Arrestcode){
    Document regQuery = new Document();
      regQuery.append("$regex", input);
      regQuery.append("$options", "i");

    AggregateIterable<Document> documents = collection.aggregate(
        Arrays.asList(
                Aggregates.match(Filters.eq("Arrest Date", regQuery)),
                Aggregates.match(Filters.eq("Arrest Type Code", Arrestcode)),
                Aggregates.project(fields(include("Arrest Type Code", "Age"), excludeId())),
                Aggregates.group("$Age", Accumulators.sum("count", 1)),
                Aggregates.sort(orderBy(descending("_id")))
        )
    );
    return documents;
  }


  public String toString(){
    return "";
  }
}
