import resources.DataGenerator;
import resources.InputVector;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jeremy on 11/26/2015.
 */
public class test {

    public static void main(String[]args)
    {
        long seed = System.currentTimeMillis();
        Random generator = new Random(seed);
        DataGenerator data = new DataGenerator(generator);
        ArrayList<InputVector> list = data.generateDataSet(1, 4);

        for (InputVector vedt :
                list) {
            for (int i = 0; i < vedt.size(); i++) {
                System.out.println(vedt.get(i));
            }
        }
    }
}
