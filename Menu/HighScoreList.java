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
        setBackground(new Color(150,150,150));
        setPreferredSize(new Dimension(250, 300));
        setLayout(new FlowLayout());

        String text;

        text = "High Score List";
        add(new JLabel(text));

        Set<String> names = highScoreList.keySet();

        for (String name : names) {
            int score = highScoreList.get(name);
            text = " "+ name + " " + score;
            JLabel label = new JLabel(text);
            label.setOpaque(true);
            label.setBackground(new Color(200, 200, 200));
            label.setPreferredSize(new Dimension(170, 50));
            add(label);
        }

    
    }



}