package com.mediscreen.microservice_DiabetesReport.proxy;

import com.mediscreen.microservice_DiabetesReport.bean.PractitionerNoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


/**
 * Interface that link microservice-DiabetesReport to microservice-PractitionerNote and granted access to listed methods
 * It permits to manage everything patient note record
 */
@FeignClient(name = "microservice-PractitionerNote", url = "practitionernote:8082")
public interface PractitionerNoteProxy {

    // getMapping methods

    /**
     * Method that get all the notes of a patient by its patient id
     * @param patId -> refer to the patient id = patId
     * @return a list of notes, if there is no notes for this patient it will return an empty list
     */
    @GetMapping("/patHistory/{patId}")
    public Optional<List<PractitionerNoteBean>> getPatientNotes(@PathVariable("patId") String patId);
}
