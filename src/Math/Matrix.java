package Math;
public class Matrix {
    private double[][] data = null;
    private int rows = 0;
    private int columns = 0;

    public Matrix(int rows, int columns) {
        data = new double[rows][columns];
        this.rows = rows;
        this.columns = columns;
    }

    public Matrix(double[][] data) {
        this.data = data.clone();
        rows = this.data.length;
        columns = this.data[0].length;
    }

    private boolean isSquare() {
        return rows == columns;
    }

    public double getElement(int n, int m) {
        return data[n][m];
    }

    public void setValue(int n, int m, double value) {
        data[n][m] = value;
    }

    public void display() {
        System.out.print("[");
        for (int row = 0; row < rows; ++row) {
            if (row != 0) {
                System.out.print(" ");
            }

            System.out.print("[");

            for (int col = 0; col < columns; ++col) {
                System.out.printf("%8.3f", data[row][col]);

                if (col != columns - 1) {
                    System.out.print(" ");
                }
            }

            System.out.print("]");

            if (row == rows - 1) {
                System.out.print("]");
            }

            System.out.println();
        }
    }

    public double[] getEigenvector() {
        double[] vector;
        double sum = 0;
        if (isSquare())
            vector = new double[rows];
        else
            return null;
        for (int i = 0; i < vector.length; i++) {
            vector[i] = MathUtils.getGeometricMean(data[i]);
            sum += vector[i];
        }
        for (int i = 0; i < vector.length; i++) {
            vector[i] = vector[i] / sum;
        }
        return vector;
    }
}
