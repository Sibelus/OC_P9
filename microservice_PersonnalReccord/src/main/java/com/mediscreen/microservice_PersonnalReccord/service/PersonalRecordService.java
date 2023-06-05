package com.mediscreen.microservice_PersonnalReccord.service;


import com.mediscreen.microservice_PersonnalReccord.model.PersonalRecord;
import com.mediscreen.microservice_PersonnalReccord.repository.PersonalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalRecordService implements IPersonalRecordService {

    @Autowired
    PersonalRecordRepository personalRecordRepository;


    @Override
    public List<PersonalRecord> getPatientList() {
        List<PersonalRecord> allPatient = personalRecordRepository.findAll();
        return allPatient;
    }

    @Override
    public Optional<PersonalRecord> getPatientInfos(int id) {
        Optional<PersonalRecord> patientInfo = personalRecordRepository.findById(id);
        return patientInfo;
    }

    @Override
    public void addPatientInfo(PersonalRecord personalRecord) {
        personalRecordRepository.save(personalRecord);
    }

    @Override
    public void updatePatientInfo(PersonalRecord personalRecord) {
        personalRecordRepository.save(personalRecord);
    }

    @Override
    public void deletePatientInfo(PersonalRecord personalRecord) {
        personalRecordRepository.deleteById(personalRecord.getId());
    }
}
