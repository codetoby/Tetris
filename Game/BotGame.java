import javax.swing.Timer;

public class BotGame extends TetrisBase {

    private int[][][] currentPermutation;
    private Timer pieceTimer;

    public BotGame(int width, int height, int size, StartingMenu startingMenu) {
        super(width, height, size, startingMenu, 500);
    }

    @Override
    public void updateGame() {
        pieceTimer = new Timer(200, e -> handlePieceMovement());

        pieceTimer.setRepeats(true);
        pieceTimer.start();
    }

    private void handlePieceMovement() {
        // timer.stop();
        clearBoard(field, prevPiece, tempEntryX, tempEntryY, id);
        tempEntryY = entryY;
        tempEntryX = entryX;
        prevPiece = piece;

        addPiece(field, piece, id, entryX, entryY);
        grid.setGrid(field, id);

        if (entryX + piece.length > (height / size) || checkCollision(field, piece, entryX, entryY, id)) {
            placePieceOnField(piece, field);

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

    @Override
    public void checkForFullLines(int[][] field) {
        for (int k = field.length - 1; k >= 0; k--) {
            boolean full = true;
            for (int j = 0; j < field[0].length; j++) {
                if (field[k][j] == -1) {
                    full = false;
                    break;
                }
            }
            if (full) {
                final int line = k;
                for (int l = 0; l < field[0].length; l++) {
                    field[line][l] = -1;
                }
                grid.setGrid(field, id);

                // Instead of a busy-wait, use ActionListener to perform actions after delay
                Timer delay = new Timer(200, e -> {
                    cascadeGravity(field);
                    grid.setGrid(field, id); // Update grid after cascading
                    checkForFullLines(field); // Check for new full lines after cascading
                    menu.score.incrementScore();
                    pieceTimer.start(); // Restart movement after cascading
                });
                delay.setRepeats(false);
                delay.start();

                pieceTimer.stop(); // Stop the piece movement while clearing lines
                return; // Exit the function to wait for the delay to finish
            }
        }
    }

    @Override
    public void nextPiece(int index) {

        id = PentominoBuilder.characterToID(input[index]);
        currentPermutation = PentominosDatabase.data[id];

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
