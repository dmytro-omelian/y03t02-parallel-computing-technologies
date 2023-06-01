package org.task3;

import org.task3.classregister.ClassRegister;
import org.task3.group.Group;
import org.task3.teacher.Assistant;
import org.task3.teacher.Lecturer;
import org.task3.teacher.Teacher;


public class ClassRegisterExample {

    public static final Integer WEEKS = 12;
    private static final Integer STUDENTS = 20;
    private static final Integer N_GROUPS = 3;

    public static void main(String[] args) {

        Group[] groups = new Group[N_GROUPS];
        for (int i = 0; i < N_GROUPS; i++) {
            groups[i] = new Group("Group-" + i, STUDENTS);
        }
        ClassRegister classRegister = new ClassRegister(groups, WEEKS);
        Teacher lecturer1 = new Lecturer("Lecturer 1", classRegister, groups, WEEKS);
        Teacher assistant1 = new Assistant("Assistant 1 ", classRegister, groups, WEEKS);
        Teacher assistant2 = new Assistant("Assistant 2", classRegister, groups, WEEKS);
        Teacher assistant3 = new Assistant("Assistant 3", classRegister, groups, WEEKS);

        lecturer1.start();
        assistant1.start();
        assistant2.start();
        assistant3.start();

        try {
            lecturer1.join();
            assistant1.join();
            assistant2.join();
            assistant3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        classRegister.print();
    }
}
