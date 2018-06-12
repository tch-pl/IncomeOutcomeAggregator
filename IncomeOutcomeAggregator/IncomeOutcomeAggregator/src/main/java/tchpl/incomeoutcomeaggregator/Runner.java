package tchpl.incomeoutcomeaggregator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tch
 */
public class Runner {
    public static void main(String[] args) {
        DataReaderFactory factory = new DataReaderFactory();
        
        try {
            DataReader reader = factory.getStandardReadrer("file:/D:/tmp/history.csv");
            reader.getData().forEach(s -> System.out.println(s));
        } catch (Exception ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
