package com.dba.batch.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dba.batch.web.rest.TestUtil;

public class BatchParamsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BatchParams.class);
        BatchParams batchParams1 = new BatchParams();
        batchParams1.setId(1L);
        BatchParams batchParams2 = new BatchParams();
        batchParams2.setId(batchParams1.getId());
        assertThat(batchParams1).isEqualTo(batchParams2);
        batchParams2.setId(2L);
        assertThat(batchParams1).isNotEqualTo(batchParams2);
        batchParams1.setId(null);
        assertThat(batchParams1).isNotEqualTo(batchParams2);
    }
}
