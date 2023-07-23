import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        BlockingQueue<Runnable> batchQueue = new ArrayBlockingQueue<>(5);
        int processingCapacity = 2;
        BlockingQueue<Runnable> processingQueue = new ArrayBlockingQueue<>(processingCapacity);
        ThreadPoolExecutor concurrentBatchProcessor = new ThreadPoolExecutor(processingCapacity, processingCapacity, 500000, TimeUnit.MILLISECONDS, processingQueue);

        Neot dd = new Neot();

        boolean processingPending = true;
        boolean pendingRecordsToBeReadFromFile = true;
        List<String> nn;
        NeoCall neo;

        do {
            while (batchQueue.remainingCapacity() >= 1 && pendingRecordsToBeReadFromFile) {
                nn = new ArrayList<>();
                pendingRecordsToBeReadFromFile = dd.getNext(nn);
                if (pendingRecordsToBeReadFromFile) {
                    System.out.println("Found data to be read");
                    neo = new NeoCall(nn);
                    batchQueue.add(neo);
                }
            }

            while (processingQueue.remainingCapacity() >= 1 && !batchQueue.isEmpty()) {
                System.out.println("Submit ");
                concurrentBatchProcessor.submit(batchQueue.poll());
            }
            if (!pendingRecordsToBeReadFromFile && batchQueue.isEmpty() && processingQueue.isEmpty()){
                processingPending = false;
            }
        } while (processingPending);

        System.out.println("OUt");
        concurrentBatchProcessor.shutdown();


    }
}
