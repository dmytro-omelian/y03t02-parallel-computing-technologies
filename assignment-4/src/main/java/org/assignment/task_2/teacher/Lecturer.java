package org.assignment.task_2.teacher;


import org.assignment.task_2.classregister.ClassRegister;
import org.assignment.task_2.group.Group;

public class Lecturer extends Teacher {

    public Lecturer(String name, ClassRegister classRegister, Group[] groups, int weeks) {
        super(name, classRegister, groups, weeks);
    }

}
