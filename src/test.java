import resources.DataGenerator;
import resources.DataVector;
import resources.InputVector;
import resources.NeuralNetwork;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jeremy on 11/26/2015.
 */
public class test {

    public static void main(String[]args)
    {
        Random generator = new Random(System.currentTimeMillis());
        NeuralNetwork net = new NeuralNetwork(new DataGenerator(generator).generateDataSet(1, 4), 1, generator);
        net.addHiddenLayer(4);
        net.initializeWeights();
        for (int i = 0; i < net.adjMat.length; i++) {
            for (int j = 0; j < net.adjMat[i].length; j++) {
                System.out.print(net.adjMat[i][j] + " ");
            }
            System.out.println();
        }
    }
}
