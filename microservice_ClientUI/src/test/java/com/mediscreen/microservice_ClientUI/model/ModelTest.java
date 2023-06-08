package com.mediscreen.microservice_ClientUI.model;

import com.mediscreen.microservice_ClientUI.beans.PersonalRecordBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ModelTest {

    @Test
    public void testPersonalRecordBean_SetAndGetInfo() {
        //GIVEN
        PersonalRecordBean personalRecordBean = new PersonalRecordBean();
        int Id = 0;
        String firstname = "Steff";
        String lastname = "Bihaille";
        Date birthdate = new Date();
        String sex = "F";
        String address = "Secret service headquarter";
        String phone = "111-222-3333";
        String toString = "PersonalRecordBean{" +
                            "Id=" + Id +
                            ", firstname='" + firstname + '\'' +
                            ", lastname='" + lastname + '\'' +
                            ", birthdate=" + birthdate +
                            ", sex='" + sex + '\'' +
                            ", address='" + address + '\'' +
                            ", phone='" + phone + '\'' +
                            '}';

        //WHEN
        personalRecordBean.setId(Id);
        personalRecordBean.setFirstname(firstname);
        personalRecordBean.setLastname(lastname);
        personalRecordBean.setBirthdate(birthdate);
        personalRecordBean.setSex(sex);
        personalRecordBean.setAddress(address);
        personalRecordBean.setPhone(phone);

        //THEN
        Assertions.assertEquals(Id, personalRecordBean.getId());
        Assertions.assertEquals(firstname, personalRecordBean.getFirstname());
        Assertions.assertEquals(lastname, personalRecordBean.getLastname());
        Assertions.assertEquals(birthdate, personalRecordBean.getBirthdate());
        Assertions.assertEquals(sex, personalRecordBean.getSex());
        Assertions.assertEquals(address, personalRecordBean.getAddress());
        Assertions.assertEquals(phone, personalRecordBean.getPhone());
        Assertions.assertEquals(toString, personalRecordBean.toString());
    }
}
