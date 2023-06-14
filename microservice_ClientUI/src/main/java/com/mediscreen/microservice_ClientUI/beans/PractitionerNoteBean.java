package com.mediscreen.microservice_ClientUI.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;

public class PractitionerNoteBean implements Comparable<PractitionerNoteBean>{

    private String id;
    private String patId;
    private String content;
    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;


    @Override
    public String toString() {
        return "PractitionerNoteBean{" +
                "id='" + id + '\'' +
                ", patId='" + patId + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }

    /**
     * method to compare NoteBean practitioners by their date value
     * @param PractitionerNoteBean the object to be compared.
     * @return
     */
    public int compareTo(PractitionerNoteBean PractitionerNoteBean) {
        return this.date.compareTo(PractitionerNoteBean.date);
    }


    // getters & setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
