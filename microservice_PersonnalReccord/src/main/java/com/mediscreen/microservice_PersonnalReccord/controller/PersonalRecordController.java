package com.mediscreen.microservice_PersonnalReccord.controller;

import com.mediscreen.microservice_PersonnalReccord.exceptions.PersonalRecordNotFoundException;
import com.mediscreen.microservice_PersonnalReccord.model.PersonalRecord;
import com.mediscreen.microservice_PersonnalReccord.service.IPersonalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonalRecordController {

    @Autowired
    IPersonalRecordService iPersonalRecordService;


    // GetMapping methods
    @GetMapping("/personalRecord/list")
    public List<PersonalRecord> getPatientList() {
        List<PersonalRecord> allPatients = iPersonalRecordService.getPatientList();
        if (allPatients.isEmpty()) throw new PersonalRecordNotFoundException("There is no data stored in database");
        return allPatients;
    }

    @GetMapping("/personalRecord/{id}")
    public Optional<PersonalRecord> getPatientInfo(@PathVariable int id) {
        Optional<PersonalRecord> patientInfo = iPersonalRecordService.getPatientInfos(id);
        if (!patientInfo.isPresent()) throw new PersonalRecordNotFoundException("There is no data for the id: " + id);
        return patientInfo;
    }

    @GetMapping("/personalRecord/update/{id}")
    public Optional<PersonalRecord> updatePatientInfo(@PathVariable int id) {
        Optional<PersonalRecord> patientInfo = iPersonalRecordService.getPatientInfos(id);
        if (!patientInfo.isPresent()) throw new PersonalRecordNotFoundException("There is no data for the id: " + id);
        return patientInfo;
    }


    // PostMapping methods
    @PostMapping("/personalRecord/add")
    public void createPatientInfo_submit(PersonalRecord personalRecord) {
        iPersonalRecordService.addPatientInfo(personalRecord);
    }

    @PostMapping("/personalRecord/update/{id}")
    public void updatePatientInfo_Submit(@PathVariable int id, PersonalRecord personalRecord) {
        personalRecord.setId(id);
        iPersonalRecordService.updatePatientInfo(personalRecord);
    }

    @PostMapping("/personalRecord/delete/{id}")
    public void deletePatientInfo_submit(@PathVariable int id) {
        Optional<PersonalRecord> personalRecord = iPersonalRecordService.getPatientInfos(id);
        if (personalRecord.isPresent()) {
            iPersonalRecordService.deletePatientInfo(personalRecord.get());
        } else {
            throw new PersonalRecordNotFoundException("Personal record id: " + id + " doesn't exist");
        }
    }
}
