package com.mediscreen.microservice_PersonnalReccord.service;

import com.mediscreen.microservice_PersonnalReccord.model.PersonalRecord;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
public class ServiceTest {

    @Autowired
    IPersonalRecordService iPersonalRecordService;


    @Test
    public void testGetPatientList() throws Exception {
        List<PersonalRecord> personalRecords = iPersonalRecordService.getPatientList();
        Assertions.assertTrue(personalRecords.size() > 0);
    }

    @Test
    public void testGetPatientInfos() throws Exception {
        PersonalRecord personalRecord = iPersonalRecordService.getPatientInfos(1).get();
        Assertions.assertEquals("Test", personalRecord.getFirstname());
    }

    @Test
    public void testGetPatientInfoByFamilyName() throws Exception {
        PersonalRecord personalRecord = iPersonalRecordService.getPatientInfosByFamilyName("TestNone").get();
        Assertions.assertEquals("Test", personalRecord.getFirstname());
    }

    @Test
    public void testAddPatientInfo() throws Exception {
        //GIVEN
        PersonalRecord personalRecord = new PersonalRecord();
        personalRecord.setId(1);
        personalRecord.setFirstname("Steff");
        personalRecord.setLastname("Bihaille");
        personalRecord.setBirthdate(new Date());
        personalRecord.setSex("F");
        personalRecord.setAddress("Secret service headquarter");
        personalRecord.setPhone("111-222-3333");

        //WHEN
        iPersonalRecordService.updatePatientInfo(personalRecord);
        Optional<PersonalRecord> personalRecord1 = iPersonalRecordService.getPatientInfos(1);

        //THEN
        Assertions.assertEquals(personalRecord.getFirstname(), iPersonalRecordService.getPatientInfos(1).get().getFirstname());
    }

    @Test
    public void testUpdatePatientInfo() throws Exception {
        //GIVEN
        PersonalRecord personalRecord = new PersonalRecord();
        personalRecord.setId(1);
        personalRecord.setFirstname("Steff");
        personalRecord.setLastname("Bihaille");
        personalRecord.setBirthdate(new Date());
        personalRecord.setSex("F");
        personalRecord.setAddress("Secret service headquarter");
        personalRecord.setPhone("111-222-3333");

        //WHEN
        iPersonalRecordService.updatePatientInfo(personalRecord);

        //THEN
        Assertions.assertEquals(personalRecord.getFirstname(), iPersonalRecordService.getPatientInfos(1).get().getFirstname());
    }

    @Test
    public void testDeletePatientInfo() throws Exception {
        //GIVEN
        PersonalRecord personalRecord = new PersonalRecord();
        personalRecord.setId(1);
        personalRecord.setFirstname("Steff");
        personalRecord.setLastname("Bihaille");
        personalRecord.setBirthdate(new Date());
        personalRecord.setSex("F");
        personalRecord.setAddress("Secret service headquarter");
        personalRecord.setPhone("111-222-3333");

        //WHEN
        iPersonalRecordService.addPatientInfo(personalRecord);
        iPersonalRecordService.deletePatientInfo(personalRecord);
        Optional<PersonalRecord> personalRecord1 = iPersonalRecordService.getPatientInfos(1);

        //THEN
        Assertions.assertTrue(personalRecord1.isEmpty());
    }
}
