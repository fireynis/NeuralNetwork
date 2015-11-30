package resources;

/**
 * this class stores the input matrix for the neural network
 * @author Eric Froese
 *
 */
public class InputVector {

    double[] matrix;

    public InputVector() {}

    public InputVector(double...args) {
        this.matrix = args;
    }

    public int size() {
        return this.matrix.length;
    }

    public double get( int i ) {
        if (i < this.matrix.length)
            return this.matrix[i];
        else {
            System.out.println("Sorry, your array index is out of bounds SON!!!");
            return 8008135;
        }
    }

    public void add ( int i , double val ) {
        if (i < this.matrix.length)
            this.matrix[i] = val;
        else {
            System.out.println("Sorry, your array index is out of bounds SON!!!");
        }
    }
}
