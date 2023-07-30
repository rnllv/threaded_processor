import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class NeoThreader implements Runnable {
    public NeoThreader(int processingCapacity, BlockingQueue<Runnable> batchQueue, AtomicBoolean pendingRecordsToBeReadFromFile) {
        this.processingQueue = new ArrayBlockingQueue<>(processingCapacity);
        this.batchQueue = batchQueue;
        this.concurrentBatchProcessor = new ThreadPoolExecutor(processingCapacity, processingCapacity, 500000, TimeUnit.MILLISECONDS, processingQueue);
        this.pendingRecordsToBeReadFromFile = pendingRecordsToBeReadFromFile;
    }

    private BlockingQueue<Runnable> processingQueue;
    private BlockingQueue<Runnable> batchQueue;
    private ThreadPoolExecutor concurrentBatchProcessor;
    private AtomicBoolean pendingRecordsToBeReadFromFile;
    @Override
    public void run() {

        boolean processingPending = true;
        do {
            while (processingQueue.remainingCapacity() >= 1 && !batchQueue.isEmpty()) {
                System.out.println("Submit ");
                concurrentBatchProcessor.submit(batchQueue.poll());
            }
            if (!pendingRecordsToBeReadFromFile.get() && batchQueue.isEmpty() && processingQueue.isEmpty()){
                processingPending = false;
            }
        } while (processingPending);

        concurrentBatchProcessor.shutdown();
        try {
            concurrentBatchProcessor.awaitTermination(5, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
