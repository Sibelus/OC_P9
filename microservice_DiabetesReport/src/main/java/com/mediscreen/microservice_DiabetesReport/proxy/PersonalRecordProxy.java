package com.mediscreen.microservice_DiabetesReport.proxy;

import com.mediscreen.microservice_DiabetesReport.bean.PersonalRecordBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


/**
 * Interface that link microservice-DiabetesReport to microservice-PersonalRecord and granted access to listed methods
 * It permits to manage everything concerning personal record
 */
@FeignClient(name = "microservice-PersonalRecord", url = "localhost:8081")
public interface PersonalRecordProxy {

    // getMapping methods

    /**
     * Method that get a personal record from database by its id
     * @param id -> refer to the id of a personal record
     * @return an Optional<PersonalRecordBean> {@link Optional} {@link PersonalRecordBean}
     */
    @GetMapping("/personalRecord/{id}")
    public Optional<PersonalRecordBean> getPatientInfo(@PathVariable int id);

    /**
     * Method that get a personal record from database by its family name
     * @param familyName -> refer to the family name of a patient register in a personal record
     * @return an Optional<PersonalRecordBean> {@link Optional} {@link PersonalRecordBean}
     */
    @GetMapping("/personalRecord/familyName/{familyName}")
    public Optional<PersonalRecordBean> getPatientInfoByFamilyName(@PathVariable String familyName);
}
