package com.mediscreen.microservice_DiabetesReport.controller;

import com.mediscreen.microservice_DiabetesReport.model.DiabetesInfo;
import com.mediscreen.microservice_DiabetesReport.service.IDiabetesReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiabetesReportController {

    @Autowired
    private IDiabetesReportService iDiabetesReportService;
    Logger logger = LoggerFactory.getLogger(DiabetesReportController.class);



    // getMapping method
    @GetMapping("/assess/{id}")
    public String getDiabetesAssessmentById(@PathVariable int id) {
        DiabetesInfo diabetesInfo = iDiabetesReportService.getDiabetesInfo(id);
        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        return diabetesAssessment;
    }


    // postMapping method
    @PostMapping("/assess/id")
    public String postDiabetesAssessmentById(@RequestParam int patId) {
        DiabetesInfo diabetesInfo = iDiabetesReportService.getDiabetesInfo(patId);
        String diabetesAssessment = iDiabetesReportService.diabetesAssessment(diabetesInfo);
        return diabetesAssessment;
    }

    @PostMapping("/assess/familyName")
    public String postDiabetesAssessmentByFamilyName(@RequestParam String familyName) {
        List<DiabetesInfo> diabetesInfos = iDiabetesReportService.getDiabetesInfoList();
        List<String> diabetesAssessments = iDiabetesReportService.diabetesAssessmentByRiskLevel(diabetesInfos, familyName);
        return diabetesAssessments.toString();
    }
}
