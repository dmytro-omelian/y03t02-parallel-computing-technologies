package com.assignment.assignment8.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MatrixService {
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
        return null;
    }

}
