import java.awt.*;
import javax.swing.*;

public class HighScore extends JPanel{

    int score = 0;
    public JLabel scoreText;
    public JLabel currentScore;

    public HighScore() {
    
        setBackground(Color.RED);
        setPreferredSize(new Dimension(100, 75));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        scoreText = new JLabel("High Score: ");
        currentScore = new JLabel(Integer.toString(score));
        add(scoreText, gbc);
        add(currentScore, gbc);

    }    

    // @Override
    // public void paintComponent(Graphics g) {

    // }

    public void incrementScore() {
        this.score += 1;
        repaint();
    }
    
}
