package org.task3.entity;

import java.util.ArrayList;
import java.util.List;

public class ClassRegister {
    private final List<Group> cours;

    public ClassRegister() {
        this.cours = new ArrayList<>();
    }

    public void addCourse(Group group) {
        cours.add(group);
    }

    public List<Group> getCourses() {
        return cours;
    }

    public void display() {
        for (Group group : cours) {
            System.out.println("Group: " + group.getGroupName());
            System.out.println("Students:");
            for (Student student : group.getStudents()) {
                System.out.println(student.getName() + " " + student.getSurname() + ": " + student.getGrades());
            }
        }
    }
}
