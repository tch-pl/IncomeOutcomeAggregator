package tchpl.incomeoutcomeaggregator;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Tomasz_Ch
 */
public class AcceptorTest {
    
    public AcceptorTest() {
    }

    @Test
    public void testAccept() {     
        String line = "\"2018-07-02\",\"2018-06-30\",\"P³atnoœæ kart¹\",\"-15.60\",\"PLN\",\"+53207.83\",\"Tytu³: 000498849 74230788182058952292816\",\"Lokalizacja: Kraj: POLSKA Miasto: RUMIA Adres: GOTUS SP. Z O.O.\",\"Data i czas operacji: 2018-06-30\",\"Oryginalna kwota operacji: 15,60 PLN\",\"Numer karty: 42512509*****539\",\"\"";
        DataSingleLineItem item = new DataSingleLineItem(line);
        List<String> matching = new ArrayList<>();
        matching.add("GOTUS");
                
        Acceptor instance = new Acceptor(Type.DESCRIPTION, matching, line);
        boolean expResult = true;
        boolean result = instance.accept(item);
        assertEquals(expResult, result);
        
    }

    
    
}
