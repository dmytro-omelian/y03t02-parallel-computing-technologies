package org.task3.entity;

import java.util.List;

public record Group(String groupName, List<Student> students) {

    public int getNumberOfStudents() {
        return this.students.size();
    }
}

