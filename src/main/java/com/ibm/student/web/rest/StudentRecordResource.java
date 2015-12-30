package com.ibm.student.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ibm.student.domain.StudentRecord;
import com.ibm.student.domain.StudentRequest;
import com.ibm.student.repository.StudentRecordRepository;
import com.ibm.student.service.StudentRecordService;
import com.ibm.student.web.rest.dto.StudentRecordDTO;
import com.ibm.student.web.rest.mapper.StudentRecordMapper;
import com.ibm.student.web.rest.util.HeaderUtil;
import com.ibm.student.web.rest.util.PaginationUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.Collectors;

/**
 * REST controller for managing StudentRecord.
 */
@RestController
@RequestMapping("/api")
public class StudentRecordResource {

    private final Logger log = LoggerFactory.getLogger(StudentRecordResource.class);

    @Inject
    private StudentRecordService studentRecordService;
    @Inject
    private StudentRecordMapper studentRecordMapper;

    /**
     * POST  /studentRecords -> Create a new studentRecord.
     */
    @RequestMapping(value = "/studentRecords",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentRecordDTO> createStudentRecord(@RequestBody StudentRecordDTO studentRecordDTO) throws URISyntaxException {
        log.debug("REST request to save StudentRecord : {}", studentRecordDTO);
        if (studentRecordDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("studentRecord", "idexists", "A new studentRecord cannot already have an ID")).body(null);
        }
        StudentRecordDTO result = studentRecordService.save(studentRecordDTO);
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
    public ResponseEntity<StudentRecordDTO> updateStudentRecord(@RequestBody StudentRecordDTO studentRecordDTO) throws URISyntaxException {
        log.debug("REST request to update StudentRecord : {}", studentRecordDTO);
        if (studentRecordDTO.getId() == null) {
            return createStudentRecord(studentRecordDTO);
        }
        StudentRecordDTO result = studentRecordService.save(studentRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("studentRecord", studentRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /studentRecords -> get all the studentRecords.
     */
    @RequestMapping(value = "/studentRecords",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<StudentRecordDTO>> getAllStudentRecords(Pageable pageable) throws  URISyntaxException{
        log.debug("REST request to get all StudentRecords");
        Page<StudentRecord> page = studentRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page,"api/studentRecords");
        return new ResponseEntity<>(page.getContent().stream()
            .map(studentRecordMapper::studentRecordTOStudentRecordDTO)
            .collect(Collectors.toCollection(LinkedList::new)),headers,HttpStatus.OK);
            }

    /**
     * GET  /studentRecords/:id -> get the "id" studentRecord.
     */
    @RequestMapping(value = "/studentRecords/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentRecordDTO> getStudentRecord(@PathVariable Long id) {
        log.debug("REST request to get StudentRecord : {}", id);
        StudentRecordDTO studentRecordDTO = studentRecordService.findOne(id);
        return Optional.ofNullable(studentRecordDTO)
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
        studentRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("studentRecord", id.toString())).build();
    }
    /**
     * DELETE  /studentRecords/:id -> delete the "id" studentRecord.
     */
    @RequestMapping(value = "/studentRecords/selected",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStudentSelected(@RequestBody StudentRequest studentRequest){
        log.debug("REST request to delete StudentRecords : {}", studentRequest.getIds());
        List<Long> ids = studentRequest.getIds();
        for (Long id : ids){
            studentRecordService.delete(id);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntitiesDeletionAlert("studentRecord", studentRequest.idstoString(studentRequest.getIds()))).build();
    }
}
