import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> batchQueue = new ArrayBlockingQueue<>(5);

        Neot dd = new Neot();

        AtomicBoolean pendingRecordsToBeReadFromFile = new AtomicBoolean(true);
        List<String> nn;
        NeoCall neo;
        Thread loaderThread = new Thread(new NeoThreader(5, batchQueue, pendingRecordsToBeReadFromFile));
        loaderThread.start();
        do {
            while (batchQueue.remainingCapacity() >= 1 && pendingRecordsToBeReadFromFile.get()) {
                nn = new ArrayList<>();
                pendingRecordsToBeReadFromFile.set(dd.getNext(nn));
                if (pendingRecordsToBeReadFromFile.get()) {
                    System.out.println("Found data to be read");
                    neo = new NeoCall(nn);
                    batchQueue.add(neo);
                }
            }
        } while (pendingRecordsToBeReadFromFile.get());

        loaderThread.join();
        System.out.println("OUt");


    }
}
