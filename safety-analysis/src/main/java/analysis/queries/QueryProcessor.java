package analysis.queries;
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

public class QueryProcessor{
  private MongoClient mongoClient;
  private MongoDatabase database;
  private MongoCollection<Document> collection;

  public QueryProcessor(MongoClient mongoClientIn, MongoDatabase databaseIn, MongoCollection<Document> collectionIn){
    mongoClient = mongoClientIn;
    database = databaseIn;
    collection = collectionIn;
  }

  public void setCollection(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public Result process(QNames type, String input){
    Result result = null;
    Query q = null;

    switch(type){
      case NUM_CRIMES:
        q = new NumCrimesQuery(collection);
        break;
      // case COMMON_CRIMES:
      //   q = new CommonCrimesQuery(collection);
      //   break
      // case HIGHEST_CRIMES:
      //   q = new HighestCrimesQuery(collection);
      //   break;
      // case DEMO_INFO:
      //   q= new DemoInfoQuery(collection);
      //   break;
      // case DEMO_FUTURE:
      //   q = new DemoFutureQuery(collection);
      //   break;
      // case OFFEND_STATS:
      //   q = new OffendStatsQuery(collection);
      //   break;
      // case SAFETY_VAL:
      //   q = new SafetyValQuery(collection);
      //   break;
      // case SAFETY_VAL_FUTURE:
      //   q = new SafetyValFuture(collection);
        // break;
      default:
        System.out.println("DEFAULT");
    }

    if(q != null){
        result = q.process(input);
    }else{
      result = new Result("ERROR: NULL VALUE");
    }
    // System.out.println(result.toString());
    return result;
  }

  //
  //
  //
  //   public String calculateSafety(String input){
  //     String result = "";
  //     //TO DO: Error check, input should be an Area ID
  //
  //     AggregateIterable<Document> output = collection.aggregate(
  //         Arrays.asList(
  //               Aggregates.match(Filters.regex("Date Occurred", input+".")),
  //                 Aggregates.project(fields(include("Crime Code", "Crime Code Description"), excludeId())),
  //                 Aggregates.group("$Crime Code", Accumulators.sum("count", 1), Accumulators.first("description", "$Crime Code Description")),
  //                 Aggregates.sort(orderBy(descending("count")))
  //         )
  //       );
  //
  //       int i = 1;
  //       for(Document d : output){
  //         System.out.println(d.toJson());
  //         //oof, inefficient
  //         // result = result + i + ")" + d.get("Area Name") + " (id." + d.get("_id") + "): " + d.get("count") + " crime reports <br><br>";
  //         // i++;
  //       }
  //
  //       return result;
  //
  //   }
  //
  // public String highestCrimes(String input){
  //   String result = "";
  //
  //   if(!validYear(input)){
  //     result = "ERROR: Invalid input. Please input in a year between 2010 and 2019";
  //     return result;
  //   }
  //
  //   //ref: https://stackoverflow.com/questions/31643109/mongodb-aggregation-with-java-driver
  //   //ref: https://mongodb.github.io/mongo-java-driver/3.6/driver/tutorials/aggregation/
  //   AggregateIterable<Document> output = collection.aggregate(
  //       Arrays.asList(
  //               Aggregates.project(fields(include("Area ID", "Area Name"), excludeId())),
  //               Aggregates.group("$Area ID", Accumulators.sum("count", 1), Accumulators.first("Area Name", "$Area Name")),
  //               Aggregates.sort(orderBy(descending("count")))
  //       )
  //     );
  //
  //     result = "<html>";
  //     int i = 1;
  //     for(Document d : output){
  //       // System.out.println(d.toJson());
  //       //oof, inefficient
  //       result = result + i + ")" + d.get("Area Name") + " (id." + d.get("_id") + "): " + d.get("count") + " crime reports <br><br>";
  //       i++;
  //     }
  //
  //     return result + " </html>";
  // }

  // public String numCrimes(String input){
  //   String result = "";
  //
  //   if(!validYear(input)){
  //     result = "ERROR: Invalid input. Please input in a year between 2010 and 2019";
  //     return result;
  //   }
  //
  //   String regString = input + ".";
  //
  //   Document regQuery = new Document();
  //     regQuery.append("$regex", regString);
  //     regQuery.append("$options", "i");
  //
  //   Document findQuery = new Document();
  //     findQuery.append("Date Occurred", regQuery);
  //
  //   int count =  (int) collection.countDocuments(findQuery);
  //
  //   // System.out.println("COUNT: " + count);
  //   result = "The total number of crime reported in " + input + " is: " + count;
  //   return result;
  // }

//
// public static void mostCommonTypeOfCrime(MongoCollection<Document> collection, String option, String userInput){
//
//  //ref: https://stackoverflow.com/questions/31643109/mongodb-aggregation-with-java-driver
//  //ref: https://mongodb.github.io/mongo-java-driver/3.6/driver/tutorials/aggregation/
//
//  Document regQuery = new Document();
//    regQuery.append("$regex", userInput);
//    regQuery.append("$options", "i");
//
//  AggregateIterable<Document> output = collection.aggregate(
//      Arrays.asList(
//              Aggregates.match(Filters.eq(option, regQuery)),
//              Aggregates.project(fields(include("Crime Code", "Crime Code Description"), excludeId())),
//              Aggregates.group("$Crime Code", Accumulators.sum("count", 1), Accumulators.first("Crime Code Description", "$Crime Code Description")),
//              Aggregates.sort(orderBy(descending("count")))
//      )
//    );
//
//    System.out.println("Search by " + option + ": \n");
//    int i = 1;
//    for(Document d : output){
//      // System.out.println(d.toJson());
//      System.out.println(i + ")" + d.get("Crime Code Description") + " (Crime Code." + d.get("_id") + "): " + d.get("count") + " crime reports\n");
//      i++;
//   }
// }
//
// public static void demographicInfo(MongoCollection<Document> collection, String option, String userInput){
//
//    Document regQuery = new Document();
//      regQuery.append("$regex", userInput);
//      regQuery.append("$options", "i");
//
//    AggregateIterable<Document> output = collection.aggregate(
//        Arrays.asList(
//                Aggregates.match(Filters.eq(option, regQuery)),
//                Aggregates.project(fields(include("Victim Sex"), excludeId())),
//                Aggregates.group("$Victim Sex", Accumulators.sum("count", 1)),
//                Aggregates.sort(orderBy(descending("count")))
//        )
//      );
//
//      System.out.println("Search by " + option + ": \n");
//      int i = 1;
//      for(Document d : output){
//        // System.out.println(d.toJson());
//        System.out.println(i + ")" + " (Victim Sex: " + d.get("_id") + "): " + d.get("count") + " crime reports\n");
//        i++;
//      }
// }

}
