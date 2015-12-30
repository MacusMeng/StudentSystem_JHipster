package com.ibm.student.web.rest.mapper;

import com.ibm.student.domain.StudentRecord;
import com.ibm.student.web.rest.dto.StudentRecordDTO;
import org.mapstruct.Mapper;

/**
 * Created by MengR on 15/12/29.
 */
@Mapper(componentModel = "spring",uses = {})
public interface StudentRecordMapper {
    StudentRecordDTO studentRecordTOStudentRecordDTO(StudentRecord studentRecord);
    StudentRecord studentRecordDTOToStudentRecord(StudentRecordDTO studentRecordDTO);
}
