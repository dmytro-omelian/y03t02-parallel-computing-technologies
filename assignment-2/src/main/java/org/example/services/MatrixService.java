package org.example.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.example.strategies.CalculationStrategy;
import org.example.tasks.MatrixTask;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class MatrixService {

    private static final Logger logger = LogManager.getLogger(MatrixService.class);
    private static final Random random = new Random();

    public MatrixService() {
    }

    public double[][] processMatrices(double[][] A, double[][] B, CalculationStrategy strategy) {
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

        logger.info(strategy.getDescription() + " was processed successfully.");
        return result;
    }


    public double[][] multiplyByNumber(double[][] A, double[][] B) {
        double number = B[0][0];
        int n = A.length;
        int m = A[0].length;
        double[][] result = new double[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                result[i][j] = A[i][j] * number;
            }
        }
        logger.info("Matrix multiplication with a number was processed successfully.");
        return result;
    }

    public double[][] min(double[][] A) {
        int n = A.length;
        double[][] result = new double[1][1];
        for (double[] doubles : A) {
            IntStream.range(0, n).forEach(j -> result[0][0] = Math.min(result[0][0], doubles[j]));
        }
        logger.info("Finding min value in Matrix was processed successfully.");
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


}
