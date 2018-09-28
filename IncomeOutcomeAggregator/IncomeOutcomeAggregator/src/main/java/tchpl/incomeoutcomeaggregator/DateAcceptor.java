package tchpl.incomeoutcomeaggregator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author tch
 */
public class DateAcceptor {

    private final Type type;
    private final LocalDate from;
    private final LocalDate to;
    private final String name;

    public DateAcceptor(Type type, LocalDate from, LocalDate to, String name) {
        if (from == null || to == null || from.isAfter(to)) {
            throw new IllegalStateException();
        }
        this.type = type;
        this.from = from;
        this.to = to;
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
        try {
            LocalDate itemTime = LocalDate.parse(itemValue);
            return (itemTime.isAfter(from) && itemTime.isBefore(to)) || (itemTime.isEqual(from) || itemTime.isEqual(to));
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return type;
    }

}
