package com.mediscreen.microservice_PractitionerNote.controller;

import com.mediscreen.microservice_PractitionerNote.exceptions.PractitionerNoteNotFoundException;
import com.mediscreen.microservice_PractitionerNote.model.PractitionerNote;
import com.mediscreen.microservice_PractitionerNote.service.IPractitionerNoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PractitionerNoteController {

    @Autowired
    private IPractitionerNoteService iPractitionerNoteService;
    Logger logger = LoggerFactory.getLogger(PractitionerNoteController.class);


    /**
     * Method that get all the notes of a patient by its patient id
     * @param patId -> refer to the patient id
     * @return a list of notes, if there is no notes for this patient it will return an empty list
     * @throws Exception
     */
    @GetMapping("/patHistory/{patId}")
    public List<PractitionerNote> getPatientNotes(@PathVariable("patId") String patId) throws Exception {
        logger.info("GET_patHistory -> Check if patient history id: {} exists", patId);
        List<PractitionerNote> practitionerNotes = iPractitionerNoteService.getPatientNotes(patId);
        return practitionerNotes;
    }

    /**
     * Method that get a note by its id
     * @param id -> refer to the note's id
     * @return if practitionerNote's id exist it will return {@link PractitionerNote}
     *         else it will throw a {@link PractitionerNoteNotFoundException}
     * @throws Exception
     */
    @GetMapping("/patHistory/get/{id}")
    public Optional<PractitionerNote> getPatientNote(@PathVariable String id) throws Exception {
        logger.info("GET_patHistory -> Check if patient history id: {} exists", id);
        Optional<PractitionerNote> practitionerNote = iPractitionerNoteService.getPatientNote(id);
        if (!practitionerNote.isPresent()) throw new PractitionerNoteNotFoundException("There is no data for the id: " + id);
        return practitionerNote;
    }

    /**
     * Method that get a note by its id to update its content
     * @param id -> refer to the note's id
     * @return if practitionerNote's id exist it will return {@link PractitionerNote}
     *         else it will throw a {@link PractitionerNoteNotFoundException}
     * @throws Exception
     */
    @GetMapping("/patHistory/update/{id}")
    public Optional<PractitionerNote> getUpdatePatientNote(@PathVariable String id) throws Exception {
        logger.info("UPDATE_patHistory -> Check if patient history id: {} exists", id);
        Optional<PractitionerNote> practitionerNote = iPractitionerNoteService.getPatientNote(id);
        if (!practitionerNote.isPresent()) throw new PractitionerNoteNotFoundException("There is no data for the id: " + id);
        return practitionerNote;
    }

    /**
     * Method that delete a note by its id
     * If note's id doesn't exist it will throw a {@link PractitionerNoteNotFoundException}
     * @param id -> refer to the note's id
     * @throws Exception
     */
    @GetMapping("/patHistory/delete/{id}")
    public void getDeletePatientNote(@PathVariable String id) throws Exception {
        logger.info("DELETE_patHistory -> Check if patient history id: {} exists", id);
        Optional<PractitionerNote> practitionerNote = iPractitionerNoteService.getPatientNote(id);
        if (practitionerNote.isPresent()){
            iPractitionerNoteService.deletePatientNote(practitionerNote.get());
        }else {
            throw new PractitionerNoteNotFoundException("Practitioner note id: " + id + " doesn't exist");
        }
    }

    /**
     * Method that delete the list of notes related to the patient id = patId
     * @param patId -> refer to the patient id
     * @throws Exception
     */
    @GetMapping("/patHistory/delete/list/{patId}")
    public void getDeletePatientNotes(@PathVariable("patId") String patId) throws Exception {
        logger.info("GET_patHistory -> Check if patient history id: {} exists", patId);
        List<PractitionerNote> practitionerNotes = iPractitionerNoteService.getPatientNotes(patId);
        if (!practitionerNotes.isEmpty()) {
            iPractitionerNoteService.deletePatientNotes(practitionerNotes);
        }
    }



    // postMapping methods

    /**
     * Method that save a new note into database
     * @param practitionerNote {@link PractitionerNote}
     * @return a ResponseEntity<PractitionerNote> {@link ResponseEntity} {@link PractitionerNote} with http status 201
     * @throws Exception
     */
    @PostMapping("/patHistory/add")
    public ResponseEntity<PractitionerNote> addPatientNote(@RequestBody PractitionerNote practitionerNote) throws Exception {
        logger.info("Create request body contains, {}", practitionerNote.toString());
        iPractitionerNoteService.addPatientNote(practitionerNote);
        return new ResponseEntity<PractitionerNote>(practitionerNote, HttpStatus.CREATED);
    }

    /**
     * Method that update the content of a note into database
     * @param practitionerNote {@link PractitionerNote}
     * @return a ResponseEntity<PractitionerNote> {@link ResponseEntity} {@link PractitionerNote} with http status 200
     * @throws Exception
     */
    @PostMapping("/patHistory/update")
    public ResponseEntity<PractitionerNote> updatePatientNote(@RequestBody PractitionerNote practitionerNote) throws Exception {
        logger.info("Update request body contains, {}", practitionerNote.toString());
        iPractitionerNoteService.updatePatientNote(practitionerNote);
        return new ResponseEntity<PractitionerNote>(practitionerNote, HttpStatus.OK);
    }
}
