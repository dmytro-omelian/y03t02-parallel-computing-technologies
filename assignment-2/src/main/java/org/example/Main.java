package org.example;

import org.example.services.MatrixService;
import org.example.strategies.CalculationStrategy;
import org.example.strategies.MultiplyOperation;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws Exception {
        int n = 25;

        MatrixService matrixService = new MatrixService();

        double[][] B = matrixService.generate(n, n);

        double[][] MC = matrixService.generate(n, n);

        long start = System.currentTimeMillis();
        double[][] foxResult = matrixService.multiplyMatrixFox(B, MC, 25).getResult();
        long end = System.currentTimeMillis();

        print("result: ", foxResult);
        System.out.printf("Fox Time: %d ms\n", (end - start));

        CalculationStrategy multiplication = new MultiplyOperation();

        start = System.currentTimeMillis();
        double[][] E = matrixService.processMatrices(B, MC, multiplication);
        end = System.currentTimeMillis();

        System.out.println("Result valid: " + matrixesAreSame(foxResult, E));
        print("E=", E);
        System.out.printf("Time: %d ms\n", (end - start));
    }

    private static Boolean matrixesAreSame(double[][] foxResult, double[][] e) {
        if (foxResult.length != e.length || foxResult[0].length != e[0].length) {
            return false;
        }
        final DecimalFormat df = new DecimalFormat("0.00");
        int rows = foxResult.length;
        int columns = foxResult[0].length;
        for (int i = 0; i < rows; ++ i) {
            for (int j = 0; j < columns; ++ j) {
                if (!Objects.equals(df.format(foxResult[i][j]), df.format(e[i][j]))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void print(String message, double[][] result) {
        System.out.println(message);
        System.out.println("n=" + result.length + " m=" + result[0].length);
        for (double[] doubles : result) {
            Arrays.stream(doubles).mapToObj(aDouble -> aDouble + " ").forEach(System.out::print);
            System.out.println();
        }
    }
}
