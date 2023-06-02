package com.assignment.assignment8.entity;

public class MatrixMultiplicationResponse {

    private final double[][] values;

    public MatrixMultiplicationResponse(double[][] values) {
        this.values = values;
    }

    public double[][] getValues() {
        return values;
    }
}
