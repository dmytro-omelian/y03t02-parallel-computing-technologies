package org.task3.entity;

public class Student {
    private final String id;
    private final String name;
    private final String surname;

    public Student(String id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
