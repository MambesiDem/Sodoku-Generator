import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuGenerator {
    private SudokuGrid grid;
    private Random random;

    public SudokuGenerator() {
    }

    public SudokuGrid generateSudoku() {
        grid = new SudokuGrid();
        random = new Random();
        this.reducePossibilities();
        return this.grid;
    }

    private boolean possibilitiesExist() {
        return grid.getGrid().entrySet().stream()
                .anyMatch(entry-> entry.getValue().stream()
                        .anyMatch(box -> box.getNumberOfPossibilities()>=1));
    }

    private void reducePossibilities() {
        while(!allBoxesAreFacts() && possibilitiesExist()) {
            List<SudokuGrid.Box> boxesWithPossibilities = findBoxesWithPossibilities();
            SudokuGrid.Box boxToReduce = selectBoxWithFewestPossibilities(boxesWithPossibilities);
            Set<Integer> possibilities = boxToReduce.getPossibilities();
            List<Integer> possibilityList = new ArrayList<>(possibilities);
            if (!possibilityList.isEmpty()) {
                Collections.shuffle(possibilityList);
                int randomPossibility = possibilityList.get(0);
                int row = boxToReduce.getRow();
                int col = boxToReduce.getCol();
                if(grid.isValidPlacement(row,col,randomPossibility)){
                    grid.setFact(row,col,randomPossibility);
                }
                removePossibilityFromRelatedBoxes(boxToReduce, randomPossibility);
            }
        }
    }

    private boolean allBoxesAreFacts() {
        return grid.getGrid().entrySet().stream()
                .allMatch(entry-> entry.getValue().stream()
                        .allMatch(box -> box.isFact()));
    }

    private List<SudokuGrid.Box> findBoxesWithPossibilities() {
        return grid.getGrid().entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .filter(box -> !box.isFact() && box.getNumberOfPossibilities() > 0))
                .collect(Collectors.toList());
    }

    private SudokuGrid.Box selectBoxWithFewestPossibilities(List<SudokuGrid.Box> boxesWithPossibilities) {
        int min = boxesWithPossibilities.stream().
                mapToInt(SudokuGrid.Box::getNumberOfPossibilities).
                min().orElse(0);
        List<SudokuGrid.Box> boxesWithMinPoss = boxesWithPossibilities.stream()
                .filter((box) -> box.getNumberOfPossibilities() == min)
                .collect(Collectors.toList());
        int randomBox = this.random.nextInt(boxesWithMinPoss.size());
        return boxesWithMinPoss.get(randomBox);
    }

    private void removePossibilityFromRelatedBoxes(SudokuGrid.Box box, int possibility) {
        int row = box.getRow();
        int col = box.getCol();
        int blockRow = row / 3 * 3;
        int blockCol = col / 3 * 3;
        IntStream.range(0, 9).forEach((i) -> {
            grid.getBox(row, i).removePossibility(possibility);
            grid.getBox(i, col).removePossibility(possibility);
            int blockRowIdx = blockRow + i / 3;
            int blockColIdx = blockCol + i % 3;
            grid.getBox(blockRowIdx, blockColIdx).removePossibility(possibility);
        });
    }
}
