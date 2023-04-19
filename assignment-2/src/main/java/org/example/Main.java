package org.example;

import org.example.exception.IllegalDimensionsException;
import org.example.services.MatrixService;
import org.example.strategies.CalculationStrategy;
import org.example.strategies.MatrixFoxThread;
import org.example.strategies.MultiplyOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static Result multiplyMatrixFox(double[][] matrix1, double[][] matrix2, int blockSize) throws InterruptedException {
        int rows = matrix1.length;
        int columns = matrix1[0].length;
        if (rows % blockSize != 0 || columns % blockSize != 0) {
            throw new IllegalArgumentException("The matrixes must be evenly divisible into blocks of size " + blockSize);
        }
        int numBlocks  = rows / blockSize;
        double[][] result = new double[rows][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                result[i][j] = 0;
            }
        }

        double[][][][] blocks1 = splitMatrixIntoBlocks(matrix1, blockSize);
        double[][][][] blocks2 = splitMatrixIntoBlocks(matrix2, blockSize);
        double[][][][] cBlocks = splitMatrixIntoBlocks(result, blockSize);
        List<MatrixFoxThread> threads = new ArrayList<>();

        for (int i = 0; i < numBlocks; i++) {
            for (int j = 0; j < numBlocks; j++) {
                for (int k = 0; k < numBlocks; k++) {
                    final int row = i;
                    final int col = j;
                    final int mod = (i+k)%numBlocks;
                    MatrixFoxThread thread = new MatrixFoxThread(cBlocks, blocks1[i][mod], blocks2[mod][j], row, col, blockSize);

                    threads.add(thread);
                    thread.start();
                }
            }
        }

        for (MatrixFoxThread thread : threads) {
            thread.join();
        }

        convertTo2DArray(cBlocks, result);

        return new Result(result);
    }

    public static void convertTo2DArray(double[][][][] arr, double[][] result) {
        int subMatrixSize = arr[0][0].length;
        int numSubMatrices = arr.length;
        int numRows = numSubMatrices * subMatrixSize;
        for (int i = 0; i < numSubMatrices; i++) {
            for (int j = 0; j < numSubMatrices; j++) {
                double[][] subMatrix = arr[i][j];
                int subMatrixStartRow = i * subMatrixSize;
                int subMatrixStartCol = j * subMatrixSize;
                for (int k = 0; k < subMatrixSize; k++) {
                    for (int l = 0; l < subMatrixSize; l++) {
                        result[subMatrixStartRow + k][subMatrixStartCol + l] = subMatrix[k][l];
                    }
                }
            }
        }
    }

    public static double[][][][] splitMatrixIntoBlocks(double[][] matrix, int blockSize) {
        int numBlocks = matrix.length / blockSize;
        double[][][][] blocks = new double[numBlocks][numBlocks][blockSize][blockSize];

        for (int i = 0; i < numBlocks; i++) {
            for (int j = 0; j < numBlocks; j++) {
                for (int x = 0; x < blockSize; x++) {
                    for (int y = 0; y < blockSize; y++) {
                        blocks[i][j][x][y] = matrix[i*blockSize+x][j*blockSize+y];
                    }
                }
            }
        }

        return blocks;
    }

    public static void main(String[] args) throws IllegalDimensionsException, InterruptedException {
        int n = 100;

        MatrixService matrixService = new MatrixService();

        double[][] B = matrixService.generate(n, n);

        double[][] MC = matrixService.generate(n, n);

        double[][] foxResult = multiplyMatrixFox(B, MC, 25).getResult();
        print("result: ", foxResult);


        CalculationStrategy multiplication = new MultiplyOperation();

        long start = System.currentTimeMillis();

        double[][] E = matrixService.processMatrices(B, MC, multiplication);

        long end = System.currentTimeMillis();

        print("E=", E);
        System.out.println("Time: " + (end - start) + " ms");
    }

    private static void print(String message, double[][] result) {
        System.out.println(message);
        System.out.println("n=" + result.length + " m=" + result[0].length);
        for (double[] doubles : result) {
            Arrays.stream(doubles).mapToObj(aDouble -> aDouble + " ").forEach(System.out::print);
            System.out.println();
        }
    }

    static class Result {
        private double[][] result;

        public Result(double[][] result) {
            this.result = result;
        }

        public double[][] getResult() {
            return result;
        }
    }

}
