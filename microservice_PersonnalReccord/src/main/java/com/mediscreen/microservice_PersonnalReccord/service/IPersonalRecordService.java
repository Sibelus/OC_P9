package com.mediscreen.microservice_PersonnalReccord.service;

import com.mediscreen.microservice_PersonnalReccord.model.PersonalRecord;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IPersonalRecordService {

    List<PersonalRecord> getPatientList();
    Optional<PersonalRecord> getPatientInfos(int id);

    void addPatientInfo(PersonalRecord personalRecord);
    void updatePatientInfo(PersonalRecord personalRecord);
    void deletePatientInfo(PersonalRecord personalRecord);
}
