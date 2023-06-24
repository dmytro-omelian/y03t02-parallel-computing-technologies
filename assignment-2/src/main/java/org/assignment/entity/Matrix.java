package org.assignment.entity;

import java.util.Random;

public class Matrix {
    private final int numRows;
    private final int numCols;
    double[][] data;

    public Matrix(int size) {
        this.numRows = size;
        this.numCols = size;
        this.data = new double[numRows][numCols];
    }

    public Matrix(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.data = new double[numRows][numCols];
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void generateRandomMatrix(double minVal, double maxVal) {
        Random random = new Random();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                data[i][j] = Math.floor(minVal + (maxVal - minVal) * random.nextDouble());
            }
        }
    }

    public Matrix multiply(Matrix other) {
        if (numCols != other.numRows) {
            throw new IllegalArgumentException("Number of columns in the first matrix must match the number of rows in the second matrix.");
        }

        Matrix result = new Matrix(numRows, other.numCols);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < other.numCols; j++) {
                for (int k = 0; k < numCols; k++) {
                    result.data[i][j] += data[i][k] * other.data[k][j];
                }
            }
        }
        return result;
    }

    public boolean equals(Matrix other) {
        if (numRows != other.numRows || numCols != other.numCols) {
            return false;
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (data[i][j] != other.data[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private double[][] getSquareBlock(int startRow, int startCol, int size) {
        double[][] result = new double[size][size];
        for (int i = 0; i < size && (i + startRow < numRows); i++) {
            for (int j = 0; j < size && (j + startCol < numCols); j++) {
                result[i][j] = data[i + startRow][j + startCol];
            }
        }
        return result;
    }

    public double[][][][] getFoxBlockSplit(int blocksNumSqrt) {
        double[][][][] matrixBlocks = new double[blocksNumSqrt][blocksNumSqrt][numRows][numCols];
        final int blockSize = ceilDiv(numRows, blocksNumSqrt);
        for (int i = 0; i < blocksNumSqrt; i++) {
            for (int j = 0; j < blocksNumSqrt; j++) {
                matrixBlocks[i][j] = getSquareBlock(i * blockSize, j * blockSize, blockSize);
            }
        }
        return matrixBlocks;
    }

    public double[][][][] getFoxBlockSplit(int blocksNumSqrt, int blockSize) {
        double[][][][] matrixBlocks = new double[blocksNumSqrt][blocksNumSqrt][numRows][numCols];
        for (int i = 0; i < blocksNumSqrt; i++) {
            for (int j = 0; j < blocksNumSqrt; j++) {
                matrixBlocks[i][j] = getSquareBlock(i * blockSize, j * blockSize, blockSize);
            }
        }
        return matrixBlocks;
    }

    private int ceilDiv(int x, int y) {
        return -Math.floorDiv(-x, y);
    }
}
