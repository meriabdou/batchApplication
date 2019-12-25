package com.dba.batch.web.rest;

import com.dba.batch.domain.BatchFile;
import com.dba.batch.repository.BatchFileRepository;
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
 * REST controller for managing {@link com.dba.batch.domain.BatchFile}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BatchFileResource {

    private final Logger log = LoggerFactory.getLogger(BatchFileResource.class);

    private static final String ENTITY_NAME = "batchFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BatchFileRepository batchFileRepository;

    public BatchFileResource(BatchFileRepository batchFileRepository) {
        this.batchFileRepository = batchFileRepository;
    }

    /**
     * {@code POST  /batch-files} : Create a new batchFile.
     *
     * @param batchFile the batchFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new batchFile, or with status {@code 400 (Bad Request)} if the batchFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/batch-files")
    public ResponseEntity<BatchFile> createBatchFile(@RequestBody BatchFile batchFile) throws URISyntaxException {
        log.debug("REST request to save BatchFile : {}", batchFile);
        if (batchFile.getId() != null) {
            throw new BadRequestAlertException("A new batchFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BatchFile result = batchFileRepository.save(batchFile);
        return ResponseEntity.created(new URI("/api/batch-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /batch-files} : Updates an existing batchFile.
     *
     * @param batchFile the batchFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batchFile,
     * or with status {@code 400 (Bad Request)} if the batchFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the batchFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/batch-files")
    public ResponseEntity<BatchFile> updateBatchFile(@RequestBody BatchFile batchFile) throws URISyntaxException {
        log.debug("REST request to update BatchFile : {}", batchFile);
        if (batchFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BatchFile result = batchFileRepository.save(batchFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batchFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /batch-files} : get all the batchFiles.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of batchFiles in body.
     */
    @GetMapping("/batch-files")
    public List<BatchFile> getAllBatchFiles() {
        log.debug("REST request to get all BatchFiles");
        return batchFileRepository.findAll();
    }

    /**
     * {@code GET  /batch-files/:id} : get the "id" batchFile.
     *
     * @param id the id of the batchFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the batchFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/batch-files/{id}")
    public ResponseEntity<BatchFile> getBatchFile(@PathVariable Long id) {
        log.debug("REST request to get BatchFile : {}", id);
        Optional<BatchFile> batchFile = batchFileRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(batchFile);
    }

    /**
     * {@code DELETE  /batch-files/:id} : delete the "id" batchFile.
     *
     * @param id the id of the batchFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/batch-files/{id}")
    public ResponseEntity<Void> deleteBatchFile(@PathVariable Long id) {
        log.debug("REST request to delete BatchFile : {}", id);
        batchFileRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
