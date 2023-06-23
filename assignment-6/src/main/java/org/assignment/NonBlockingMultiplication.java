package org.assignment;

import mpi.MPI;
import mpi.Request;

import java.util.ArrayList;
import java.util.List;

public class NonBlockingMultiplication {

    public static int NUMBER_OF_ROWS_IN_A = 1000;
    public static int NUMBER_OF_COLS_IN_A = 1000;
    public static int NUMBER_OF_COLS_IN_B = 1000;
    public static int MASTER = 0;

    public static boolean RESULT_IS_PRINTED = false;
    public static boolean RANDOMIZE_MATRICES = false;
    public static boolean VALIDATE_RESULT = false;

    public static void main(String[] args) {
        int taskId, tasksNumber, workersNumber;

        int[] rows = {0}, offset = {0};
        double[][] a = new double[NUMBER_OF_ROWS_IN_A][NUMBER_OF_COLS_IN_A];
        double[][] b = new double[NUMBER_OF_COLS_IN_A][NUMBER_OF_COLS_IN_B];
        double[][] c = new double[NUMBER_OF_ROWS_IN_A][NUMBER_OF_COLS_IN_B];

        final MatrixService matrixService = new MatrixService();

        MPI.Init(args);
        taskId = MPI.COMM_WORLD.Rank();
        tasksNumber = MPI.COMM_WORLD.Size();
        workersNumber = tasksNumber - 1;

        if (tasksNumber < 2) {
            System.err.println("Required to have at least 2 MPI tasks.");
            MPI.COMM_WORLD.Abort(1);
            return;
        }

        if (taskId == MASTER) {
            System.out.println("MPI_NBMM has started with " + tasksNumber + " tasks.");
            if (RANDOMIZE_MATRICES) {
                matrixService.initializeMatrixWithRandom(a, 50, 500);
                matrixService.initializeMatrixWithRandom(b, 50, 500);
            } else {
                matrixService.initializeMatrixWithNumber(a, 100);
                matrixService.initializeMatrixWithNumber(b, 100);
            }

            long startTime = System.currentTimeMillis();
            int averow = NUMBER_OF_ROWS_IN_A / workersNumber;
            int extra = NUMBER_OF_ROWS_IN_A % workersNumber;

            for (int destination = 1; destination <= workersNumber; destination++) {
                rows[0] = (destination <= extra) ? averow + 1 : averow;
                var offsetRequest = MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, destination, MessageTag.OFFSET_FROM_MASTER.ordinal());
                var rowsRequest = MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, destination, MessageTag.ROWS_FROM_MASTER.ordinal());
                MPI.COMM_WORLD.Isend(a, offset[0], rows[0], MPI.OBJECT, destination, MessageTag.A_FROM_MASTER.ordinal());
                MPI.COMM_WORLD.Isend(b, 0, NUMBER_OF_COLS_IN_A, MPI.OBJECT, destination, MessageTag.B_FROM_MASTER.ordinal());
                offsetRequest.Wait();
                rowsRequest.Wait();
                offset[0] += rows[0];
            }
            List<Request> subTasksRequests = new ArrayList<>();
            for (int source = 1; source <= workersNumber; source++) {
                var offsetRequest = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, source, MessageTag.OFFSET_FROM_WORKER.ordinal());
                var rowsRequest = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, source, MessageTag.ROWS_FROM_WORKER.ordinal());
                offsetRequest.Wait();
                rowsRequest.Wait();
                subTasksRequests.add(MPI.COMM_WORLD.Irecv(c, offset[0], rows[0], MPI.OBJECT, source, MessageTag.C_FROM_WORKER.ordinal()));
            }
            for (var request : subTasksRequests) {
                request.Wait();
            }

            long endTime = System.currentTimeMillis();
            System.out.println(" ~~~~~~ RESULTS ~~~~~~ ");
            if (RESULT_IS_PRINTED) {
                matrixService.outputMatrix(c);
            }
            System.out.println("Execution time: " + (endTime - startTime) + " ms");
            if (VALIDATE_RESULT) {
                System.out.println("Result is valid: " + matrixService.validateMultiplicationResult(a, b, c));
            }
        } else {
            var offsetRequest = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, MASTER, MessageTag.OFFSET_FROM_MASTER.ordinal());
            var rowsRequest = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, MASTER, MessageTag.ROWS_FROM_MASTER.ordinal());
            var bRequest = MPI.COMM_WORLD.Irecv(b, 0, NUMBER_OF_COLS_IN_A, MPI.OBJECT, MASTER, MessageTag.B_FROM_MASTER.ordinal());
            offsetRequest.Wait();
            rowsRequest.Wait();
            MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, MASTER, MessageTag.OFFSET_FROM_WORKER.ordinal());
            MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, MASTER, MessageTag.ROWS_FROM_WORKER.ordinal());
            var aRequest = MPI.COMM_WORLD.Irecv(a, 0, rows[0], MPI.OBJECT, MASTER, MessageTag.A_FROM_MASTER.ordinal());
            aRequest.Wait();
            bRequest.Wait();

            for (int k = 0; k < NUMBER_OF_COLS_IN_B; k++) {
                for (int i = 0; i < rows[0]; i++) {
                    for (int j = 0; j < NUMBER_OF_COLS_IN_A; j++) {
                        c[i][k] += a[i][j] * b[j][k];
                    }
                }
            }

            MPI.COMM_WORLD.Isend(c, 0, rows[0], MPI.OBJECT, MASTER, MessageTag.C_FROM_WORKER.ordinal());
        }
        MPI.Finalize();
    }
}