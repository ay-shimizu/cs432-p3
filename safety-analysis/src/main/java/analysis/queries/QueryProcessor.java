package analysis.queries;
import analysis.util.Result;

//ref: api for 4.0 Java driver: https://mongodb.github.io/mongo-java-driver/4.0/apidocs/mongodb-driver-sync/com/mongodb/client/package-summary.html
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

public class QueryProcessor{
  private MongoCollection<Document> collectionCrime;
  private MongoCollection<Document> collectionArrest;

  public QueryProcessor(MongoCollection<Document> collCrimeIn, MongoCollection<Document> collArrestIn){
    collectionCrime = collCrimeIn;
    collectionArrest = collArrestIn;
  }

  public Result process(QNames type, String input){
    Result result = null;
    Query q = null;
    String option = null;

    switch(type){
      case NUM_CRIMES:
        q = new NumCrimesQuery(collectionCrime);
        break;

      case COMMON_CRIMES_YEAR:
        q = new CommonCrimesQuery(collectionCrime);
        option = "Date Occurred";
        break;

       case COMMON_CRIMES_AREA:
        q = new CommonCrimesQuery(collectionCrime);
        option = "Area ID";
        break;

      case HIGHEST_CRIMES:
        q = new HighestCrimesQuery(collectionCrime);
        break;

       case DEMO_INFO_YEAR:
          q = new DemoInfoQuery(collectionArrest);
          option = "Arrest Date";
          break;

      case DEMO_INFO_AREA:
          q = new DemoInfoQuery(collectionArrest);
          option = "Area ID";
          break;

      case DEMO_FUTURE:
         q = new DemoFutureQuery(collectionArrest);
         break;

       case OFFEND_STATS:
         q = new OffendStatsQuery(collectionArrest);
         break;

      case SAFETY_VAL:
        q = new SafetyValQuery(collectionCrime);
        break;

      case SAFETY_VAL_FUTURE:
        q = new SafetyValFutureQuery(collectionCrime);
        break;
      default:
        System.out.println("ERROR: Invalid Query Called");
    }

    if(q != null){
        result = q.process(input, option);
    }else{
      result = new Result("ERROR: NULL VALUE");
    }

    return result;
  }


}
