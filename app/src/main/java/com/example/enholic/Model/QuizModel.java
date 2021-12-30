package com.example.enholic.Model;


import com.google.firebase.firestore.DocumentId;

public class QuizModel {
    @DocumentId
    private String questionId;

    private String level, answer, question, Option_A, Option_B, Option_C, Option_D;

    public QuizModel(){}

    public String getQuestionId() {
        return questionId;
    }

    public String getOption_A() {
        return Option_A;
    }

    public void setOption_A(String option_A) {
        this.Option_A = option_A;
    }

    public String getOption_B() {
        return Option_B;
    }

    public void setOption_B(String option_B) {
        this.Option_B = option_B;
    }

    public String getOption_C() {
        return Option_C;
    }

    public void setOption_C(String option_C) {
        this.Option_C = option_C;
    }

    public String getOption_D() {
        return Option_D;
    }

    public void setOption_D(String option_D) {
        this.Option_D = option_D;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuizModel(String questionId, String level, String answer, String question, String option_A, String option_B, String option_C, String option_D) {
        this.questionId = questionId;
        this.level = level;
        this.answer = answer;
        this.question = question;
        this.Option_A = option_A;
        this.Option_B = option_B;
        this.Option_C = option_C;
        this.Option_D = option_D;
    }
}
