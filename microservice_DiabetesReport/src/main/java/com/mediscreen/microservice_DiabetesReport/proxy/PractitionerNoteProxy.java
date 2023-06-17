package com.mediscreen.microservice_DiabetesReport.proxy;

import com.mediscreen.microservice_DiabetesReport.bean.PractitionerNoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "microservice-PractitionerNote", url = "localhost:8082")
public interface PractitionerNoteProxy {

    // getMapping methods
    @GetMapping("/patHistory/{patId}")
    public Optional<List<PractitionerNoteBean>> getPatientNotes(@PathVariable("patId") String patId);
}
