package com.mediscreen.microservice_DiabetesReport.model;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DiabetesInfo {

    private int patientId;
    private String sex;
    private Date birthdate;
    private List<String> notes;


    @Override
    public String toString() {
        return "DiabetesInfo{" +
                "patientId=" + patientId +
                ", sex='" + sex + '\'' +
                ", birthdate=" + birthdate +
                ", notes=" + notes +
                '}';
    }

    // getters & setters
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
}
