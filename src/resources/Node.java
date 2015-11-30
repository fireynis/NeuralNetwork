package resources;

import java.util.ArrayList;

public class Node {

    public int id;
    public double errSig;
    public double currentOutput;

    public Node (int id) {
        this.id = id;
        this.errSig = 0.0;
    }

    public double summation(ArrayList<Double> results) {

        double sum = 0;

        for (int i = 0; i < results.size(); i++) {
            sum += results.get(i);
        }
        return sum;
    }

    public double activationFunctionSigmoid(double sum) {
        this.currentOutput = 1.0 / (1.0 + Math.pow(Math.E, (-1.0 * sum)));
        return this.currentOutput;
    }

    public double getErrorSignal() {
        return this.errSig;
    }

    public double sumAndsigmoid(ArrayList<Double> input) {
        double sum = summation(input);
        return activationFunctionSigmoid(sum);
    }

    public void setId(int id) {
        this.id = id;
    }
}
