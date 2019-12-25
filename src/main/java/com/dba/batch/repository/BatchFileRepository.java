package com.dba.batch.repository;

import com.dba.batch.domain.BatchFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BatchFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BatchFileRepository extends JpaRepository<BatchFile, Long> {

}
