package thkoeln.st.st2praktikum.map;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;

@Component
public class LinearSystem {

    public static double EPSILON = 0.000001;

    /**
     * A naive implementation of Gauss' Algorithm.
     * Swap, when more speed is needed.
     *
     * @return solution to the LS
     */
    public Pair<double[], Integer> solve(double[][] matrix) {
        matrix = deepCopyMatrix(matrix);
        this.checkMatrix(matrix);
        var dimensions = dimensions(matrix);
        var numberOfVariables = dimensions.n - 1;

        BiFunction<double[], Integer, double[]> normalizeRow = (double[] row, Integer column) -> {
            double[] rowCopy = Arrays.copyOf(row, row.length);
            var factor = 1 / row[column];
            for (int i = column; i < row.length; i++) {
                rowCopy[i] *= factor;
            }
            return rowCopy;
        };

        BiFunction<double[], Double, double[]> multiplyRow = (double[] row, Double factor) -> {
            double[] rowCopy = Arrays.copyOf(row, row.length);
            for (int i = 0; i < rowCopy.length; i++) {
                rowCopy[i] *= factor;
            }
            return rowCopy;
        };

        BiFunction<double[], double[], double[]> addRow = (double[] addend1, double[] addend2) -> {
            double[] addendCopy = Arrays.copyOf(addend1, addend1.length);
            for (int i = 0; i < addendCopy.length; i++) {
                addendCopy[i] += addend2[i];
            }
            return addendCopy;
        };

        ToIntBiFunction<double[][], Dimension> rangOfGaussMatrix = (double[][] gaussMatrix, Dimension gaussDimension) -> {
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
        };

        Predicate<double[][]> isGaussMatrixInconsistent = (double[][] gaussMatrix) -> {
            for (var row : gaussMatrix) {
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
        };

        for (int i = 0; i < numberOfVariables - 1; i++) {
            if(matrix[i][i] == 0) {
                for(int j = i +1; j < numberOfVariables; j++) {
                    if(matrix[j][i] != 0) {
                        matrix[i] = addRow.apply(matrix[i], matrix[j]);
                        break;
                    }
                }
            }
            matrix[i] = normalizeRow.apply(matrix[i], i);
            for (int j = i + 1; j < numberOfVariables; j++) {
                var factor = matrix[j][i] * -1.0;
                matrix[j] = addRow.apply(matrix[j], multiplyRow.apply(matrix[i], factor));
            }
        }

        for (int i = numberOfVariables - 1; i >= 0; i--) {
            matrix[i] = normalizeRow.apply(matrix[i], i);
            for (int j = i - 1; j >= 0; j--) {
                var factor = matrix[j][i] * -1.0;
                matrix[j] = addRow.apply(matrix[j], multiplyRow.apply(matrix[i], factor));
            }
        }

        double[] result = new double[dimensions.n - 1];

        for (int i = 0; i < dimensions.n - 1; i++) {
            result[i] = matrix[i][dimensions.n - 1];
        }

        if (rangOfGaussMatrix.applyAsInt(matrix, dimensions) < dimensions.n - 1) {
            return Pair.of(result, Integer.MAX_VALUE);
        }

        if (isGaussMatrixInconsistent.test(matrix)) {
            return Pair.of(result, 0);
        }

        return Pair.of(result, 1);
    }

    private double[][] deepCopyMatrix(double[][] matrix) {
        double[][] copyMatrix = new double[dimensions(matrix).m][dimensions(matrix).n];
        for (int i = 0; i < dimensions(matrix).m; i++) {
            if (dimensions(matrix).n >= 0) System.arraycopy(matrix[i], 0, copyMatrix[i], 0, dimensions(matrix).n);
        }
        return copyMatrix;
    }

    private boolean checkMatrix(double[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("No input matrix");
        }
        if (this.isUnderSpecified(matrix)) {
            throw new IllegalArgumentException("LS is underspecified");
        }
        if (!this.allTheSameLength(matrix)) {
            throw new IllegalArgumentException("Malformed matrix");
        }
        return true;
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

    private class Dimension {
        private final int m;
        private final int n;

        public Dimension(int m, int n) {
            this.m = m;
            this.n = n;
        }
    }
}
