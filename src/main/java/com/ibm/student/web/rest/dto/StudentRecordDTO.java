package com.ibm.student.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by MengR on 15/12/29.
 */
public class StudentRecordDTO implements Serializable {
    private Long id;
    private String number;
    private String name;
    private String gender;
    private String classes;
    private String subject;
    private String scord;

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getClasses() {
        return classes;
    }

    public String getSubject() {
        return subject;
    }

    public String getScord() {
        return scord;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setScord(String scord) {
        this.scord = scord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentRecordDTO that = (StudentRecordDTO) o;

        if(!Objects.equals(id,that.id)) return false;
        return true;

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StudentRecordDTO{" +
            "id=" + id +
            ", number='" + number + '\'' +
            ", name='" + name + '\'' +
            ", gender='" + gender + '\'' +
            ", classes='" + classes + '\'' +
            ", subject='" + subject + '\'' +
            ", scord='" + scord + '\'' +
            '}';
    }
}
