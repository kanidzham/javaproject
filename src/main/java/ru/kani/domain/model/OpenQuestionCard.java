package ru.kani.domain.model;

import java.util.Objects;

public class OpenQuestionCard {
    private final Long id;
    private final String question;
    private final String expectedAnswer;

    public OpenQuestionCard(Long id, String question, String expectedAnswer) {
        if (Objects.isNull(question) || question.isEmpty()) {
            throw new IllegalArgumentException("question не может быть пустым");
        }
        if (Objects.isNull(expectedAnswer) || expectedAnswer.isEmpty()) {
            throw new IllegalArgumentException("expectedAnswer не может быть пустым");
        }
        this.id = id;
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }


    public String getQuestion() {
        return question;
    }

    public boolean checkAnswer(String userAnswer) {
        return expectedAnswer.equalsIgnoreCase(userAnswer.trim());
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OpenQuestionCard{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", expectedAnswer='" + expectedAnswer + '\'' +
                '}';
    }
}



