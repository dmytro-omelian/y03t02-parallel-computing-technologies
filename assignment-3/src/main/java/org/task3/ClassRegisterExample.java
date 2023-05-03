package org.task3;

import org.task3.entity.ClassRegister;
import org.task3.entity.Group;
import org.task3.entity.Student;
import org.task3.entity.teacher.Assistant;
import org.task3.entity.teacher.Lecturer;
import org.task3.entity.teacher.Teacher;

import java.util.ArrayList;

public class ClassRegisterExample {

    public static final Integer WEEKS = 12;
    private static final Integer STUDENTS = 20;

    public static void main(String[] args) {

        var students = new ArrayList<Student>();
        for (int j = 0; j < STUDENTS; j++) {
            var student = new Student("Student", String.valueOf(j), j);
            students.add(student);
        }

        Group group = new Group("Group 0", students);
        ClassRegister classRegister = new ClassRegister(group, WEEKS);
        Teacher lecturer1 = new Lecturer("Lecturer", "0", classRegister);
        Teacher assistant1 = new Assistant("Assistant", "1", classRegister);
        Teacher assistant2 = new Assistant("Assistant", "2", classRegister);
        Teacher assistant3 = new Assistant("Assistant", "3", classRegister);

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

        classRegister.printGrades();
    }
}
