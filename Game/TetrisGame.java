import java.awt.event.*;
import java.util.Arrays;

public class TetrisGame extends TetrisBase implements KeyListener {

    public TetrisGame(int width, int height, int size, StartingMenu startingMenu, boolean randomSequence) {
        super(width, height, size, startingMenu, 200);
        addKeyListener(this);
        if (randomSequence) {
            Utils.shuffleArray(input);
        }
        System.out.println(Arrays.toString(input));
    }

    public void nextPiece(int index) {
        id = Utils.characterToID(input[index]);
        piece = PentominosDatabase.data[id][mutation];
        prevPiece = Utils.deepCopy(piece);

        nextId = Utils.characterToID(input[(index + 1) % input.length]);
        nextPiece = PentominosDatabase.data[nextId][0];
        menu.nextPentomino.setPiece(nextPiece, nextId);
    }

    @Override
    public void updateGame() {
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
                timer.stop();
                return;
            }
            index = (index + 1) % input.length;
            nextPiece(index);
            resetPosition();
        } else {
            entryX++;
        }

    }

    public void moveLeft() {
        if (entryY != 0) {
            boolean canMove = true;
            for (int i = 0; i < piece.length; i++) {
                for (int j = 0; j < piece[0].length; j++) {
                    if (piece[i][j] == 1) {
                        if (field[entryX + i][entryY + j - 1] != -1
                                && field[entryX + i][entryY + j - 1] != id) {
                            canMove = false;
                            break;
                        }
                    }
                }
            }
            if (canMove == true) {
                entryY--;
                entryX--;
            }
        }
    }

    public void moveRight() {
        if (entryY + piece[0].length < (width / size)) {
            boolean canMove = true;
            for (int i = 0; i < piece.length; i++) {
                for (int j = 0; j < piece[0].length; j++) {
                    if (piece[i][j] == 1) {
                        if (field[entryX + i][entryY + j + 1] != -1
                                && field[entryX + i][entryY + j + 1] != id) {
                            canMove = false;
                            break;
                        }
                    }
                }
            }
            if (canMove == true) {
                entryY++;
                if (entryX != 0) {
                    entryX--;
                }
                
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'a':
                moveLeft();
                break;
            case 'd':
                moveRight();
                break;
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                int rows = piece.length;
                int cols = piece[0].length;
                int[][] newPiece = new int[cols][rows];

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        newPiece[j][rows - 1 - i] = piece[i][j];
                    }
                }

                int centerX = entryX + (rows - 1) / 2;
                int centerY = entryY + (cols - 1) / 2;

                int newEntryX = centerX - (cols - 1) / 2;
                int newEntryY = centerY - (rows - 1) / 2;

                if (newEntryX >= 0 && newEntryX + cols <= height / size && newEntryY >= 0
                        && newEntryY + rows <= width / size
                        && checkCollision(field, newPiece, newEntryX, newEntryY, id) == false) {

                    prevPiece = Utils.deepCopy(piece);
                    piece = newPiece;
                    entryX = newEntryX;
                    entryY = newEntryY;
                    entryX++;
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'a':
                break;
            case 'd':
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
