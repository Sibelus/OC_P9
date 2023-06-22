package com.mediscreen.microservice_DiabetesReport.service;

import com.mediscreen.microservice_DiabetesReport.bean.PersonalRecordBean;
import com.mediscreen.microservice_DiabetesReport.bean.PractitionerNoteBean;
import com.mediscreen.microservice_DiabetesReport.exception.PersonalRecordNotFoundException;
import com.mediscreen.microservice_DiabetesReport.model.DiabetesInfo;
import com.mediscreen.microservice_DiabetesReport.proxy.PersonalRecordProxy;
import com.mediscreen.microservice_DiabetesReport.proxy.PractitionerNoteProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceTest {

    @Autowired
    IDiabetesReportService iDiabetesReportService;
    @MockBean
    private PersonalRecordProxy personalRecordProxy;
    @MockBean
    private PractitionerNoteProxy practitionerNoteProxy;



    @Test
    public void testGetDiabetesInfo() throws Exception {
        PersonalRecordBean personalRecordBean = new PersonalRecordBean();
        personalRecordBean.setId(1);
        personalRecordBean.setBirthdate(new Date());
        personalRecordBean.setSex("F");
        when(personalRecordProxy.getPatientInfo(1)).thenReturn(Optional.of(personalRecordBean));

        List<PractitionerNoteBean> practitionerNoteBeans = new ArrayList<>();
        when(practitionerNoteProxy.getPatientNotes("1")).thenReturn(Optional.of(practitionerNoteBeans));


        DiabetesInfo diabetesInfo = iDiabetesReportService.getDiabetesInfo(1);
        Assertions.assertEquals(1, diabetesInfo.getPatientId());
    }

    @Test
    public void testGetDiabetesInfo_NonExistentPersonalRecord() throws Exception {
        when(personalRecordProxy.getPatientInfo(1)).thenThrow(PersonalRecordNotFoundException.class);
        Assertions.assertThrows(PersonalRecordNotFoundException.class, () -> iDiabetesReportService.getDiabetesInfo(1));
    }

    @Test
    public void testGetDiabetesInfoByFamilyName() throws Exception {
        PersonalRecordBean personalRecordBean = new PersonalRecordBean();
        personalRecordBean.setId(1);
        personalRecordBean.setLastname("lastname");
        personalRecordBean.setBirthdate(new Date());
        personalRecordBean.setSex("F");
        when(personalRecordProxy.getPatientInfoByFamilyName("lastname")).thenReturn(Optional.of(personalRecordBean));

        List<PractitionerNoteBean> practitionerNoteBeans = new ArrayList<>();
        when(practitionerNoteProxy.getPatientNotes("1")).thenReturn(Optional.of(practitionerNoteBeans));


        DiabetesInfo diabetesInfo = iDiabetesReportService.getDiabetesInfoByFamilyName("lastname");
        Assertions.assertEquals(1, diabetesInfo.getPatientId());
    }

    @Test
    public void testGetDiabetesInfoByFamilyName_NonExistentPersonalRecord() throws Exception {
        when(personalRecordProxy.getPatientInfoByFamilyName("lastname")).thenThrow(PersonalRecordNotFoundException.class);
        Assertions.assertThrows(PersonalRecordNotFoundException.class, () -> iDiabetesReportService.getDiabetesInfoByFamilyName("lastname"));
    }

    @Test
    public void testDiabetesAssessment_None() throws Exception {
        DiabetesInfo diabetesInfo = new DiabetesInfo();
        diabetesInfo.setPatientId(1);
        diabetesInfo.setFirstname("Test");
        diabetesInfo.setLastname("TestNone");
        diabetesInfo.setSex("M");
        diabetesInfo.setBirthdate(Date.from(Instant.parse("1980-04-09T00:00:00.00Z")));
        diabetesInfo.setNotes(new ArrayList<>());

        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        Assertions.assertTrue(diabetesAssessment.contains("TestNone"));
    }

    @Test
    public void testDiabetesAssessment_ManEarlyOnset() throws Exception {
        DiabetesInfo diabetesInfo = new DiabetesInfo();
        diabetesInfo.setPatientId(1);
        diabetesInfo.setFirstname("Test");
        diabetesInfo.setLastname("TestEarlyOnset");
        diabetesInfo.setSex("M");
        diabetesInfo.setBirthdate(new Date());
        diabetesInfo.setNotes(List.of("hémoglobine a1c", "microalbumine", "taille", "poids", "fumeur"));

        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        Assertions.assertTrue(diabetesAssessment.contains("TestEarlyOnset"));
    }

    @Test
    public void testDiabetesAssessment_WomanEarlyOnset() throws Exception {
        DiabetesInfo diabetesInfo = new DiabetesInfo();
        diabetesInfo.setPatientId(1);
        diabetesInfo.setFirstname("Test");
        diabetesInfo.setLastname("TestEarlyOnset");
        diabetesInfo.setSex("F");
        diabetesInfo.setBirthdate(new Date());
        diabetesInfo.setNotes(List.of("hémoglobine a1c", "microalbumine", "taille", "poids", "fumeur", "anormal", "cholestérol"));

        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        Assertions.assertTrue(diabetesAssessment.contains("TestEarlyOnset"));
    }

    @Test
    public void testDiabetesAssessment_ManInDanger() throws Exception {
        DiabetesInfo diabetesInfo = new DiabetesInfo();
        diabetesInfo.setPatientId(1);
        diabetesInfo.setFirstname("Test");
        diabetesInfo.setLastname("TestInDanger");
        diabetesInfo.setSex("M");
        diabetesInfo.setBirthdate(new Date());
        diabetesInfo.setNotes(List.of("hémoglobine a1c", "microalbumine", "taille"));

        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        Assertions.assertTrue(diabetesAssessment.contains("TestInDanger"));
    }

    @Test
    public void testDiabetesAssessment_WomanInDanger() throws Exception {
        DiabetesInfo diabetesInfo = new DiabetesInfo();
        diabetesInfo.setPatientId(1);
        diabetesInfo.setFirstname("Test");
        diabetesInfo.setLastname("TestInDanger");
        diabetesInfo.setSex("F");
        diabetesInfo.setBirthdate(new Date());
        diabetesInfo.setNotes(List.of("hémoglobine a1c", "microalbumine", "taille", "poids"));

        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        Assertions.assertTrue(diabetesAssessment.contains("TestInDanger"));
    }

    @Test
    public void testDiabetesAssessment_OldPersonInDanger() throws Exception {
        DiabetesInfo diabetesInfo = new DiabetesInfo();
        diabetesInfo.setPatientId(1);
        diabetesInfo.setFirstname("Test");
        diabetesInfo.setLastname("TestInDanger");
        diabetesInfo.setSex("F");
        diabetesInfo.setBirthdate(Date.from(Instant.parse("1980-04-09T00:00:00.00Z")));
        diabetesInfo.setNotes(List.of("hémoglobine a1c", "microalbumine", "taille", "poids", "fumeur", "anormal"));

        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        Assertions.assertTrue(diabetesAssessment.contains("TestInDanger"));
    }

    @Test
    public void testDiabetesAssessment_ManBorderline() throws Exception {
        DiabetesInfo diabetesInfo = new DiabetesInfo();
        diabetesInfo.setPatientId(1);
        diabetesInfo.setFirstname("Test");
        diabetesInfo.setLastname("TestBorderline");
        diabetesInfo.setSex("M");
        diabetesInfo.setBirthdate(Date.from(Instant.parse("1980-04-09T00:00:00.00Z")));
        diabetesInfo.setNotes(List.of("hémoglobine a1c", "microalbumine"));

        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        Assertions.assertTrue(diabetesAssessment.contains("TestBorderline"));
    }

    @Test
    public void testDiabetesAssessment_Woman3TriggerBorderline() throws Exception {
        DiabetesInfo diabetesInfo = new DiabetesInfo();
        diabetesInfo.setPatientId(1);
        diabetesInfo.setFirstname("Test");
        diabetesInfo.setLastname("TestBorderline");
        diabetesInfo.setSex("F");
        diabetesInfo.setBirthdate(Date.from(Instant.parse("1980-04-09T00:00:00.00Z")));
        diabetesInfo.setNotes(List.of("hémoglobine a1c", "microalbumine", "taille"));

        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        Assertions.assertTrue(diabetesAssessment.contains("TestBorderline"));
    }

    @Test
    public void testCalculateAge() throws Exception {
        Date birthdate = new Date();
        int age = iDiabetesReportService.calculateAge(birthdate);

        Assertions.assertEquals(0, age);
    }

    @Test
    public void testCountTrigger() throws Exception {
        DiabetesInfo diabetesInfo = new DiabetesInfo();
        diabetesInfo.setNotes(List.of("hémoglobine a1c", "microalbumine", "taille"));

        int countTrigger = iDiabetesReportService.countTrigger(diabetesInfo);
        Assertions.assertEquals(3, countTrigger);
    }
}
