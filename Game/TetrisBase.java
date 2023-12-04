import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public abstract class TetrisBase extends JPanel {

    public static Timer timer;
    public int width;
    public int height;
    public int size;
    public Grid grid;
    public Menu menu;
    public int entryX = 0;
    public int entryY = 0;
    public int[][] piece;
    public int mutation;
    public int id;
    public int[][] field;
    public final int WIDTHMENU = 300;
    public int nextId;
    public int[][] nextPiece;
    public char[] input = { 'N', 'V', 'L', 'P', 'X', 'I', 'W', 'F', 'Y', 'Z', 'U' };
    public int index = 0;
    public int tempEntryY = entryY;
    public int tempEntryX = entryX;
    public int[][] prevPiece;
    public StartingMenu startingMenu;
    public int delay;
    public boolean keyPressed = false;

    public TetrisBase(int width, int height, int size, StartingMenu startingMenu, int delay) {
        this.width = width;
        this.height = height;
        this.size = size;
        this.startingMenu = startingMenu;
        this.delay = delay;
    }

    public void initilize() {
        startingMenu.setSize(width + WIDTHMENU, height + size);
        startingMenu.setResizable(false);
        setPreferredSize(new Dimension(width + WIDTHMENU, height));
        setMaximumSize(new Dimension(width + WIDTHMENU, height));
        setVisible(true);
        requestFocusInWindow();
        setFocusable(true);
        setLayout(new BorderLayout());

        field = new int[height / size][width / size];
        grid = new Grid(size, width, height, field);
        add(grid, BorderLayout.WEST);

        menu = new Menu(WIDTHMENU, height, this);
        add(menu, BorderLayout.EAST);

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = -1;
            }
        }

        nextPiece(index);

        // int count = 0;
        timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateGame();
            }
        });
        timer.setRepeats(false);
        timer.start();

    }

    public abstract void nextPiece(int index);

    public abstract void updateGame();

    public void resetPosition() {
        entryX = 0;
        entryY = 0;
    }

    public boolean checkGameEnds(int[][] field, int id) {
        for (int m = 0; m < field[0].length; m++) {
            if (field[0][m] == id) {
                return true;
            }
        }
        return false;
    }

    public void checkForFullLines(int[][] field) {
        for (int k = field.length - 1; k >= 0; k--) {
            boolean full = true;
            int line = k;
            for (int j = 0; j < field[0].length; j++) {
                if (field[k][j] == -1) {
                    full = false;
                    break;
                }
            }
            if (full == true) {
                for (int l = 0; l < field[0].length; l++) {
                    field[line][l] = -1;
                }
                cascadeGravity(field);

                Timer delay = new Timer(100, null);
                delay.setRepeats(false);
                delay.start();
                while(delay.isRunning());
                grid.setGrid(field, index);

                checkForFullLines(field);

                menu.score.incrementScore();
            }
        }
    }

    public void cascadeGravity(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] != -1) {
                    int x = i;
                    while (x < rows - 1 && board[x + 1][j] == -1) {
                        x++;
                    }
                    if (x != i) {
                        board[x][j] = board[i][j];
                        board[i][j] = -1;
                    }
                }
            }
        }
    }

    public boolean checkCollision(int[][] field, int[][] piece, int x, int y, int id) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] == 1) {
                    int fieldX = x + i + 1;
                    int fieldY = y + j;

                    if (fieldX < 0 || fieldX >= field.length || fieldY < 0 || fieldY >= field[0].length) {
                        return true;
                    }

                    if (field[fieldX][fieldY] != -1 && field[fieldX][fieldY] != id) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void clearBoard(int[][] field, int[][] piece, int prevX, int prevY, int id) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] == 1) {
                    int fieldX = prevX + i;
                    int fieldY = prevY + j;
                    if (fieldX >= 0 && fieldX < field.length && fieldY >= 0 && fieldY < field[0].length) {
                        if (field[fieldX][fieldY] == id) {
                            field[fieldX][fieldY] = -1;
                        }
                    }
                }
            }
        }
    }

    public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] == 1) {
                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }

    public void placePieceOnField(int[][] piece, int[][] field) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] == 1) {
                    field[entryX + i][entryY + j] = id;
                }
            }
        }
    }

    public Timer getTimer() {
        return timer;
    }

}
