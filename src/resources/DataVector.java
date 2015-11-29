package resources;

/**
 * this class stores the input matrix for the neural network
 * @author Eric Froese
 *
 */
public class DataVector {

    int[] matrix;
    int expected;

    public DataVector (int[] ints, int expected) {
        this.matrix = ints;
        this.expected = expected;
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

    public int getExpected() {
        return this.expected;
    }
}
