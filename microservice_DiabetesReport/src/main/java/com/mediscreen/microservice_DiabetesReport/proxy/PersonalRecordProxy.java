package com.mediscreen.microservice_DiabetesReport.proxy;

import com.mediscreen.microservice_DiabetesReport.bean.PersonalRecordBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "microservice-PersonalRecord", url = "localhost:8081")
public interface PersonalRecordProxy {

    // getMapping methods
    @GetMapping("/personalRecord/list")
    List<PersonalRecordBean> getPatientList();

    @GetMapping("/personalRecord/{id}")
    public Optional<PersonalRecordBean> getPatientInfo(@PathVariable int id);
}