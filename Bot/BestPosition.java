import java.util.ArrayList;

public class BestPosition {

    public int x;
    public int y;
    public int[][] piece;
    public int score;
    public ArrayList<EmptySpace> emptySpaces;
    public BestPosition(int x, int y, int[][] piece, int score, ArrayList<EmptySpace> emptySpaces) {
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.score = score;
        this.emptySpaces = emptySpaces;
    }
    
}
