import resources.*;

import java.util.Random;

public class test {

    public static void main(String[]args)
    {
        long seed = System.currentTimeMillis();
        Random generator = new Random(seed);
        DataGenerator data = new DataGenerator(generator);
        double lower = 0.1, upper = 0.5;
        double learningRate = 0.1;

        NeuralNetwork net = new NeuralNetwork(data.generateDataSet(1000, 4), generator, learningRate);
        net.addHiddenLayer(8);

        while (!net.run()) {
            System.out.println("Reset with new variables");
            seed = System.currentTimeMillis();
            generator = new Random(seed);
            learningRate = lower + (generator.nextDouble() * (upper-lower));

            System.out.println(learningRate);
            net = new NeuralNetwork(data.generateDataSet(560, 4), generator, learningRate);
            net.addHiddenLayer(8);
        }

        System.out.println();
        System.out.println("The seed utilized was "+seed+" with 8 hidden nodes and a learning rate of "+learningRate);
    }
}
