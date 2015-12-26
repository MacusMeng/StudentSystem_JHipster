package com.ibm.student.web.rest;

import com.ibm.student.Application;
import com.ibm.student.domain.StudentRecord;
import com.ibm.student.repository.StudentRecordRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the StudentRecordResource REST controller.
 *
 * @see StudentRecordResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StudentRecordResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAA";
    private static final String UPDATED_NUMBER = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_GENDER = "AAAAA";
    private static final String UPDATED_GENDER = "BBBBB";
    private static final String DEFAULT_CLASSES = "AAAAA";
    private static final String UPDATED_CLASSES = "BBBBB";
    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";
    private static final String DEFAULT_SCORD = "AAAAA";
    private static final String UPDATED_SCORD = "BBBBB";

    @Inject
    private StudentRecordRepository studentRecordRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStudentRecordMockMvc;

    private StudentRecord studentRecord;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudentRecordResource studentRecordResource = new StudentRecordResource();
        ReflectionTestUtils.setField(studentRecordResource, "studentRecordRepository", studentRecordRepository);
        this.restStudentRecordMockMvc = MockMvcBuilders.standaloneSetup(studentRecordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        studentRecord = new StudentRecord();
        studentRecord.setNumber(DEFAULT_NUMBER);
        studentRecord.setName(DEFAULT_NAME);
        studentRecord.setGender(DEFAULT_GENDER);
        studentRecord.setClasses(DEFAULT_CLASSES);
        studentRecord.setSubject(DEFAULT_SUBJECT);
        studentRecord.setScord(DEFAULT_SCORD);
    }

    @Test
    @Transactional
    public void createStudentRecord() throws Exception {
        int databaseSizeBeforeCreate = studentRecordRepository.findAll().size();

        // Create the StudentRecord

        restStudentRecordMockMvc.perform(post("/api/studentRecords")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentRecord)))
                .andExpect(status().isCreated());

        // Validate the StudentRecord in the database
        List<StudentRecord> studentRecords = studentRecordRepository.findAll();
        assertThat(studentRecords).hasSize(databaseSizeBeforeCreate + 1);
        StudentRecord testStudentRecord = studentRecords.get(studentRecords.size() - 1);
        assertThat(testStudentRecord.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testStudentRecord.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStudentRecord.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testStudentRecord.getClasses()).isEqualTo(DEFAULT_CLASSES);
        assertThat(testStudentRecord.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testStudentRecord.getScord()).isEqualTo(DEFAULT_SCORD);
    }

    @Test
    @Transactional
    public void getAllStudentRecords() throws Exception {
        // Initialize the database
        studentRecordRepository.saveAndFlush(studentRecord);

        // Get all the studentRecords
        restStudentRecordMockMvc.perform(get("/api/studentRecords?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(studentRecord.getId().intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].classes").value(hasItem(DEFAULT_CLASSES.toString())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].scord").value(hasItem(DEFAULT_SCORD.toString())));
    }

    @Test
    @Transactional
    public void getStudentRecord() throws Exception {
        // Initialize the database
        studentRecordRepository.saveAndFlush(studentRecord);

        // Get the studentRecord
        restStudentRecordMockMvc.perform(get("/api/studentRecords/{id}", studentRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(studentRecord.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.classes").value(DEFAULT_CLASSES.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.scord").value(DEFAULT_SCORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentRecord() throws Exception {
        // Get the studentRecord
        restStudentRecordMockMvc.perform(get("/api/studentRecords/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentRecord() throws Exception {
        // Initialize the database
        studentRecordRepository.saveAndFlush(studentRecord);

		int databaseSizeBeforeUpdate = studentRecordRepository.findAll().size();

        // Update the studentRecord
        studentRecord.setNumber(UPDATED_NUMBER);
        studentRecord.setName(UPDATED_NAME);
        studentRecord.setGender(UPDATED_GENDER);
        studentRecord.setClasses(UPDATED_CLASSES);
        studentRecord.setSubject(UPDATED_SUBJECT);
        studentRecord.setScord(UPDATED_SCORD);

        restStudentRecordMockMvc.perform(put("/api/studentRecords")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentRecord)))
                .andExpect(status().isOk());

        // Validate the StudentRecord in the database
        List<StudentRecord> studentRecords = studentRecordRepository.findAll();
        assertThat(studentRecords).hasSize(databaseSizeBeforeUpdate);
        StudentRecord testStudentRecord = studentRecords.get(studentRecords.size() - 1);
        assertThat(testStudentRecord.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testStudentRecord.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStudentRecord.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testStudentRecord.getClasses()).isEqualTo(UPDATED_CLASSES);
        assertThat(testStudentRecord.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testStudentRecord.getScord()).isEqualTo(UPDATED_SCORD);
    }

    @Test
    @Transactional
    public void deleteStudentRecord() throws Exception {
        // Initialize the database
        studentRecordRepository.saveAndFlush(studentRecord);

		int databaseSizeBeforeDelete = studentRecordRepository.findAll().size();

        // Get the studentRecord
        restStudentRecordMockMvc.perform(delete("/api/studentRecords/{id}", studentRecord.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentRecord> studentRecords = studentRecordRepository.findAll();
        assertThat(studentRecords).hasSize(databaseSizeBeforeDelete - 1);
    }
}
