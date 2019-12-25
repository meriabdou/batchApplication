package com.dba.batch.web.rest;

import com.dba.batch.domain.BatchParams;
import com.dba.batch.repository.BatchParamsRepository;
import com.dba.batch.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dba.batch.domain.BatchParams}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BatchParamsResource {

    private final Logger log = LoggerFactory.getLogger(BatchParamsResource.class);

    private static final String ENTITY_NAME = "batchParams";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BatchParamsRepository batchParamsRepository;

    public BatchParamsResource(BatchParamsRepository batchParamsRepository) {
        this.batchParamsRepository = batchParamsRepository;
    }

    /**
     * {@code POST  /batch-params} : Create a new batchParams.
     *
     * @param batchParams the batchParams to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new batchParams, or with status {@code 400 (Bad Request)} if the batchParams has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/batch-params")
    public ResponseEntity<BatchParams> createBatchParams(@RequestBody BatchParams batchParams) throws URISyntaxException {
        log.debug("REST request to save BatchParams : {}", batchParams);
        if (batchParams.getId() != null) {
            throw new BadRequestAlertException("A new batchParams cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BatchParams result = batchParamsRepository.save(batchParams);
        return ResponseEntity.created(new URI("/api/batch-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /batch-params} : Updates an existing batchParams.
     *
     * @param batchParams the batchParams to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchParams,
     * or with status {@code 400 (Bad Request)} if the batchParams is not valid,
     * or with status {@code 500 (Internal Server Error)} if the batchParams couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/batch-params")
    public ResponseEntity<BatchParams> updateBatchParams(@RequestBody BatchParams batchParams) throws URISyntaxException {
        log.debug("REST request to update BatchParams : {}", batchParams);
        if (batchParams.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BatchParams result = batchParamsRepository.save(batchParams);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batchParams.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /batch-params} : get all the batchParams.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of batchParams in body.
     */
    @GetMapping("/batch-params")
    public List<BatchParams> getAllBatchParams() {
        log.debug("REST request to get all BatchParams");
        return batchParamsRepository.findAll();
    }

    /**
     * {@code GET  /batch-params/:id} : get the "id" batchParams.
     *
     * @param id the id of the batchParams to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the batchParams, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/batch-params/{id}")
    public ResponseEntity<BatchParams> getBatchParams(@PathVariable Long id) {
        log.debug("REST request to get BatchParams : {}", id);
        Optional<BatchParams> batchParams = batchParamsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(batchParams);
    }

    /**
     * {@code DELETE  /batch-params/:id} : delete the "id" batchParams.
     *
     * @param id the id of the batchParams to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/batch-params/{id}")
    public ResponseEntity<Void> deleteBatchParams(@PathVariable Long id) {
        log.debug("REST request to delete BatchParams : {}", id);
        batchParamsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
