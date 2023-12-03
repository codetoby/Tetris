import javax.swing.Timer;

public class BotGame extends TetrisBase {

    private int[][][] currentPermutation;
    private Timer pieceTimer;

    public BotGame(int width, int height, int size, StartingMenu startingMenu) {
        super(width, height, size, startingMenu, 500);
    }

    @Override
    public void updateGame() {
        pieceTimer = new Timer(500, e -> handlePieceMovement());
        pieceTimer.setRepeats(true);
        pieceTimer.start();
    }


    private void handlePieceMovement() {
        timer.stop();
        clearBoard(field, prevPiece, tempEntryX, tempEntryY, id);
        tempEntryY = entryY;
        tempEntryX = entryX;
        prevPiece = piece;

        addPiece(field, piece, id, entryX, entryY);
        grid.setGrid(field, id);

        if (entryX + piece.length > (height / size) || checkCollision(field, piece, entryX, entryY, id)) {
            placePieceOnField(piece, field);
            checkForFullLines(field);
            if (checkGameEnds(field, id)) {
                pieceTimer.stop();
                return;
            }
            prepareNextPiece();
        } else {
            entryX++;
            timer.start();
        }
    }

    private void prepareNextPiece() {
        index = (index + 1) % input.length;
        resetPosition();
        nextPiece(index);
    }

    @Override
    public void nextPiece(int index) {

        id = PentominoBuilder.characterToID(input[index]);
        currentPermutation = PentominosDatabase.data[id];

        Utils.printMatrix(field);

        BestPosition info = Bot.computeScore(field, currentPermutation);
        int y = info.y;
        piece = info.piece; 
        prevPiece = Utils.deepCopy(piece);
        entryY = y;



        nextId = PentominoBuilder.characterToID(input[(index + 1) % input.length]);
        nextPiece = PentominosDatabase.data[nextId][0];
        menu.nextPentomino.setPiece(nextPiece, nextId);
    }
}
