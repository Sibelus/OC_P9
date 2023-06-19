package com.mediscreen.microservice_DiabetesReport.controller;

import com.mediscreen.microservice_DiabetesReport.bean.PersonalRecordBean;
import com.mediscreen.microservice_DiabetesReport.bean.PractitionerNoteBean;
import com.mediscreen.microservice_DiabetesReport.model.DiabetesInfo;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void testGet_DiabetesAssessmentById() throws Exception {
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
        when(practitionerNoteProxy.getPatientNotes("1")).thenReturn(Optional.of(practitionerNoteBeans));


        mockMvc.perform(get("/assess/1")).andExpect(status().isOk())
                        .andExpect(content().string("Patient: Test TestNone (age 0) diabetes assessment is: None"));
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
        when(practitionerNoteProxy.getPatientNotes("1")).thenReturn(Optional.of(practitionerNoteBeans));


        mockMvc.perform(post("/assess/id")
                .param("patId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Patient: Test TestNone (age 0) diabetes assessment is: None"));
    }

    @Test
    public void testPost_DiabetesAssessmentByFamilyName() throws Exception {
        List<PersonalRecordBean> personalRecordBeans = new ArrayList<>();
        PersonalRecordBean personalRecordBean = new PersonalRecordBean();
        personalRecordBean.setId(1);
        personalRecordBean.setBirthdate(new Date());
        personalRecordBean.setSex("F");
        personalRecordBeans.add(personalRecordBean);
        when(personalRecordProxy.getPatientList()).thenReturn(personalRecordBeans);

        List<PractitionerNoteBean> practitionerNoteBeans = new ArrayList<>();
        when(practitionerNoteProxy.getPatientNotes("1")).thenReturn(Optional.of(practitionerNoteBeans));


        mockMvc.perform(post("/assess/familyName")
                        .param("familyName", "TestNone"))
                .andExpect(status().isOk())
                .andExpect(content().string("[Patient: Test TestNone (age 0) diabetes assessment is: None]"));
    }
}
