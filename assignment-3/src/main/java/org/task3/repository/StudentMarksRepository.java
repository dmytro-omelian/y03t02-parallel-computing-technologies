package org.task3.repository;

import org.task3.entity.Mark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StudentMarksRepository {

    private final Map<String, List<Mark>> marks;

    public StudentMarksRepository() {
        this.marks = new HashMap<>();
    }

    public List<Mark> getMarksByStudentId(String studentId) {
        return marks.getOrDefault(studentId, List.of());
    }

    public List<Double> getMarksByStudentIdAndSubject(String studentId, String subjectName) {
        List<Mark> studentMarks = getMarksByStudentId(studentId);
        return studentMarks.stream().map(studentMark -> {
            String studentMarkSubject = studentMark.getSubject();
            if (studentMarkSubject.equals(subjectName)) {
                return studentMark;
            }
            return null;
        }).filter(Objects::nonNull).map(Mark::getScore).toList();
    }

    public synchronized void markStudentOnSubject(String studentId, Mark mark) {
        List<Mark> currentSubjectMarks = this.marks.getOrDefault(studentId, List.of());
        currentSubjectMarks.add(mark);
        this.marks.put(studentId, currentSubjectMarks);
    }
}
