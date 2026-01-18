import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SudokuTransformations {
    public static char[][] stringToGrid(String board) {
        char[][] grid = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = board.charAt(i * 9 + j);
            }
        }
        return grid;
    }

    public static String gridToString(char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(grid[i][j]);
            }
        }
        return sb.toString();
    }

    public static char[][] rotate90(char[][] grid) {
        char[][] rotated = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                rotated[j][9 - 1 - i] = grid[i][j];
            }
        }
        return rotated;
    }

    public static char[][] flipHorizontal(char[][] grid) {
        char[][] flipped = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                flipped[i][9 - 1 - j] = grid[i][j];
            }
        }
        return flipped;
    }

    public static char[][] flipVertical(char[][] grid) {
        char[][] flipped = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                flipped[9 - 1 - i][j] = grid[i][j];
            }
        }
        return flipped;
    }

    public static List<String> getAllRotations(String board) {
        char[][] grid = stringToGrid(board);
        List<String> rotations = new ArrayList<>();
        char[][] rotated90 = rotate90(grid);
        char[][] rotated180 = rotate90(rotated90);
        char[][] rotated270 = rotate90(rotated180);
        rotations.add(gridToString(grid));
        rotations.add(gridToString(rotated90));
        rotations.add(gridToString(rotated180));
        rotations.add(gridToString(rotated270));
        return rotations;
    }

    public static List<String> getAllFlips(String board) {
        char[][] grid = stringToGrid(board);
        List<String> flips = new ArrayList<>();
        flips.add(gridToString(flipHorizontal(grid)));
        flips.add(gridToString(flipVertical(grid)));
        return flips;
    }

    public static List<String> getAllTransformations(String board) {
        List<String> transformations = new ArrayList<>();
        transformations.addAll(getAllRotations(board));
        for (String rotatedBoard : getAllRotations(board)) {
            transformations.addAll(getAllFlips(rotatedBoard));
        }
        return transformations;
    }

    public static String getSimplestForm(String board) {
        List<String> transformations = getAllTransformations(board);
        return Collections.min(transformations);
    }
}
