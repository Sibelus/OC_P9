package com.mediscreen.microservice_ClientUI.proxy;

import com.mediscreen.microservice_ClientUI.beans.PractitionerNoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


/**
 * Interface that link microservice-ClientUI to microservice-PractitionerNote and granted access to listed methods
 * It permits to manage everything concerning patient note
 */
@FeignClient(name = "microservice-PractitionerNote", url = "practitionernote:8082")
public interface PractitionerNoteProxy {


    // getMapping methods

    /**
     * Method that get all the notes of a patient by its patient id
     * @param patId -> refer to the patient id
     * @return a list of notes, if there is no notes for this patient it will return an empty list
     */
    @GetMapping("/patHistory/{patId}")
    public List<PractitionerNoteBean> getPatientNotes(@PathVariable("patId") String patId);

    /**
     * Method that get a note by its id
     * @param id -> refer to the note's id
     * @return an Optional<PractitionerNoteBean> {@link Optional} {@link PractitionerNoteBean}
     */
    @GetMapping("/patHistory/get/{id}")
    public Optional<PractitionerNoteBean> getPatientNote(@PathVariable String id);

    /**
     * Method that get a note by its id to update its content
     * @param id -> refer to the note's id
     * @return an Optional<PractitionerNoteBean> {@link Optional} {@link PractitionerNoteBean}
     */
    @GetMapping("/patHistory/update/{id}")
    public Optional<PractitionerNoteBean> getUpdatePatientNote(@PathVariable String id);


    /**
     * Method that delete a note by its id
     * @param id -> refer to the note's id
     */
    @GetMapping("/patHistory/delete/{id}")
    public void getDeletePatientNote(@PathVariable String id);

    /**
     * Method that delete the list of notes related to the patient id = patId
     * @param patId -> refer to the patient id
     */
    @GetMapping("/patHistory/delete/list/{patId}")
    public void getDeletePatientNotes(@PathVariable("patId") String patId);



    // postMapping methods

    /**
     * Method that create a new note into database
     * @param practitionerNote {@link PractitionerNoteBean}
     * @return a {@link PractitionerNoteBean} into the request body
     */
    @PostMapping("/patHistory/add")
    public ResponseEntity<PractitionerNoteBean> addPatientNote(@RequestBody PractitionerNoteBean practitionerNote);

    /**
     * Method that update the content of note into database
     * @param practitionerNote {@link PractitionerNoteBean}
     * @return a {@link PractitionerNoteBean} into the request body
     */
    @PostMapping("/patHistory/update")
    public ResponseEntity<PractitionerNoteBean> updatePatientNote(@RequestBody PractitionerNoteBean practitionerNote);
}
