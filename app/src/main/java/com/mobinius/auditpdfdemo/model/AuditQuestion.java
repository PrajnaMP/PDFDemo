package com.mobinius.auditpdfdemo.model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prajna on 24/11/17.
 */

public class AuditQuestion {
    private String questionName;
    private String description;
    private String answerType;
    private List<String> listTicket;
    private ArrayList<String> listAnswer;
    private String choice;
    private List<CharSequence> listMultipleChoice;

    public ArrayList<String> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(ArrayList<String> listAnswer) {
        this.listAnswer = listAnswer;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public List<String> getListTicket() {
        return listTicket;
    }

    public void setListTicket(List<String> listTicket) {
        this.listTicket = listTicket;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public List<CharSequence> getListMultipleChoice() {
        return listMultipleChoice;
    }

    public void setListMultipleChoice(List<CharSequence> listMultipleChoice) {
        this.listMultipleChoice = listMultipleChoice;
    }

}
