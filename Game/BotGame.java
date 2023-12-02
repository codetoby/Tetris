import javax.swing.Timer;
import java.awt.event.ActionEvent;
public class BotGame extends TetrisBase {

    private int[][][] currentPermutation;
    // private Timer pieceTimer;
    private int countPiecesPlacedOnBoard = 0;

    public BotGame(int width, int height, int size, StartingMenu startingMenu) {
        super(width, height, size, startingMenu, 2000);
        // Utils.shuffleArray(input);
        // pieceTimer = new Timer(2000, new ActionListener() {
        //     public void actionPerformed(ActionEvent evt) {                
        //     }
        // });
        // pieceTimer.setRepeats(false);

        

    }

    public void updateGame(ActionEvent evt) {
        checkForFullLines(field);
        grid.setGrid(field, index);
        // if (countPiecesPlacedOnBoard == 50) {
        //     timer.stop();
        //     return;
        // }
        
        
        BestPosition info = Bot.computeScore(field, currentPermutation);

        int x = info.x;
        int y = info.y;
        int[][] piece = info.piece;

        addPiece(field, piece, index, x, y);
        grid.setGrid(field, index);

        index = (index + 1) % input.length;
        countPiecesPlacedOnBoard += 1;
        
        nextPiece(index);
        resetPosition();

    }

    @Override
    public void nextPiece(int index) {
        id = PentominoBuilder.characterToID(input[index]);
        currentPermutation = PentominosDatabase.data[id];

        nextId = PentominoBuilder.characterToID(input[(index + 1) % input.length]);
        nextPiece = PentominosDatabase.data[nextId][0];
        menu.nextPentomino.setPiece(nextPiece, nextId);

    }
}
