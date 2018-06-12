package tchpl.incomeoutcomeaggregator;

/**
 *
 * @author tch
 */
public class CSVParser {

    private final String delimeter = ",";
    private final String toRemove = "\"";
    
    public String[] parseString(String line) {
        line = line.replace(toRemove, "");
        return line.split(delimeter);
    } 

}
