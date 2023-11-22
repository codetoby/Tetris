import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighScoreList extends JPanel {
    
    public static HashMap<String, Integer> highScoreList = new HashMap<>();

    static {
        highScoreList.put("Toby", 10);
        highScoreList.put("Luke", 30);
        highScoreList.put("Mical", 20);
    }

    public HighScoreList() {
        setBackground(Color.GREEN);
        setPreferredSize(new Dimension(250, 300));
        setLayout(new FlowLayout());

        String text;

        text = "High Score List";
        add(new JLabel(text));

        Set<String> names = highScoreList.keySet();

        for (String name : names) {
            int score = highScoreList.get(name);
            text = name + " " + score;
            JLabel label = new JLabel(text);
            label.setOpaque(true);
            label.setBackground(Color.RED);
            label.setPreferredSize(new Dimension(200, 50));
            add(label);
        }

    
    }



}