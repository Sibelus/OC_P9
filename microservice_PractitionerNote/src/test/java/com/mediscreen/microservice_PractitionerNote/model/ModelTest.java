package com.mediscreen.microservice_PractitionerNote.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ModelTest {

    @Test
    public void testPractitionerNoteBean_SetAndGetInfo() {
        //GIVEN
        PractitionerNote practitionerNote = new PractitionerNote();
        String id = "1";
        String patId = "1";
        String content = "test content";
        Date date = new Date();
        String toString = "PractitionerNote{" +
                "id='" + id + '\'' +
                ", patId='" + patId + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';

        //WHEN
        practitionerNote.setId(id);
        practitionerNote.setPatId(patId);
        practitionerNote.setContent(content);
        practitionerNote.setDate(date);

        //THEN
        Assertions.assertEquals(id, practitionerNote.getId());
        Assertions.assertEquals(patId, practitionerNote.getPatId());
        Assertions.assertEquals(content, practitionerNote.getContent());
        Assertions.assertEquals(date, practitionerNote.getDate());
        Assertions.assertEquals(toString, practitionerNote.toString());
    }
}
