package analysis.util;

public class Helper{

  public static boolean isValidYear(String input){
    try{
      int year = Integer.parseInt(input);

      if(year < 2010 || year > 2019){
        //System.out.println("ERROR: Invalid year inputted.");
        return false;
      }

    }catch(Exception e){
      System.err.println("Exception caught... \n" + e);
      return false;
    }
    return true;
  }

  public static boolean isValidArea(String input){
    try{
      int area = Integer.parseInt(input);
      if(area < 1 || area > 50){
        //System.out.println("ERROR: Invalid area code inputted.");
        return false;
      }
    }catch(Exception e){
      System.err.println("Exception caught... \n" + e);
      return false;
    }
    return true;

  }
}
