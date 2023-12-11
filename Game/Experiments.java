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
    // private char[] input = { 'I', 'X', 'Y', 'V', 'T', 'U', 'Z', 'N', 'F', 'P', 'W', 'L'};
    double[] weights = { 0, 0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75};
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

        // Utils.shuffleArray(input);
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
                        if (experimentBot.scoreCounter == 1000) {
                            experimentBot.getTimer().stop();
                        }

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

    // double weightClearLines = setNameIndex == 0 ? weight : 0;
                // double weightHeightDifference = setNameIndex == 1 ? weight : 0;
                // double weightDeadspaces = setNameIndex == 2 ? weight : 0;
//                 for (double weightClearLines = 0; weightClearLines < 2; weightClearLines = weightClearLines + 0.25) {
//                     for (double weightHeightDifference = 0; weightHeightDifference < 2; weightHeightDifference = weightHeightDifference
//                             + 0.25) {
//                         for (double weightDeadspaces = 0; weightDeadspaces < 2; weightDeadspaces = weightDeadspaces
//                                 + 0.25) {

//                             final Bot bot = new Bot(weightClearLines, weightDeadspaces, weightHeightDifference);
//                             experimentBot = new ExperimentBot(width, height, size, startingMenu, bot, input);
//                             add(experimentBot);
//                             experimentBot.initilize();

//                             while (experimentBot.getTimer().isRunning()) {
//                                 Thread.sleep(100);
//                             }

//                             int score = experimentBot.scoreCounter;
//                             String outputString = "weightClearLines: " + weightClearLines + " weightHeightDifference: "
//                                     + weightHeightDifference + " weightDeadspaces: " + weightDeadspaces + "    ";
//                             System.out.print(outputString);
//                             saveResult(outputString);
//                             publish(score);
//                             remove(experimentBot);
//                         }
//                     }
//                 }

//                 return null;
//             }
// }

