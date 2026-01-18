import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FileSaver implements Runnable {
    private BlockingQueue<String> uniqueBoardsQueue;
    private AtomicInteger fileCounter;

    public FileSaver(BlockingQueue<String> uniqueQueue) {
        this.uniqueBoardsQueue = uniqueQueue;
        this.fileCounter = new AtomicInteger(0);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String board = uniqueBoardsQueue.take();
                if(board.equals("STOP"))break;
                saveBoardToFile(board, fileCounter.getAndIncrement());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void saveBoardToFile(String board, int id) {
        char[][] gridFormat = SudokuTransformations.stringToGrid(board);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                sb.append("" + gridFormat[i][j] + " ");
                if ((j + 1) % 3 == 0 && j < 8) {
                    sb.append("| ");
                }
            }

            sb.append("\n");
            if ((i + 1) % 3 == 0 && i < 8) {
                sb.append("---------------------\n");
            }
        }
        String filename = id + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
