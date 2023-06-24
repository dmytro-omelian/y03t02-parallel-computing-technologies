package org.assignment;

import java.util.Random;

public class MatrixService {
    private static final Random random = new Random();

    public void initializeMatrixWithNumber(double[][] matrix, double number) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = number;
            }
        }
    }

    public void initializeMatrixWithRandom(double[][] matrix, int low, int high) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = low + random.nextDouble() * high;
            }
        }
    }

    public void outputMatrix(double[][] matrix) {
        for (var row : matrix) {
            for (var number : row) {
                System.out.print(number + " ");
            }
            System.out.println();
        }
    }

    public double[][] performMatrixMultiplication(double[][] a, double[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;
        double[][] c = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    public Boolean validateMultiplicationResult(double[][] a, double[][] b, double[][] c) {
        double[][] groundTruthC = this.performMatrixMultiplication(a, b);
        for (int i = 0; i < groundTruthC.length; ++i) {
            for (int j = 0; j < groundTruthC[i].length; ++j) {
                if (groundTruthC[i][j] != c[i][j]) return false;
            }
        }
        return true;
    }

}
