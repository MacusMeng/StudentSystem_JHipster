package com.ibm.student.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StudentRecord.
 */
@Entity
@Table(name = "student_record")
public class StudentRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "classes")
    private String classes;

    @Column(name = "subject")
    private String subject;

    @Column(name = "scord")
    private String scord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getScord() {
        return scord;
    }

    public void setScord(String scord) {
        this.scord = scord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentRecord studentRecord = (StudentRecord) o;
        return Objects.equals(id, studentRecord.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StudentRecord{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", name='" + name + "'" +
            ", gender='" + gender + "'" +
            ", classes='" + classes + "'" +
            ", subject='" + subject + "'" +
            ", scord='" + scord + "'" +
            '}';
    }
}
