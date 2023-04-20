package org.example.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.example.entity.Result;
import org.example.exception.IllegalDimensionsException;
import org.example.strategies.CalculationStrategy;
import org.example.strategies.MultiplyOperation;
import org.example.tasks.MatrixFoxTask;
import org.example.tasks.MatrixTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MatrixService {

    private static final Logger logger = LogManager.getLogger(MatrixService.class);
    private static final Random random = new Random();

    public MatrixService() {
    }

    public double[][] processMatrices(double[][] A, double[][] B, CalculationStrategy strategy) throws IllegalDimensionsException {

        if (strategy instanceof MultiplyOperation && A[0].length != B.length) {
            logger.error(String.format("Dimensions error. A: %dx%d, B: %dx%d",
                    A.length, A[0].length, B.length, B[0].length));
            throw new IllegalDimensionsException("Can't multiply matrices, please check dimensions.");
        }

        int n = A.length;
        int m = B[0].length;

        double[][] result = new double[n][m];

        MatrixTask.ConcurrencyContext context = new MatrixTask.ConcurrencyContext(result.length);
        Lock lock = new ReentrantLock();
        int nThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        for (int i = 0; i < nThreads; ++i) {
            Callable<Void> task = new MatrixTask(lock, context, A, B, result, strategy);
            executorService.submit(task);
        }
        executorService.shutdown();

        try {
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public double[][] generate(int rows, int columns) {
        double[][] result = new double[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                result[i][j] = 100 * random.nextDouble();
            }
        }
        return result;
    }


    public Result multiplyMatrixFox(double[][] matrix1, double[][] matrix2, int blockSize) throws Exception {
        int rows = matrix1.length;
        int columns = matrix1[0].length;
        if (rows % blockSize != 0 || columns % blockSize != 0) {
            throw new IllegalArgumentException("The matrixes must be evenly divisible into blocks of size " + blockSize);
        }
        int numBlocks = rows / blockSize;
        double[][] result = new double[rows][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                result[i][j] = 0;
            }
        }

        double[][][][] blocks1 = splitMatrixIntoBlocks(matrix1, blockSize);
        double[][][][] blocks2 = splitMatrixIntoBlocks(matrix2, blockSize);
        double[][][][] cBlocks = splitMatrixIntoBlocks(result, blockSize);
        List<MatrixFoxTask> threads = new ArrayList<>();

        for (int i = 0; i < numBlocks; i++) {
            for (int j = 0; j < numBlocks; j++) {
                for (int k = 0; k < numBlocks; k++) {
                    final int row = i;
                    final int col = j;
                    final int mod = (i + k) % numBlocks;
                    MatrixFoxTask thread = new MatrixFoxTask(cBlocks, blocks1[i][mod], blocks2[mod][j], row, col, blockSize);

                    threads.add(thread);
                    thread.run();
                }
            }
        }

        for (MatrixFoxTask thread : threads) {
            thread.join();
        }

        convertTo2DArray(cBlocks, result);

        return new Result(result);
    }

    public void convertTo2DArray(double[][][][] arr, double[][] result) {
        int subMatrixSize = arr[0][0].length;
        int numSubMatrices = arr.length;
        int numRows = numSubMatrices * subMatrixSize;
        for (int i = 0; i < numSubMatrices; i++) {
            for (int j = 0; j < numSubMatrices; j++) {
                double[][] subMatrix = arr[i][j];
                int subMatrixStartRow = i * subMatrixSize;
                int subMatrixStartCol = j * subMatrixSize;
                for (int k = 0; k < subMatrixSize; k++) {
                    System.arraycopy(subMatrix[k], 0, result[subMatrixStartRow + k], subMatrixStartCol + 0, subMatrixSize);
                }
            }
        }
    }

    public double[][][][] splitMatrixIntoBlocks(double[][] matrix, int blockSize) {
        int numBlocks = matrix.length / blockSize;
        double[][][][] blocks = new double[numBlocks][numBlocks][blockSize][blockSize];

        for (int i = 0; i < numBlocks; i++) {
            for (int j = 0; j < numBlocks; j++) {
                for (int x = 0; x < blockSize; x++) {
                    System.arraycopy(matrix[i * blockSize + x], j * blockSize + 0, blocks[i][j][x], 0, blockSize);
                }
            }
        }

        return blocks;
    }

}
