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

public class SafetyValQuery implements Query{
  private MongoCollection<Document> collection;
  private String year = "2019";
  private int value = 0;
  private String grade = "";

  public SafetyValQuery(MongoCollection<Document> collectionIn){
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

      String regString = "assault";

      Document regQuery = new Document();
        regQuery.append("$regex", year);
        regQuery.append("$options", "i");
      //
      // Document match = new Document();
      //   match.append("$match",
      //     new Document("AreaID",
      //     new Document("$eq", input)));

      Document findQuery = new Document();
        findQuery.append("Date Occurred", regQuery);
        // findQuery.append(match);

      int totalCrimes =  (int) collection.countDocuments(findQuery);

      //ref: https://mongodb.github.io/mongo-java-driver/4.0/apidocs/mongodb-driver-sync/com/mongodb/client/FindIterable.html
      // FindIterable<Document> docs = collection.find(findQuery);

      //ref: https://stackoverflow.com/questions/31643109/mongodb-aggregation-with-java-driver
      //ref: https://mongodb.github.io/mongo-java-driver/3.6/driver/tutorials/aggregation/

      int intV = Integer.parseInt(input);

      AggregateIterable<Document> output = collection.aggregate(
                        Arrays.asList(
                                Aggregates.match(Filters.regex("Date Occurred", year)),
                                Aggregates.match(Filters.eq("Area ID", intV)),
                                Aggregates.project(fields(include("Area ID", "Crime Code", "Crime Code Description"), excludeId())),
                                Aggregates.group("$Crime Code", Accumulators.sum("count", 1), Accumulators.first("Area ID", "$Area ID"), Accumulators.first("Crime Description", "$Crime Code Description")),
                                Aggregates.sort(orderBy(descending("count")))
                        )
                      );



      // int i = 1;
      int total = 0;
      int violent = 0;
      int property = 0;

      int currCount = 0;
      for(Document d : output){
        // System.out.println("asfjhaskjdfh");
        // System.out.println(d.toJson());
        String description = d.getString("Crime Description");
        currCount = d.getInteger("count");
        if(isViolentCrime(description)){
          violent += currCount;
        }
        if(isPropertyCrime(description)){
          property += currCount;
        }
        total += currCount;

        // System.out.println(description + " || " + isViolentCrime(description));
        // String s = i + ")" + d.get("Area Name") + " (id." + d.get("_id") + "): " + d.get("count") + " crime reports";
        // result.add(s);
        // i++;
      }

      result.add("Year: " + year);
      result.add("Total Crimes in LA: " + totalCrimes);
      result.add("Total Crimes in Area " + input + ": " + total);
      result.add("Number of Violent Crimes: " + violent);
      result.add("Number of Property Crimes: " + property);

      value = (int)Math.round(((double)total/totalCrimes)*(violent*0.75 + property*0.25));
      grade = getGrade(value);

      result.add("Safety Value: " + value);
      result.add("Safety Grade: " + grade);

      System.out.println("Year " + year + "\nTotal in LA: " + totalCrimes + "\nTotal in area: " + total + "\nViolent: " + violent + "\nProperty: " + property);
      System.out.println("Value: " + value + " = " + grade);
      return result;
  }

  public boolean isViolentCrime(String description){
    String[] keywords = {
      "assault",
      "battery",
      "robbery",
      "rape",
      "sex",
      "weapon",
      "arson",
      "murder",
      "homicide",
      "threat",
      "shots fired",
      "kidnap",
      "throw"
    };
    boolean result = false;
    for(String keyword : keywords){
      result = description.toLowerCase().contains(keyword);
      if(result == true) break;
    }

    return result;
  }

  public boolean isPropertyCrime(String description){
    String[] keywords = {
      "theft",
      "burglary",
      "vandalism",
      "trespassing",
      "stolen",
      "property"
    };

    boolean result = false;
    for(String keyword : keywords){
      result = description.toLowerCase().contains(keyword);
      if(result == true) break;
    }

    return result;
  }

  public String getGrade(int value){
    String result = "";

    //gross
    if(value < 20){
      result = "A+";
    }else if(value < 40){
      result = "A";
    }else if(value < 60){
      result = "A-";
    }else if(value < 80){
      result = "B+";
    }else if(value < 100){
      result = "B";
    }else if(value < 120){
      result = "B-";
    }else if(value < 140){
      result = "C+";
    }else if(value < 180){
      result = "C";
    }else if(value < 200){
      result = "C-";
    }else if(value < 220){
      result = "D+";
    }else if(value < 240){
      result = "D";
    }else if(value < 260){
      result = "D-";
    }else{
      result = "F";
    }

    return result;
  }

  public int getValue(){
    if(value == 0){
      System.out.println("Processing with default location: Area ID = 19");
      process("1");
    }
    return value;
  }

  public String getGrade(){
    if(grade.equals("")){
      System.out.println("Processing with default location: Area ID = 19");
      process("1");
    }
    return grade;
  }

  public String getYear(){
    return year;
  }

  public String toString(){
    return "";
  }
}
