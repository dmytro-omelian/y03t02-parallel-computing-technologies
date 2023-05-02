package org.task3.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class Mark {

    private final String subject;
    private final Double score;
    private final Date date;

    public Mark(String subject, Double score) {
        this(subject, score, Date.from(Instant.from(LocalDate.now())));
    }

    public Mark(String subject, Double score, Date date) {
        this.subject = subject;
        this.score = score;
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public Double getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }
}
