package org.task3;

import org.task3.entity.Group;
import org.task3.entity.Student;

import java.util.List;

public class GradeThread extends Thread {
    private final Group group;
    private final String lecturerName;
    private final List<String> assistantNames;
    private final int grade;

    public GradeThread(Group group, String lecturerName, List<String> assistantNames, int grade) {
        this.group = group;
        this.lecturerName = lecturerName;
        this.assistantNames = assistantNames;
        this.grade = grade;
    }

    @Override
    public void run() {
        for (Student student : group.getStudents()) {
            boolean hasGrade = false;
            for (int g : student.getGrades()) {
                if (g != -1 && lecturerName.equals("Lecturer") || assistantNames.contains("Assistant")) {
                    hasGrade = true;
                    break;
                }
            }
            if (!hasGrade) {
                group.updateGrade(student, grade);
            }
        }
    }
}

