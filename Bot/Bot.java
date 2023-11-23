import java.util.ArrayList;
import javax.swing.JFrame;

public class Bot {

    public static ArrayList<EmptySpace> emptySpaces;

    public static void main(String[] args) {

        int[][] board = {
                { 1, 1, 1, 1, -1 },
                { 1, 1, 1, 1, -1 },
                { 1, 1, 1, 1, -1 },
                { 1, 1, 1, 1, -1 },
                { 1, 1, 1, 1, -1 },
                { 1, 1, 1, 1, -1 },
                { 1, 1, 1, 1, -1 },
                { 1, 1, 1, 1, -1 },
        };

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = -1;
            }
        }

        // char[] input = { 'L', 'I', 'U', 'V', 'W', 'Y', 'Z', 'P', 'N', 'F', 'X' };
        int id = Utils.characterToID('L');
        int[][][] permuations = PentominosDatabase.data[id];
        

        BestPosition bestpos = computeScore(board, permuations);
        int totalScore = calculatePoints(board);
        // System.out.println(totalScore);
        System.out.println("Amount of permutations: " + permuations.length);
        int bestX = bestpos.x;
        int bestY = bestpos.y;
        int[][] bestPiece = bestpos.piece;
        int maxScore = bestpos.score;

        int width = board[0].length * 50;
        int height = board.length * 50;

        System.out.println("Best Position (x, y): (" + bestX + "," + bestY + ")");
        System.out.println("Best Score: " + (totalScore - maxScore));
        System.out.println("Best Piece: ");
        Utils.printMatrix(bestPiece);

        JFrame frame = new JFrame();
        frame.setTitle("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width + 13, height + 35);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        Grid grid = new Grid(50, width, height * 50, board);
        frame.add(grid);
        addPiece(board, bestPiece, 2, bestX, bestY);
        grid.setGrid(board, 2);
        frame.setVisible(true);
    }

    /**
     * adds all empty spaces to the emptySpace array list
     * returns the added score of each empty position
     * 
     * @param board game board
     * @return total score of every position
     */
    public static int calculatePoints(int[][] board) {

        emptySpaces = new ArrayList<>();

        int rows = board.length;
        int cols = board[0].length;

        int[][] directions = {
                { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }
        };

        int totalScore = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == -1) {
                    int score = 0;
                    for (int[] direction : directions) {
                        int newI = i + direction[0];
                        int newJ = j + direction[1];

                        if (newJ >= 0 && newJ < cols && newI >= 0 && newI < rows) {
                            if (board[newI][newJ] != -1) {
                                score += 1;
                            }
                        }
                        // } else if (i == rows - 1) {
                        //     score += 1;
                        // }

                    }
                    if (((j == 0 || j == cols - 1) && i != 0) || i == rows - 1) {
                        score += 1;
                    }
                    if (score > 0) {
                        totalScore += score;
                        emptySpaces.add(new EmptySpace(i, j, score));
                        System.out.println("x: " + i + ", y: " + j + " Score: " + score);
                    }
                }
            }
        }

        return totalScore;
    }

    /**
     * get the score of a specific position
     * 
     * @param x x positon
     * @param y position
     * @return score of the position (x,y)
     */
    public static int getScoreFromPosition(int x, int y) {
        for (int i = 0; i < emptySpaces.size(); i++) {
            EmptySpace space = emptySpaces.get(i);
            if (space.x == x && space.y == y) {
                return space.score;
            }
        }
        return 0;
    }

    /**
     * Evaluates the best permuation of a piece to a specific
     * postion
     * 
     * @param board game board
     * @param data  all permutation of a piece
     */
    public static BestPosition computeScore(int[][] board, int[][][] data) {

        int totalScore = calculatePoints(board);
        int maxScore = totalScore;

        int rows = board.length;
        int cols = board[0].length;

        int bestX = -1;
        int bestY = -1;
        int[][] bestPiece = {};

        int score;

        for (int[][] piece : data) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    score = 0;
                    if (board[i][j] == -1) {
                        score = checkIfPieceFitsIn(i, j, piece, board);
                        // if (score != 0) {
                        // System.out.println(score);
                        // }
                        if (totalScore - score <= maxScore) {
                            maxScore = totalScore - score;
                            bestX = i;
                            bestY = j;
                            bestPiece = piece;
                        }
                    }
                }
            }
        }

        int adj = 0;
        int lastRow = bestPiece.length - 1;
        for (int i = 0; i < bestPiece[0].length; i++) {
            if (bestPiece[lastRow][i] == 1) {
                adj = i;
                break;
            }
        }

        return new BestPosition(bestX - bestPiece.length + 1, bestY - adj, bestPiece, maxScore);
    }

    /**
     * checks if a piece fits into an empty space
     * returns the score if the piece fits into the space
     * 
     * @param x     starting x postion
     * @param y     starting y position
     * @param piece piece that we are trying to fit in
     * @param board game board
     * @return total score for the specific position
     */
    public static int checkIfPieceFitsIn(int x, int y, int[][] piece, int[][] board) {
        int rows = piece.length;
        int cols = piece[0].length;
        int adj = 0;
        for (int i = 0; i < cols; i++) {
            if (piece[rows - 1][i] != 0) {
                adj = i;
                break;
            }
        }

        int startX = x - rows + 1;
        int startY = y - adj;
        int totalScore = 0;
        if (startX >= 0 && startX + rows <= board.length && startY >= 0 && startY + cols <= board[0].length) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (piece[i][j] == 1) {
                        if (board[i + startX][j + startY] != -1) {
                            return 0;
                        }
                        int score = getScoreFromPosition(startX + i, startY + j);
                        totalScore += score;
                    }
                }
            }
        } else {
            return 0;
        }

        // System.out.println("Starting X: " + startX + ", Starting Y: " + startY);

        int[][] _board = Utils.deepCopy(board);
        addPiece(_board, piece, 1, startX, startY);
        int fullRows = countFullRows(_board);
        totalScore += (fullRows * 10);

        int boardHeightNew = firstEmptyRowFromBottom(_board);
        int boardHeightOld = firstEmptyRowFromBottom(board);
        int diffrence = boardHeightNew - boardHeightOld;
        totalScore -= (diffrence);

        return totalScore;
    }

    public static int firstEmptyRowFromBottom(int[][] board) {

        int rows = board.length;
        int cols = board[0].length;

        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] != -1) {
                    return rows - i - 1;
                }
            }
        }
        return rows;
    }

    public static int countFullRows(int[][] field) {
        int fullRows = 0;
        for (int k = field.length - 1; k >= 0; k--) {
            boolean full = true;
            for (int j = 0; j < field[0].length; j++) {
                if (field[k][j] == -1) {
                    full = false;
                    break;
                }
            }
            if (full) {
                fullRows += 1;
            }
        }
        return fullRows;
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
}
