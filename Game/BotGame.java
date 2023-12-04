import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Timer;

public class BotGame extends TetrisBase {

    private int[][][] currentPermutation;
    private Timer pieceTimer;

    public BotGame(int width, int height, int size, StartingMenu startingMenu) {
        super(width, height, size, startingMenu, 10);
        // Utils.shuffleArray(input);
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
            placePieceOnField(piece, field);
            grid.setGrid(field, id);

            // prepareNextPiece();
            checkForFullLines(field);
            // grid.setGrid(field, id);
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

    
    public void checkForFullLine(int[][] field) {
        // Utils.printMatrix(field);
        grid.setGrid(field, id);
        pieceTimer.stop();

        AtomicInteger linesToClear = new AtomicInteger();
        AtomicInteger linesCleared = new AtomicInteger();

        for (int k = field.length - 1; k >= 0; k--) {
            boolean full = true;
            for (int j = 0; j < field[0].length; j++) {
                if (field[k][j] == -1) {
                    full = false;
                    break;
                }
            }
            if (full) {
                linesToClear.getAndIncrement();
                final int line = k;
                for (int l = 0; l < field[0].length; l++) {
                    field[line][l] = -1;
                }

                Timer delay = new Timer(200, e -> {
                    grid.setGrid(field, id);
                    cascadeGravity(field);
                    grid.setGrid(field, id);
                    checkForFullLines(field);
                    menu.score.incrementScore();
                });
                delay.setRepeats(false);
                delay.start();
                while(delay.isRunning());
                pieceTimer.start();
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
