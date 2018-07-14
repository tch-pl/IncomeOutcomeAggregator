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
        List<String> aptekaDescriptions = new ArrayList<>();
        List<String> petrolDescriptions = new ArrayList<>();
        Acceptor acceptor = new Acceptor(Type.DESCRIPTION, foodDescriptions, "FOOD");
        foodDescriptions.add("Gosia");
        foodDescriptions.add("GOTUS");
        foodDescriptions.add("LIDL");
        foodDescriptions.add("KONKOL");
        foodDescriptions.add("BIEDRONKA");
        Acceptor acceptor2 = new Acceptor(Type.DESCRIPTION, petrolDescriptions, "PETROL");
        petrolDescriptions.add("ORLEN");
        Acceptor acceptorApteka = new Acceptor(Type.DESCRIPTION, aptekaDescriptions, "Apteka");
        aptekaDescriptions.add("APTEKA");
        List<Acceptor> acceptors = new ArrayList<>();
        acceptors.add(acceptor);
        acceptors.add(acceptor2);
        acceptors.add(acceptorApteka);

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
