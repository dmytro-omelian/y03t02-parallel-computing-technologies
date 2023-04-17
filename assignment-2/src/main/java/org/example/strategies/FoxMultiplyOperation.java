package org.example.strategies;

public class FoxMultiplyOperation {

    // Matrix multiplication using the Fox algorithm
    public static double[][] calculate(double[][] A, double[][] B, int blockSize) {
        int n = A.length;
        int p = A[0].length;
        int q = B[0].length;

        // Create the submatrices for A and B
        double[][][] A_sub = createSubmatrices(A, blockSize);
        double[][][] B_sub = createSubmatrices(B, blockSize);

        // Create the submatrices for C
        double[][][] C_sub = new double[n / blockSize][n / blockSize][blockSize * blockSize];

        // Initialize the submatrices for C
        for (int i = 0; i < n / blockSize; i++) {
            for (int j = 0; j < n / blockSize; j++) {
                for (int k = 0; k < blockSize * blockSize; k++) {
                    C_sub[i][j][k] = 0.0;
                }
            }
        }

        // Calculate the submatrices for C using the Fox algorithm
        for (int i = 0; i < n / blockSize; i++) {
            for (int j = 0; j < n / blockSize; j++) {
                for (int k = 0; k < n / blockSize; k++) {
                    for (int x = 0; x < blockSize; x++) {
                        for (int y = 0; y < blockSize; y++) {
                            for (int z = 0; z < blockSize; z++) {
                                int p1 = i * blockSize + x;
                                int p2 = (j + x - k + blockSize) % blockSize + k * blockSize + z;
                                int p3 = j * blockSize + y;
                                C_sub[i][j][x * blockSize + y] += A_sub[i][k][x * blockSize + z] * B_sub[k][j][z * blockSize + y];
                            }
                        }
                    }
                }
            }
        }

        // Merge the submatrices for C into one matrix
        double[][] C = new double[n][q];
        for (int i = 0; i < n / blockSize; i++) {
            for (int j = 0; j < n / blockSize; j++) {
                for (int x = 0; x < blockSize; x++) {
                    for (int y = 0; y < blockSize; y++) {
                        C[i * blockSize + x][j * blockSize + y] = C_sub[i][j][x * blockSize + y];
                    }
                }
            }
        }

        return C;
    }

    // Create the submatrices for a given matrix
    private static double[][][] createSubmatrices(double[][] M, int blockSize) {
        int n = M.length;

        double[][][] M_sub = new double[n / blockSize][n / blockSize][blockSize * blockSize];

        for (int i = 0; i < n / blockSize; i++) {
            for (int j = 0; j < n / blockSize; j++) {
                for (int x = 0; x < blockSize; x++) {
                    for (int y = 0; y < blockSize; y++) {
                        M_sub[i][j][x * blockSize + y] = M[i * blockSize + x][j * blockSize + y];
                    }
                }
            }
        }

        return M_sub;
    }

    public String getDescription() {
        return "Fox algorithm matrix multiplication.";
    }
}
