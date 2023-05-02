package org.task3.entity;

import java.util.List;

public class ClassRegister {

    private final List<Group> groups;

    public ClassRegister(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void mark(String groupId, String studentId, Double score) {

    }
}
