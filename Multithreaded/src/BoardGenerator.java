import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class BoardGenerator implements Runnable {
    private BlockingQueue<String> generatedBoardsQueue;
    private final Set<String> uniqueBoardSet;

    public BoardGenerator(BlockingQueue<String> queue, Set<String> uniqueBoardSet) {
        this.generatedBoardsQueue = queue;
        this.uniqueBoardSet = uniqueBoardSet;
    }

    @Override
    public void run() {
        while (true) {

            String board = generateBoardAsString();
            synchronized (uniqueBoardSet) {
                if (uniqueBoardSet.size() >= 10000) {
                    break;
                }
            }
            try {
                generatedBoardsQueue.put(board);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private String generateBoardAsString() {
        SudokuGenerator generator = new SudokuGenerator();
        SudokuGrid grid = generator.generateSudoku();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                sb.append(grid.getValue(i,j));
            }
        }
        return sb.toString();
    }
}
