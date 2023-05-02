package org.task3.entity;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String id;
    private List<Student> students;

    public Group(String id) {
        this.id = id;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public String getId() {
        return this.id;
    }
}
