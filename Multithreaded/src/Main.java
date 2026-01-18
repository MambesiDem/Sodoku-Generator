import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static BlockingQueue<String> generatedBoardsQueue = new LinkedBlockingQueue<>();
    private static BlockingQueue<String> uniqueBoardsQueue = new LinkedBlockingQueue<>();
    public static Set<String> uniqueBoardSet = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws InterruptedException {

        ExecutorService generatorPool = Executors.newFixedThreadPool(4);
        for (int i=0;i<4;i++) {
            generatorPool.submit(new BoardGenerator(generatedBoardsQueue, uniqueBoardSet));
        }

        ExecutorService uniquenessCheckerPool = Executors.newFixedThreadPool(4);
        for (int i=0; i<4;i++) {
            uniquenessCheckerPool.submit(new UniquenessChecker(generatedBoardsQueue, uniqueBoardsQueue, uniqueBoardSet));
        }

        Thread fileSaver = new Thread(new FileSaver(uniqueBoardsQueue));
        fileSaver.start();

        generatorPool.shutdown();
        generatorPool.awaitTermination(1, TimeUnit.HOURS);

        for(int i=1;i<=4;i++){
            generatedBoardsQueue.put("STOP");
        }

        uniquenessCheckerPool.shutdown();
        uniquenessCheckerPool.awaitTermination(1, TimeUnit.HOURS);
        uniqueBoardsQueue.put("STOP");

        fileSaver.join();

        System.out.println("All boards generated, checked, and saved successfully.");
    }
}