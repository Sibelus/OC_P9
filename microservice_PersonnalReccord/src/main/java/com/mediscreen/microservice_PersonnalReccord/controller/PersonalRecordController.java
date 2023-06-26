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

    /**
     * Method that get all personal records
     * @return a list of all personal record present in database
     */
    @GetMapping("/personalRecord/list")
    public List<PersonalRecord> getPatientList() {
        List<PersonalRecord> allPatients = iPersonalRecordService.getPatientList();
        return allPatients;
    }

    /**
     * Method that get a personal record from database by its id
     * @param id -> refer to the personal record id
     * @return if personal record's id exist it will return an optional {@link PersonalRecord}
     *         else it will throw a {@link PersonalRecordNotFoundException}
     */
    @GetMapping("/personalRecord/{id}")
    public Optional<PersonalRecord> getPatientInfo(@PathVariable int id) {
        logger.info("GET_personalRecord -> Check if personal record id: {} exists", id);
        Optional<PersonalRecord> patientInfo = iPersonalRecordService.getPatientInfos(id);
        if (!patientInfo.isPresent()) throw new PersonalRecordNotFoundException("There is no data for the id: " + id);
        return patientInfo;
    }

    /**
     * Method that get a personal record from database by its family name
     * @param familyName -> refer to the patient family name register into the personal record
     * @return if personal record's family name exist it will return an Optional<PersonalRecord> {@link Optional} {@link PersonalRecord}
     *         else it will throw a {@link PersonalRecordNotFoundException}
     */
    @GetMapping("/personalRecord/familyName/{familyName}")
    public Optional<PersonalRecord> getPatientInfoByFamilyName(@PathVariable String familyName) {
        logger.info("GET_personalRecord -> Check if personal record familyName: {} exists", familyName);
        Optional<PersonalRecord> patientInfo = iPersonalRecordService.getPatientInfosByFamilyName(familyName);
        if (!patientInfo.isPresent()) throw new PersonalRecordNotFoundException("There is no data for the familyName: " + familyName);
        return patientInfo;
    }

    /**
     * Method that get a personal record from database by its id to update its content
     * @param id -> refer to the personal record id
     * @return if personal record's id exist it will return an Optional<PersonalRecord> {@link Optional} {@link PersonalRecord}
     *         else it will throw a {@link PersonalRecordNotFoundException}
     */
    @GetMapping("/personalRecord/update/{id}")
    public Optional<PersonalRecord> updatePatientInfo(@PathVariable int id) {
        logger.info("UPDATE_personalRecord -> Check if personal record id: {} exists", id);
        Optional<PersonalRecord> patientInfo = iPersonalRecordService.getPatientInfos(id);
        if (!patientInfo.isPresent()) throw new PersonalRecordNotFoundException("There is no data for the id: " + id);
        return patientInfo;
    }

    /**
     * Method that delete a personal record by its id
     * if personal record's id exist it will delete it
     * else it will throw a {@link PersonalRecordNotFoundException}
     * @param id -> refer to the personal record id
     */
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

    /**
     * Method that save a new personal record into database
     * @param personalRecord {@link PersonalRecord}
     * @return it returns a ResponseEntity<PersonalRecord> {@link ResponseEntity} {@link PersonalRecord} with http status 201
     */
    @PostMapping("/personalRecord/validate")
    public ResponseEntity<PersonalRecord> createPatientInfo_submit(@RequestBody PersonalRecord personalRecord) {
        logger.info("Create request body contains, {}", personalRecord.toString());
        iPersonalRecordService.addPatientInfo(personalRecord);
        return new ResponseEntity<PersonalRecord>(personalRecord, HttpStatus.CREATED);
    }

    /**
     * Method that update content of a personal record into database
     * @param personalRecord {@link PersonalRecord}
     * @return it returns a ResponseEntity<PersonalRecord> {@link ResponseEntity} {@link PersonalRecord} with http status 200
     */
    @PostMapping("/personalRecord/update")
    public ResponseEntity<PersonalRecord> updatePatientInfo_Submit(@RequestBody PersonalRecord personalRecord) {
        logger.info("Update request body contains, {}", personalRecord.toString());
        iPersonalRecordService.updatePatientInfo(personalRecord);
        return new ResponseEntity<PersonalRecord>(personalRecord, HttpStatus.OK);
    }
}
