package org.task3.student;

import org.task3.group.Group;

public record Student(Group group, String name) {
    public String toString() {
        return this.group.getGroupName() + " " + this.name;
    }
}