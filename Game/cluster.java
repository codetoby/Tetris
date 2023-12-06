import java.util.ArrayList;

public class cluster {
    static ArrayList<int[]> cells;
    public cluster(ArrayList<int[]> _cells){
        cells = _cells;
    }

    public static void moveDown(){
        for (int[] cords : cells) {
            cords[1]=+1;
        }
    }


}
