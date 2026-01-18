import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class UniquenessChecker implements Runnable {
    private BlockingQueue<String> generatedBoardsQueue;
    private BlockingQueue<String> uniqueBoardsQueue;
    private Set<String> uniqueBoardSet;


    public UniquenessChecker(BlockingQueue<String> generatedQueue, BlockingQueue<String> uniqueQueue, Set<String> boardSet) {
        this.generatedBoardsQueue = generatedQueue;
        this.uniqueBoardsQueue = uniqueQueue;
        this.uniqueBoardSet = boardSet;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String board = generatedBoardsQueue.take();

                String canonicalForm = getCanonicalForm(board);

                synchronized (uniqueBoardSet) {
                    if (uniqueBoardSet.size() >= 10000) {
                        break;
                    }
                    if (!uniqueBoardSet.contains(canonicalForm)) {
                        uniqueBoardSet.add(canonicalForm);
                        uniqueBoardsQueue.put(board);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private String getCanonicalForm(String board) {

        return SudokuTransformations.getSimplestForm(board);
    }
}
