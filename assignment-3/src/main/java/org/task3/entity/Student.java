package org.task3.entity;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private final String surname;
    private final int groupNumber;
    private final List<Integer> grades;

    public Student(String name, String surname, int groupNumber) {
        this.name = name;
        this.surname = surname;
        this.groupNumber = groupNumber;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }
}