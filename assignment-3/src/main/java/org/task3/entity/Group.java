package org.task3.entity;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final String groupName;
    private final List<Student> students;

    public Group(String groupName) {
        this.groupName = groupName;
        this.students = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void updateGrade(Student student, int grade) {
        for (Student s : students) {
            if (s.equals(student)) {
                s.addGrade(grade);
                return;
            }
        }
        student.addGrade(grade);
        students.add(student);
    }
}

