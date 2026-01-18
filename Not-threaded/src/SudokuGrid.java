import java.util.*;

public class SudokuGrid {
    private Map<Integer, List<Box>> grid;

    public SudokuGrid() {
        grid = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            List<Box> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                row.add(new Box(0,i, j));
            }
            grid.put(i, row);
        }
    }

    public int getValue(int row, int col) {
        return grid.get(row).get(col).getValue();
    }

    public boolean isValidPlacement(int row, int col, int value) {
        return isValidRow(row, value) && this.isValidColumn(col, value) && this.isValidBlock(row, col, value);
    }

    private boolean isValidRow(int row, int value) {
        for(int col = 0; col < 9; ++col) {
            if (grid.get(row).get(col).getValue() == value) {
                System.out.println("Invalid row");
                return false;

            }
        }
        return true;
    }

    private boolean isValidColumn(int col, int value) {
        for(int row = 0; row < 9; ++row) {
            if (this.grid.get(row).get(col).getValue() == value) {
                System.out.println("Invalid column");
                return false;
            }
        }

        return true;
    }

    private boolean isValidBlock(int row, int col, int value) {
        int blockRow = row / 3 * 3;
        int blockCol = col / 3 * 3;

        for(int i = blockRow; i < blockRow + 3; ++i) {
            for(int j = blockCol; j < blockCol + 3; ++j) {
                if (grid.get(i).get(j).getValue() == value) {
                    System.out.println("Invalid block");
                    return false;
                }
            }
        }

        return true;
    }

    public void setFact(int row, int col, int value) {
        grid.get(row).get(col).setValue(value);
        grid.get(row).get(col).setFact(true);
        grid.get(row).get(col).clearPossibilities();
    }
    public Box getBox(int row, int col) {
        return grid.get(row).get(col);
    }

    public Map<Integer,List<Box>> getGrid() {
        return grid;
    }
    public class Box {
        private int row;
        private int col;
        private boolean isFact;
        private Set<Integer> possibilities;
        private int value;

        public Box(int value, int row, int col) {
            this.row = row;
            this.col = col;
            this.isFact = false;
            this.possibilities = new HashSet();
            this.value = value;

            for(int i = 1; i <= 9; ++i) {
                this.possibilities.add(i);
            }

        }

        public int getRow() {
            return this.row;
        }

        public int getCol() {
            return this.col;
        }

        public boolean isFact() {
            return this.isFact;
        }

        public void setFact(boolean fact) {
            this.isFact = fact;
        }

        public Set<Integer> getPossibilities() {
            return new HashSet(this.possibilities);
        }

        public int getNumberOfPossibilities() {
            return this.possibilities.size();
        }

        public void removePossibility(int possibility) {
            this.possibilities.remove(possibility);
        }

        public void clearPossibilities() {
            this.possibilities.clear();
        }

        public int getValue() {
            return value;
        }
        public void setValue(int value){
            this.value = value;
        }
    }
}