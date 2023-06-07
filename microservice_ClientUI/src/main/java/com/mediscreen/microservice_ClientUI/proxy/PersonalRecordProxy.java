package com.mediscreen.microservice_ClientUI.proxy;

import com.mediscreen.microservice_ClientUI.beans.PersonalRecordBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "microservice-PersonalRecord", url = "localhost:9001")
public interface PersonalRecordProxy {

    // getMapping methods
    @GetMapping("/personalRecord/list")
    List<PersonalRecordBean> getPatientList();

    @GetMapping("/personalRecord/{id}")
    public Optional<PersonalRecordBean> getPatientInfo(@PathVariable int id);

    @GetMapping("/personalRecord/update/{id}")
    public Optional<PersonalRecordBean> updatePatientInfo(@PathVariable int id);

    @GetMapping("/personalRecord/delete/{id}")
    public void deletePatientInfo(@PathVariable int id);



    // postMapping methods
    @PostMapping("/personalRecord/validate")
    public PersonalRecordBean createPatientInfo_submit(@RequestBody PersonalRecordBean personalRecord);

    @PostMapping("/personalRecord/update")
    public PersonalRecordBean updatePatientInfo_Submit(@RequestBody PersonalRecordBean personalRecord);
}
