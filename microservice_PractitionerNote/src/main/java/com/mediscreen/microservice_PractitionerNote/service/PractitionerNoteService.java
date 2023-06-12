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

    @Override
    public List<PractitionerNote> getPatientNotes(String patientId) {
        List<PractitionerNote> practitionerNotes = practitionerNoteRepository.findAllByPatId(patientId);
        return practitionerNotes;
    }

    @Override
    public Optional<PractitionerNote> getPatientNote(String id) {
        Optional<PractitionerNote> practitionerNote = practitionerNoteRepository.findById(id);
        logger.debug("Practitioner note id {} is present in database: {}", id, practitionerNote.isPresent());
        return practitionerNote;
    }

    @Override
    public void addPatientNote(PractitionerNote practitionerNote) {
        logger.debug("Create new practitioner note to database, {}", practitionerNote.toString());
        practitionerNoteRepository.save(practitionerNote);
    }

    @Override
    public void updatePatientNote(PractitionerNote practitionerNote) {
        logger.debug("Update practitioner note to database, {}", practitionerNote.toString());
        practitionerNoteRepository.save(practitionerNote);
    }

    @Override
    public void deletePatientNote(PractitionerNote practitionerNote) {
        logger.debug("Delete practitioner note to database, {}", practitionerNote.toString());
        practitionerNoteRepository.delete(practitionerNote);
    }
}
