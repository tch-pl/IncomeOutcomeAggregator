package tchpl.incomeoutcomeaggregator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tch
 */
public class Runner {

    public static void main(String[] args) {
        DataReaderFactory factory = new DataReaderFactory();
        List<DataSingleLineItem> items = new ArrayList<>();
        List<String> foodDescriptions = new ArrayList<>();
        List<String> petrolDescriptions = new ArrayList<>();
        Acceptor acceptor = new Acceptor(Type.DESCRIPTION, foodDescriptions, "FOOD");
        foodDescriptions.add("Gosia");
        Acceptor acceptor2 = new Acceptor(Type.DESCRIPTION, petrolDescriptions, "PETROL");
        petrolDescriptions.add("ORLEN");
        List<Acceptor> acceptors = new ArrayList<>();
        acceptors.add(acceptor);
        acceptors.add(acceptor2);

        for (Acceptor a : acceptors) {
            try {
                DataReader reader = factory.getStandardReadrer(args[0]);
                reader.getData().forEach(s -> items.add(new DataSingleLineItem(s)));
                BigDecimal total = BigDecimal.ZERO;
                for (DataSingleLineItem single : items) {
                    if (a.accept(single)) {
                        BigDecimal b = new BigDecimal(Double.valueOf(single.getValue(Type.AMOUNT)));
                        total = total.add(b);
                    }
                }
                System.out.println(a.getName() + ":" + total);
            } catch (Exception ex) {
                Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
