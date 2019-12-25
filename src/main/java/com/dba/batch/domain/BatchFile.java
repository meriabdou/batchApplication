package com.dba.batch.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A BatchFile.
 */
@Entity
@Table(name = "batch_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BatchFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "batch_name")
    private String batchName;

    @Column(name = "path")
    private String path;

    @OneToMany(mappedBy = "batchFile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BatchParams> batchParams = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("batchFiles")
    private BatchParams batchParams;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchName() {
        return batchName;
    }

    public BatchFile batchName(String batchName) {
        this.batchName = batchName;
        return this;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getPath() {
        return path;
    }

    public BatchFile path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<BatchParams> getBatchParams() {
        return batchParams;
    }

    public BatchFile batchParams(Set<BatchParams> batchParams) {
        this.batchParams = batchParams;
        return this;
    }

    public BatchFile addBatchParams(BatchParams batchParams) {
        this.batchParams.add(batchParams);
        batchParams.setBatchFile(this);
        return this;
    }

    public BatchFile removeBatchParams(BatchParams batchParams) {
        this.batchParams.remove(batchParams);
        batchParams.setBatchFile(null);
        return this;
    }

    public void setBatchParams(Set<BatchParams> batchParams) {
        this.batchParams = batchParams;
    }

    public BatchParams getBatchParams() {
        return batchParams;
    }

    public BatchFile batchParams(BatchParams batchParams) {
        this.batchParams = batchParams;
        return this;
    }

    public void setBatchParams(BatchParams batchParams) {
        this.batchParams = batchParams;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchFile)) {
            return false;
        }
        return id != null && id.equals(((BatchFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BatchFile{" +
            "id=" + getId() +
            ", batchName='" + getBatchName() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }
}
