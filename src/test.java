import resources.*;

import java.util.Random;

public class test {

    public static void main(String[]args)
    {
        long seed = System.currentTimeMillis(); //Perfect seed = 1448893265937
        Random generator = new Random(seed);
        DataGenerator data = new DataGenerator(generator);
        double learningRate = 0.1;

        NeuralNetwork net = new NeuralNetwork(data.generateDataSet(100, 4), generator, learningRate);
        net.addHiddenLayer(8);
        net.run();

        System.out.println();
        System.out.println("The seed utilized was "+seed+" with 8 hidden nodes and a learning rate of "+learningRate);
    }
}
