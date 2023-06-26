package com.mediscreen.microservice_PractitionerNote.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.microservice_PractitionerNote.model.PractitionerNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }



    // Method that create new practitioner note for test
    public PractitionerNote createNoteForTest(String id, String patId) {
        PractitionerNote practitionerNote = new PractitionerNote();
        practitionerNote.setId(id);
        practitionerNote.setPatId(patId);
        practitionerNote.setDate(new Date());
        practitionerNote.setContent("test content");

        return practitionerNote;
    }

    // Method that converts object into Json string
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    /* ------- PractitionerNoteController ------- */
    @Test
    public void testGet_PractitionerNotesByPatId() throws Exception {
        mockMvc.perform(get("/patHistory/1")).andExpect(status().isOk());
    }


    // GET practitioner note
    @Test
    public void testGet_PractitionerNoteById() throws Exception {
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(createNoteForTest("1", "1")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/patHistory/get/1")).andExpect(status().isOk());
        mockMvc.perform(get("/patHistory/delete/1"));
    }

    @Test
    public void testGet_PractitionerNoteById_NonExistentId() throws Exception {
        mockMvc.perform(get("/patHistory/get/1")).andExpect(status().is4xxClientError());
    }


    // GET update practitioner note
    @Test
    public void testGet_UpdatePractitionerNoteById() throws Exception {
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(createNoteForTest("1", "1")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/patHistory/update/1")).andExpect(status().isOk());
        mockMvc.perform(get("/patHistory/delete/1"));
    }

    @Test
    public void testGet_UpdatePractitionerNote() throws Exception {
        mockMvc.perform(get("/patHistory/update/1")).andExpect(status().is4xxClientError());
    }


    // GET delete practitioner note(s)
    @Test
    public void testGet_DeletePractitionerNoteById() throws Exception {
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(createNoteForTest("1", "10")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/patHistory/delete/1")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testGet_DeletePractitionerNoteById_NonExistentId() throws Exception {
        mockMvc.perform(get("/patHistory/delete/1")).andExpect(status().is4xxClientError());
    }

    @Test
    public void testGet_DeletePractitionerNotesByPatId() throws Exception {
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(createNoteForTest("1", "10")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(createNoteForTest("2", "10")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/patHistory/delete/list/10")).andExpect(status().is2xxSuccessful());
    }


    // POST practitioner note
    @Test
    public void testPost_NewPractitionerNote() throws Exception {
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(createNoteForTest("1", "1")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists());

        mockMvc.perform(get("/patHistory/delete/1"));
    }


    // POST practitioner note
    @Test
    public void testPost_UpdatePractitionerNote() throws Exception {
        mockMvc.perform(post("/patHistory/update")
                        .content(asJsonString(createNoteForTest("1", "1")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/patHistory/delete/1"));
    }
}
