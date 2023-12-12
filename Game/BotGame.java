import java.util.Arrays;

public class BotGame extends TetrisBase {

    private int[][][] currentPermutation;
    private Bot bot;

    public BotGame(int width, int height, int size, StartingMenu startingMenu, boolean randomSequence) {
        super(width, height, size, startingMenu, 200);  
        bot = new Bot(1, 1, 0);
        if (randomSequence) {
            Utils.shuffleArray(input);
        }
        System.out.println(Arrays.toString(input));
    }

    @Override
    public void updateGame() {
        handlePieceMovement();
    }

    private void handlePieceMovement() {

        clearBoard(field, prevPiece, tempEntryX, tempEntryY, id);
        tempEntryY = entryY;
        tempEntryX = entryX;
        prevPiece = piece;

        addPiece(field, piece, id, entryX, entryY);
        grid.setGrid(field, id);

        if (entryX + piece.length > (height / size) || checkCollision(field, piece, entryX, entryY, id)) {
            addPiece(field, piece, id, entryX, entryY);
            grid.setGrid(field, id);
            checkForFullLines(field);

            if (checkGameEnds(field, id)) {
                timer.stop();
                return;
            }
            prepareNextPiece();
        } else {
            entryX++;
            grid.setGrid(field, id);
        }
    }

    private void prepareNextPiece() {
        index = (index + 1) % input.length;
        resetPosition();
        nextPiece(index);
    }


    
    /** 
     * @param index
     */
    @Override
    public void nextPiece(int index) {

        id = PentominoBuilder.characterToID(input[index]);
        currentPermutation = PentominosDatabase.data[id];

        BestPosition info = bot.computeScore(field, currentPermutation);
        int y = info.y;
        piece = info.piece;
        prevPiece = Utils.deepCopy(piece);
        grid.setGrid(field, y, info.emptySpaces);
        entryY = y;

        nextId = PentominoBuilder.characterToID(input[(index + 1) % input.length]);
        nextPiece = PentominosDatabase.data[nextId][0];
        menu.nextPentomino.setPiece(nextPiece, nextId);
    }
}
