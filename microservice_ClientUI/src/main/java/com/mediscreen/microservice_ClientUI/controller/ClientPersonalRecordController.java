package com.mediscreen.microservice_ClientUI.controller;

import com.mediscreen.microservice_ClientUI.beans.PersonalRecordBean;
import com.mediscreen.microservice_ClientUI.proxy.PersonalRecordProxy;
import jakarta.validation.Valid;
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
        logger.debug("*** getPersonalRecord *** is requested to microservice-PersonalRecord");
        PersonalRecordBean personalRecordBean = personalRecordProxy.getPatientInfo(id).get();
        model.addAttribute("personalRecordBean", personalRecordBean);
        return "personalRecord/get";
    }

    @GetMapping("personalRecord/add")
    public String addPatientInfo(PersonalRecordBean personalRecordBean) {
        return "personalRecord/add";
    }

    @GetMapping("/personalRecord/update/{id}")
    public String updatePatientInfo(@PathVariable int id, Model model) {
        logger.debug("*** updatePatientInfo *** is requested to microservice-PersonalRecord");
        PersonalRecordBean personalRecordBean = personalRecordProxy.updatePatientInfo(id).get();
        model.addAttribute("personalRecordBean", personalRecordBean);
        return "personalRecord/update";
    }

    @GetMapping("/personalRecord/delete/{id}")
    public String deletePatientInfo_submit(@PathVariable int id) {
        logger.debug("*** deletePatientInfo *** is requested to microservice-PersonalRecord");
        personalRecordProxy.deletePatientInfo(id);
        return "redirect:/";
    }



    // postMapping methods
    @PostMapping("/personalRecord/validate")
    public String createPatientInfo_submit(@Valid PersonalRecordBean personalRecordBean, BindingResult result, Model model) {
        logger.debug("POST personalRecordBean: {}", personalRecordBean.toString());

        if (!result.hasErrors()) {
            logger.debug("*** createPatientInfo_submit *** is requested to microservice-PersonalRecord");
            personalRecordProxy.createPatientInfo_submit(personalRecordBean);
            model.addAttribute("patients", personalRecordProxy.getPatientList());
            return "redirect:/";
        }

        logger.info("Try to send invalid request");
        return "personalRecord/add";
    }

    @PostMapping("/personalRecord/update/{id}")
    public String updatePatientInfo_Submit(@PathVariable int id, @Valid PersonalRecordBean personalRecordBean, BindingResult result, Model model) {
        logger.debug("POST personalRecordBean: {}", personalRecordBean.toString());

        if (!result.hasErrors()) {
            personalRecordBean.setId(id);
            logger.debug("*** updatePatientInfo_Submit *** is requested to microservice-PersonalRecord");
            personalRecordProxy.updatePatientInfo_Submit(personalRecordBean);
            model.addAttribute("patient", personalRecordBean);
            return "redirect:/personalRecord/{id}";
        }

        logger.info("Try to send invalid request");
        return "personalRecord/update";
    }
}
