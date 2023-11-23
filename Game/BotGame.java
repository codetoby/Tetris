import java.awt.event.*;

public class BotGame extends TetrisBase {

    private int[][][] currentPermutation;
  
    public BotGame(int width, int height, int size, StartingMenu startingMenu)  {
        super(width, height, size, startingMenu, 1000);
        Utils.shuffleArray(input);
    }

    public void updateGame(ActionEvent evt) {
        checkForFullLines(field);
        BestPosition info = Bot.computeScore(field, currentPermutation);

        int x = info.x;
        int y = info.y;
        int[][] piece = info.piece;

        
        addPiece(field, piece, index, x, y);
        grid.setGrid(field, index);
    
        index = (index + 1) % input.length;
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
