package org.assignment;

import java.util.Random;

public class MatrixService {

    private static final Random random = new Random();

    public double[][] createRandomMatrix(int size) {
        double[][] result = new double[size][size];
        for (int i = 0; i < size; ++ i) {
            for (int j = 0; j < size; ++ j) {
                double value = random.nextInt(100) + 1.0 * random.nextInt(10) / 10;
                result[i][j] = value;
            }
        }
        return result;
    }

}
