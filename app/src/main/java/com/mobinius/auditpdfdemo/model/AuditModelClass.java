package com.mobinius.auditpdfdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prajna on 24/11/17.
 */

public class AuditModelClass {
    private String id;
    private String rev;
    private String archived;
    private String auditType, groupId, name, project, status, template, type;
    private ArrayList<String> author;
    private ArrayList<String> dates;
    private List<AuditCategory> listCategorys;
    private List<AuditSignature> listSignatures;
    private List<TicketTimeline> listAuditTimelines;

    private List<AuditMap> listAuditMaps;
    private List<AuditParticipants> participants;

    public List<AuditMap> getListAuditMaps() {
        return listAuditMaps;
    }

    public void setListAuditMaps(List<AuditMap> listAuditMaps) {
        this.listAuditMaps = listAuditMaps;
    }

    public List<AuditParticipants> getParticipants() {
        return participants;
    }

    public void setParticipants(List<AuditParticipants> participants) {
        this.participants = participants;
    }

    public List<TicketTimeline> getListAuditTimelines() {
        return listAuditTimelines;
    }

    public void setListAuditTimelines(List<TicketTimeline> listAuditTimelines) {
        this.listAuditTimelines = listAuditTimelines;
    }

    public List<AuditSignature> getListSignatures() {
        return listSignatures;
    }

    public void setListSignatures(List<AuditSignature> listSignatures) {
        this.listSignatures = listSignatures;
    }

    public List<AuditCategory> getListCategorys() {
        return listCategorys;
    }

    public void setListCategorys(List<AuditCategory> listCategorys) {
        this.listCategorys = listCategorys;
    }

    public ArrayList<String> getAuthor() {
        return author;
    }

    public void setAuthor(ArrayList<String> author) {
        this.author = author;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getArchived() {
        return archived;
    }

    public void setArchived(String archived) {
        this.archived = archived;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
