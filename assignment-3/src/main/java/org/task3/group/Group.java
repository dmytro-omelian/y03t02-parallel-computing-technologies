package org.task3.group;

import org.task3.student.Student;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private final String groupName;
    private final List<Student> students;

    public Group(String groupName, int nStudents) {
        this.groupName = groupName;
        this.students = new ArrayList<>();
        initStudents(nStudents);
    }

    public void initStudents(int nStudents) {
        for (int i = 0; i < nStudents; i++) {
            this.students.add(new Student(this, "student #" + i));
        }
    }

    public Student[] getStudents() {
        return students.toArray(new Student[0]);
    }

    public String getGroupName() {
        return groupName;
    }
}

