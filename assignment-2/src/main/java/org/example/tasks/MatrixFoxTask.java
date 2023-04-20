package org.example.tasks;

public class MatrixFoxTask extends Thread {
    double[][][][] result;
    double[][] matrix1;
    double[][] matrix2;
    int row;
    int col;
    int blockSize;

    public MatrixFoxTask(double[][][][] result, double[][] m1, double[][] m2, int row, int col, int blockSize) {
        this.result = result;
        matrix1 = m1;
        matrix2 = m2;
        this.row = row;
        this.col = col;
        this.blockSize = blockSize;
    }

    @Override
    public void run() {
        double[][] subResult = multiplyMatrices(matrix1, matrix2);
        result[row][col] = addMatrices(result[row][col], subResult); // [ [0,0] [0,0] ] => [ [0,1] [0,0] ]; => [ [0,0] [0,1] ]
    }

    public double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        int rowsInFirst = firstMatrix.length;
        int columnsInFirst = firstMatrix[0].length; // same as rows in second matrix
        int columnsInSecond = secondMatrix[0].length;
        double[][] result = new double[rowsInFirst][columnsInSecond];

        for (int i = 0; i < rowsInFirst; i++) {
            for (int j = 0; j < columnsInSecond; j++) {
                for (int k = 0; k < columnsInFirst; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        return result;
    }

    public double[][] addMatrices(double[][] matrix1, double[][] matrix2) {
        int numBlocks = matrix1.length; // number of blocks in each row/column
        double[][] result = new double[numBlocks][numBlocks];

        for (int i = 0; i < numBlocks; i++) {
            for (int j = 0; j < numBlocks; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        return result;
    }
}