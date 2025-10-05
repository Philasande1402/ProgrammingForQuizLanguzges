package com.quizc.programmingquizforalllanguages.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATA_tbl")
public class PersonalInfo {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String province;
    private String qualification;
    private String email;

    public PersonalInfo() {
    }

    public PersonalInfo(Long id, String firstName, String lastName, String idNumber, String province, String qualification, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.province = province;
        this.qualification = qualification;
        this.email = email;
    }

    public PersonalInfo(String firstName, String lastName, String idNumber, String province, String qualification, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.province = province;
        this.qualification = qualification;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
