package tchpl.incomeoutcomeaggregator;

import java.util.Objects;

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

    @Override
    public String toString() {
        return line;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.line);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataSingleLineItem other = (DataSingleLineItem) obj;
        if (!Objects.equals(this.line, other.line)) {
            return false;
        }
        return true;
    }
    
    
    
}
