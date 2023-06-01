package org.task3.teacher;

import org.task3.classregister.ClassRegister;
import org.task3.group.Group;

public class Assistant extends Teacher {

    public Assistant(String name, ClassRegister classRegister, Group[] groups, int weeks) {
        super(name, classRegister, groups, weeks);
    }
}
