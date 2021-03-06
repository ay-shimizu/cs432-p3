package analysis.util;

import java.util.ArrayList;
import java.util.List;

public class Result{
  private List<String> results;

  public Result(){
    results = new ArrayList<String>();
  }

  public Result(String s){
    results = new ArrayList<String>();
    results.add(s);
  }

  public void add(String s){
    results.add(s);
  }

  public int size(){
    return results.size();
  }

  public String toTextString(){
    String result = "<html>";
    for(String s : results){
      result = result + s + "<br><br>";
    }

    return result + "</html>";
  }


}
