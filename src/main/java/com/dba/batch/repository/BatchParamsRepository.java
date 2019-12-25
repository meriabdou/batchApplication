package com.dba.batch.repository;

import com.dba.batch.domain.BatchParams;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BatchParams entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BatchParamsRepository extends JpaRepository<BatchParams, Long> {

}
