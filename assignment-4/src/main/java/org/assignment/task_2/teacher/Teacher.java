package org.assignment.task_2.teacher;


import org.assignment.task_2.classregister.ClassRegister;
import org.assignment.task_2.group.Group;
import org.assignment.task_2.student.Student;

import java.util.Random;

public abstract class Teacher extends Thread {
    private static final Random random = new Random();

    private final String name;
    private final ClassRegister classRegister;
    private final Group[] groups;
    private final int weeks;

    public Teacher(String name, ClassRegister classRegister, Group[] groups, int weeks) {
        this.name = name;
        this.classRegister = classRegister;
        this.groups = groups;
        this.weeks = weeks;
    }

    @Override
    public void run() {
        for (int i = 0; i < weeks; ++i) {
            for (Group group : groups) {
                for (Student student : group.getStudents()) {
                    double grade = random.nextInt(100) + 1.0 * random.nextInt(10) / 10;
                    classRegister.grade(student, i, grade);
                }
            }
        }
    }

}
