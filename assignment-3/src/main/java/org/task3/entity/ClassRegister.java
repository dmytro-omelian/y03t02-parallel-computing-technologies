package org.task3.entity;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClassRegister {
    private final Lock lock = new ReentrantLock();

    private final Group group;
    private final Integer WEEKS;
    private final double[][] grades;

    public ClassRegister(Group group, int WEEKS) {
        this.group = group;
        this.WEEKS = WEEKS;

        this.grades = new double[group.getNumberOfStudents()][WEEKS];
    }

    public void grade(double[] studentGrades, int weekIndex) {
        int n = group.getNumberOfStudents();
        for (int i = 0; i < n; ++i) {
            synchronized (grades[i]) {
                grades[i][weekIndex] = studentGrades[i];
            }
        }
    }

    public void printGrades() {
        lock.lock();

        try {
            int nStudents = group.getNumberOfStudents();
            for (int i = 0; i < nStudents; ++i) {
                System.out.print("Student [" + i + "] has grades: ");
                for (int j = 0; j < WEEKS; ++j) {
                    System.out.printf("w-%d|%f ", j, grades[i][j]);
                }
                System.out.println();
            }
        } finally {
            lock.unlock();
        }
    }

    public int getNumberOfStudents() {
        return grades.length;
    }
}
