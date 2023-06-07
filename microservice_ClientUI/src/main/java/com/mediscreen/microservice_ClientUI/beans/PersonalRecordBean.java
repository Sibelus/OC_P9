package com.mediscreen.microservice_ClientUI.beans;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;


public class PersonalRecordBean {

    @NotNull
    private int Id;

    @NotEmpty
    @NotBlank(message = "firstname is mandatory")
    private String firstname;

    @NotEmpty
    @NotBlank(message = "lastname is mandatory")
    private String lastname;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "birthdate is mandatory")
    private Date birthdate;

    @NotEmpty
    @NotBlank(message = "sex is mandatory")
    private String sex;

    @NotEmpty
    @NotBlank(message = "address is mandatory")
    private String address;

    @NotEmpty
    @NotBlank(message = "phone is mandatory")
    private String phone;


    // toString method
    @Override
    public String toString() {
        return "PersonalRecordBean{" +
                "Id=" + Id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthdate=" + birthdate +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }


    // getters & setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
