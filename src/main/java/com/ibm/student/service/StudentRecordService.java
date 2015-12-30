package com.ibm.student.service;

import com.ibm.student.domain.StudentRecord;
import com.ibm.student.web.rest.dto.StudentRecordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by MengR on 15/12/29.
 */
public interface StudentRecordService {
    /**
     *
     * @param studentRecordDTO
     * @return
     */
    public StudentRecordDTO save(StudentRecordDTO studentRecordDTO);

    /**
     *
     * @param pageable
     * @return
     */
    public Page<StudentRecord> findAll(Pageable pageable);

    /**
     *
     * @param id
     * @return
     */
    public StudentRecordDTO findOne(Long id);

    /**
     *
     * @param id
     */
    public void delete(Long id);

}
