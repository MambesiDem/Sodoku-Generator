import java.io.PrintStream;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        int numberOfInvalidGrids = 0;

        for(int z = 1; z <= 10; ++z) {
            SudokuGenerator generator = new SudokuGenerator();
            SudokuGrid sudoku = generator.generateSudoku();
            boolean invalidGrid = false;

            int i;
            int j;
            for(i = 0; i < 9; ++i) {
                for(j = 0; j < 9; ++j) {
                    if (sudoku.getValue(i, j) == 0) {
                        invalidGrid = true;
                    }
                }
            }

            if (invalidGrid) {
                numberOfInvalidGrids++;
                System.out.println();
                System.out.println("INVALID GRID");
            } else {
                System.out.println();
                System.out.println("Grid " + z);

                for(i = 0; i < 9; ++i) {
                    for(j = 0; j < 9; ++j) {
                        PrintStream var10000 = System.out;
                        int var10001 = sudoku.getValue(i, j);
                        var10000.print("" + var10001 + " ");
                        if ((j + 1) % 3 == 0 && j < 8) {
                            System.out.print("| ");
                        }
                    }

                    System.out.println();
                    if ((i + 1) % 3 == 0 && i < 8) {
                        System.out.println("---------------------");
                    }
                }
            }
        }

        System.out.println();
        System.out.println("There are " + numberOfInvalidGrids + " invalid grids generated.");
    }
}