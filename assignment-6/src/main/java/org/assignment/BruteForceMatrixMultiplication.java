package org.assignment;

public class BruteForceMatrixMultiplication {

    private static final int SIZE = 1000;

    public static void main(String[] args) {
        MatrixService matrixService = new MatrixService();

        double[][] a = matrixService.createRandomMatrix(SIZE);
        double[][] b = matrixService.createRandomMatrix(SIZE);
        double[][] c = matrixService.createRandomMatrix(SIZE);

        long startTime = System.nanoTime();
        performMatrixMultiplication(a, b, c);
        long endTime = System.nanoTime();

        System.out.println("Execution time: " + (endTime - startTime) + " ns");
    }

    private static void performMatrixMultiplication(double[][] a, double[][] b, double[][] c) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
    }

}