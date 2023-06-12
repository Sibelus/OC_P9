package com.mediscreen.microservice_PractitionerNote.service;

import com.mediscreen.microservice_PractitionerNote.model.PractitionerNote;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IPractitionerNoteService {
    List<PractitionerNote> getPatientNotes(String patientId);
    Optional<PractitionerNote> getPatientNote(String id);

    void addPatientNote(PractitionerNote practitionerNote);
    void updatePatientNote(PractitionerNote practitionerNote);
    void deletePatientNote(PractitionerNote practitionerNote);
}
