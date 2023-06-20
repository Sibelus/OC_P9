package com.mediscreen.microservice_ClientUI.controller;

import com.mediscreen.microservice_ClientUI.beans.PractitionerNoteBean;
import com.mediscreen.microservice_ClientUI.exception.PersonalRecordNotFoundException;
import com.mediscreen.microservice_ClientUI.exception.PractitionerNoteNotFoundException;
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

import java.util.*;

@Controller
public class ClientPractitionerNoteController {

    @Autowired
    private PractitionerNoteProxy practitionerNoteProxy;
    Logger logger = LoggerFactory.getLogger(ClientPractitionerNoteController.class);


    // getMapping methods
    @GetMapping("/patHistory/{patId}")
    public String getPatientNotes(@PathVariable("patId") String patId, Model model) {
        List<PractitionerNoteBean> practitionerNoteBeans = practitionerNoteProxy.getPatientNotes(patId);
        Collections.sort(practitionerNoteBeans, Collections.reverseOrder());

        int patId_int = Integer.valueOf(patId);
        model.addAttribute("practitionerNoteBeans", practitionerNoteBeans);
        model.addAttribute("patId", patId);
        model.addAttribute("patId_int", patId_int);
        return "practitionerNote/notes";
    }

    @GetMapping("/patHistory/get/{id}")
    public String getPatientNote(@PathVariable String id, Model model) {
        logger.debug("*** getPatientNote *** is requested to microservice-PractitionerNote");
        try {
            PractitionerNoteBean practitionerNoteBean = practitionerNoteProxy.getPatientNote(id).get();
            model.addAttribute("practitionerNoteBean", practitionerNoteBean);
            return "practitionerNote/note";
        } catch (PractitionerNoteNotFoundException e ) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
    }

    @GetMapping("/patHistory/add/{patId}")
    public String addPatientNote(@PathVariable("patId") String patId, PractitionerNoteBean practitionerNoteBean) {
        return "practitionerNote/add";
    }

    @GetMapping("/patHistory/update/{id}")
    public String getUpdatePatientNote(@PathVariable String id, Model model) {
        logger.debug("*** updatePatientNote *** is requested to microservice-PractitionerNote");
        try {
            PractitionerNoteBean practitionerNoteBean = practitionerNoteProxy.getUpdatePatientNote(id).get();
            model.addAttribute("practitionerNoteBean", practitionerNoteBean);
            return "practitionerNote/update";
        } catch (PractitionerNoteNotFoundException e ) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
    }

    @GetMapping("/patHistory/delete/{id}")
    public String getDeletePatientNote(@PathVariable String id, Model model) {
        logger.debug("*** deletePatientNote *** is requested to microservice-PractitionerNote");
        try {
            practitionerNoteProxy.getDeletePatientNote(id);
            String patId = practitionerNoteProxy.getPatientNote(id).get().getPatId();
            return "redirect:/patHistory/" + patId;
        }  catch (PractitionerNoteNotFoundException e ) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
    }


    // postMapping methods
    @PostMapping("/patHistory/add/{patId}")
    public String addPatientNote(@PathVariable("patId") String patId, @Valid PractitionerNoteBean practitionerNoteBean, BindingResult result, Model model) {
        logger.debug("POST practitionerNoteBean: {}", practitionerNoteBean.toString());
        model.addAttribute("patId", patId);

        if (!result.hasErrors()) {
            logger.debug("*** addPatientNote *** is requested to microservice-PractitionerNote");
            practitionerNoteBean.setPatId(patId);
            practitionerNoteBean.setDate(new Date());
            practitionerNoteProxy.addPatientNote(practitionerNoteBean);
            return "redirect:/patHistory/" + practitionerNoteBean.getPatId();
        }

        logger.info("Try to send invalid request");
        return "practitionerNote/add";
    }


    @PostMapping("/patHistory/update/{id}")
    public String updatePatientNote(@PathVariable String id, @Valid PractitionerNoteBean practitionerNote, BindingResult result, Model model){
        /*
        A practitionerNote does'nt have to change the patient to which it is linked
        The date value has to be set when the content is changed
        -> patId & date are inaccessible for modification, so they are returned with null value
        -> Then patId & date are reset with correct info
        */
        logger.debug("POST practitionerNoteBean: {}", practitionerNote.toString());

        if (!result.hasErrors()) {
            logger.debug("*** updatePatientNote *** is requested to microservice-PractitionerNote");
            String patId = practitionerNoteProxy.getPatientNote(id).get().getPatId();
            practitionerNote.setPatId(practitionerNoteProxy.getPatientNote(id).get().getPatId());
            practitionerNote.setDate(new Date());
            practitionerNoteProxy.updatePatientNote(practitionerNote);
            return "redirect:/patHistory/" + patId;
        }

        logger.info("Try to send invalid request");
        return "practitionerNote/update";
    }
}
