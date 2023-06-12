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



    @GetMapping("/patHistory/{patId}")
    public List<PractitionerNote> getPatientNotes(@PathVariable("patId") String patId) throws Exception {
        logger.info("GET_patHistory -> Check if patient history id: {} exists", patId);
        List<PractitionerNote> practitionerNotes = iPractitionerNoteService.getPatientNotes(patId);
        return practitionerNotes;
    }

    @GetMapping("/patHistory/get/{id}")
    public Optional<PractitionerNote> getPatientNote(@PathVariable String id) throws Exception {
        logger.info("GET_patHistory -> Check if patient history id: {} exists", id);
        Optional<PractitionerNote> practitionerNote = iPractitionerNoteService.getPatientNote(id);
        if (!practitionerNote.isPresent()) throw new PractitionerNoteNotFoundException("There is no data for the id: " + id);
        return practitionerNote;
    }

    @GetMapping("/patHistory/update/{id}")
    public Optional<PractitionerNote> getUpdatePatientNote(@PathVariable String id) throws Exception {
        logger.info("UPDATE_patHistory -> Check if patient history id: {} exists", id);
        Optional<PractitionerNote> practitionerNote = iPractitionerNoteService.getPatientNote(id);
        if (!practitionerNote.isPresent()) throw new PractitionerNoteNotFoundException("There is no data for the id: " + id);
        return practitionerNote;
    }

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





    @PostMapping("/patHistory/add")
    public ResponseEntity<PractitionerNote> addPatientNote(@RequestBody PractitionerNote practitionerNote) throws Exception {
        logger.info("Create request body contains, {}", practitionerNote.toString());
        iPractitionerNoteService.addPatientNote(practitionerNote);
        return new ResponseEntity<PractitionerNote>(practitionerNote, HttpStatus.CREATED);
    }

    @PostMapping("/patHistory/update")
    public ResponseEntity<PractitionerNote> updatePatientNote(@RequestBody PractitionerNote practitionerNote) throws Exception {
        logger.info("Update request body contains, {}", practitionerNote.toString());
        iPractitionerNoteService.updatePatientNote(practitionerNote);
        return new ResponseEntity<PractitionerNote>(practitionerNote, HttpStatus.OK);
    }
}
