package com.mediscreen.microservice_ClientUI.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "microservice-DiabetesReport", url = "localhost:8083")
public interface DiabetesReportProxy {

    // getMapping method
    @GetMapping("/assess/{id}")
    public String getDiabetesAssessmentById(@PathVariable int id);


    // postMapping method
    @PostMapping("/assess/id")
    public String postDiabetesAssessmentById(@RequestParam int patId);

    @PostMapping("/assess/familyName")
    public String postDiabetesAssessmentByFamilyName(@RequestParam String familyName);
}
