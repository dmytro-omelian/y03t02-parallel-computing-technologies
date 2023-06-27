package org.exam.task3;

import mpi.MPI;

import java.util.*;

public class Main {

    public static final Integer MASTER = 0;
    public static int FROM_MASTER_TAG_1 = 1;
    public static int FROM_MASTER_TAG_2 = 2;
    public static int FROM_WORKER_TAG = 3;

    public static void main(String[] args) {
        int taskId, tasksNumber, workersNumber;

        int[] rows = {0}, offset = {0};

        int N_ELEMENTS = 10;
        int[][] A = generate(N_ELEMENTS);
        int[][] B = generate(N_ELEMENTS);

        int[] C = new int[N_ELEMENTS];

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
            System.out.println("MPI_BMM has started with " + tasksNumber + " tasks.");

            int rowsPerTask = N_ELEMENTS / workersNumber;
            int extraRows = N_ELEMENTS % workersNumber;

            for (int destination = 1; destination <= workersNumber; destination++) {
                rows[0] = (destination <= extraRows) ? rowsPerTask + 1 : rowsPerTask;
                MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, destination, FROM_MASTER_TAG_1);
                MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, destination, FROM_MASTER_TAG_1);
                MPI.COMM_WORLD.Send(A, offset[0], rows[0], MPI.OBJECT, destination, FROM_MASTER_TAG_1);
                MPI.COMM_WORLD.Send(B, offset[0], rows[0], MPI.OBJECT, destination, FROM_MASTER_TAG_2);
                MPI.COMM_WORLD.Send(C, offset[0], rows[0], MPI.INT, destination, FROM_MASTER_TAG_1);
                offset[0] += rows[0];
            }

            for (int source = 1; source <= workersNumber; source++) {
                MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, source, FROM_WORKER_TAG);
                MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, source, FROM_WORKER_TAG);
                MPI.COMM_WORLD.Recv(C, offset[0], rows[0], MPI.INT, source, FROM_WORKER_TAG);
            }

            System.out.println("MPI Result array C: ");
            for (int i = 0; i < C.length; ++ i) {
                System.out.print(C[i] + " ");
            }
            System.out.println();


            System.out.println("Brute Force array C result: ");
            for (int i = 0; i < C.length; ++ i) {

                int totalSumInA = 0;
                int totalSumInB = 0;
                for (int j = 0; j < C.length; ++ j) {
                    totalSumInA += A[i][j];
                    totalSumInB += B[i][j];
                }
                int multiplication = totalSumInA * totalSumInB;
                System.out.print(multiplication + " ");
            }

        } else {
            MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, MASTER, FROM_MASTER_TAG_1);
            MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, MASTER, FROM_MASTER_TAG_1);
            MPI.COMM_WORLD.Recv(A, offset[0], rows[0], MPI.OBJECT, MASTER, FROM_MASTER_TAG_1);
            MPI.COMM_WORLD.Recv(B, offset[0], rows[0], MPI.OBJECT, MASTER, FROM_MASTER_TAG_2);
            MPI.COMM_WORLD.Recv(C, offset[0], rows[0], MPI.INT, MASTER, FROM_MASTER_TAG_1);

            System.out.println("Process: " + MPI.COMM_WORLD.Rank());
            int n = C.length;
            for (int i = offset[0]; i < offset[0] + rows[0]; ++ i) {

                int totalSumInA = 0;
                int totalSumInB = 0;
                for (int j = 0; j < n; ++ j) {
                    totalSumInA += A[i][j];
                    totalSumInB += B[i][j];
                }
                int multiplication = totalSumInA * totalSumInB;
                C[i] = multiplication;
            }

            MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, MASTER, FROM_WORKER_TAG);
            MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, MASTER, FROM_WORKER_TAG);
            MPI.COMM_WORLD.Send(C, offset[0], rows[0], MPI.INT, MASTER, FROM_WORKER_TAG);
        }
        MPI.Finalize();
    }

    private static int[][] generate(int n_elements) {
        int[][] result = new int[n_elements][n_elements];
        Random random = new Random();
        for (int i = 0; i < n_elements; ++i) {
            for (int j = 0; j < n_elements; ++ j) {
                result[i][j] = random.nextInt(5);
            }
        }
        return result;
    }
}
