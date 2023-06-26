package com.mediscreen.microservice_PractitionerNote.service;

import com.mediscreen.microservice_PractitionerNote.model.PractitionerNote;
import com.mediscreen.microservice_PractitionerNote.repository.PractitionerNoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PractitionerNoteService implements IPractitionerNoteService {

    @Autowired
    private PractitionerNoteRepository practitionerNoteRepository;
    Logger logger = LoggerFactory.getLogger(PractitionerNoteService.class);

    /**
     * Method that get all patient's notes from database by patient's id
     * @param patientId
     * @return
     */
    @Override
    public List<PractitionerNote> getPatientNotes(String patientId) {
        List<PractitionerNote> practitionerNotes = practitionerNoteRepository.findAllByPatId(patientId);
        return practitionerNotes;
    }

    /**
     * Method that get a note from database by its id
     * @param id
     * @return
     */
    @Override
    public Optional<PractitionerNote> getPatientNote(String id) {
        Optional<PractitionerNote> practitionerNote = practitionerNoteRepository.findById(id);
        logger.debug("Practitioner note id {} is present in database: {}", id, practitionerNote.isPresent());
        return practitionerNote;
    }

    /**
     * Method that create a new note into database
     * @param practitionerNote {@Link PractitionerNote}
     */
    @Override
    public void addPatientNote(PractitionerNote practitionerNote) {
        logger.debug("Create new practitioner note to database, {}", practitionerNote.toString());
        practitionerNoteRepository.save(practitionerNote);
    }

    /**
     * Method that update a note into database
     * @param practitionerNote {@Link PractitionerNote}
     */
    @Override
    public void updatePatientNote(PractitionerNote practitionerNote) {
        logger.debug("Update practitioner note to database, {}", practitionerNote.toString());
        practitionerNoteRepository.save(practitionerNote);
    }

    /**
     * Method that delete a note from database
     * @param practitionerNote {@Link PractitionerNote}
     */
    @Override
    public void deletePatientNote(PractitionerNote practitionerNote) {
        logger.debug("Delete practitioner note to database, {}", practitionerNote.toString());
        practitionerNoteRepository.delete(practitionerNote);
    }

    /**
     * Method that delete a list of notes from database
     * @param practitionerNotes is a list of {@link PractitionerNote}
     */
    @Override
    public void deletePatientNotes(List<PractitionerNote> practitionerNotes) {
        logger.debug("Delete practitioner note to database, {}", practitionerNotes.toString());
        practitionerNoteRepository.deleteAll(practitionerNotes);
    }
}
