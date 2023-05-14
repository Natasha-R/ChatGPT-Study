package thkoeln.st.st2praktikum.linearAlgebra;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LinearSystem {

    public static double EPSILON = 0.000001;

    private static LinearSystem linearSystem;

    private LinearSystem() {
    }

    public static LinearSystem getInstance() {
        if (LinearSystem.linearSystem == null) {
            LinearSystem.linearSystem = new LinearSystem();
        }
        return LinearSystem.linearSystem;
    }

    ;

    /**
     * A naive implementation of Gauss' Algorithm.
     * Swap, when more speed is needed.
     *
     * @return solution to the LS
     */
    public Pair<double[], Integer> solve(double[][] matrix) {
        this.checkMatrix(matrix);

        matrix = this.eliminate(matrix);

        double[] result = this.extractSolution(matrix);
        if (this.isGaussMatrixInconsistent(matrix)) {
            return Pair.of(result, 0);
        }
        if (this.hasInfiniteSolutions(matrix)) {
            return Pair.of(result, Integer.MAX_VALUE);
        }
        return Pair.of(result, 1);
    }

    private void checkMatrix(double[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("No input matrix");
        }
        if (this.isUnderSpecified(matrix)) {
            throw new IllegalArgumentException("LS is underspecified");
        }
        if (!this.allTheSameLength(matrix)) {
            throw new IllegalArgumentException("Malformed matrix");
        }
    }

    private double[][] eliminate(double[][] gaussMatrix) {
        gaussMatrix = this.goDown(gaussMatrix);
        gaussMatrix = this.goUp(gaussMatrix);

        return gaussMatrix;
    }

    private double[][] goDown(double[][] gaussMatrix) {
        gaussMatrix = this.deepCopyMatrix(gaussMatrix);
        var dimensions = dimensions(gaussMatrix);
        var numberOfVariables = dimensions.n - 1;

        for (int i = 0; i < numberOfVariables - 1; i++) {
            if (gaussMatrix[i][i] == 0) {
                for (int j = i + 1; j < numberOfVariables; j++) {
                    if (gaussMatrix[j][i] != 0) {
                        gaussMatrix[i] = this
                                .addRow(gaussMatrix[i], gaussMatrix[j]);
                        break;
                    }
                }
            }
            gaussMatrix[i] = this.normalizeRow(gaussMatrix[i], i);
            for (int j = i + 1; j < numberOfVariables; j++) {
                var factor = gaussMatrix[j][i] * -1.0;
                gaussMatrix[j] = this
                        .addRow(gaussMatrix[j], this.multiplyRow(gaussMatrix[i],
                                factor));
            }
        }
        return gaussMatrix;
    }

    private double[][] goUp(double[][] gaussMatrix) {
        gaussMatrix = this.deepCopyMatrix(gaussMatrix);
        var dimensions = dimensions(gaussMatrix);
        var numberOfVariables = dimensions.n - 1;

        for (int i = numberOfVariables - 1; i >= 0; i--) {
            gaussMatrix[i] = this.normalizeRow(gaussMatrix[i], i);
            for (int j = i - 1; j >= 0; j--) {
                var factor = gaussMatrix[j][i] * -1.0;
                gaussMatrix[j] = this
                        .addRow(gaussMatrix[j], this.multiplyRow(gaussMatrix[i],
                                factor));
            }
        }

        return gaussMatrix;
    }

    private double[] extractSolution(double[][] solvedGaussMatrix) {
        var dimensions = dimensions(solvedGaussMatrix);
        double[] result = new double[dimensions.n - 1];

        for (int i = 0; i < dimensions.n - 1; i++) {
            result[i] = solvedGaussMatrix[i][dimensions.n - 1];
        }
        return result;
    }

    private boolean isUnderSpecified(double[][] matrix) {
        var dimensions = this.dimensions(matrix);
        return dimensions.m < dimensions.n - 1;
    }

    private boolean allTheSameLength(double[][] matrix) {
        var dimensions = this.dimensions(matrix);
        for (double[] row : matrix) {
            if (row.length != dimensions.n) {
                return false;
            }
        }
        return true;
    }

    private Dimension dimensions(double[][] matrix) {
        return new Dimension(matrix.length, matrix[0].length);
    }

    private double[] normalizeRow(double[] row, int column) {
        double[] rowCopy = Arrays.copyOf(row, row.length);
        var factor = 1 / row[column];
        for (int i = column; i < row.length; i++) {
            rowCopy[i] *= factor;
        }
        return rowCopy;
    }

    private double[] multiplyRow(double[] row, double factor) {
        double[] rowCopy = Arrays.copyOf(row, row.length);
        for (int i = 0; i < rowCopy.length; i++) {
            rowCopy[i] *= factor;
        }
        return rowCopy;
    }

    private double[] addRow(double[] addend1, double[] addend2) {
        double[] addendCopy = Arrays.copyOf(addend1, addend1.length);
        for (int i = 0; i < addendCopy.length; i++) {
            addendCopy[i] += addend2[i];
        }
        return addendCopy;
    }

    private int rangOfGaussMatrix(double[][] gaussMatrix,
                                  Dimension dimension) {
        var independentRows = 0;
        for (double[] row : gaussMatrix) {
            for (double value : row) {
                if (Math.abs(value) > EPSILON) {
                    independentRows++;
                    break;
                }
            }
        }
        return independentRows;
    }

    private boolean isGaussMatrixInconsistent(double[][] solvedGaussMatrix) {
        return this.areSolutionsInconsistent(solvedGaussMatrix)
                || this.areOverSpecifiedRowsInconsistent(solvedGaussMatrix);
    }

    private boolean areOverSpecifiedRowsInconsistent(double[][] solvedGaussMatrix) {
        var dimensions = this.dimensions(solvedGaussMatrix);
        var numberOfVariables = dimensions.n - 1;
        for (int i = dimensions.n - 1; i < dimensions.m; i++) {
            var sum = 0.0;
            for (int j = 0; j < numberOfVariables; j++) {
                sum += solvedGaussMatrix[j][dimensions.n - 1] * solvedGaussMatrix[i][j];
            }
            if (sum != solvedGaussMatrix[i][dimensions.n - 1]) {
                return true;
            }
        }
        return false;
    }

    private boolean areSolutionsInconsistent(double[][] solvedGaussMatrix) {
        for (var row : solvedGaussMatrix) {
            var allZero = true;
            for (int i = 0; i < row.length - 1; i++) {
                if (Math.abs(row[i]) > EPSILON) {
                    allZero = false;
                    break;
                }
            }
            if (allZero) {
                if (Math.abs(row[row.length - 1]) > EPSILON) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasInfiniteSolutions(double[][] solvedGaussMatrix) {
        var dimensions = this.dimensions(solvedGaussMatrix);
        return this
                .rangOfGaussMatrix(solvedGaussMatrix, dimensions) < dimensions.n - 1;
    }

    private double[][] deepCopyMatrix(double[][] matrix) {
        double[][] copyMatrix = new double[dimensions(matrix).m][dimensions(matrix).n];
        for (int i = 0; i < dimensions(matrix).m; i++) {
            if (dimensions(matrix).n >= 0)
                System.arraycopy(matrix[i], 0, copyMatrix[i], 0, dimensions(matrix).n);
        }
        return copyMatrix;
    }

    private static class Dimension {
        private final int m;
        private final int n;

        public Dimension(int m, int n) {
            this.m = m;
            this.n = n;
        }
    }
}
