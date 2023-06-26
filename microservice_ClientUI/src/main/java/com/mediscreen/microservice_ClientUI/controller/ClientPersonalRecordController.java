package com.mediscreen.microservice_ClientUI.controller;

import com.mediscreen.microservice_ClientUI.beans.PersonalRecordBean;
import com.mediscreen.microservice_ClientUI.exception.PersonalRecordNotFoundException;
import com.mediscreen.microservice_ClientUI.proxy.PersonalRecordProxy;
import com.mediscreen.microservice_ClientUI.proxy.PractitionerNoteProxy;
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
    @Autowired
    private PractitionerNoteProxy practitionerNoteProxy;
    Logger logger = LoggerFactory.getLogger(ClientPersonalRecordController.class);


    // getMapping methods

    /**
     * Method that get all personal records from database
     * @param model {@link PersonalRecordBean}
     * @return return a list that contains all personal records {@link PersonalRecordBean}
     */
    @RequestMapping("/")
    public String accueil(Model model){
        List<PersonalRecordBean> patients =  personalRecordProxy.getPatientList();
        model.addAttribute("patients", patients);
        return "home";
    }

    /**
     * Method that get a personal record by its id and set it to the view
     * If the personal record's id doesn't exist it will throw {@link PersonalRecordNotFoundException}
     * @param id -> refer to the id of a personal record
     * @param model {@link PersonalRecordBean}
     * @return if personal record's id exist it will return to the personal record's page
     *         else it will return error page with errorMessage that specify the error
     */
    @GetMapping("/personalRecord/{id}")
    public String getPersonalRecord(@PathVariable int id, Model model) {
        logger.debug("*** getPersonalRecord *** is requested to microservice-PersonalRecord");
        try {
            PersonalRecordBean personalRecordBean = personalRecordProxy.getPatientInfo(id).get();
            model.addAttribute("personalRecordBean", personalRecordBean);
            return "personalRecord/get";
        } catch (PersonalRecordNotFoundException e ) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
    }

    /**
     * Method that set a view with necessary fields to create a new personal record
     * @param personalRecordBean {@link PersonalRecordBean}
     * @return
     */
    @GetMapping("personalRecord/add")
    public String addPatientInfo(PersonalRecordBean personalRecordBean) {
        return "personalRecord/add";
    }

    /**
     * Method that get a personal record by its id and set all info to a view that has field to update its content
     * If personal record's id doesn't exist it will throw {@link PersonalRecordNotFoundException}
     * @param id -> refer to the id of a personal record
     * @param model {@link PersonalRecordBean}
     * @return if personal record's id exist it will return to the personal record's update page
     *         else it will return error page with errorMessage that specify the error
     */
    @GetMapping("/personalRecord/update/{id}")
    public String updatePatientInfo(@PathVariable int id, Model model) {
        logger.debug("*** updatePatientInfo *** is requested to microservice-PersonalRecord");
        try {
            PersonalRecordBean personalRecordBean = personalRecordProxy.updatePatientInfo(id).get();
            model.addAttribute("personalRecordBean", personalRecordBean);
            return "personalRecord/update";
        } catch (PersonalRecordNotFoundException e ) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
    }

    /**
     * Method that delete a personal record by its id
     * If the personal record's id doesn't exist it will throw {@link PersonalRecordNotFoundException}
     * @param id -> refer to the id of a personal record
     * @param model
     * @return if personal record's id exist it will return to the personal record's list page
     *         else it will return error page with errorMessage that specify the error
     */
    @GetMapping("/personalRecord/delete/{id}")
    public String deletePatientInfo_submit(@PathVariable int id, Model model) {
        logger.debug("*** deletePatientInfo *** is requested to microservice-PersonalRecord");
        try {
            personalRecordProxy.deletePatientInfo(id);
            practitionerNoteProxy.getDeletePatientNotes(String.valueOf(id));
            return "redirect:/";
        } catch (PersonalRecordNotFoundException e ) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
    }



    // postMapping methods

    /**
     * Method that save a new personal record to database
     * The personal record's id is set automatically by database
     * @param personalRecordBean {@link PersonalRecordBean}
     * @param result
     * @param model
     * @return if a valid PersonalRecord is sent, the method save it to database and then redirect to the list of personal records
     *         else it will return the personal record's creation page and specify attribute's constraints under each field that doesn't respect it
     */
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

    /**
     * Method that update the content of a personal record to database
     * A personal record doesn't have to change its id
     * -> id is inaccessible for modification, so it was returned with null value
     * -> Then id reset with correct info
     * @param id -> refer to the id of a personal record
     * @param personalRecordBean {@link PersonalRecordBean}
     * @param result
     * @param model
     * @return if a valid PersonalRecord is sent, the method update it to database and then redirect to the personal record's page
     *         else it will return the personal record's update page and specify attribute's constraints under each field that doesn't respect it
     */
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
