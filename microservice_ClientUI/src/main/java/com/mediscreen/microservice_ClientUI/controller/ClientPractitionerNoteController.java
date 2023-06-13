package com.mediscreen.microservice_ClientUI.controller;

import com.mediscreen.microservice_ClientUI.beans.PractitionerNoteBean;
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

import java.util.Date;
import java.util.List;

@Controller
public class ClientPractitionerNoteController {

    @Autowired
    private PractitionerNoteProxy practitionerNoteProxy;
    Logger logger = LoggerFactory.getLogger(ClientPractitionerNoteController.class);


    // getMapping methods
    @GetMapping("/patHistory/{patId}")
    public String getPatientNotes(@PathVariable("patId") String patId, Model model) {
        List<PractitionerNoteBean> practitionerNoteBeans = practitionerNoteProxy.getPatientNotes(patId);
        int patId_int = Integer.valueOf(patId);
        model.addAttribute("practitionerNoteBeans", practitionerNoteBeans);
        model.addAttribute("patId_int", patId_int);
        return "practitionerNote/notes";
    }

    @GetMapping("/patHistory/get/{id}")
    public String getPatientNote(@PathVariable String id, Model model) {
        logger.debug("*** getPatientNote *** is requested to microservice-PractitionerNote");
        PractitionerNoteBean practitionerNoteBean = practitionerNoteProxy.getPatientNote(id).get();
        model.addAttribute("practitionerNoteBean", practitionerNoteBean);
        return "practitionerNote/note";
    }

    @GetMapping("/patHistory/add")
    public String addPatientNote(PractitionerNoteBean practitionerNoteBean) {
        return "practitionerNote/add";
    }

    @GetMapping("/patHistory/update/{id}")
    public String getUpdatePatientNote(@PathVariable String id, Model model) {
        logger.debug("*** updatePatientNote *** is requested to microservice-PractitionerNote");
        PractitionerNoteBean practitionerNoteBean = practitionerNoteProxy.getUpdatePatientNote(id).get();
        model.addAttribute("practitionerNoteBean", practitionerNoteBean);
        return "practitionerNote/update";
    }

    @GetMapping("/patHistory/delete/{id}")
    public String getDeletePatientNote(@PathVariable String id) {
        logger.debug("*** deletePatientNote *** is requested to microservice-PractitionerNote");
        String patId = practitionerNoteProxy.getPatientNote(id).get().getPatId();
        practitionerNoteProxy.getDeletePatientNote(id);
        return "redirect:/patHistory/" + patId;
    }


    // postMapping methods
    @PostMapping("/patHistory/add")
    public String addPatientNote(@Valid PractitionerNoteBean practitionerNoteBean, BindingResult result, Model model) {
        logger.debug("POST practitionerNoteBean: {}", practitionerNoteBean.toString());

        if (!result.hasErrors()) {
            logger.debug("*** addPatientNote *** is requested to microservice-PractitionerNote");
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
