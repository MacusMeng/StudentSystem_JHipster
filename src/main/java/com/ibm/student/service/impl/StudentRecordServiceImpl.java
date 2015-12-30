package com.ibm.student.service.impl;

import com.ibm.student.domain.StudentRecord;
import com.ibm.student.repository.StudentRecordRepository;
import com.ibm.student.service.StudentRecordService;
import com.ibm.student.web.rest.dto.StudentRecordDTO;
import com.ibm.student.web.rest.mapper.StudentRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by MengR on 15/12/29.
 */
@Service
@Transactional
public class StudentRecordServiceImpl implements StudentRecordService {
    private final Logger log = LoggerFactory.getLogger(StudentRecordServiceImpl.class);
    @Inject
    private StudentRecordRepository studentRecordRepository;
    @Inject
    private StudentRecordMapper studentRecordMapper;
    @Override
    public StudentRecordDTO save(StudentRecordDTO studentRecordDTO) {
        log.debug("Request to save StudentRecord : {}",studentRecordDTO);
        StudentRecord studentRecord = studentRecordMapper.studentRecordDTOToStudentRecord(studentRecordDTO);
        studentRecord = studentRecordRepository.save(studentRecord);
        StudentRecordDTO result = studentRecordMapper.studentRecordTOStudentRecordDTO(studentRecord);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentRecord> findAll(Pageable pageable) {
        log.debug("Request to get all StudentRecords");
        Page<StudentRecord> result = studentRecordRepository.findAll(pageable);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public StudentRecordDTO findOne(Long id) {
        log.debug("Request to get StudentRecord : {}",id);
        StudentRecord studentRecord = studentRecordRepository.findOne(id);
        StudentRecordDTO studentRecordDTO = studentRecordMapper.studentRecordTOStudentRecordDTO(studentRecord);
        return studentRecordDTO;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentRecord : {}",id );
        studentRecordRepository.delete(id);
    }
}
