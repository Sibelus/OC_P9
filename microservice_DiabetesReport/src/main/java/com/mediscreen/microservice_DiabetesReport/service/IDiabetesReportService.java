package com.mediscreen.microservice_DiabetesReport.service;

import com.mediscreen.microservice_DiabetesReport.model.DiabetesInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IDiabetesReportService {

    List<DiabetesInfo> getDiabetesInfoList();
    DiabetesInfo getDiabetesInfo(int id);
    String diabetesAssessment(DiabetesInfo diabetesInfo);
    List<String> diabetesAssessmentByRiskLevel(List<DiabetesInfo> diabetesInfoList, String riskLevel);
    int calculateAge(Date birthdate);
    int countTrigger(DiabetesInfo diabetesInfo);
}
