package org.assignment.task_2.classregister;


import org.assignment.task_2.exception.IllegalScoreException;
import org.assignment.task_2.group.Group;
import org.assignment.task_2.student.Student;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class ClassRegister {
    private final HashMap<Student, ArrayBlockingQueue<Double>[]> studentGrades;

    public ClassRegister(Group[] groups, int WEEKS) {
        this.studentGrades = new HashMap<>();
        initStudentGrades(groups, WEEKS);
    }

    public void initStudentGrades(Group[] groups, int weeks) {
        for (Group group : groups) {
            for (Student student : group.getStudents()) {
                var grades = new ArrayBlockingQueue[weeks];
                for (int i = 0; i < weeks; i++) {
                    grades[i] = new ArrayBlockingQueue<Integer>(4);
                }
                studentGrades.put(student, grades);
            }
        }
    }

    public void grade(Student student, int week, double grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalScoreException("Score should be in range from 0 to 100");
        }
        studentGrades.get(student)[week].add(grade);
    }

    public void print() {
        for (Student student : studentGrades.keySet()) {
            var grades = studentGrades.get(student);
            System.out.print(student.toString() + " | ");
            for (int i = 0; i < grades.length; i++) {
                System.out.print("WEEK " + i + ": ");
                for (var grade : grades[i]) {
                    System.out.print(grade + " ");
                }
                System.out.print("| ");
            }
            System.out.print('\n');
        }
    }
}
