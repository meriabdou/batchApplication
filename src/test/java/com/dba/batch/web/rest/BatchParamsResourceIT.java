package com.dba.batch.web.rest;

import com.dba.batch.BatchApplicationApp;
import com.dba.batch.domain.BatchParams;
import com.dba.batch.repository.BatchParamsRepository;
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
 * Integration tests for the {@link BatchParamsResource} REST controller.
 */
@SpringBootTest(classes = BatchApplicationApp.class)
public class BatchParamsResourceIT {

    private static final String DEFAULT_PARAM = "AAAAAAAAAA";
    private static final String UPDATED_PARAM = "BBBBBBBBBB";

    private static final Integer DEFAULT_REQUIRED = 1;
    private static final Integer UPDATED_REQUIRED = 2;

    private static final String DEFAULT_PARAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_NAME = "BBBBBBBBBB";

    @Autowired
    private BatchParamsRepository batchParamsRepository;

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

    private MockMvc restBatchParamsMockMvc;

    private BatchParams batchParams;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BatchParamsResource batchParamsResource = new BatchParamsResource(batchParamsRepository);
        this.restBatchParamsMockMvc = MockMvcBuilders.standaloneSetup(batchParamsResource)
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
    public static BatchParams createEntity(EntityManager em) {
        BatchParams batchParams = new BatchParams()
            .param(DEFAULT_PARAM)
            .required(DEFAULT_REQUIRED)
            .paramName(DEFAULT_PARAM_NAME);
        return batchParams;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BatchParams createUpdatedEntity(EntityManager em) {
        BatchParams batchParams = new BatchParams()
            .param(UPDATED_PARAM)
            .required(UPDATED_REQUIRED)
            .paramName(UPDATED_PARAM_NAME);
        return batchParams;
    }

    @BeforeEach
    public void initTest() {
        batchParams = createEntity(em);
    }

    @Test
    @Transactional
    public void createBatchParams() throws Exception {
        int databaseSizeBeforeCreate = batchParamsRepository.findAll().size();

        // Create the BatchParams
        restBatchParamsMockMvc.perform(post("/api/batch-params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(batchParams)))
            .andExpect(status().isCreated());

        // Validate the BatchParams in the database
        List<BatchParams> batchParamsList = batchParamsRepository.findAll();
        assertThat(batchParamsList).hasSize(databaseSizeBeforeCreate + 1);
        BatchParams testBatchParams = batchParamsList.get(batchParamsList.size() - 1);
        assertThat(testBatchParams.getParam()).isEqualTo(DEFAULT_PARAM);
        assertThat(testBatchParams.getRequired()).isEqualTo(DEFAULT_REQUIRED);
        assertThat(testBatchParams.getParamName()).isEqualTo(DEFAULT_PARAM_NAME);
    }

    @Test
    @Transactional
    public void createBatchParamsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = batchParamsRepository.findAll().size();

        // Create the BatchParams with an existing ID
        batchParams.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatchParamsMockMvc.perform(post("/api/batch-params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(batchParams)))
            .andExpect(status().isBadRequest());

        // Validate the BatchParams in the database
        List<BatchParams> batchParamsList = batchParamsRepository.findAll();
        assertThat(batchParamsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBatchParams() throws Exception {
        // Initialize the database
        batchParamsRepository.saveAndFlush(batchParams);

        // Get all the batchParamsList
        restBatchParamsMockMvc.perform(get("/api/batch-params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(batchParams.getId().intValue())))
            .andExpect(jsonPath("$.[*].param").value(hasItem(DEFAULT_PARAM)))
            .andExpect(jsonPath("$.[*].required").value(hasItem(DEFAULT_REQUIRED)))
            .andExpect(jsonPath("$.[*].paramName").value(hasItem(DEFAULT_PARAM_NAME)));
    }
    
    @Test
    @Transactional
    public void getBatchParams() throws Exception {
        // Initialize the database
        batchParamsRepository.saveAndFlush(batchParams);

        // Get the batchParams
        restBatchParamsMockMvc.perform(get("/api/batch-params/{id}", batchParams.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(batchParams.getId().intValue()))
            .andExpect(jsonPath("$.param").value(DEFAULT_PARAM))
            .andExpect(jsonPath("$.required").value(DEFAULT_REQUIRED))
            .andExpect(jsonPath("$.paramName").value(DEFAULT_PARAM_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingBatchParams() throws Exception {
        // Get the batchParams
        restBatchParamsMockMvc.perform(get("/api/batch-params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBatchParams() throws Exception {
        // Initialize the database
        batchParamsRepository.saveAndFlush(batchParams);

        int databaseSizeBeforeUpdate = batchParamsRepository.findAll().size();

        // Update the batchParams
        BatchParams updatedBatchParams = batchParamsRepository.findById(batchParams.getId()).get();
        // Disconnect from session so that the updates on updatedBatchParams are not directly saved in db
        em.detach(updatedBatchParams);
        updatedBatchParams
            .param(UPDATED_PARAM)
            .required(UPDATED_REQUIRED)
            .paramName(UPDATED_PARAM_NAME);

        restBatchParamsMockMvc.perform(put("/api/batch-params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBatchParams)))
            .andExpect(status().isOk());

        // Validate the BatchParams in the database
        List<BatchParams> batchParamsList = batchParamsRepository.findAll();
        assertThat(batchParamsList).hasSize(databaseSizeBeforeUpdate);
        BatchParams testBatchParams = batchParamsList.get(batchParamsList.size() - 1);
        assertThat(testBatchParams.getParam()).isEqualTo(UPDATED_PARAM);
        assertThat(testBatchParams.getRequired()).isEqualTo(UPDATED_REQUIRED);
        assertThat(testBatchParams.getParamName()).isEqualTo(UPDATED_PARAM_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBatchParams() throws Exception {
        int databaseSizeBeforeUpdate = batchParamsRepository.findAll().size();

        // Create the BatchParams

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatchParamsMockMvc.perform(put("/api/batch-params")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(batchParams)))
            .andExpect(status().isBadRequest());

        // Validate the BatchParams in the database
        List<BatchParams> batchParamsList = batchParamsRepository.findAll();
        assertThat(batchParamsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBatchParams() throws Exception {
        // Initialize the database
        batchParamsRepository.saveAndFlush(batchParams);

        int databaseSizeBeforeDelete = batchParamsRepository.findAll().size();

        // Delete the batchParams
        restBatchParamsMockMvc.perform(delete("/api/batch-params/{id}", batchParams.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BatchParams> batchParamsList = batchParamsRepository.findAll();
        assertThat(batchParamsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
