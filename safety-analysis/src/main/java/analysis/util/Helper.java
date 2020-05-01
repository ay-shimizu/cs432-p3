package analysis.util;

public class Helper{

  public static boolean isValidYear(String input){
    try{
      int year = Integer.parseInt(input);

      if(year < 2010 || year > 2019){
        System.out.println("ERROR: Invalid year inputted.");
        return false;
      }
    }catch(Exception e){
      System.err.println("Exception caught... \n" + e);
      return false;
    }

    return true;
  }

  public static boolean isValidAreaID(String input){
    try{
      int year = Integer.parseInt(input);

      if(year < 1 || year > 21){
        System.out.println("ERROR: Invalid Area ID inputted.");
        return false;
      }
    }catch(Exception e){
      System.err.println("Exception caught... \n" + e);
      return false;
    }

    return true;
  }
}
