package com.mediscreen.microservice_PractitionerNote.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Model class that defined all attributes and their constraints rules of a practitioner note related to the patient id = patId
 */
@Document(collection = "practitionerNote")
public class PractitionerNote {

    private String id;
    private String patId;
    @NotBlank(message = "content is mandatory")
    private String content;

    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
