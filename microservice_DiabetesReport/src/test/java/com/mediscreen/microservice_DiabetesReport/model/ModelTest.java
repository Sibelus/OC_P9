package com.mediscreen.microservice_DiabetesReport.model;

import com.mediscreen.microservice_DiabetesReport.bean.PersonalRecordBean;
import com.mediscreen.microservice_DiabetesReport.bean.PractitionerNoteBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelTest {


    @Test
    public void testDiabetesInfo_SetAndGetInfo() {
        //GIVEN
        DiabetesInfo diabetesInfo = new DiabetesInfo();
        int patientId = 1;
        String firstname = "Steff";
        String lastname = "Bihaille";
        String sex = "F";
        Date birthdate = new Date();
        List<String> notes = new ArrayList<>();
        String toString = "DiabetesInfo{" +
                "patientId=" + patientId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", sex='" + sex + '\'' +
                ", birthdate=" + birthdate +
                ", notes=" + notes +
                '}';

        //WHEN
        diabetesInfo.setPatientId(patientId);
        diabetesInfo.setFirstname(firstname);
        diabetesInfo.setLastname(lastname);
        diabetesInfo.setSex(sex);
        diabetesInfo.setBirthdate(birthdate);
        diabetesInfo.setNotes(notes);

        //THEN
        Assertions.assertEquals(patientId, diabetesInfo.getPatientId());
        Assertions.assertEquals(firstname, diabetesInfo.getFirstname());
        Assertions.assertEquals(lastname, diabetesInfo.getLastname());
        Assertions.assertEquals(sex, diabetesInfo.getSex());
        Assertions.assertEquals(birthdate, diabetesInfo.getBirthdate());
        Assertions.assertEquals(notes, diabetesInfo.getNotes());
        Assertions.assertEquals(toString, diabetesInfo.toString());
    }


    @Test
    public void testPersonalRecordBean_SetAndGetInfo() {
        //GIVEN
        PersonalRecordBean personalRecordBean = new PersonalRecordBean();
        int Id = 0;
        String firstname = "Steff";
        String lastname = "Bihaille";
        Date birthdate = new Date();
        String sex = "F";
        String address = "Secret service headquarter";
        String phone = "111-222-3333";
        String toString = "PersonalRecordBean{" +
                "Id=" + Id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthdate=" + birthdate +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';

        //WHEN
        personalRecordBean.setId(Id);
        personalRecordBean.setFirstname(firstname);
        personalRecordBean.setLastname(lastname);
        personalRecordBean.setBirthdate(birthdate);
        personalRecordBean.setSex(sex);
        personalRecordBean.setAddress(address);
        personalRecordBean.setPhone(phone);

        //THEN
        Assertions.assertEquals(Id, personalRecordBean.getId());
        Assertions.assertEquals(firstname, personalRecordBean.getFirstname());
        Assertions.assertEquals(lastname, personalRecordBean.getLastname());
        Assertions.assertEquals(birthdate, personalRecordBean.getBirthdate());
        Assertions.assertEquals(sex, personalRecordBean.getSex());
        Assertions.assertEquals(address, personalRecordBean.getAddress());
        Assertions.assertEquals(phone, personalRecordBean.getPhone());
        Assertions.assertEquals(toString, personalRecordBean.toString());
    }

    @Test
    public void testPractitionerNoteBean_SetAndGetInfo() {
        //GIVEN
        PractitionerNoteBean practitionerNoteBean = new PractitionerNoteBean();
        String id = "1";
        String patId = "1";
        String content = "test content";
        Date date = new Date();
        String toString = "PractitionerNoteBean{" +
                "id='" + id + '\'' +
                ", patId='" + patId + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';

        //WHEN
        practitionerNoteBean.setId(id);
        practitionerNoteBean.setPatId(patId);
        practitionerNoteBean.setContent(content);
        practitionerNoteBean.setDate(date);

        //THEN
        Assertions.assertEquals(id, practitionerNoteBean.getId());
        Assertions.assertEquals(patId, practitionerNoteBean.getPatId());
        Assertions.assertEquals(content, practitionerNoteBean.getContent());
        Assertions.assertEquals(date, practitionerNoteBean.getDate());
        Assertions.assertEquals(toString, practitionerNoteBean.toString());
    }
}
