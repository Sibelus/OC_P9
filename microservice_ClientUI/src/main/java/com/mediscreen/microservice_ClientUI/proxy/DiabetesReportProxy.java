package com.mediscreen.microservice_ClientUI.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interface that link microservice-ClientUI to microservice-DiabetesReport and granted access to listed methods
 * It permits to manage everything concerning diabetes report
 */
@FeignClient(name = "microservice-DiabetesReport", url = "diabetesreport:8083")
public interface DiabetesReportProxy {


    // postMapping method

    /**
     * Method that assess diabetes risk level for a patient by its id
     * @param patId -> refer to the patient id
     * @return a string that contains demographic info and diabetes assessment
     */
    @PostMapping("/assess/id")
    public String postDiabetesAssessmentById(@RequestParam int patId);

    /**
     * Method that assess diabetes risk level for a patient by its family name
     * @param familyName -> refer to the patient family name
     * @return a string that contains demographic info and diabetes assessment
     */
    @PostMapping("/assess/familyName")
    public String postDiabetesAssessmentByFamilyName(@RequestParam String familyName);
}
