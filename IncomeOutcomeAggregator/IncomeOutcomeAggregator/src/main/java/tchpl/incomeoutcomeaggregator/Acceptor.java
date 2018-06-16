package tchpl.incomeoutcomeaggregator;

import java.util.List;

/**
 *
 * @author tch
 */
public class Acceptor {

    private final Type type;
    private final List<String> matchingExpressions;
    private final String name;

    public Acceptor(Type type, List<String> matchingExpressions, String name) {
        this.type = type;
        this.matchingExpressions = matchingExpressions;
        this.name = name;
    }

    public boolean accept(DataSingleLineItem item) {
        if (item == null) {
            return false;
        }
        String itemValue = item.getValue(this.type);
        if (itemValue == null) {
            return false;
        }
        return matchingExpressions.stream().parallel().anyMatch(itemValue::contains);
    }
    
    public String getName() {
        return this.name;
    }

}
