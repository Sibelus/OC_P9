package com.mediscreen.microservice_PersonnalReccord.service;


import com.mediscreen.microservice_PersonnalReccord.model.PersonalRecord;
import com.mediscreen.microservice_PersonnalReccord.repository.PersonalRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalRecordService implements IPersonalRecordService {

    @Autowired
    PersonalRecordRepository personalRecordRepository;
    Logger logger = LoggerFactory.getLogger(PersonalRecordService.class);


    @Override
    public List<PersonalRecord> getPatientList() {
        logger.debug("Get all personal records");
        List<PersonalRecord> allPatient = personalRecordRepository.findAll();
        return allPatient;
    }

    @Override
    public Optional<PersonalRecord> getPatientInfos(int id) {
        Optional<PersonalRecord> patientInfo = personalRecordRepository.findById(id);
        logger.debug("Personal record id is present in database: {}", patientInfo.isPresent());
        return patientInfo;
    }

    @Override
    public void addPatientInfo(PersonalRecord personalRecord) {
        logger.debug("Create new personal record to database, {}", personalRecord.toString());
        personalRecordRepository.save(personalRecord);
    }

    @Override
    public void updatePatientInfo(PersonalRecord personalRecord) {
        logger.debug("Update personal record to database, {}", personalRecord.toString());
        personalRecordRepository.save(personalRecord);
    }

    @Override
    public void deletePatientInfo(PersonalRecord personalRecord) {
        logger.debug("Delete personal record from database, {}", personalRecord.toString());
        personalRecordRepository.deleteById(personalRecord.getId());
    }
}
