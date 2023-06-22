package com.mediscreen.microservice_DiabetesReport.service;

import com.mediscreen.microservice_DiabetesReport.model.DiabetesInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IDiabetesReportService {

    DiabetesInfo getDiabetesInfo(int id);
    DiabetesInfo getDiabetesInfoByFamilyName(String familyName);
    String diabetesAssessment(DiabetesInfo diabetesInfo);
    int calculateAge(Date birthdate);
    int countTrigger(DiabetesInfo diabetesInfo);
}
