package resources;

import java.util.ArrayList;
import java.util.Random;

public class DataGenerator {

    Random generator;

    public DataGenerator(Random generator) {
        this.generator = generator;
    };

    public ArrayList<DataVector> generateDataSet(int setSize, int dataSize) {

        ArrayList<DataVector> out = new ArrayList<>(setSize);


        for (int i = 0; i < setSize; i++) {
            int parity = 0;
            int[] datums = new int[dataSize];
            for (int j = 0; j < dataSize; j++) {
                int val = this.generator.nextInt(10);
                if (val > 5) {
                    datums[j] = 1;
                    parity++;
                } else {
                    datums[j] = 0;
                }
            }
            if (parity%2 == 0) {
                out.add(new DataVector(datums, 0));
            } else {
                out.add(new DataVector(datums, 1));
            }
        }

        return out;
    }
}
