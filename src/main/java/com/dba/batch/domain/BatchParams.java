package com.dba.batch.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A BatchParams.
 */
@Entity
@Table(name = "batch_params")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BatchParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "param")
    private String param;

    @Column(name = "required")
    private Integer required;

    @Column(name = "param_name")
    private String paramName;

    @OneToMany(mappedBy = "batchParams")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BatchFile> batchFiles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("batchParams")
    private BatchFile batchFile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParam() {
        return param;
    }

    public BatchParams param(String param) {
        this.param = param;
        return this;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getRequired() {
        return required;
    }

    public BatchParams required(Integer required) {
        this.required = required;
        return this;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public String getParamName() {
        return paramName;
    }

    public BatchParams paramName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Set<BatchFile> getBatchFiles() {
        return batchFiles;
    }

    public BatchParams batchFiles(Set<BatchFile> batchFiles) {
        this.batchFiles = batchFiles;
        return this;
    }

    public BatchParams addBatchFile(BatchFile batchFile) {
        this.batchFiles.add(batchFile);
        batchFile.setBatchParams(this);
        return this;
    }

    public BatchParams removeBatchFile(BatchFile batchFile) {
        this.batchFiles.remove(batchFile);
        batchFile.setBatchParams(null);
        return this;
    }

    public void setBatchFiles(Set<BatchFile> batchFiles) {
        this.batchFiles = batchFiles;
    }

    public BatchFile getBatchFile() {
        return batchFile;
    }

    public BatchParams batchFile(BatchFile batchFile) {
        this.batchFile = batchFile;
        return this;
    }

    public void setBatchFile(BatchFile batchFile) {
        this.batchFile = batchFile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatchParams)) {
            return false;
        }
        return id != null && id.equals(((BatchParams) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BatchParams{" +
            "id=" + getId() +
            ", param='" + getParam() + "'" +
            ", required=" + getRequired() +
            ", paramName='" + getParamName() + "'" +
            "}";
    }
}
