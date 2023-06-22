package com.mediscreen.microservice_PersonnalReccord.controller;

import com.mediscreen.microservice_PersonnalReccord.exceptions.PersonalRecordNotFoundException;
import com.mediscreen.microservice_PersonnalReccord.model.PersonalRecord;
import com.mediscreen.microservice_PersonnalReccord.service.IPersonalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonalRecordController {

    @Autowired
    IPersonalRecordService iPersonalRecordService;
    Logger logger = LoggerFactory.getLogger(PersonalRecordController.class);


    // GetMapping methods
    @GetMapping("/personalRecord/list")
    public List<PersonalRecord> getPatientList() {
        List<PersonalRecord> allPatients = iPersonalRecordService.getPatientList();
        return allPatients;
    }

    @GetMapping("/personalRecord/{id}")
    public Optional<PersonalRecord> getPatientInfo(@PathVariable int id) {
        logger.info("GET_personalRecord -> Check if personal record id: {} exists", id);
        Optional<PersonalRecord> patientInfo = iPersonalRecordService.getPatientInfos(id);
        if (!patientInfo.isPresent()) throw new PersonalRecordNotFoundException("There is no data for the id: " + id);
        return patientInfo;
    }

    @GetMapping("/personalRecord/familyName/{familyName}")
    public Optional<PersonalRecord> getPatientInfoByFamilyName(@PathVariable String familyName) {
        logger.info("GET_personalRecord -> Check if personal record familyName: {} exists", familyName);
        Optional<PersonalRecord> patientInfo = iPersonalRecordService.getPatientInfosByFamilyName(familyName);
        if (!patientInfo.isPresent()) throw new PersonalRecordNotFoundException("There is no data for the familyName: " + familyName);
        return patientInfo;
    }

    @GetMapping("/personalRecord/update/{id}")
    public Optional<PersonalRecord> updatePatientInfo(@PathVariable int id) {
        logger.info("UPDATE_personalRecord -> Check if personal record id: {} exists", id);
        Optional<PersonalRecord> patientInfo = iPersonalRecordService.getPatientInfos(id);
        if (!patientInfo.isPresent()) throw new PersonalRecordNotFoundException("There is no data for the id: " + id);
        return patientInfo;
    }

    @GetMapping("/personalRecord/delete/{id}")
    public void deletePatientInfo(@PathVariable int id) {
        logger.info("DELETE_personalRecord -> Check if personal record id: {} exists", id);
        Optional<PersonalRecord> personalRecord = iPersonalRecordService.getPatientInfos(id);
        if (personalRecord.isPresent()) {
            iPersonalRecordService.deletePatientInfo(personalRecord.get());
        } else {
            throw new PersonalRecordNotFoundException("Personal record id: " + id + " doesn't exist");
        }
    }


    // PostMapping methods
    @PostMapping("/personalRecord/validate")
    public ResponseEntity<PersonalRecord> createPatientInfo_submit(@RequestBody PersonalRecord personalRecord) {
        logger.info("Create request body contains, {}", personalRecord.toString());
        iPersonalRecordService.addPatientInfo(personalRecord);
        return new ResponseEntity<PersonalRecord>(personalRecord, HttpStatus.CREATED);
    }

    @PostMapping("/personalRecord/update")
    public ResponseEntity<PersonalRecord> updatePatientInfo_Submit(@RequestBody PersonalRecord personalRecord) {
        logger.info("Update request body contains, {}", personalRecord.toString());
        iPersonalRecordService.updatePatientInfo(personalRecord);
        return new ResponseEntity<PersonalRecord>(personalRecord, HttpStatus.OK);
    }
}
