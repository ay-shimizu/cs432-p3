package analyis.util;

import java.util.ArrayList;

public class Result{
  private List<String> results;

  public Result(){
    results = new ArrayList<String>();
  }

  public void add(String s){
    results.push(s);
  }

}
