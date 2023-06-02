package com.assignment.assignment8.service;

import com.assignment.assignment8.entity.MatrixTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Service
public class MatrixService {
    private static final Logger logger = LogManager.getLogger(MatrixService.class);
    private static final Random random = new Random();

    public MatrixService() {
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

    public double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int n = matrixA.length;
        int m = matrixB[0].length;

        double[][] result = new double[n][m];

        MatrixTask.ConcurrencyContext context = new MatrixTask.ConcurrencyContext(result.length);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MatrixTask task = new MatrixTask(matrixA, matrixB, result, context, this);

        forkJoinPool.invoke(task);
        forkJoinPool.shutdown();

        try {
            forkJoinPool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Matrix processing was interrupted.");
            Thread.currentThread().interrupt();
        }
        return result;
    }

    public double[][] multiply(MultipartFile matrixAFile, MultipartFile matrixBFile) throws IOException {
        double[][] matrixA = convertMultipartFileToMatrix(matrixAFile);
        double[][] matrixB = convertMultipartFileToMatrix(matrixBFile);

        return multiply(matrixA, matrixB);
    }

    public void calculate(double[][] A, double[][] B, double[][] result, int row) {
        for (int j = 0; j < B[0].length; j++) {
            double sum = 0.0;
            double error = 0.0;
            for (int k = 0; k < A[0].length; k++) {
                double x = A[row][k] * B[k][j] - error;
                double tempSum = sum + x;
                error = (tempSum - sum) - x;
                sum = tempSum;
            }
            result[row][j] = sum;
        }
    }

    private double[][] convertMultipartFileToMatrix(MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        String[] rows = content.split("\n");
        int numRows = rows.length;
        int numCols = rows[0].trim().split("\\s+").length;
        double[][] matrix = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            String[] values = rows[i].trim().split("\\s+");
            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = Double.parseDouble(values[j]);
            }
        }
        return matrix;
    }

}
