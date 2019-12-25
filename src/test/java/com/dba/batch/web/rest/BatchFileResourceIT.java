package com.dba.batch.web.rest;

import com.dba.batch.BatchApplicationApp;
import com.dba.batch.domain.BatchFile;
import com.dba.batch.repository.BatchFileRepository;
import com.dba.batch.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dba.batch.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BatchFileResource} REST controller.
 */
@SpringBootTest(classes = BatchApplicationApp.class)
public class BatchFileResourceIT {

    private static final String DEFAULT_BATCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    @Autowired
    private BatchFileRepository batchFileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBatchFileMockMvc;

    private BatchFile batchFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BatchFileResource batchFileResource = new BatchFileResource(batchFileRepository);
        this.restBatchFileMockMvc = MockMvcBuilders.standaloneSetup(batchFileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BatchFile createEntity(EntityManager em) {
        BatchFile batchFile = new BatchFile()
            .batchName(DEFAULT_BATCH_NAME)
            .path(DEFAULT_PATH);
        return batchFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BatchFile createUpdatedEntity(EntityManager em) {
        BatchFile batchFile = new BatchFile()
            .batchName(UPDATED_BATCH_NAME)
            .path(UPDATED_PATH);
        return batchFile;
    }

    @BeforeEach
    public void initTest() {
        batchFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createBatchFile() throws Exception {
        int databaseSizeBeforeCreate = batchFileRepository.findAll().size();

        // Create the BatchFile
        restBatchFileMockMvc.perform(post("/api/batch-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(batchFile)))
            .andExpect(status().isCreated());

        // Validate the BatchFile in the database
        List<BatchFile> batchFileList = batchFileRepository.findAll();
        assertThat(batchFileList).hasSize(databaseSizeBeforeCreate + 1);
        BatchFile testBatchFile = batchFileList.get(batchFileList.size() - 1);
        assertThat(testBatchFile.getBatchName()).isEqualTo(DEFAULT_BATCH_NAME);
        assertThat(testBatchFile.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    public void createBatchFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = batchFileRepository.findAll().size();

        // Create the BatchFile with an existing ID
        batchFile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatchFileMockMvc.perform(post("/api/batch-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(batchFile)))
            .andExpect(status().isBadRequest());

        // Validate the BatchFile in the database
        List<BatchFile> batchFileList = batchFileRepository.findAll();
        assertThat(batchFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBatchFiles() throws Exception {
        // Initialize the database
        batchFileRepository.saveAndFlush(batchFile);

        // Get all the batchFileList
        restBatchFileMockMvc.perform(get("/api/batch-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(batchFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].batchName").value(hasItem(DEFAULT_BATCH_NAME)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
    }
    
    @Test
    @Transactional
    public void getBatchFile() throws Exception {
        // Initialize the database
        batchFileRepository.saveAndFlush(batchFile);

        // Get the batchFile
        restBatchFileMockMvc.perform(get("/api/batch-files/{id}", batchFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(batchFile.getId().intValue()))
            .andExpect(jsonPath("$.batchName").value(DEFAULT_BATCH_NAME))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH));
    }

    @Test
    @Transactional
    public void getNonExistingBatchFile() throws Exception {
        // Get the batchFile
        restBatchFileMockMvc.perform(get("/api/batch-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBatchFile() throws Exception {
        // Initialize the database
        batchFileRepository.saveAndFlush(batchFile);

        int databaseSizeBeforeUpdate = batchFileRepository.findAll().size();

        // Update the batchFile
        BatchFile updatedBatchFile = batchFileRepository.findById(batchFile.getId()).get();
        // Disconnect from session so that the updates on updatedBatchFile are not directly saved in db
        em.detach(updatedBatchFile);
        updatedBatchFile
            .batchName(UPDATED_BATCH_NAME)
            .path(UPDATED_PATH);

        restBatchFileMockMvc.perform(put("/api/batch-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBatchFile)))
            .andExpect(status().isOk());

        // Validate the BatchFile in the database
        List<BatchFile> batchFileList = batchFileRepository.findAll();
        assertThat(batchFileList).hasSize(databaseSizeBeforeUpdate);
        BatchFile testBatchFile = batchFileList.get(batchFileList.size() - 1);
        assertThat(testBatchFile.getBatchName()).isEqualTo(UPDATED_BATCH_NAME);
        assertThat(testBatchFile.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingBatchFile() throws Exception {
        int databaseSizeBeforeUpdate = batchFileRepository.findAll().size();

        // Create the BatchFile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchFileMockMvc.perform(put("/api/batch-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(batchFile)))
            .andExpect(status().isBadRequest());

        // Validate the BatchFile in the database
        List<BatchFile> batchFileList = batchFileRepository.findAll();
        assertThat(batchFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBatchFile() throws Exception {
        // Initialize the database
        batchFileRepository.saveAndFlush(batchFile);

        int databaseSizeBeforeDelete = batchFileRepository.findAll().size();

        // Delete the batchFile
        restBatchFileMockMvc.perform(delete("/api/batch-files/{id}", batchFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BatchFile> batchFileList = batchFileRepository.findAll();
        assertThat(batchFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
