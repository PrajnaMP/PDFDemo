package com.mobinius.auditpdfdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prajna on 29/11/17.
 */

public class AuditParticipants {
    private String type;
    private List<String> informed;
    private List<String> responsible;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getInformed() {
        return informed;
    }

    public void setInformed(List<String> informed) {
        this.informed = informed;
    }

    public List<String> getResponsible() {
        return responsible;
    }

    public void setResponsible(List<String> responsible) {
        this.responsible = responsible;
    }
}
