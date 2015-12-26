package com.ibm.student.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ibm.student.domain.StudentRecord;
import com.ibm.student.repository.StudentRecordRepository;
import com.ibm.student.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StudentRecord.
 */
@RestController
@RequestMapping("/api")
public class StudentRecordResource {

    private final Logger log = LoggerFactory.getLogger(StudentRecordResource.class);
        
    @Inject
    private StudentRecordRepository studentRecordRepository;
    
    /**
     * POST  /studentRecords -> Create a new studentRecord.
     */
    @RequestMapping(value = "/studentRecords",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentRecord> createStudentRecord(@RequestBody StudentRecord studentRecord) throws URISyntaxException {
        log.debug("REST request to save StudentRecord : {}", studentRecord);
        if (studentRecord.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("studentRecord", "idexists", "A new studentRecord cannot already have an ID")).body(null);
        }
        StudentRecord result = studentRecordRepository.save(studentRecord);
        return ResponseEntity.created(new URI("/api/studentRecords/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("studentRecord", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /studentRecords -> Updates an existing studentRecord.
     */
    @RequestMapping(value = "/studentRecords",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentRecord> updateStudentRecord(@RequestBody StudentRecord studentRecord) throws URISyntaxException {
        log.debug("REST request to update StudentRecord : {}", studentRecord);
        if (studentRecord.getId() == null) {
            return createStudentRecord(studentRecord);
        }
        StudentRecord result = studentRecordRepository.save(studentRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("studentRecord", studentRecord.getId().toString()))
            .body(result);
    }

    /**
     * GET  /studentRecords -> get all the studentRecords.
     */
    @RequestMapping(value = "/studentRecords",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StudentRecord> getAllStudentRecords() {
        log.debug("REST request to get all StudentRecords");
        return studentRecordRepository.findAll();
            }

    /**
     * GET  /studentRecords/:id -> get the "id" studentRecord.
     */
    @RequestMapping(value = "/studentRecords/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentRecord> getStudentRecord(@PathVariable Long id) {
        log.debug("REST request to get StudentRecord : {}", id);
        StudentRecord studentRecord = studentRecordRepository.findOne(id);
        return Optional.ofNullable(studentRecord)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /studentRecords/:id -> delete the "id" studentRecord.
     */
    @RequestMapping(value = "/studentRecords/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStudentRecord(@PathVariable Long id) {
        log.debug("REST request to delete StudentRecord : {}", id);
        studentRecordRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("studentRecord", id.toString())).build();
    }
}
