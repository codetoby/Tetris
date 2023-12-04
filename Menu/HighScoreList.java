import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

        try {
            readScores();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Map<String, Integer> sortedScores = highScoreList.entrySet()//sorting the entries by their scores
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .collect(Collectors.toMap(
            Map.Entry::getKey, Map.Entry::getValue, 
            (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        Set<String> names = sortedScores.keySet();

        JTextArea playerName = new JTextArea();
        playerName.setPreferredSize(new Dimension(130, 20));
        playerName.setBorder(new LineBorder(new Color(200,200,200), 2));

        JLabel textAreaNameLabel = new JLabel("Name");
        textAreaNameLabel.setLabelFor(playerName);
        
        JTextArea playerScore = new JTextArea();
        playerScore.setPreferredSize(new Dimension(130, 20));
        playerScore.setBorder(new LineBorder(new Color(200,200,200), 2));

        JLabel textAreaScoreLabel = new JLabel("Score");
        textAreaScoreLabel.setLabelFor(playerScore);

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
                        saveScore(name, score);
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
        add(textAreaNameLabel);
        add(playerName);
        add(textAreaScoreLabel);
        add(playerScore);
        
        for (String name : names) {
            int score = sortedScores.get(name);
            text = " "+ name + " " + score;
            JLabel label = new JLabel(text);
            label.setName("Entry");
            label.setOpaque(true);
            label.setBackground(new Color(200, 200, 200));
            label.setPreferredSize(new Dimension(170, 50));
            add(label);
        }
    
    }
    public static void saveScore(String name, int score) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("./Assets/Highscores.txt", true));
        String output = name + " " + String.valueOf(score) + "\n";
        writer.append(output);
        writer.close();
    }

    public static void readScores() throws IOException{
        Scanner reader = new Scanner(new File("./Assets/Highscores.txt"));
        if(!reader.hasNext()) {
            reader.close();
            return;   
        }
        while (reader.hasNextLine()) {
            String nextLine = reader.nextLine();
            int space = nextLine.indexOf(" ");
            String name =  nextLine.substring(0, space);
            int score = Integer.parseInt(nextLine.substring(space+1));
            if(!highScoreList.containsKey(name) ||(highScoreList.containsKey(name) && highScoreList.get(name)< score)){
                highScoreList.put(name, score);
            }
        }
        reader.close();
    }


}