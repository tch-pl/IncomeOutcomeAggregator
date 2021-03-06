package tchpl.incomeoutcomeaggregator;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DateTimeDV;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author tch
 */
public class Runner {

    public static void main(String[] args) {
        Runner runner = new Runner();
        try {
            runner.run(args);
        } catch (InterruptedException ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(String[] args) throws InterruptedException, ExecutionException {
        if (args == null || args.length == 0 || args[0] == null) {
            throw new IllegalStateException("!!!");
        }
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
        runNormal(acceptors, args[0]);
        runParallel(acceptors, args[0]);
    }

    private void runNormal(List<Acceptor> acceptors, String path) {
        Date start = new Date();
        for (Acceptor a : acceptors) {
            System.out.println(aggregate(path, a));
        }
        Date end = new Date();
        System.out.println(String.format("Normal:%s", end.getTime() - start.getTime()));
    }

    private void runParallel(List<Acceptor> acceptors, String path) throws InterruptedException, ExecutionException {
        Date start = new Date();
        List<CompletableFuture> tasks = new ArrayList<>();
        for (Acceptor a : acceptors) {
            Supplier<BigDecimal> sup = () -> aggregate(path, a);
            tasks.add(CompletableFuture.supplyAsync(sup));
        }
        CompletableFuture<Void> combinedFuture
                = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[tasks.size()]));
        combinedFuture.get();
        for (CompletableFuture t : tasks) {
            System.out.println(t.get());
        }
        Date end = new Date();
        System.out.println(String.format("Parallel:%s", end.getTime() - start.getTime()));
    }

    BigDecimal aggregate(String dataSourcePath, Acceptor acceptor) {
        DataReaderFactory factory = new DataReaderFactory();
        List<DataSingleLineItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        try {
            DataReader reader = factory.getStandardReadrer(dataSourcePath);
            reader.getData().forEach(s -> items.add(new DataSingleLineItem(s)));

            for (DataSingleLineItem single : items) {
                if (acceptor.accept(single)) {
                    BigDecimal b = new BigDecimal(Double.valueOf(single.getValue(Type.AMOUNT)));
                    total = total.add(b);
                }
            }
//            System.out.println(acceptor.getName() + ":" + total);
        } catch (Exception ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
}
