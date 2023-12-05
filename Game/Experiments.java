public class Experiments {

    public static void main(String[] args) {
        double[] weights = {0.25, .5, .75, 1};
        String[] names = {"Amount of Cleared Lines: ", "Height Difference: ", "Amount of Deadspaces: "};

        double defaultWeight = 0;

    System.out.println(names[0]);
    for (double weight : weights) {
        System.out.println("Weight: " + weight);
        Bot bot = new Bot(weight, defaultWeight, defaultWeight);
    }

    System.out.println(names[1]);
    for (double weight : weights) {
        System.out.println("Weight: " + weight);
        Bot bot = new Bot(defaultWeight, weight, defaultWeight);
    }

    System.out.println(names[2]);
    for (double weight : weights) {
        System.out.println("Weight: " + weight);
        Bot bot = new Bot(defaultWeight, defaultWeight, weight);
    }
        
}
    
}
