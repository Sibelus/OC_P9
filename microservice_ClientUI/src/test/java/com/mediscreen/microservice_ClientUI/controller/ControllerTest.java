package com.mediscreen.microservice_ClientUI.controller;

import com.mediscreen.microservice_ClientUI.beans.PersonalRecordBean;
import com.mediscreen.microservice_ClientUI.exception.PersonalRecordNotFoundException;
import com.mediscreen.microservice_ClientUI.proxy.PersonalRecordProxy;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private WebApplicationContext context;
    @MockBean
    private PersonalRecordProxy personalRecordProxy;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }



    /* ------- PersonalRecordController ------- */
    @Test
    public void testGetError() throws Exception {
        mockMvc.perform(get("/error")).andExpect(status().isOk());
    }



    /* ------- PersonalRecordController ------- */
    @Test
    public void testGetAccueil() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }


    // GET personal record
    @Test
    public void testGetPersonalRecordById() throws Exception {
        PersonalRecordBean personalRecordBean = new PersonalRecordBean();
        personalRecordBean.setId(1);
        personalRecordBean.setFirstname("Steff");
        personalRecordBean.setLastname("Bihaille");
        personalRecordBean.setBirthdate(new Date());
        personalRecordBean.setSex("F");
        personalRecordBean.setAddress("Secret service headquarter");
        personalRecordBean.setPhone("111-222-3333");
        when(personalRecordProxy.getPatientInfo(1)).thenReturn(Optional.of(personalRecordBean));

        mockMvc.perform(get("/personalRecord/1")).andExpect(status().isOk());
    }


    // GET create new personal record
    @Test
    public void testGetPersonalRecordAdd() throws Exception {
        mockMvc.perform(get("/personalRecord/add")).andExpect(status().isOk());
    }


    // GET update personal record
    @Test
    public void testGetPersonalRecordUpdateById() throws Exception {
        PersonalRecordBean personalRecordBean = new PersonalRecordBean();
        personalRecordBean.setId(1);
        personalRecordBean.setFirstname("Steff");
        personalRecordBean.setLastname("Bihaille");
        personalRecordBean.setBirthdate(new Date());
        personalRecordBean.setSex("F");
        personalRecordBean.setAddress("Secret service headquarter");
        personalRecordBean.setPhone("111-222-3333");
        when(personalRecordProxy.updatePatientInfo(1)).thenReturn(Optional.of(personalRecordBean));

        mockMvc.perform(get("/personalRecord/update/1")).andExpect(status().isOk());
    }

    @Test
    public void testGetPersonalRecordUpdateById_NonExistentId() throws Exception {
        when(personalRecordProxy.updatePatientInfo(1)).thenThrow(PersonalRecordNotFoundException.class);
        mockMvc.perform(get("/personalRecord/update/1"))
                .andExpect(status().is4xxClientError());
    }


    // GET delete personal record
    @Test
    public void testDeletePersonalRecordById() throws Exception {
        mockMvc.perform(get("/personalRecord/delete/1")).andExpect(status().is3xxRedirection());
    }


    // POST create new personal record
    @Test
    public void testPostNewPersonalRecord() throws Exception {
        mockMvc.perform(post("/personalRecord/validate")
                        .param("firstname", "Steff")
                        .param("lastname", "Bihaille")
                        .param("birthdate", "2000-12-12")
                        .param("sex", "F")
                        .param("address", "Secret service headquarter")
                        .param("phone", "111-222-3333"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));
    }

    @Test
    public void testPostNewPersonalRecord_WithEmptyFields() throws Exception {
        mockMvc.perform(post("/personalRecord/validate")
                        .param("firstname", "")
                        .param("lastname", "")
                        .param("birthdate", "")
                        .param("sex", "")
                        .param("address", "")
                        .param("phone", ""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "firstname", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "lastname", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "birthdate", "NotNull"))
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "sex", "Pattern"));
    }

    @Test
    public void testPostNewPersonalRecord_WithBirthdateInFuture() throws Exception {
        mockMvc.perform(post("/personalRecord/validate")
                        .param("firstname", "Steff")
                        .param("lastname", "Bihaille")
                        .param("birthdate", "3000-12-12")
                        .param("sex", "F")
                        .param("address", "Secret service headquarter")
                        .param("phone", "111-222-3333"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "birthdate", "PastOrPresent"));
    }

    @Test
    public void testPostNewPersonalRecord_WithWrongBirthdateFormat() throws Exception {
        mockMvc.perform(post("/personalRecord/validate")
                        .param("firstname", "Steff")
                        .param("lastname", "Bihaille")
                        .param("birthdate", "12-12-2000")
                        .param("sex", "F")
                        .param("address", "Secret service headquarter")
                        .param("phone", "111-222-3333"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "birthdate", "typeMismatch"));
    }

    @Test
    public void testPostNewPersonalRecord_WithWrongSexFormat() throws Exception {
        mockMvc.perform(post("/personalRecord/validate")
                        .param("firstname", "Steff")
                        .param("lastname", "Bihaille")
                        .param("birthdate", "12-12-2000")
                        .param("sex", "f")
                        .param("address", "Secret service headquarter")
                        .param("phone", "111-222-3333"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "sex", "Pattern"));
    }


    // POST update personal record
    @Test
    public void testPostUpdatePersonalRecordById() throws Exception {
        mockMvc.perform(post("/personalRecord/update/1")
                        .param("firstname", "Steff")
                        .param("lastname", "Bihaille")
                        .param("birthdate", "2000-12-12")
                        .param("sex", "F")
                        .param("address", "Secret service headquarter")
                        .param("phone", "111-222-3333"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/personalRecord/1"));
    }

    @Test
    public void testPostUpdatePersonalRecordById_WithEmptyFields() throws Exception {
        mockMvc.perform(post("/personalRecord/update/1")
                        .param("firstname", "")
                        .param("lastname", "")
                        .param("birthdate", "")
                        .param("sex", "")
                        .param("address", "")
                        .param("phone", ""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "firstname", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "lastname", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "birthdate", "NotNull"))
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "sex", "Pattern"));
    }

    @Test
    public void testPostUpdatePersonalRecordById_WithBirthdateInFuture() throws Exception {
        mockMvc.perform(post("/personalRecord/update/1")
                        .param("firstname", "Steff")
                        .param("lastname", "Bihaille")
                        .param("birthdate", "3000-12-12")
                        .param("sex", "F")
                        .param("address", "Secret service headquarter")
                        .param("phone", "111-222-3333"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "birthdate", "PastOrPresent"));
    }

    @Test
    public void testPostUpdatePersonalRecordById_WithWrongBirthdateFormat() throws Exception {
        mockMvc.perform(post("/personalRecord/update/1")
                        .param("firstname", "Steff")
                        .param("lastname", "Bihaille")
                        .param("birthdate", "12-12-2000")
                        .param("sex", "F")
                        .param("address", "Secret service headquarter")
                        .param("phone", "111-222-3333"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "birthdate", "typeMismatch"));
    }

    @Test
    public void testPostUpdatePersonalRecordById_WithWrongSexFormat() throws Exception {
        mockMvc.perform(post("/personalRecord/update/1")
                        .param("firstname", "Steff")
                        .param("lastname", "Bihaille")
                        .param("birthdate", "12-12-2000")
                        .param("sex", "f")
                        .param("address", "Secret service headquarter")
                        .param("phone", "111-222-3333"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrorCode("personalRecordBean", "sex", "Pattern"));
    }
}
