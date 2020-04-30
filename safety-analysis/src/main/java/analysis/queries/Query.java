package analysis.queries;

import analyis.util.Result;

public interface Query{

    public Result process(String input);

    public String toString();

}
