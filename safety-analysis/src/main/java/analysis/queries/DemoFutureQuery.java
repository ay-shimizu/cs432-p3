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
import java.util.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.*;
import java.util.Map;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;

public class DemoFutureQuery implements Query{
  private MongoCollection<Document> collection;
  private static DecimalFormat df2 = new DecimalFormat("#.##");

  public DemoFutureQuery(MongoCollection<Document> collectionIn){
    collection = collectionIn;
  }

  public Result process(String input, String option){
    Result result = new Result();

    if(!Helper.isValidYear(input)){
      result.add("ERROR: Invalid input. Please input in a year between 2010 and 2019");
      return result;
    }

    result.add("Year in (" + input + ") Arrest Type (M) num Arrest order by Gender: \n" + processHelperGender(input, "M") + "\n");
    result.add("Year in (" + input + ") Arrest Type (F) num Arrest order by Gender: \n" + processHelperGender(input, "F") + "\n");
    result.add("Year in (" + input + ") Arrest Type (I) num Arrest order by Gender: \n" + processHelperGender(input, "I") + "\n");
    result.add("Year in (" + input + ") Arrest Type (O) num Arrest order by Gender: \n" + processHelperGender(input, "O") + "\n");
    result.add("Year in (" + input + ") Arrest Type (D) num Arrest order by Gender: \n" + processHelperGender(input, "D") + "\n");

    result.add("Arrest Type (M) predict percentage order by Age top3: \n" + processHelperAge(input, "M") + "\n");
    result.add("Arrest Type (F) predict percentage order by Age top3: \n" + processHelperAge(input, "F") + "\n");
    result.add("Arrest Type (I) predict percentage order by Age top3: \n" + processHelperAge(input, "I") + "\n");
    result.add("Arrest Type (O) predict percentage order by Age top3: \n" + processHelperAge(input, "O") + "\n");
    result.add("Arrest Type (D) predict percentage order by Age top3: \n" + processHelperAge(input, "D") + "\n");

    return result;
  }

  public String processHelperGender(String input, String code){
    String result = "";
    AggregateIterable<Document> genderDocuments_2019 = processByGender("2019", code);
    AggregateIterable<Document> genderDocuments_2018 = processByGender("2017", code);
    List<ArrayList<String>> listGenderData_2019 = intoList(genderDocuments_2019);
    List<ArrayList<String>> listGenderData_2018 = intoList(genderDocuments_2018);

    int male_2019 = Integer.parseInt(listGenderData_2019.get(0).get(1));
    int female_2019 = Integer.parseInt(listGenderData_2019.get(1).get(1));
    int male_2018 = Integer.parseInt(listGenderData_2018.get(0).get(1));
    int female_2018 = Integer.parseInt(listGenderData_2018.get(1).get(1));

    double pIncrease_male = (double) (male_2019 - male_2018)/male_2018;
    double predict_male = (double) male_2019 + male_2019 * pIncrease_male;
    double pIncrease_female = (double) (female_2019 - female_2018)/female_2018;
    double predict_female = (double) female_2019 + female_2019 * pIncrease_female;

    result += "Male 2019: " + listGenderData_2019.get(0).get(1) + "    ";
    result += "Percentage increase from 2018: " + df2.format(pIncrease_male* 100) + "%     ";
    result += "Predit value: " + df2.format(predict_male) + "\n";

    result += "Female 2019: " + listGenderData_2019.get(1).get(1) + "    ";
    result += "Percentage increase from 2018: "+ df2.format(pIncrease_female* 100) + "%    ";
    result += "Predit value: " + df2.format(predict_female) + "\n";

    return result;
  }

  public String processHelperAge(String input, String code){
    String result = "";
    AggregateIterable<Document> ageDocuments = processByAge(input, code);
    List<ArrayList<String>> listAgeData = intoList(ageDocuments);
    HashMap<String, Double> range = calculateRange(listAgeData);
    HashMap<String, Double> top3 = sortByValue(range);
    // print the sorted hashmap
    int i = 0;
    for(Map.Entry<String, Double> en : top3.entrySet()){
        //Entry<String, Double> en = top3.pollFirstEntry();
        result += en.getKey() + " " + df2.format(en.getValue()) + "%\n";
        if(i == 2){break;}
        i++;
    }
    return result;
  }

