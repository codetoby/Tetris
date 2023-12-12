import java.awt.*;
import javax.swing.*;

public class Score extends JPanel {

    int score = 0;
    public JLabel scoreText;
    public JLabel currentScore;

    public Score() {

        setBackground(new Color(150, 150, 150));
        setPreferredSize(new Dimension(122, 75));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        scoreText = new JLabel("Score: ");
        currentScore = new JLabel(Integer.toString(score));
        add(scoreText, gbc);
        add(currentScore, gbc);

    }

    // @Override
    // public void paintComponent(Graphics g) {

    // }

    public void incrementScore() {
        this.score += 1;
        currentScore.setText(Integer.toString(score));
        repaint();
    }
}
