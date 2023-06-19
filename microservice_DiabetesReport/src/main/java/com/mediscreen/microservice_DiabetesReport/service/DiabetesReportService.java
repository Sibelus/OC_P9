package com.mediscreen.microservice_DiabetesReport.service;

import com.mediscreen.microservice_DiabetesReport.bean.PersonalRecordBean;
import com.mediscreen.microservice_DiabetesReport.bean.PractitionerNoteBean;
import com.mediscreen.microservice_DiabetesReport.model.DiabetesInfo;
import com.mediscreen.microservice_DiabetesReport.proxy.PersonalRecordProxy;
import com.mediscreen.microservice_DiabetesReport.proxy.PractitionerNoteProxy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class DiabetesReportService implements IDiabetesReportService {

    @Autowired
    private PersonalRecordProxy personalRecordProxy;
    @Autowired
    private PractitionerNoteProxy practitionerNoteProxy;

    Logger logger = LoggerFactory.getLogger(DiabetesReportService.class);


    /**
     * Method that get info needed for diabetes assessment for all patients
     * Patient's id, birthdate & sex are getting from microservice-PersonalRecord
     * Practitioner's notes are getting from microservice-PractitionerNote
     * If id provided in parameter doesn't have a match in microservice-PersonalRecord database, it will return 400 error
     * If the practitioner has not written any notes for this patient, the method will provide an empty list
     * @return {@Link DiabetesInfo}
     */
    @Override
    public List<DiabetesInfo> getDiabetesInfoList() {
        logger.debug("*** getPatientList *** is requested to microservice-PersonalRecord");
        List<PersonalRecordBean> personalRecordBeans = personalRecordProxy.getPatientList();
        List<DiabetesInfo> diabetesInfos = new ArrayList<>();

        for (PersonalRecordBean personalRecordBean : personalRecordBeans) {
            //DiabetesInfo diabetesInfo = getDiabetesInfo(personalRecordBean.getId());
            //diabetesInfos.add(diabetesInfo);

            DiabetesInfo diabetesInfo = new DiabetesInfo();
            diabetesInfo.setPatientId(personalRecordBean.getId());
            diabetesInfo.setBirthdate(personalRecordBean.getBirthdate());
            diabetesInfo.setSex(personalRecordBean.getSex());

            logger.debug("*** getPatientNotes *** is requested to microservice-PractitionerNote");
            Optional<List<PractitionerNoteBean>> practitionerNoteBeans = practitionerNoteProxy.getPatientNotes(String.valueOf(personalRecordBean.getId()));
            if (practitionerNoteBeans.isPresent()) {
                List<String> notes = new ArrayList<>();
                for (PractitionerNoteBean practitionerNoteBean : practitionerNoteBeans.get()) {
                    notes.add(practitionerNoteBean.getContent());
                }
                diabetesInfo.setNotes(notes);
            } else {
                diabetesInfo.setNotes(new ArrayList<>());
            }

            diabetesInfos.add(diabetesInfo);
        }

        return diabetesInfos;
    }

    /**
     * Method that get all info needed for diabetes assessment
     * Patient's id, birthdate & sex are getting from microservice-PersonalRecord
     * Practitioner's notes are getting from microservice-PractitionerNote
     * If id provided in parameter doesn't have a match in microservice-PersonalRecord database, it will return 400 error
     * If the practitioner has not written any notes for this patient, the method will provide an empty list
     * @param id
     * @return {@Link DiabetesInfo}
     */
    @Override
    public DiabetesInfo getDiabetesInfo(int id) {
        DiabetesInfo diabetesInfo = new DiabetesInfo();
        logger.debug("*** getPersonalRecord *** is requested to microservice-PersonalRecord");
        Optional<PersonalRecordBean> personalRecordBean = personalRecordProxy.getPatientInfo(id);
        logger.debug("*** getPatientNotes *** is requested to microservice-PractitionerNote");
        Optional<List<PractitionerNoteBean>> practitionerNoteBeans = practitionerNoteProxy.getPatientNotes(String.valueOf(id));

        if (personalRecordBean.isPresent()) {
            diabetesInfo.setPatientId(personalRecordBean.get().getId());
            diabetesInfo.setBirthdate(personalRecordBean.get().getBirthdate());
            diabetesInfo.setSex(personalRecordBean.get().getSex());
        }

        if (practitionerNoteBeans.isPresent()) {
            List<String> notes = new ArrayList<>();
            for (PractitionerNoteBean practitionerNoteBean : practitionerNoteBeans.get()) {
                notes.add(practitionerNoteBean.getContent());
            }
            diabetesInfo.setNotes(notes);
        } else {
            diabetesInfo.setNotes(new ArrayList<>());
        }

        return diabetesInfo;
    }

    /**
     * Method that get diabetes assessment from patient info
     * @param diabetesInfo {@Link DiabetesInfo}
     * @return
     */
    @Override
    public String diabetesAssessment(DiabetesInfo diabetesInfo) {
        int triggerCount = countTrigger(diabetesInfo);
        int age = calculateAge(diabetesInfo.getBirthdate());
        String sex = diabetesInfo.getSex();

        if ((sex.equals("M") && age < 30 && triggerCount >= 5) || (sex.equals("F") && age < 30 && triggerCount >= 7)) {
            logger.info("Patient sex: {}, age: {} & number of trigger: {} -> diabetes assessment: Early onset", sex, age, triggerCount);
            return "Patient: Test TestEarlyOnset (age " + age +") diabetes assessment is: Early onset";
        }
        if ((sex.equals("M") && age < 30 && triggerCount >= 3) || (sex.equals("F") && age < 30 && triggerCount >= 4) || (age > 30 && triggerCount >= 6)) {
            logger.info("Patient sex: {}, age: {} & number of trigger: {} -> diabetes assessment: In danger", sex, age, triggerCount);
            return "Patient: Test TestInDanger (age " + age +") diabetes assessment is: In danger";
        }
        if (age > 30 && triggerCount >= 2) {
            logger.info("Patient age: {} & number of trigger: {} -> diabetes assessment: Borderline", age, triggerCount);
            return "Patient: Test TestBorderline (age " + age +") diabetes assessment is: Borderline";
        }
        if (triggerCount == 0) {
            logger.info("Patient number of trigger: {} -> diabetes assessment: None", triggerCount);
            return "Patient: Test TestNone (age " + age +") diabetes assessment is: None";
        }
        return "Patient: Test TestUndefined (age " + age +") diabetes assessment is: Undefined";
    }

    /**
     * Method that return a list of diabetes assessments for patients concerned by the diabetes risk level provided
     * @param diabetesInfoList
     * @param riskLevel
     * @return
     */
    @Override
    public List<String> diabetesAssessmentByRiskLevel(List<DiabetesInfo> diabetesInfoList, String riskLevel) {
        List<String> diabetesAssessments = new ArrayList<>();

        for (DiabetesInfo diabetesInfo : diabetesInfoList) {
            String diabetesAssessment = diabetesAssessment(diabetesInfo);
            if (diabetesAssessment.contains(riskLevel)) {
                logger.debug("Patient id: {} match to risk level: {}", diabetesInfo.getPatientId(), riskLevel);
                diabetesAssessments.add(diabetesAssessment);
            }
        }
        return diabetesAssessments;
    }

    /**
     * Method that calculate the age of a person from her birthdate
     * @param birthdate
     * @return
     */
    @Override
    public int calculateAge(Date birthdate) {
        int age;
        long birthdateMillis = birthdate.getTime();
        long todayMillis = System.currentTimeMillis();


        long durationMillis = todayMillis - birthdateMillis;
        TimeUnit time = TimeUnit.DAYS;
        long ageInDays = time.convert(durationMillis, TimeUnit.MILLISECONDS);
        age = (int) ageInDays/365;

        return age;
    }

    /**
     * Method that count the number trigger's into practitioner notes
     * @param diabetesInfo
     * @return
     */
    @Override
    public int countTrigger(DiabetesInfo diabetesInfo) {
        int triggerCount = 0;
        List<String> triggers = List.of("hémoglobine a1c", "microalbumine", "taille", "poids", "fumeur", "anormal", "cholestérol", "vertige", "rechute", "réaction", "anticorps");

        for (String trigger : triggers) {
            for (String note : diabetesInfo.getNotes()) {
                int count = StringUtils.countMatches(note.toLowerCase(), trigger);
                if (count > 0) {
                    logger.debug("--- {} --- is a trigger detected in patient notes", trigger);
                }
                triggerCount += count;
            }
        }
        return triggerCount;
    }
}
