package tchpl.incomeoutcomeaggregator;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author tch
 */
public class Aggregator {

    private final List<DataSingleLineItem> items;

    public Aggregator(List<DataSingleLineItem> items) {
        this.items = items;
    }

    public List<DataSingleLineItem> getItems() {
        return items;
    }

    public BigDecimal aggregate() {
        BigDecimal total = BigDecimal.ZERO;
        for (DataSingleLineItem single : items) {
            try {
                BigDecimal b = new BigDecimal(Double.valueOf(single.getValue(Type.AMOUNT)));
                total = total.add(b);
            } catch (java.lang.NumberFormatException nfe) {
                //skip    
            }
        }

        return total;
    }

}
