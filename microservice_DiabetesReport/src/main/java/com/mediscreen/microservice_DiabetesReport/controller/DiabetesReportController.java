package com.mediscreen.microservice_DiabetesReport.controller;

import com.mediscreen.microservice_DiabetesReport.model.DiabetesInfo;
import com.mediscreen.microservice_DiabetesReport.service.IDiabetesReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiabetesReportController {

    @Autowired
    private IDiabetesReportService iDiabetesReportService;
    Logger logger = LoggerFactory.getLogger(DiabetesReportController.class);



    // postMapping method
    @PostMapping("/assess/id")
    public String postDiabetesAssessmentById(@RequestParam int patId) {
        DiabetesInfo diabetesInfo = iDiabetesReportService.getDiabetesInfo(patId);
        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        return diabetesAssessment;
    }

    @PostMapping("/assess/familyName")
    public String postDiabetesAssessmentByFamilyName(@RequestParam String familyName) {
        DiabetesInfo diabetesInfo = iDiabetesReportService.getDiabetesInfoByFamilyName(familyName);
        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        return diabetesAssessment;
    }
}
