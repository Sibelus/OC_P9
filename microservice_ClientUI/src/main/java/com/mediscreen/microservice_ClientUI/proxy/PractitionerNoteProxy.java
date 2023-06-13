package com.mediscreen.microservice_ClientUI.proxy;

import com.mediscreen.microservice_ClientUI.beans.PractitionerNoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "microservice-PractitionerNote", url = "localhost:8082")
public interface PractitionerNoteProxy {


    // getMapping methods
    @GetMapping("/patHistory/{patId}")
    public List<PractitionerNoteBean> getPatientNotes(@PathVariable("patId") String patId);

    @GetMapping("/patHistory/get/{id}")
    public Optional<PractitionerNoteBean> getPatientNote(@PathVariable String id);

    @GetMapping("/patHistory/update/{id}")
    public Optional<PractitionerNoteBean> getUpdatePatientNote(@PathVariable String id);

    @GetMapping("/patHistory/delete/{id}")
    public void getDeletePatientNote(@PathVariable String id);



    // postMapping methods
    @PostMapping("/patHistory/add")
    public ResponseEntity<PractitionerNoteBean> addPatientNote(@RequestBody PractitionerNoteBean practitionerNote);
    @PostMapping("/patHistory/update")
    public ResponseEntity<PractitionerNoteBean> updatePatientNote(@RequestBody PractitionerNoteBean practitionerNote);
}
