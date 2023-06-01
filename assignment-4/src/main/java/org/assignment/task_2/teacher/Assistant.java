package org.assignment.task_2.teacher;


import org.assignment.task_2.classregister.ClassRegister;
import org.assignment.task_2.group.Group;

public class Assistant extends Teacher {

    public Assistant(String name, ClassRegister classRegister, Group[] groups, int weeks) {
        super(name, classRegister, groups, weeks);
    }
}
