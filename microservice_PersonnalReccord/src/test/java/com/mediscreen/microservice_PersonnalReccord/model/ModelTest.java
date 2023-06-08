package com.mediscreen.microservice_PersonnalReccord.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ModelTest {

    @Test
    public void testPersonalRecordBean_SetAndGetInfo() {
        //GIVEN
        PersonalRecord personalRecord = new PersonalRecord();
        int Id = 0;
        String firstname = "Steff";
        String lastname = "Bihaille";
        Date birthdate = new Date();
        String sex = "F";
        String address = "Secret service headquarter";
        String phone = "111-222-3333";
        String toString = "PersonalRecord{" +
                "Id=" + Id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthdate=" + birthdate +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';

        //WHEN
        personalRecord.setId(Id);
        personalRecord.setFirstname(firstname);
        personalRecord.setLastname(lastname);
        personalRecord.setBirthdate(birthdate);
        personalRecord.setSex(sex);
        personalRecord.setAddress(address);
        personalRecord.setPhone(phone);

        //THEN
        Assertions.assertEquals(Id, personalRecord.getId());
        Assertions.assertEquals(firstname, personalRecord.getFirstname());
        Assertions.assertEquals(lastname, personalRecord.getLastname());
        Assertions.assertEquals(birthdate, personalRecord.getBirthdate());
        Assertions.assertEquals(sex, personalRecord.getSex());
        Assertions.assertEquals(address, personalRecord.getAddress());
        Assertions.assertEquals(phone, personalRecord.getPhone());
        Assertions.assertEquals(toString, personalRecord.toString());
    }
}
