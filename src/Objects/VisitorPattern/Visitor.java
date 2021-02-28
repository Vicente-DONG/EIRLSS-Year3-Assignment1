
package Objects.VisitorPattern;

public class Visitor {
    public String visitBoolean(boolean b){
        if(b == true)
        {
            return "Yes";
        } else 
        {
            return "No";
        }
    }
}
