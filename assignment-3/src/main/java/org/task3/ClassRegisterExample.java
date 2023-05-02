package org.task3;

import org.task3.entity.ClassRegister;
import org.task3.entity.Group;
import org.task3.entity.Student;

public class ClassRegisterExample {

    public static void main(String[] args) {
        // create a journal to keep track of grades
        ClassRegister journal = new ClassRegister();

        // create three courses, one for each group of students
        Group group1 = new Group("Group 1");
        Group group2 = new Group("Group 2");
        Group group3 = new Group("Group 3");

        // add some students to each course
//        group1.addStudent(new Student("John", "Doe"));
//        group1.addStudent(new Student("Jane", "Doe"));
//
//        group2.addStudent(new Student("Bob", "Smith"));
//        group2.addStudent(new Student("Alice", "Johnson"));
//
//        group3.addStudent(new Student("Tom", "Jones"));
//        group3.addStudent(new Student("Samantha", "Lee"));

        // add the courses to the journal
        journal.addCourse(group1);
        journal.addCourse(group2);
        journal.addCourse(group3);

        // simulate grading for a week
        for (int i = 1; i <= 7; i++) {
            // iterate over each course
            for (Group group : journal.getCourses()) {
                // iterate over each student in the group
                for (Student student : group.getStudents()) {
                    // generate a random grade for the student and update it in the group
                    int grade = (int) (Math.random() * 100);
                    group.updateGrade(student, grade);
                }
            }
        }

        // display the grades for each student
        journal.display();
    }
}
