import java.util.ArrayList;
import javax.swing.JFrame;

public class Bot {

    public static void main(String[] args) {

        Bot bot = new Bot();

        int[][] board = new int[15][5];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = -1;
            }
        }

        board[14][0] = 1;
        board[14][1] = 1;
        board[14][2] = 1;
        board[14][4] = 1;

        board[13][0] = 1;
        board[13][2] = 1;
        board[13][4] = 1;

        board[12][0] = 1;
        board[12][1] = 1;
        board[12][2] = 1;
        board[12][4] = 1;

        board[11][0] = 1;
        board[11][1] = 1;

        board[10][1] = 1;
        int width = board[0].length * 50;
        int height = board.length * 50;

        // { 'L', 'I', 'U', 'V', 'W', 'Y', 'Z', 'P', 'N', 'F', 'X' };
        int id = Utils.characterToID('L');
        int[][][] permuations = PentominosDatabase.data[id];

        BestPosition bestpos = bot.computeScore(board, permuations);
        int totalScore = bot.calculatePoints(board);
        System.out.println(totalScore);
        System.out.println("Amount of permutations: " + permuations.length);
        int bestX = bestpos.x;
        int bestY = bestpos.y;
        int[][] bestPiece = bestpos.piece;
        int maxScore = bestpos.score;

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
        bot.addPiece(board, bestPiece, 2, bestX, bestY);
        grid.setGrid(board, 2, bot.emptySpaces);
        frame.setVisible(true);

    }

    private ArrayList<EmptySpace> emptySpaces;
    private double weightClearLines;
    private double weightDeadspaces;
    private double weightHeightDifference;

    public Bot() {
        this.weightClearLines = 1;
        this.weightDeadspaces = 0.25;
        this.weightHeightDifference = 0.5;
    }

    public Bot(double weightClearLines, double weightDeadspaces, double weightHeightDifference) {
        this.weightClearLines = weightClearLines;
        this.weightDeadspaces = weightDeadspaces;
        this.weightHeightDifference = weightHeightDifference;
    }

    /**
     * adds all empty spaces to the emptySpace array list
     * returns the added score of each empty position
     * 
     * @param board game board
     * @return total score of every position
     */
    public int calculatePoints(int[][] board) {

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

                    }
                    if (((j == 0 || j == cols - 1) && i != 0) || i == rows - 1) {
                        score += 1;
                    }
                    if (score > 0) {
                        totalScore += score;
                        emptySpaces.add(new EmptySpace(i, j, score));
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
    public int getScoreFromPosition(int x, int y) {
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
    public BestPosition computeScore(int[][] board, int[][][] data) {

        int totalScore = calculatePoints(board);
        double maxScore = totalScore;

        int rows = board.length;
        int cols = board[0].length;

        int bestX = -1;
        int bestY = -1;
        int[][] bestPiece = {};

        double score = 0;

        for (int[][] piece : data) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    score = 0;
                    if (board[i][j] == -1) {
                        score = checkIfPieceFitsIn(i, j, piece, board);
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
        return new BestPosition(bestX - bestPiece.length + 1, bestY - adj, bestPiece, (int) maxScore, emptySpaces);
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
    public double checkIfPieceFitsIn(int x, int y, int[][] piece, int[][] board) {
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

        double totalScore = 0;
        if (startX >= 0 && startX + rows <= board.length && startY >= 0 && startY + cols <= board[0].length) {
            if (!checkIllegalMove(board, startX, startY, piece)) {
                return 0;
            }
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

        // amount of cleared lines
        int[][] _board = Utils.deepCopy(board);
        addPiece(_board, piece, 1, startX, startY);
        int fullRows = countFullRows(_board);

        // height difference
        int boardHeightNew = firstEmptyRowFromBottom(_board);
        int boardHeightOld = firstEmptyRowFromBottom(board);
        int diffrence = boardHeightNew - boardHeightOld;

        // // count deadspaces
        int deadspaces = countDeadSpace(_board);

        totalScore += weightClearLines * fullRows;
        totalScore -= weightHeightDifference * diffrence;
        totalScore -= weightDeadspaces * deadspaces;

        return totalScore;
    }

    public boolean checkIllegalMove(int[][] board, int x, int y, int[][] piece) {
        int rows = piece.length;
        int cols = piece[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (piece[i][j] == 1) {
                    int startX = x + i;
                    int startY = y + j;

                    for (int k = 0; k < startX; k++) {
                        if (board[k][startY] != -1) {
                            return false;
                        }
                    }
                }
                
            }
        }
        return true;
    }

    public int countDeadSpace(int[][] board) {

        int rows = board.length;
        int cols = board[0].length;

        int deadspaces = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == -1) {
                    deadspaces += checkSpace(board, rows, cols, i, j);
                }
            }
        }

        return deadspaces;
    }

    public int checkSpace(int[][] board, int boardHeight, int boardWidth, int i, int j) {

        int[][] directions = {
                { 1, 0 },
                { -1, 0 },
                { 0, -1 },
                { 0, 1 }
        };

        for (int[] direction : directions) {
            int newI = i + direction[0];
            int newJ = j + direction[1];

            if (newI < 0 || newI >= boardHeight || newJ < 0 || newJ >= boardWidth) {
                continue;
            }
            if (board[newI][newJ] == -1) {
                return 0;
            }
        }
        return 1;
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
                cascadeGravity(field, line);
                checkForFullLines(field);

            }
        }
    }

    public int firstEmptyRowFromBottom(int[][] board) {

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

    public int countFullRows(int[][] field) {
        int fullRows = 0;
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
                cascadeGravity(field, line);
                countFullRows(field);
                fullRows += 1;
            }
        }
        return fullRows;
    }

    public void cascadeGravity(int[][] board, int line) {
        int rows = board.length;
        int cols = board[0].length;

        for (int i = line; i >= 0; i--) {
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

    public void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] == 1) {
                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }
}
