package com.mediscreen.microservice_DiabetesReport.controller;

import com.mediscreen.microservice_DiabetesReport.bean.PersonalRecordBean;
import com.mediscreen.microservice_DiabetesReport.bean.PractitionerNoteBean;
import com.mediscreen.microservice_DiabetesReport.exception.PersonalRecordNotFoundException;
import com.mediscreen.microservice_DiabetesReport.proxy.PersonalRecordProxy;
import com.mediscreen.microservice_DiabetesReport.proxy.PractitionerNoteProxy;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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



    @Test
    public void testPost_DiabetesAssessmentById() throws Exception {
        PersonalRecordBean personalRecordBean = new PersonalRecordBean();
        personalRecordBean.setId(1);
        personalRecordBean.setFirstname("Steff");
        personalRecordBean.setLastname("Bihaille");
        personalRecordBean.setBirthdate(new Date());
        personalRecordBean.setSex("F");
        personalRecordBean.setAddress("Secret service headquarter");
        personalRecordBean.setPhone("111-222-3333");
        when(personalRecordProxy.getPatientInfo(1)).thenReturn(Optional.of(personalRecordBean));

        List<PractitionerNoteBean> practitionerNoteBeans = new ArrayList<>();
        PractitionerNoteBean practitionerNoteBean = new PractitionerNoteBean();
        practitionerNoteBean.setId("1");
        practitionerNoteBean.setPatId("1");
        practitionerNoteBean.setDate(new Date());
        practitionerNoteBean.setContent("test content");
        practitionerNoteBeans.add(practitionerNoteBean);
        when(practitionerNoteProxy.getPatientNotes("1")).thenReturn(Optional.of(practitionerNoteBeans));


        mockMvc.perform(post("/assess/id")
                .param("patId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Patient: Steff Bihaille (age 0) diabetes assessment is: None"));
    }

    @Test
    public void testPost_DiabetesAssessmentById_WithoutPractitionerNote() throws Exception {
        PersonalRecordBean personalRecordBean = new PersonalRecordBean();
        personalRecordBean.setId(1);
        personalRecordBean.setFirstname("Steff");
        personalRecordBean.setLastname("Bihaille");
        personalRecordBean.setBirthdate(new Date());
        personalRecordBean.setSex("F");
        personalRecordBean.setAddress("Secret service headquarter");
        personalRecordBean.setPhone("111-222-3333");
        when(personalRecordProxy.getPatientInfo(1)).thenReturn(Optional.of(personalRecordBean));


        mockMvc.perform(post("/assess/id")
                        .param("patId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Patient: Steff Bihaille (age 0) diabetes assessment is: None"));
    }

    @Test
    public void testPost_DiabetesAssessmentById_NonExistentId() throws Exception {
        when(personalRecordProxy.getPatientInfo(1)).thenThrow(PersonalRecordNotFoundException.class);

        mockMvc.perform(post("/assess/id")
                        .param("patId", "1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPost_DiabetesAssessmentByFamilyName() throws Exception {
        PersonalRecordBean personalRecordBean = new PersonalRecordBean();
        personalRecordBean.setId(1);
        personalRecordBean.setFirstname("Steff");
        personalRecordBean.setLastname("Bihaille");
        personalRecordBean.setBirthdate(new Date());
        personalRecordBean.setSex("F");
        personalRecordBean.setAddress("Secret service headquarter");
        personalRecordBean.setPhone("111-222-3333");
        when(personalRecordProxy.getPatientInfoByFamilyName("Bihaille")).thenReturn(Optional.of(personalRecordBean));

        List<PractitionerNoteBean> practitionerNoteBeans = new ArrayList<>();
        PractitionerNoteBean practitionerNoteBean = new PractitionerNoteBean();
        practitionerNoteBean.setId("1");
        practitionerNoteBean.setPatId("1");
        practitionerNoteBean.setDate(new Date());
        practitionerNoteBean.setContent("test content");
        practitionerNoteBeans.add(practitionerNoteBean);
        when(practitionerNoteProxy.getPatientNotes("1")).thenReturn(Optional.of(practitionerNoteBeans));


        mockMvc.perform(post("/assess/familyName")
                        .param("familyName", "Bihaille"))
                .andExpect(status().isOk())
                .andExpect(content().string("Patient: Steff Bihaille (age 0) diabetes assessment is: None"));
    }

    @Test
    public void testPost_DiabetesAssessmentByFamilyName_WithoutPractitionerNote() throws Exception {
        PersonalRecordBean personalRecordBean = new PersonalRecordBean();
        personalRecordBean.setId(1);
        personalRecordBean.setFirstname("Steff");
        personalRecordBean.setLastname("Bihaille");
        personalRecordBean.setBirthdate(new Date());
        personalRecordBean.setSex("F");
        personalRecordBean.setAddress("Secret service headquarter");
        personalRecordBean.setPhone("111-222-3333");
        when(personalRecordProxy.getPatientInfoByFamilyName("Bihaille")).thenReturn(Optional.of(personalRecordBean));


        mockMvc.perform(post("/assess/familyName")
                        .param("familyName", "Bihaille"))
                .andExpect(status().isOk())
                .andExpect(content().string("Patient: Steff Bihaille (age 0) diabetes assessment is: None"));
    }

    @Test
    public void testPost_DiabetesAssessmentByFamilyName_NonExistentFamilyName() throws Exception {
        when(personalRecordProxy.getPatientInfoByFamilyName("Bihaille")).thenThrow(PersonalRecordNotFoundException.class);

        mockMvc.perform(post("/assess/familyName")
                        .param("familyName", "Bihaille"))
                .andExpect(status().is4xxClientError());
    }
}
