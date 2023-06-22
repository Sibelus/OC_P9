package com.mediscreen.microservice_PersonnalReccord.repository;

import com.mediscreen.microservice_PersonnalReccord.model.PersonalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer> {
    Optional<PersonalRecord> findByLastname(String lastname);
}
