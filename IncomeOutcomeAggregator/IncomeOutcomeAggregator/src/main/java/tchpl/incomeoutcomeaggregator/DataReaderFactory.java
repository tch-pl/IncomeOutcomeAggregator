package tchpl.incomeoutcomeaggregator;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tch
 */
public class DataReaderFactory {

    public DataReader getStandardReadrer(String fullPath) throws Exception {
        Path path = Paths.get(fullPath);
        BufferedReader br;
        try {
            br = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1);
            return () -> br.lines();
        } catch (IOException ex) {
            Logger.getLogger(DataReaderFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public DataReader getDummyReader(List<String> input) {
        List<String> dummyData = new ArrayList<>(input);

        return () -> {
            return dummyData.stream();
        };
    }

    private static Runnable asUncheckedRunnable(Closeable c) {
        return () -> {
            try {
                c.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }
}
