public class ExperimentBot extends TetrisBase {

    private int[][][] currentPermutation;
    private Bot bot;
    private int pieceCounter;
    public int scoreCounter = 0;
    public boolean twelve;
    public boolean countScore;

    public ExperimentBot(int width, int height, int size, StartingMenu startingMenu, Bot bot, char[] input, boolean twelve, boolean countScore) {
        super(width, height, size, startingMenu, 1);  
        this.bot = bot;
        this.input = input;
        this.pieceCounter = 0;
        this.twelve = twelve;
        this.countScore = countScore;
    }

    @Override
    public void updateGame() {
        handlePieceMovement();
    }

    public void handlePieceMovement() {
        if (twelve && this.pieceCounter == input.length) {
            timer.stop();
        }

        if (countScore && scoreCounter >= 60) {
            timer.stop();
        }
        
        clearBoard(field, prevPiece, tempEntryX, tempEntryY, id);
        tempEntryY = entryY;
        tempEntryX = entryX;
        prevPiece = piece;

        addPiece(field, piece, id, entryX, entryY);
        grid.setGrid(field, id);

        if (entryX + piece.length > (height / size) || checkCollision(field, piece, entryX, entryY, id)) {
            addPiece(field, piece, id, entryX, entryY);
            pieceCounter++;
            grid.setGrid(field, id);

            checkForFullLines(field);
            if (checkGameEnds(field, id)) {
                timer.stop();
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
        grid.setGrid(field, id);

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

                gravity(field, line);
                checkForFullLines(field);
                menu.score.incrementScore();

                this.scoreCounter += 1;
        
            }
        }
    }


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
