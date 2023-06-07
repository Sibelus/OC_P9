package com.mediscreen.microservice_ClientUI.controller;

import com.mediscreen.microservice_ClientUI.beans.PersonalRecordBean;
import com.mediscreen.microservice_ClientUI.proxy.PersonalRecordProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ClientPersonalRecordController {

    @Autowired
    private PersonalRecordProxy personalRecordProxy;
    Logger logger = LoggerFactory.getLogger(ClientPersonalRecordController.class);


    // getMapping methods
    @RequestMapping("/")
    public String accueil(Model model){
        List<PersonalRecordBean> patients =  personalRecordProxy.getPatientList();
        model.addAttribute("patients", patients);
        return "home";
    }

    @GetMapping("/personalRecord/{id}")
    public String getPersonalRecord(@PathVariable int id, Model model) {
        PersonalRecordBean personalRecordBean = personalRecordProxy.getPatientInfo(id).get();
        model.addAttribute("patient", personalRecordBean);
        return "personalRecord";
    }

    @GetMapping("personalRecord/add")
    public String addPatientInfo(PersonalRecordBean patient, Model model) {
        model.addAttribute("patient", patient);
        return "personalRecordAdd";
    }

    @GetMapping("/personalRecord/update/{id}")
    public String updatePatientInfo(@PathVariable int id, Model model) {
        PersonalRecordBean personalRecordBean = personalRecordProxy.updatePatientInfo(id).get();
        model.addAttribute("patient", personalRecordBean);
        return "personalRecordUpdate";
    }

    @GetMapping("/personalRecord/delete/{id}")
    public String deletePatientInfo_submit(@PathVariable int id) {
        personalRecordProxy.deletePatientInfo(id);
        return "redirect:/";
    }



    // postMapping methods
    @PostMapping("/personalRecord/validate")
    public String createPatientInfo_submit(@Valid PersonalRecordBean patient, BindingResult result, Model model) {
        logger.debug("POST personalRecordBean: {}", patient.toString());

        //TODO fix if statement -> constraint validation
        if (!result.hasErrors()) {
            logger.debug("Send request to microservice-PersonalRecord");
            personalRecordProxy.createPatientInfo_submit(patient);
            model.addAttribute("patients", personalRecordProxy.getPatientList());
            return "redirect:/";
        }
        return "personalRecordAdd";
    }

    @PostMapping("/personalRecord/update/{id}")
    public String updatePatientInfo_Submit(@PathVariable int id, @Valid PersonalRecordBean patient, BindingResult result, Model model) {
        logger.debug("POST personalRecordBean: {}", patient.toString());

        //TODO fix if statement constraint validation
        if (!result.hasErrors()) {
            patient.setId(id);
            logger.debug("Send request to microservice-PersonalRecord");
            personalRecordProxy.updatePatientInfo_Submit(patient);
            model.addAttribute("patient", patient);
            return "redirect:/personalRecord/{id}";
        }
        return "redirect:/personalRecord/update/{id}";
    }
}
