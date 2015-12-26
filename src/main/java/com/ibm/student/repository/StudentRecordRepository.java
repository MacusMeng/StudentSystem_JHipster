package com.ibm.student.repository;

import com.ibm.student.domain.StudentRecord;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StudentRecord entity.
 */
public interface StudentRecordRepository extends JpaRepository<StudentRecord,Long> {

}
