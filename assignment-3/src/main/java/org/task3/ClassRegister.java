package org.task3;

import java.util.List;

public class ClassRegister {

    private final List<Group> groups;

    public ClassRegister(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
