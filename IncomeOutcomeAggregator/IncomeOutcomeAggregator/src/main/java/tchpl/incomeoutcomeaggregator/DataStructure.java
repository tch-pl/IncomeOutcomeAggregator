package tchpl.incomeoutcomeaggregator;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author tch
 */
public class DataStructure {

    private final Map<Type, Integer> structure;

    public DataStructure() {
        structure = new HashMap<>();
        structure.put(Type.DATE, 0);
        structure.put(Type.AMOUNT, 3);
        structure.put(Type.DESCRIPTION, 7);
    }

    public Map<Type, Integer> getStructure() {
        return structure;
    }

}
