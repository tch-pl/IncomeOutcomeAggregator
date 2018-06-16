package tchpl.incomeoutcomeaggregator;

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
        try {
            DataReader reader = factory.getStandardReadrer("d:\\tmp\\history.csv");
            reader.getData().forEach(s -> items.add(new DataSingleLineItem(s)));
            for (DataSingleLineItem single : items) {
                if (acceptor.accept(single)) {
                    System.out.println("FOOD: " + single);
                } else if (acceptor2.accept(single)) {
                    System.out.println("PETROL: " + single);
                    
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
