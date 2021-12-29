package com.example.enholic.Model;


import com.google.firebase.firestore.DocumentId;

public class QuizModel {
    @DocumentId
    private String questionId;

    private String level, answer, question;
    private String[] options = new String[4];

    public QuizModel(){}

    public String getQuestionId() {
        return questionId;
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

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public QuizModel(String questionId, String level, String answer, String question, String[] options) {
        this.questionId = questionId;
        this.level = level;
        this.answer = answer;
        this.question = question;
        this.options = options;
    }
}
