package resources;

import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {

    double[][] adjMat;
    ArrayList<Node> input;
    ArrayList<ArrayList<Node> > hiddenLayers;
    ArrayList<Node> outputLayer;
    int nodeCount;

    Random generator;

    ArrayList<DataVector> data;

    public NeuralNetwork(ArrayList<DataVector> data, int outputLayerSize, Random generator){
        this.nodeCount = 0;
        this.data = data;
        this.generator = generator;

        for (int i = 0; i < data.get(0).size(); i++) {
            this.input.add(new Node(this.nodeCount));
            this.nodeCount++;
        }

        int outCount = this.nodeCount;
        for (int i = 0; i < outputLayerSize; i++) {
            this.outputLayer.add(new Node(outCount));
            outCount++;
        }
    }

    public void addHiddenLayer(int numberOfNodes) {
        ArrayList<Node> newHidden = new ArrayList<>(numberOfNodes);

        for (int i = 0; i < numberOfNodes; i++) {
            newHidden.add(new Node(this.nodeCount));
            this.nodeCount++;
        }

        int outCount = this.nodeCount;

        for (Node node :
                this.outputLayer) {
            node.setId(outCount);
            outCount++;
        }

        this.hiddenLayers.add(newHidden);
    }

    private double feedForward() {
        initializeWeights();
    }

    private void initializeWeights() {
        double lower = -0.5, upper = 0.5;
        int totalNodes = this.nodeCount+outputLayer.size();

        this.adjMat = new double[totalNodes][totalNodes];

        for (int i = 0; i < this.data.get(0).size(); i++) {
            double weight = lower + (this.generator.nextDouble() * (upper-lower));
            if (weight == 0) {
                weight = (this.generator.nextInt(11) > 5)? 0.05:-0.05;
            }
            this.adjMat[0][i] = weight;
        }

        
    }
}






















