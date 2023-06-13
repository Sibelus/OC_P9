package com.mediscreen.microservice_ClientUI.controller;

import com.mediscreen.microservice_ClientUI.beans.PersonalRecordBean;
import com.mediscreen.microservice_ClientUI.beans.PractitionerNoteBean;
import com.mediscreen.microservice_ClientUI.exception.PersonalRecordNotFoundException;
import com.mediscreen.microservice_ClientUI.exception.PractitionerNoteNotFoundException;
import com.mediscreen.microservice_ClientUI.proxy.PersonalRecordProxy;
import com.mediscreen.microservice_ClientUI.proxy.PractitionerNoteProxy;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @MockBean
    private PractitionerNoteProxy practitionerNoteProxy;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }



    /* ------- ErrorController ------- */
    @Test
    public void testGet_Error() throws Exception {
        mockMvc.perform(get("/error")).andExpect(status().isOk());
    }



    /* ------- PersonalRecordController ------- */
    @Test
    public void testGet_Accueil() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }


    // GET personal record
    @Test
    public void testGet_PersonalRecordById() throws Exception {
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

    @Test
    public void testGet_PersonalRecordById_NonExistentId() throws Exception {
        when(personalRecordProxy.getPatientInfo(1)).thenThrow(PersonalRecordNotFoundException.class);
        mockMvc.perform(get("/personalRecord/1"))
                .andExpect(status().is4xxClientError());
    }


    // GET create new personal record
    @Test
    public void testGet_PersonalRecordAdd() throws Exception {
        mockMvc.perform(get("/personalRecord/add")).andExpect(status().isOk());
    }


    // GET update personal record
    @Test
    public void testGet_UpdatePersonalRecordById() throws Exception {
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
    public void testGet_UpdatePersonalRecordById_NonExistentId() throws Exception {
        when(personalRecordProxy.updatePatientInfo(1)).thenThrow(PersonalRecordNotFoundException.class);
        mockMvc.perform(get("/personalRecord/update/1"))
                .andExpect(status().is4xxClientError());
    }


    // GET delete personal record
    @Test
    public void testGet_DeletePersonalRecordById() throws Exception {
        mockMvc.perform(get("/personalRecord/delete/1")).andExpect(status().is3xxRedirection());
    }


    // POST create new personal record
    @Test
    public void testPost_NewPersonalRecord() throws Exception {
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
    public void testPost_NewPersonalRecord_WithEmptyFields() throws Exception {
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
    public void testPost_NewPersonalRecord_WithBirthdateInFuture() throws Exception {
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
    public void testPost_NewPersonalRecord_WithWrongBirthdateFormat() throws Exception {
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
    public void testPost_NewPersonalRecord_WithWrongSexFormat() throws Exception {
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
    public void testPost_UpdatePersonalRecordById() throws Exception {
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
    public void testPost_UpdatePersonalRecordById_WithEmptyFields() throws Exception {
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
    public void testPost_UpdatePersonalRecordById_WithBirthdateInFuture() throws Exception {
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
    public void testPost_UpdatePersonalRecordById_WithWrongBirthdateFormat() throws Exception {
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
    public void testPost_UpdatePersonalRecordById_WithWrongSexFormat() throws Exception {
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





    /* ------- PractitionerNoteController ------- */
    @Test
    public void testGet_testGet_PractitionerNotesByPatId() throws Exception {
        List<PractitionerNoteBean> practitionerNoteBeans = new ArrayList<>();
        when(practitionerNoteProxy.getPatientNotes("1")).thenReturn(practitionerNoteBeans);
        mockMvc.perform(get("/patHistory/1")).andExpect(status().isOk());
    }


    // GET practitioner note
    @Test
    public void testGet_PractitionerNoteById() throws Exception {
        PractitionerNoteBean practitionerNoteBean = new PractitionerNoteBean();
        practitionerNoteBean.setId("1010101010");
        practitionerNoteBean.setPatId("1");
        practitionerNoteBean.setDate(new Date());
        practitionerNoteBean.setContent("test content");
        when(practitionerNoteProxy.getPatientNote("1")).thenReturn(Optional.of(practitionerNoteBean));

        mockMvc.perform(get("/patHistory/get/1")).andExpect(status().isOk());
    }

    @Test
    public void testGet_PractitionerNoteById_NonExistentId() throws Exception {
        when(practitionerNoteProxy.getPatientNote("1")).thenThrow(PractitionerNoteNotFoundException.class);
        mockMvc.perform(get("/patHistory/get/1")).andExpect(status().is4xxClientError());
    }


    // GET create new personal record
    @Test
    public void testGet_PractitionerNoteAdd() throws Exception {
        mockMvc.perform(get("/patHistory/add")).andExpect(status().isOk());
    }


    // GET update practitioner note
    @Test
    public void testGet_UpdatePractitionerNoteById() throws Exception {
        PractitionerNoteBean practitionerNoteBean = new PractitionerNoteBean();
        practitionerNoteBean.setId("1010101010");
        practitionerNoteBean.setPatId("1");
        practitionerNoteBean.setDate(new Date());
        practitionerNoteBean.setContent("test content");
        when(practitionerNoteProxy.getUpdatePatientNote("1")).thenReturn(Optional.of(practitionerNoteBean));

        mockMvc.perform(get("/patHistory/update/1")).andExpect(status().isOk());
    }

    @Test
    public void testGet_UpdatePractitionerNote() throws Exception {
        when(practitionerNoteProxy.getUpdatePatientNote("1")).thenThrow(PractitionerNoteNotFoundException.class);
        mockMvc.perform(get("/patHistory/update/1")).andExpect(status().is4xxClientError());
    }


    // GET delete practitioner note
    @Test
    public void testGet_DeletePractitionerNoteById() throws Exception {
        PractitionerNoteBean practitionerNoteBean = new PractitionerNoteBean();
        practitionerNoteBean.setId("1010101010");
        practitionerNoteBean.setPatId("1");
        practitionerNoteBean.setDate(new Date());
        practitionerNoteBean.setContent("test content");
        when(practitionerNoteProxy.getPatientNote("1")).thenReturn(Optional.of(practitionerNoteBean));
        mockMvc.perform(get("/patHistory/delete/1")).andExpect(status().is3xxRedirection());
    }


    // POST create new practitioner note
    @Test
    public void testPost_NewPractitionerNote() throws Exception {
        mockMvc.perform(post("/patHistory/add")
                        .param("content", "test content")
                        .param("patId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/patHistory/1"));
    }


    // POST create new practitioner note
    @Test
    public void testPost_UpdatePractitionerNote() throws Exception {
        PractitionerNoteBean practitionerNoteBean = new PractitionerNoteBean();
        practitionerNoteBean.setId("1010101010");
        practitionerNoteBean.setPatId("1");
        practitionerNoteBean.setDate(new Date());
        practitionerNoteBean.setContent("test content");
        when(practitionerNoteProxy.getPatientNote("1")).thenReturn(Optional.of(practitionerNoteBean));

        mockMvc.perform(post("/patHistory/update/1")
                        .param("content", "test content updated"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/patHistory/1"));
    }
}
