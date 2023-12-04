import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;


public class HighScoreList extends JPanel {
    
    public static HashMap<String, Integer> highScoreList = new HashMap<>();

    public HighScoreList() {
        setBackground(new Color(150,150,150));
        setPreferredSize(new Dimension(250, 300));
        setLayout(new FlowLayout());

        String text;

        text = "High Score List";
        add(new JLabel(text));

        
        Map<String, Integer> sortedScores = highScoreList.entrySet()//sorting the entries by their scores
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .collect(Collectors.toMap(
            Map.Entry::getKey, Map.Entry::getValue, 
            (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        Set<String> names = sortedScores.keySet();

        JTextArea playerName = new JTextArea("Name");
        playerName.setPreferredSize(new Dimension(83, 20));
        playerName.setBorder(new LineBorder(new Color(200,200,200), 2));
        
        JTextArea playerScore = new JTextArea("Score");
        playerScore.setPreferredSize(new Dimension(82, 20));
        playerScore.setBorder(new LineBorder(new Color(200,200,200), 2));
        
        JButton addListing = new JButton("Add Highscore");
        addListing.setPreferredSize(new Dimension(170, 30));
        addListing.setBorderPainted(false);
        addListing.setBackground(new Color(200,200,200));
        addListing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int score = Integer.parseInt(playerScore.getText());
                    String name = playerName.getText();
                    if (!highScoreList.containsKey(name) || highScoreList.get(name)< score) {
                        highScoreList.put(name, score);
                    }
                    String listing = " "+ name + " " + score;
                    JLabel label = new JLabel(listing);
                    label.setOpaque(true);
                    label.setBackground(new Color(200, 200, 200));
                    label.setPreferredSize(new Dimension(170, 50));
                    label.setName("Entry");
                    add(label);
                    revalidate();
                    repaint();
                } catch (Exception ex) {
                    System.out.println("The textfield doesnt contain an int");
                }
            }
        });
        
        add(addListing);
        add(playerName);
        add(playerScore);
        
        for (String name : names) {
            int score = sortedScores.get(name);
            text = " "+ name + " " + score;
            System.out.println(score);
            JLabel label = new JLabel(text);
            label.setName("Entry");
            label.setOpaque(true);
            label.setBackground(new Color(200, 200, 200));
            label.setPreferredSize(new Dimension(170, 50));
            add(label);
            System.out.println(label.getName());
        }
    
    }



}