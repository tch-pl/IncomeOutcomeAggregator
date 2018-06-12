package tchpl.incomeoutcomeaggregator;

/**
 *
 * @author tch
 */
public class DataSingleLineItem {

    private final CSVParser parser = new CSVParser();
    private final String line;
    private final DataStructure dataStructure = new DataStructure();

    public DataSingleLineItem(String line) {
        this.line = line;
    }

    public String getValue(Type type) {
        String[] parsed = parser.parseString(line);
        Integer index = dataStructure.getStructure().get(type);
        return parsed != null && parsed.length > index ? parsed[index] : "";
    }

}
