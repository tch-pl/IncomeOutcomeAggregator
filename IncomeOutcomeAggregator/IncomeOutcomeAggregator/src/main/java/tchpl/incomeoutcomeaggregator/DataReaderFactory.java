package tchpl.incomeoutcomeaggregator;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tch
 */
public class DataReaderFactory {
    public DataReader getStandardReadrer(String fullPath) throws Exception {
        URI uri = new URI(fullPath);
        Path path = Paths.get(uri);
        return () -> Files.lines(path);
    }
    
    public DataReader getDummyReader(List<String> input) {
       List<String> dummyData =new ArrayList<>(input);
       
        return () -> {
            return dummyData.stream();
        };
    }
}
