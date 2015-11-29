package resources;

import java.util.ArrayList;

public class Node {

    public int id;
    double errSig;

    public Node (int id) {
        this.id = id;
        this.errSig = 0.0;
    }

    public double summation(InputVector results) {

        double sum = 0;

        for (int i = 0; i < results.size(); i++) {
            sum += results.get(i);
        }
        return sum;
    }

    public double activationFunctionSigmoid(double sum) {
        return 1.0 / (1.0 + Math.pow(Math.E, (-1.0 * sum)));
    }

    public double getErrorSignal() {
        return this.errSig;
    }

    public void setId(int id) {
        this.id = id;
    }
}
