package com.mediscreen.microservice_PersonnalReccord.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Model class that defined all attributes and their constraints of a personal record for a patient
 */
@Entity
@Table(name = "personalrecord")
public class PersonalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @NotBlank(message = "firstname is mandatory")
    @Column(name = "firstname")
    private String firstname;

    @NotBlank(message = "lastname is mandatory")
    @Column(name = "lastname")
    private String lastname;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "birthdate is mandatory")
    @Column(name = "birthdate")
    private Date birthdate;

    @Pattern(regexp = "[MF]", message = "sex is mandatory and must be F or M")
    @Column(name = "sex")
    private String sex;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;


    // toString method
    @Override
    public String toString() {
        return "PersonalRecord{" +
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
