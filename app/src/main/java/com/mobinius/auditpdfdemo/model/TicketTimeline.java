package com.mobinius.auditpdfdemo.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prajna on 24/11/17.
 */

public class TicketTimeline {

    private String opearation;
    ArrayList<String> actor;
    ArrayList<String> person;
    private String role;
    private String time;
    private String type;

    public String getOpearation() {
        return opearation;
    }

    public void setOpearation(String opearation) {
        this.opearation = opearation;
    }

    public ArrayList<String> getActor() {
        return actor;
    }

    public void setActor(ArrayList<String> actor) {
        this.actor = actor;
    }

    public ArrayList<String> getPerson() {
        return person;
    }

    public void setPerson(ArrayList<String> person) {
        this.person = person;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
