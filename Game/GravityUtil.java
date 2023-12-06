import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class GravityUtil {
    public static ArrayList<int[]> xyUnderShape(ArrayList<int[]> coordinates) {
        Map<Integer, Integer> maxRowInColumn = new HashMap<>();

        for (int[] coordinate : coordinates) {
            int row = coordinate[0];
            int col = coordinate[1];

            if (!maxRowInColumn.containsKey(col) || row > maxRowInColumn.get(col)) {
                maxRowInColumn.put(col, row);
            }
        }

        ArrayList<int[]> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : maxRowInColumn.entrySet()) {
            result.add(new int[]{entry.getValue()+1, entry.getKey()});
        }

        return result;
    }

    public static void moveShapeDown(int[][] board, ArrayList<int[]> shape) {
        shape.sort(Comparator.comparingInt((int[] arr) -> arr[0]).reversed());
        for (int[] xy : shape) {
            System.out.println(Arrays.toString(xy));
            board[xy[0]+1][xy[1]] = board[xy[0]][xy[1]];
            board[xy[0]][xy[1]] = -1;
        }

    }

    public static boolean canMoveDown(int[][] board, ArrayList<int[]> xyUnderShape){
        int rows = board.length;
        for (int[] is : xyUnderShape) {
            if ( is[1] >= rows && board[is[0]][is[1]] == -1) {
                return false;
            }
        }
        
        return true;
    }

}
