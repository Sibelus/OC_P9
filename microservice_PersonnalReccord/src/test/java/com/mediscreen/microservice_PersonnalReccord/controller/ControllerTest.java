package com.mediscreen.microservice_PersonnalReccord.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.microservice_PersonnalReccord.model.PersonalRecord;
import jakarta.transaction.Transactional;
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

@Transactional
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





    // Method that converts object into Json string
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /* ------- PersonalRecordController ------- */
    @Test
    public void testGet_PersonalRecordList() throws Exception {
        mockMvc.perform(get("/personalRecord/list")).andExpect(status().isOk());
    }


    // GET personal record
    @Test
    public void testGet_PersonalRecordById() throws Exception {
        mockMvc.perform(get("/personalRecord/1")).andExpect(status().isOk());
    }

    @Test
    public void testGet_PersonalRecordById_WithNonExistentId() throws Exception {
        mockMvc.perform(get("/personalRecord/1000"))
                .andExpect(status().is4xxClientError());
    }


    // GET update personal record
    @Test
    public void testGet_UpdatePersonalRecordById() throws Exception {
        mockMvc.perform(get("/personalRecord/update/1")).andExpect(status().isOk());
    }

    @Test
    public void testGet_UpdatePersonalRecordById_NonExistentId() throws Exception {
        mockMvc.perform(get("/personalRecord/update/1000"))
                .andExpect(status().is4xxClientError());
    }


    // GET delete personal record
    @Test
    public void testGet_DeletePersonalRecordById() throws Exception {
        mockMvc.perform(get("/personalRecord/delete/1")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testGet_DeletePersonalRecordById_WithNonExistentId() throws Exception {
        mockMvc.perform(get("/personalRecord/delete/1000")).andExpect(status().is4xxClientError());
    }


    // POST create new personal record
    @Test
    public void testPost_NewPersonalRecord() throws Exception {
        PersonalRecord personalRecord = new PersonalRecord();
        personalRecord.setFirstname("Steff");
        personalRecord.setLastname("Bihaille");
        personalRecord.setBirthdate(new Date());
        personalRecord.setSex("F");
        personalRecord.setAddress("Secret service headquarter");
        personalRecord.setPhone("111-222-3333");

        mockMvc.perform(post("/personalRecord/validate")
                        .content(asJsonString(personalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }


    // POST update personal record
    @Test
    public void testPost_UpdatePersonalRecord() throws Exception {
        PersonalRecord personalRecord = new PersonalRecord();
        personalRecord.setId(100);
        personalRecord.setFirstname("Steff");
        personalRecord.setLastname("Bihaille");
        personalRecord.setBirthdate(new Date());
        personalRecord.setSex("F");
        personalRecord.setAddress("Secret service headquarter");
        personalRecord.setPhone("111-222-3333");

        mockMvc.perform(post("/personalRecord/update")
                        .content(asJsonString(personalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}
