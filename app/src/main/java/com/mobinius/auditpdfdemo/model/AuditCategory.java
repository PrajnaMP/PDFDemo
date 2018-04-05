package com.mobinius.auditpdfdemo.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by prajna on 24/11/17.
 */

public class AuditCategory {
    private String categoryName;
    private List<AuditQuestion> listQuestions = new ArrayList<>();

    public List<AuditQuestion> getListQuestions() {
        return listQuestions;
    }

    public void setListQuestions(List<AuditQuestion> listQuestions) {
        this.listQuestions = listQuestions;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
