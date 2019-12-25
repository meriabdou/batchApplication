package com.dba.batch.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.dba.batch.web.rest.TestUtil;

public class BatchFileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BatchFile.class);
        BatchFile batchFile1 = new BatchFile();
        batchFile1.setId(1L);
        BatchFile batchFile2 = new BatchFile();
        batchFile2.setId(batchFile1.getId());
        assertThat(batchFile1).isEqualTo(batchFile2);
        batchFile2.setId(2L);
        assertThat(batchFile1).isNotEqualTo(batchFile2);
        batchFile1.setId(null);
        assertThat(batchFile1).isNotEqualTo(batchFile2);
    }
}
