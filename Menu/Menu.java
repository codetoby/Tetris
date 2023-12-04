import java.awt.*;
import javax.swing.*;

public class Menu extends JPanel {

    public NextPentomino nextPentomino;  
    public Score score;  
    private int width;
    private int height;
    private TetrisBase tetrisBase;

    public Menu(int width, int height, TetrisBase tetrisBase) {
        this.width = width;
        this.height = height;
        this.tetrisBase = tetrisBase;
        initilize();
    }

    public void initilize() {
        setBackground(new Color(100,100,100));
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout()); 
        

        score = new Score();
        add(score);

        HighScore highScore = new HighScore();
        add(highScore);

        int[][] piece = {
            {1}
        };
        nextPentomino = new NextPentomino(piece);
        add(nextPentomino);

        // HighScoreList list = new HighScoreList();
        // add(list);

        StartStop startStop;
        startStop = new StartStop(tetrisBase);
        
        
        add(startStop);
    }


}
