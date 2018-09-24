package tchpl.incomeoutcomeaggregator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        Set<DataSingleLineItem> itemsUnclassified = new HashSet<>();
        Set<DataSingleLineItem> itemsClassified = new HashSet<>();
        List<String> foodDescriptions = new ArrayList<>();
        List<String> aptekaDescriptions = new ArrayList<>();
        List<String> petrolDescriptions = new ArrayList<>();
        Acceptor acceptor = new Acceptor(Type.DESCRIPTION, foodDescriptions, "FOOD");
        foodDescriptions.add("Gosia");
        foodDescriptions.add("GOTUS");
        foodDescriptions.add("LIDL");
        foodDescriptions.add("KONKOL");
        foodDescriptions.add("BIEDRONKA");
        foodDescriptions.add("TESCO");
        foodDescriptions.add("Leclerc");
        foodDescriptions.add("Auchan");
        foodDescriptions.add("Dominik");
        foodDescriptions.add("Sychta");
        foodDescriptions.add("IRENA HELMAN");
        foodDescriptions.add("Piotr i Pawel");
        Acceptor acceptor2 = new Acceptor(Type.DESCRIPTION, petrolDescriptions, "PETROL");
        petrolDescriptions.add("ORLEN");
        petrolDescriptions.add("OKTAN");
        Acceptor acceptorApteka = new Acceptor(Type.DESCRIPTION, aptekaDescriptions, "Apteka");
        aptekaDescriptions.add("APTEKA");
        List<String> rossmanDescriptions = new ArrayList<>();
        Acceptor rosmanApteka = new Acceptor(Type.DESCRIPTION, rossmanDescriptions, "Rossman");
        rossmanDescriptions.add("ROSSMAN");
        List<Acceptor> acceptors = new ArrayList<>();
        acceptors.add(acceptor);
        acceptors.add(acceptor2);
        acceptors.add(acceptorApteka);
        acceptors.add(rosmanApteka);
        try {
            DataReader reader = factory.getStandardReadrer(args[0]);
            reader.getData().forEach(s -> items.add(new DataSingleLineItem(s)));
            BigDecimal all = BigDecimal.ZERO;
            for (Acceptor a : acceptors) {
                try {
                    System.out.println("################ " + a.getName());

                    BigDecimal total = BigDecimal.ZERO;
                    for (DataSingleLineItem single : items) {
                        if (a.accept(single)) {
                            System.out.println(single.getValue(Type.DESCRIPTION) + " / " + single.getValue(Type.DATE) + " / " + single.getValue(Type.AMOUNT));
                            BigDecimal b = new BigDecimal(Double.valueOf(single.getValue(Type.AMOUNT)));
                            total = total.add(b);
                            all = all.add(total);
                        } 
                    }
                    System.out.println(a.getName() + ":" + total);
                } catch (Exception ex) {
                    Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("All:" + all);
        } catch (Exception ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
