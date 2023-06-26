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


    /**
     * Method that get the list of all personal records from database
     * @return
     */
    @Override
    public List<PersonalRecord> getPatientList() {
        logger.debug("Get all personal records");
        List<PersonalRecord> allPatient = personalRecordRepository.findAll();
        return allPatient;
    }

    /**
     * Method that get a personal record from database by its id
     * @param id
     * @return
     */
    @Override
    public Optional<PersonalRecord> getPatientInfos(int id) {
        Optional<PersonalRecord> patientInfo = personalRecordRepository.findById(id);
        logger.debug("Personal record id is present in database: {}", patientInfo.isPresent());
        return patientInfo;
    }

    /**
     * Method that get a personal record from database by its familyName
     * @param familyName
     * @return
     */
    @Override
    public Optional<PersonalRecord> getPatientInfosByFamilyName(String familyName) {
        Optional<PersonalRecord> patientInfo = personalRecordRepository.findByLastname(familyName);
        logger.debug("Personal record id is present in database: {}", patientInfo.isPresent());
        return patientInfo;
    }

    /**
     * Method that create a new personal record in database
     * @param personalRecord {@Link PersonalRecord}
     */
    @Override
    public void addPatientInfo(PersonalRecord personalRecord) {
        logger.debug("Create new personal record to database, {}", personalRecord.toString());
        personalRecordRepository.save(personalRecord);
    }

    /**
     * Method that update a personal record in database
     * @param personalRecord {@Link PersonalRecord}
     */
    @Override
    public void updatePatientInfo(PersonalRecord personalRecord) {
        logger.debug("Update personal record to database, {}", personalRecord.toString());
        personalRecordRepository.save(personalRecord);
    }

    /**
     * Method that delete a personal record from database
     * @param personalRecord {@Link PersonalRecord}
     */
    @Override
    public void deletePatientInfo(PersonalRecord personalRecord) {
        logger.debug("Delete personal record from database, {}", personalRecord.toString());
        personalRecordRepository.deleteById(personalRecord.getId());
    }
}
