package tchpl.incomeoutcomeaggregator;

import java.util.stream.Stream;

/**
 *
 * @author tch
 */
public interface DataReader {
    public Stream<String> getData() throws Exception;
}
