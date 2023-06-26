package com.mediscreen.microservice_PractitionerNote.service;

import com.mediscreen.microservice_PractitionerNote.model.PractitionerNote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private IPractitionerNoteService iPractitionerNoteService;


    @Test
    public void testAddPatientNote() throws Exception {
        //GIVEN
        PractitionerNote practitionerNote = new PractitionerNote();
        practitionerNote.setId("1");
        practitionerNote.setPatId("1");
        practitionerNote.setDate(new Date());
        practitionerNote.setContent("test content");

        //WHEN
        iPractitionerNoteService.addPatientNote(practitionerNote);

        //THEN
        Assertions.assertEquals(practitionerNote.getContent(), iPractitionerNoteService.getPatientNote("1").get().getContent());
    }


    @Test
    public void testGetPatientNotes() throws Exception {
        List<PractitionerNote> practitionerNotes = iPractitionerNoteService.getPatientNotes("1");
        Assertions.assertTrue(practitionerNotes.size() > 0);
    }


    @Test
    public void testGetPatientNote() throws Exception {
        //GIVEN
        PractitionerNote practitionerNote = new PractitionerNote();
        practitionerNote.setId("2");
        practitionerNote.setPatId("1");
        practitionerNote.setDate(new Date());
        practitionerNote.setContent("test content");

        iPractitionerNoteService.addPatientNote(practitionerNote);

        //WHEN
        PractitionerNote practitionerNote1 = iPractitionerNoteService.getPatientNote("2").get();
        Assertions.assertEquals(practitionerNote.getContent(), "test content");
        iPractitionerNoteService.deletePatientNote(practitionerNote);
    }


    @Test
    public void testUpdatePatientNote() throws Exception {
        //GIVEN
        PractitionerNote practitionerNote = new PractitionerNote();
        practitionerNote.setId("1");
        practitionerNote.setPatId("1");
        practitionerNote.setContent("test content updated");

        //WHEN
        iPractitionerNoteService.updatePatientNote(practitionerNote);

        //THEN
        Assertions.assertEquals(practitionerNote.getContent(), iPractitionerNoteService.getPatientNote("1").get().getContent());
    }


    @Test
    public void testDeletePatientNote() throws Exception {
        //GIVEN
        PractitionerNote practitionerNote = new PractitionerNote();
        practitionerNote.setId("1");
        practitionerNote.setPatId("1");
        practitionerNote.setContent("test content updated");

        //WHEN
        iPractitionerNoteService.deletePatientNote(practitionerNote);
        Optional<PractitionerNote> practitionerNote1 = iPractitionerNoteService.getPatientNote("1");

        //THEN
        Assertions.assertTrue(practitionerNote1.isEmpty());
    }

    @Test
    public void testDeletePatientNotes() throws Exception {
        //GIVEN
        List<PractitionerNote> practitionerNotes = new ArrayList<>();
        PractitionerNote practitionerNote = new PractitionerNote();
        practitionerNote.setId("1");
        practitionerNote.setPatId("1");
        practitionerNote.setDate(new Date());
        practitionerNote.setContent("test content");
        iPractitionerNoteService.addPatientNote(practitionerNote);

        PractitionerNote practitionerNote2 = new PractitionerNote();
        practitionerNote2.setId("2");
        practitionerNote2.setPatId("1");
        practitionerNote2.setDate(new Date());
        practitionerNote2.setContent("test content");
        iPractitionerNoteService.addPatientNote(practitionerNote2);

        practitionerNotes.add(practitionerNote);
        practitionerNotes.add(practitionerNote2);

        //WHEN
        iPractitionerNoteService.deletePatientNotes(practitionerNotes);
        Optional<PractitionerNote> deletedPractitionerNote1 = iPractitionerNoteService.getPatientNote("1");
        Optional<PractitionerNote> deletedPractitionerNote2 = iPractitionerNoteService.getPatientNote("2");

        //THEN
        Assertions.assertTrue(deletedPractitionerNote1.isEmpty());
        Assertions.assertTrue(deletedPractitionerNote2.isEmpty());
    }
}
