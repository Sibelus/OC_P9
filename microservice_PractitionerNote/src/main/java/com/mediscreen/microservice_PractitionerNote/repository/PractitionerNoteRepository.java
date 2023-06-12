package com.mediscreen.microservice_PractitionerNote.repository;

import com.mediscreen.microservice_PractitionerNote.model.PractitionerNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PractitionerNoteRepository extends MongoRepository<PractitionerNote, String> {
    List<PractitionerNote> findAllByPatId(String patientId);
}
