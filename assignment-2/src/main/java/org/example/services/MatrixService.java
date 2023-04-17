package org.example.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.example.exception.IllegalDimensionsException;
import org.example.strategies.CalculationStrategy;
import org.example.strategies.MultiplyOperation;
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


}
