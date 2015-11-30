package resources;

import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {

    public double[][] adjMat;
    ArrayList<Node> input;
    ArrayList<ArrayList<Node> > hiddenLayers;
    ArrayList<Node> outputLayer;
    int edgeCount;
    double learningRate;

    Random generator;

    ArrayList<DataVector> data;

    public NeuralNetwork(ArrayList<DataVector> data, Random generator, double learningRate){
        this.edgeCount = data.get(0).size(); // Keeps track to create the adjacency Matrix
        this.data = data;
        this.generator = generator;
        this.input = new ArrayList<>(data.size());
        this.outputLayer = new ArrayList<>(1);
        this.hiddenLayers = new ArrayList<>();
        this.learningRate = learningRate;

        //Create the input layer since it should be the same size as input vector size
        for (int i = 0; i < data.get(0).size(); i++) {
            this.input.add(new Node(this.edgeCount));
            this.edgeCount++;
        }

        //Initialize the one output node
        int outCount = this.edgeCount;
        for (int i = 0; i < 1; i++) {
            this.outputLayer.add(new Node(outCount));
            outCount++;
        }
    }

    public void addHiddenLayer(int numberOfNodes) {
        ArrayList<Node> newHidden = new ArrayList<>(numberOfNodes);

        //Adds a new hidden layer to the ArrayList
        for (int i = 0; i < numberOfNodes; i++) {
            newHidden.add(new Node(this.edgeCount));
            this.edgeCount++;
        }

        int outCount = this.edgeCount;

        //Increment the id of the output neuron, this is for adjacency matrix
        for (Node node : this.outputLayer) {
            node.setId(outCount);
            outCount++;
        }

        this.hiddenLayers.add(newHidden);
    }

    public boolean run() {
        initializeWeights();

        double percentCorrect;
        int correctCount = 0;
        int runCount = 0;

        //This will run until it is 100%, a few times in a row. If that is the case it is likely ready.
        while (true) {
            ArrayList<DataVector> copyData = new ArrayList<>(this.data);
            copyData = train(copyData);
            percentCorrect = test(copyData);
            if (percentCorrect == 1.0) {
                correctCount++;
            } else {
                correctCount = 0;
            }
            if (correctCount > 5) {
                break;
            }
            runCount++;
            if (runCount > 10000) {
                System.out.println();
                System.out.println("Reached "+percentCorrect*100+"% correct");
                return false;
            }
        }
        System.out.println("The system has reached 100% correct");
        return true;
    }

    public double test (ArrayList<DataVector> copyData) {

        int correct = 0;

        //This takes the 30% of the test data and uses it to check the network. Randomised each iteration
        for (DataVector data : copyData) {
            feedForward(data);
            int result = (int)Math.round(outputLayer.get(0).currentOutput);
            if (data.expected == result) {
                correct++;
            }
        }
        return (double)(correct)/(double)(copyData.size());
    }

    public void feedForward(DataVector useData) {

        //For each input value, multiply it by the weight of the connection then sum and sigmoid it
        for (Node input : this.input) {
            ArrayList<Double> preInput = new ArrayList<Double>(useData.size());
            for (int j = 0; j < useData.size(); j++) {
                preInput.add((useData.get(j)*adjMat[j][input.id]));
            }
            input.sumAndsigmoid(preInput);
        }

        //Same as first, just with input to hidden layer
        for (int j = 0; j < this.hiddenLayers.size(); j++) {
            for (Node hiddenNode : this.hiddenLayers.get(j)) {
                ArrayList<Double> preInput = new ArrayList<Double>(input.size());
                for (Node inputLayer : this.input) {
                    preInput.add((inputLayer.currentOutput*adjMat[inputLayer.id][hiddenNode.id]));
                }
                hiddenNode.sumAndsigmoid(preInput);
            }
        }

        //Lastly hidden layer to output layer.
        for (Node output : this.outputLayer) {
            ArrayList<Double> preInput = new ArrayList<>(this.hiddenLayers.get(this.hiddenLayers.size() - 1).size());
            for (Node lastHidden : this.hiddenLayers.get(this.hiddenLayers.size() - 1)) {
                preInput.add(lastHidden.currentOutput * adjMat[lastHidden.id][output.id]);
            }
            output.sumAndsigmoid(preInput);
            //The error signal is saved by each node. It starts with the out put node
            output.errSig = useData.expected-output.currentOutput;
        }
    }

    public ArrayList<DataVector> train (ArrayList<DataVector> copyData) {

        //This runs the network in order, run data, then learn from data
        for (int i = 0; i < (int) (Math.floor(data.size() * 0.7)); i++) {
            DataVector useData = copyData.remove(this.generator.nextInt(copyData.size()));
            feedForward(useData);
            backPropagation();
        }

        //Returns the unrun test examples
        return copyData;
    }

    private void backPropagation() {

        ArrayList<Node> lastHidden = this.hiddenLayers.get(this.hiddenLayers.size()-1);
        Node output = this.outputLayer.get(0);

        //For each node in the hidden layer, determine error signal from outputs error
        for (Node node : lastHidden) {
            node.errSig = adjMat[node.id][output.id]*output.errSig;
        }

        //Then for each node in input layer determine its error sig using hidden layer
        for (Node node : this.input) {
            double err = 0;
            for (Node hidden : this.hiddenLayers.get(0)) {
                err += adjMat[node.id][hidden.id] * hidden.errSig;
            }
            node.errSig = err;
        }

        //Update weights, starting with data to input node path
        for (int i = 0; i < data.get(0).size(); i++) {
            for (Node inputNode : this.input) {
                this.adjMat[i][inputNode.id] = this.adjMat[i][inputNode.id]+(this.learningRate)*inputNode.errSig*inputNode.currentOutput*(1-inputNode.currentOutput);
            }
        }

        //Input to hidden node paths
        for (Node node : this.input) {
            for (Node hiddenNode : this.hiddenLayers.get(0)) {
                this.adjMat[node.id][hiddenNode.id] = this.adjMat[node.id][hiddenNode.id]+(this.learningRate)*hiddenNode.errSig*hiddenNode.currentOutput*(1-hiddenNode.currentOutput);
            }
        }

        //Hidden nodes to output paths
        for (Node startNode : this.hiddenLayers.get(this.hiddenLayers.size() - 1)) {
            for (Node outNode : this.outputLayer) {
                adjMat[startNode.id][outNode.id] = this.adjMat[startNode.id][outNode.id] = this.adjMat[startNode.id][outNode.id]+(this.learningRate)*outNode.errSig*outNode.currentOutput*(1-outNode.currentOutput);
            }
        }
    }

    public void initializeWeights() {
        double lower = -0.5, upper = 0.5;
        int totalNodes = this.edgeCount +outputLayer.size();

        this.adjMat = new double[totalNodes][totalNodes];

        for (int i = 0; i < data.get(0).size(); i++) {
            for (Node inputNode : this.input) {
                double weight = lower + (this.generator.nextDouble() * (upper-lower));
                if (weight == 0) {
                    weight = (this.generator.nextInt(11) > 5)? 0.05:-0.05;
                }
                this.adjMat[i][inputNode.id] = weight;
            }
        }

        for (Node node : this.input) {
            for (Node hiddenNode : this.hiddenLayers.get(0)) {
                double weight = lower + (this.generator.nextDouble() * (upper - lower));
                if (weight == 0) {
                    weight = (this.generator.nextInt(11) > 5) ? 0.05 : -0.05;
                }
                this.adjMat[node.id][hiddenNode.id] = weight;
            }
        }

        for (int i = 0; i < this.hiddenLayers.size(); i++) {
            for (Node startNode : this.hiddenLayers.get(i)) {
                if (!(i+1 >= this.hiddenLayers.size())) {
                    for (Node endNode : this.hiddenLayers.get(i + 1)) {
                        double weight = lower + (this.generator.nextDouble() * (upper - lower));
                        if (weight == 0) {
                            weight = (this.generator.nextInt(11) > 5) ? 0.05 : -0.05;
                        }
                        adjMat[startNode.id][endNode.id] = weight;
                    }
                }
            }
        }

        for (Node startNode : this.hiddenLayers.get(this.hiddenLayers.size() - 1)) {
            for (Node outNode : this.outputLayer) {
                double weight = lower + (this.generator.nextDouble() * (upper - lower));
                if (weight == 0) {
                    weight = (this.generator.nextInt(11) > 5) ? 0.05 : -0.05;
                }
                adjMat[startNode.id][outNode.id] = weight;
            }
        }
    }
}






