  //https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
  // function to sort hashmap by values
  public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm) {
    // Create a list from elements of HashMap
    List<Map.Entry<String, Double>> list =
           new LinkedList<Map.Entry<String, Double> >(hm.entrySet());

    // Sort the list
    Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){
        public int compare(Map.Entry<String, Double> o1,
                           Map.Entry<String, Double> o2){
            return (o2.getValue()).compareTo(o1.getValue());
        }

    });

    // put data from sorted list to hashmap
    HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
    for (Map.Entry<String, Double> aa : list) {
        temp.put(aa.getKey(), aa.getValue());
    }
    return temp;
}

  public List<ArrayList<String>> intoList(AggregateIterable<Document> documents){
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

  public AggregateIterable<Document> processByGender(String input, String Arrestcode){
    Document regQuery = new Document();
      regQuery.append("$regex", input);
      regQuery.append("$options", "i");

    AggregateIterable<Document> documents = collection.aggregate(
        Arrays.asList(
                Aggregates.match(Filters.eq("Arrest Date", regQuery)),
                Aggregates.match(Filters.eq("Arrest Type Code", Arrestcode)),
                Aggregates.project(fields(include("Arrest Type Code", "Sex Code"), excludeId())),
                Aggregates.group("$Sex Code", Accumulators.sum("count", 1)),
                Aggregates.sort(orderBy(descending("count")))
        )
    );
    return documents;
  }

  public AggregateIterable<Document> processByAge(String input, String Arrestcode){
    Document regQuery = new Document();
      regQuery.append("$regex", "2019");
      regQuery.append("$options", "i");

    AggregateIterable<Document> documents = collection.aggregate(
        Arrays.asList(
                Aggregates.match(Filters.eq("Arrest Date", regQuery)),
                Aggregates.match(Filters.eq("Arrest Type Code", Arrestcode)),
                Aggregates.project(fields(include("Arrest Type Code", "Age"), excludeId())),
                Aggregates.group("$Age", Accumulators.sum("count", 1)),
                Aggregates.sort(orderBy(ascending("_id")))
        )
    );
    return documents;
  }

  // return a list of percentage value of each age range
  public HashMap<String, Double>calculateRange(List<ArrayList<String>> list){
    List<Integer> sum = new ArrayList<Integer>();
    for(int i = 0; i < 9; i++){
      sum.add(0);
    }

    for(int i = 0; i < list.size(); i++){
      int value = Integer.parseInt(list.get(i).get(0));
      if(value < 11){
        int temp = sum.get(0) + Integer.parseInt(list.get(i).get(1));
        sum.set(0, temp);
      }
      else if(value < 21){
        int temp = sum.get(1) + Integer.parseInt(list.get(i).get(1));
        sum.set(1, temp);
      }
      else if(value < 31){
        int temp = sum.get(2) + Integer.parseInt(list.get(i).get(1));
        sum.set(2, temp);
      }
      else if(value < 41){
        int temp = sum.get(3) + Integer.parseInt(list.get(i).get(1));
        sum.set(3, temp);
      }
      else if(value < 51){
        int temp = sum.get(4) + Integer.parseInt(list.get(i).get(1));
        sum.set(4, temp);
      }
      else if(value < 61){
        int temp = sum.get(5) + Integer.parseInt(list.get(i).get(1));
        sum.set(5, temp);
      }
      else if(value < 71){
        int temp = sum.get(6) + Integer.parseInt(list.get(i).get(1));
        sum.set(6, temp);
      }
      else{
        int temp = sum.get(7) + Integer.parseInt(list.get(i).get(1));
        sum.set(7, temp);
      }
    }
      int total = 0;
      for(int i = 0; i < sum.size(); i++){
        total += sum.get(i);
      }

      HashMap<String, Double> result = new HashMap<String, Double>();
      result.put("0 ~ 10: ", (double)sum.get(0)/total * 100);
      for(int i = 1; i < sum.size()-1; i++){
         result.put(i + "1 ~ " + (i+1) + "0: ", (double)sum.get(i)/total * 100);
      }
      result.put("80+ : ", (double)sum.get(sum.size()-1)/total * 100);
      return result;
 }

  public String toString(){
    return "";
  }
}
