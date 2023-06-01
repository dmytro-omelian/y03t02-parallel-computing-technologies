package org.assignment.task_2.student;


import org.assignment.task_2.group.Group;

public record Student(Group group, String name) {
    public String toString() {
        return this.group.getGroupName() + " " + this.name;
    }
}