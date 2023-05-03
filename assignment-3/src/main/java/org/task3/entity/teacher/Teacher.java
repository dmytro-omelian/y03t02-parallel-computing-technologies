package org.task3.entity.teacher;

import org.task3.entity.ClassRegister;

import java.util.Random;

import static org.task3.ClassRegisterExample.WEEKS;

public abstract class Teacher extends Thread {
    private static final Random random = new Random();

    private final String name;
    private final String surname;
    private final ClassRegister classRegister;

    private int currentWeek;

    public Teacher(String name, String surname, ClassRegister classRegister) {
        this.name = name;
        this.surname = surname;
        this.classRegister = classRegister;
        this.currentWeek = 1;
    }

    @Override
    public void run() {
        while (currentWeek <= WEEKS) {
            int n = classRegister.getNumberOfStudents();
            double[] grades = new double[n];
            for (int i = 0; i < n; i++) {
                // rangeMin + (rangeMax - rangeMin) * r.nextDouble()
                double grade = 100.0 * random.nextDouble();
                grades[i] = grade;
            }
            classRegister.grade(grades, currentWeek - 1);
            System.out.println(name + " "  + surname + " has graded week " + currentWeek);
            currentWeek++;
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
