package com.mediscreen.microservice_PractitionerNote.model;

import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "practitionerNote")
public class PractitionerNote {

    private String id;
    private String patId;
    private String content;
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;


    @Override
    public String toString() {
        return "PractitionerNote{" +
                "id='" + id + '\'' +
                ", patId='" + patId + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
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
