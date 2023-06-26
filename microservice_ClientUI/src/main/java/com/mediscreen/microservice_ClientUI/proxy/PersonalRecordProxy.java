package com.mediscreen.microservice_ClientUI.proxy;

import com.mediscreen.microservice_ClientUI.beans.PersonalRecordBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


/**
 * Interface that link microservice-ClientUI to microservice-PersonalRecord and granted access to listed methods
 * It permits to manage everything concerning personal record
 */
@FeignClient(name = "microservice-PersonalRecord", url = "localhost:8081")
public interface PersonalRecordProxy {

    // getMapping methods

    /**
     * Method that get all personal records
     * @return a list of all personal record present in database
     */
    @GetMapping("/personalRecord/list")
    List<PersonalRecordBean> getPatientList();

    /**
     * Method that get a personal record from database by its id
     * @param id -> refer to the id of a personal record
     * @return an Optional<PersonalRecordBean> {@link Optional} {@link PersonalRecordBean}
     */
    @GetMapping("/personalRecord/{id}")
    public Optional<PersonalRecordBean> getPatientInfo(@PathVariable int id);

    /**
     * Method that get a personal record from database by its id to update its content
     * @param id -> refer to the id of a personal record
     * @return an Optional<PersonalRecordBean> {@link Optional} {@link PersonalRecordBean}
     */
    @GetMapping("/personalRecord/update/{id}")
    public Optional<PersonalRecordBean> updatePatientInfo(@PathVariable int id);

    /**
     * Method that delete a personal record from database
     * @param id -> refer to the id of a personal record
     * @return
     */
    @GetMapping("/personalRecord/delete/{id}")
    public String deletePatientInfo(@PathVariable int id);



    // postMapping methods

    /**
     * Method that create a new personal record into database
     * @param personalRecord {@link PersonalRecordBean}
     * @return
     */
    @PostMapping("/personalRecord/validate")
    public PersonalRecordBean createPatientInfo_submit(@RequestBody PersonalRecordBean personalRecord);


    /**
     * Method that update content of a personal record
     * @param personalRecord {@link PersonalRecordBean}
     * @return
     */
    @PostMapping("/personalRecord/update")
    public PersonalRecordBean updatePatientInfo_Submit(@RequestBody PersonalRecordBean personalRecord);
}
