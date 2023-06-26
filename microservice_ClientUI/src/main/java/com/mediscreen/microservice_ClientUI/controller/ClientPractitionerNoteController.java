package com.mediscreen.microservice_ClientUI.controller;

import com.mediscreen.microservice_ClientUI.beans.PersonalRecordBean;
import com.mediscreen.microservice_ClientUI.beans.PractitionerNoteBean;
import com.mediscreen.microservice_ClientUI.exception.PersonalRecordNotFoundException;
import com.mediscreen.microservice_ClientUI.exception.PractitionerNoteNotFoundException;
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

import java.util.*;

@Controller
public class ClientPractitionerNoteController {

    @Autowired
    private PractitionerNoteProxy practitionerNoteProxy;
    @Autowired
    private PersonalRecordProxy personalRecordProxy;
    Logger logger = LoggerFactory.getLogger(ClientPractitionerNoteController.class);


    // getMapping methods

    /**
     * Method that get all notes of a patient and set it to the view
     * @param patId -> refer to the id of a patient
     * @param model {@link PractitionerNoteBean}
     * @return a list of all notes for a patient {@link PractitionerNoteBean}
     */
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

    /**
     * Method that get a note by its id and set it to the view
     * If the note's id doesn't exist it will throw {@link PractitionerNoteNotFoundException}
     * @param id -> refer to the id of a note
     * @param model {@link PractitionerNoteBean}
     * @return if note's id exist it will return to the note's page
     *         else it will return error page with errorMessage that specify the error
     */
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

    /**
     * Method that set a view with necessary fields to create a new note for the patient with id = patId
     * @param patId -> refer to the id of a patient
     * @param practitionerNoteBean {@link PractitionerNoteBean}
     * @return if patId doesn't have a match with an existing personal record's id, it will return the view to create a new note
     *         else it will return error page with errorMessage that specify the error
     */
    @GetMapping("/patHistory/add/{patId}")
    public String addPatientNote(@PathVariable("patId") String patId, PractitionerNoteBean practitionerNoteBean, Model model) {
        logger.debug("*** getPersonalRecord *** is requested to microservice-PersonalRecord");
        try {
            PersonalRecordBean personalRecordBean = personalRecordProxy.getPatientInfo(Integer.parseInt(patId)).get();
            model.addAttribute("personalRecordBean", personalRecordBean);
            return "practitionerNote/add";
        } catch (PersonalRecordNotFoundException e ) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
    }

    /**
     * Methode that get a note by its id and set all info to a view that has field to update its content
     * If the note's id doesn't exist it will throw {@link PractitionerNoteNotFoundException}
     * @param id
     * @param model {@link PractitionerNoteBean}
     * @return if note's id exist it will return to the note's update page
     *         else it will return error page with errorMessage that specify the error
     */
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

    /**
     * Methode that delete a note by its id
     * If the note's id doesn't exist it will throw {@link PractitionerNoteNotFoundException}
     * @param id -> refer to the id of a note
     * @param model {@link PractitionerNoteBean}
     * @return if note's id exist it will redirect to the patient's list of note
     *         else it will return error page with errorMessage that specify the error
     */
    @GetMapping("/patHistory/delete/{id}")
    public String getDeletePatientNote(@PathVariable String id, Model model) {
        logger.debug("*** deletePatientNote *** is requested to microservice-PractitionerNote");
        try {
            String patId = practitionerNoteProxy.getPatientNote(id).get().getPatId();
            practitionerNoteProxy.getDeletePatientNote(id);
            return "redirect:/patHistory/" + patId;
        }  catch (PractitionerNoteNotFoundException e ) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
    }


    // postMapping methods

    /**
     * Method that save a new note related to the patient id = patId
     * A practitionerNote doesn't have to change the patient to which it is linked
     * The date value has to be set when the content is created
     * -> patId & date are inaccessible for creation, so they are returned with null value
     * -> Then patId & date are reset with correct info
     * @param patId -> refer to the id of a patient
     * @param practitionerNoteBean {@link PractitionerNoteBean}
     * @param result
     * @param model
     * @return if a valid PractitionerNoteBean is sent, the method save it to database and then redirect to the list of notes related to the patient
     *         else it will return error page with errorMessage that specify the error
     */
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


    /**
     * Method that update the content of a note related to the patient id = patId
     * A practitionerNote doesn't have to change the patient to which it is linked
     * The date value has to be set when the content is changed
     * -> patId & date are inaccessible for modification, so they are returned with null value
     * -> Then patId & date are reset with correct info
     * @param id -> refer to the id of a note
     * @param practitionerNote {@link PractitionerNoteBean}
     * @param result
     * @param model
     * @return if a valid PractitionerNoteBean is sent, the method update it to database and then redirect to the list of notes related to the patient
     *         else it will return error page with errorMessage that specify the error
     */
    @PostMapping("/patHistory/update/{id}")
    public String updatePatientNote(@PathVariable String id, @Valid PractitionerNoteBean practitionerNote, BindingResult result, Model model){
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
