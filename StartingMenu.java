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
    private Experiments experiments;

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
        setSize(width, height + 100);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(new Color(150, 150, 150));

        ImageIcon startMenuPic = new ImageIcon("./Assets/startMenuPicture.png");
        JLabel logo = new JLabel(startMenuPic);

        JLabel switchJLabel = new JLabel("Random Sequence");
        ToggleSwitch switchRandomSequence = new ToggleSwitch();
        switchJLabel.setLabelFor(switchRandomSequence);

        JButton botVersionButton = new JButton("Launch Tetris Bot");
        botVersionButton.setBorderPainted(false);
        botVersionButton.setBackground(new Color(200, 200, 200));
        botVersionButton.setPreferredSize(new Dimension(184, 30));
        botVersionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setResizable(true);
                botVersion = new BotGame(width, height, size, StartingMenu.this, switchRandomSequence.isActivated());
                switchToPanel(botVersion);
                botVersion.initilize();
            }
        });

        JButton botExperiments = new JButton("Bot Experiments");
        botExperiments.setBorderPainted(false);
        botExperiments.setBackground(new Color(200, 200, 200));
        botExperiments.setPreferredSize(new Dimension(184, 30));
        botExperiments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setResizable(true);
                setVisible(false);
                experiments = new Experiments(width, height, size, StartingMenu.this,
                        switchRandomSequence.isActivated());
                experiments.setVisible(true);
                experiments.initialize();
            }
        });

        JButton humanVesrionButton = new JButton("Launch Tetris Game");
        humanVesrionButton.setBorderPainted(false);
        humanVesrionButton.setBackground(new Color(200, 200, 200));
        humanVesrionButton.setPreferredSize(new Dimension(184, 30));
        humanVesrionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setResizable(true);
                humanVersion = new TetrisGame(width, height, size, StartingMenu.this,
                        switchRandomSequence.isActivated());
                switchToPanel(humanVersion);
                humanVersion.initilize();

            }
        });
        Dimension size = new Dimension(50, 25);
        switchRandomSequence.setPreferredSize(size);
        switchRandomSequence.setMinimumSize(size);
        switchRandomSequence.setMaximumSize(size);

        HighScoreList list = new HighScoreList();

        list.setPreferredSize(new Dimension(184, 445));

        setLayout(new FlowLayout());
        add(logo);
        add(list);
        add(botVersionButton);
        add(humanVesrionButton);
        add(botExperiments);
        add(switchJLabel);
        add(switchRandomSequence);
        setVisible(true);
    }

    private void switchToPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
    }
}
