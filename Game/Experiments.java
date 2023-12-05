import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

public class Experiments extends JFrame {

    private int width;
    private int height;
    private int size;
    private StartingMenu startingMenu;
    public ExperimentBot experimentBot;
    private char[] input = { 'N', 'V', 'L', 'P', 'X', 'I', 'W', 'F', 'Y', 'Z', 'U', 'T' };
    double[] weights = { 0.25, 0.5, 0.75, 1 };
    String[] names = { "Amount of Cleared Lines", "Height Difference", "Amount of Deadspaces" };
        

    public Experiments(int width, int height, int size, StartingMenu startingMenu) {
        this.width = width;
        this.height = height;
        this.size = size;
        this.startingMenu = startingMenu;
    }

    public void initialize() {
        setTitle("Bot Experiments");
        setSize(width + 300, height + 50);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        System.out.println(Arrays.toString(input));
        startExperimentSet(0);
        
    }

    private void startExperimentSet(int setNameIndex) {
        
        if (setNameIndex >= names.length) {
            System.out.println("All experiment sets completed.");
            return;
        }
        
        System.out.println(names[setNameIndex]);

        new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (double weight : weights) {
                  
                    double weightClearLines = setNameIndex == 0 ? weight : 0;
                    double weightHeightDifference = setNameIndex == 1 ? weight : 0;
                    double weightDeadspaces = setNameIndex == 2 ? weight : 0;
                    
                    final Bot bot = new Bot(weightClearLines, weightDeadspaces, weightHeightDifference);
                    experimentBot = new ExperimentBot(width, height, size, startingMenu, bot, input);
                    add(experimentBot);
                    experimentBot.initilize();
                    
                    while (experimentBot.getTimer().isRunning()) {
                        Thread.sleep(100);
                    }
                    
                    int score = experimentBot.scoreCounter;
                    System.out.print("Weight: " + weight + " ");
                    publish(score);
                    remove(experimentBot);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                for (int score : chunks) {
                    System.out.println("Score: " + score);
                }
            }

            @Override
            protected void done() {
                startExperimentSet(setNameIndex + 1);
            }
        }.execute();
    }
}

