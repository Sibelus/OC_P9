package com.mediscreen.microservice_ClientUI.controller;

import com.mediscreen.microservice_ClientUI.proxy.DiabetesReportProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientDiabetesReportController {

    @Autowired
    private DiabetesReportProxy diabetesReportProxy;
    Logger logger = LoggerFactory.getLogger(ClientPersonalRecordController.class);


    // postMapping method

    /**
     * Method that make diabetes assessment of a patient by its id
     * @param patId -> refer to the id of a personal record
     * @param model
     * @return the diabetes assessment page
     */
    @PostMapping("/assess/id")
    public String postDiabetesAssessmentById(@RequestParam int patId, Model model) {
        logger.debug("*** postDiabetesAssessmentById *** is requested to microservice-DiabetesReport");
        String diabetesAssessment = diabetesReportProxy.postDiabetesAssessmentById(patId);
        model.addAttribute("diabetesAssessment", diabetesAssessment);
        return "diabetesReport/report";
    }

    /**
     * Method that make diabetes assessment of a patient by its family name
     * @param familyName -> refer to the family name of a personal record
     * @param model
     * @return the diabetes assessment page
     */
    @PostMapping("/assess/familyName")
    public String postDiabetesAssessmentByFamilyName(@RequestParam String familyName, Model model) {
        logger.debug("*** postDiabetesAssessmentByFamilyName *** is requested to microservice-DiabetesReport");
        String diabetesAssessment = diabetesReportProxy.postDiabetesAssessmentByFamilyName(familyName);
        model.addAttribute("diabetesAssessment", diabetesAssessment);
        return "diabetesReport/report";
    }
}
