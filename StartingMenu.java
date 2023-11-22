import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingMenu extends JFrame {

    private int width;
    private int height;
    private int size;
    private BotGame botVersion;
    private TetrisGame humanVersion;

    public StartingMenu(int width, int height, int size) {
        this.width = (width * size);
        this.height = height * size;
        this.size = size;
    }

    public void initialize() {
        setTitle("Tetris Game");
        Image icon = Toolkit.getDefaultToolkit().getImage("./Assets/icon.png");
        setIconImage(icon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height + 50);
        setLocationRelativeTo(null);
        setResizable(true);

        botVersion = new BotGame(width, height, size, this);
        humanVersion = new TetrisGame(width, height, size, this); 

     
        JButton botVersionButton = new JButton("Launch Tetris Bot");
        botVersionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel(botVersion);
                botVersion.initilize();
            }
        });

        JButton humanVesrionButton = new JButton("Launch Tetris Game");
        humanVesrionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel(humanVersion);
                humanVersion.initilize();
                
            }
        });

        setLayout(new FlowLayout());
        add(botVersionButton);
        add(humanVesrionButton);
        setVisible(true);
    }

    private void switchToPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
    }
}
