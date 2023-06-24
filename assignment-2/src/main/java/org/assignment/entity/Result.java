package org.assignment.entity;

public class Result extends Matrix {
    public Result(int numRows, int numCols) {
        super(numRows, numCols);
    }

    public static Result joinFoxBlockSplit(int numRows, int numCols, double[][][][] cBlocks) {
        Result result = new Result(numRows, numCols);
        final int blockSize = cBlocks[0][0].length;
        int numBlockRows = cBlocks.length;
        int numBlockCols = cBlocks[0].length;

        for (int i = 0; i < numBlockRows; i++) {
            for (int j = 0; j < numBlockCols; j++) {
                double[][] subMatrix = cBlocks[i][j];
                int subMatrixStartRow = i * blockSize;
                int subMatrixStartCol = j * blockSize;
                int subMatrixEndRow = Math.min(subMatrixStartRow + blockSize, numRows);
                int subMatrixEndCol = Math.min(subMatrixStartCol + blockSize, numCols);

                for (int k = subMatrixStartRow; k < subMatrixEndRow; k++) {
                    System.arraycopy(subMatrix[k - subMatrixStartRow], subMatrixStartCol, result.data[k], subMatrixStartCol, subMatrixEndCol - subMatrixStartCol);
                }
            }
        }
        return result;
    }
}
