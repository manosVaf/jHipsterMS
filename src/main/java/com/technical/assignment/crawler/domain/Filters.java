package com.technical.assignment.crawler.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Filters.
 */
@Entity
@Table(name = "filters")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Filters implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filtersSequenceGenerator")
    @SequenceGenerator(name = "filtersSequenceGenerator", sequenceName = "filters_sequence")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Type(type = "jsonb")
    @Column(name = "configuration", nullable = false, columnDefinition = "jsonb")
    private String configuration;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name="crawler_id", nullable=false)
    @JsonIgnore
    private Crawler crawler;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Filters id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfiguration() {
        return this.configuration;
    }

    public Filters configuration(String configuration) {
        this.setConfiguration(configuration);
        return this;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public Crawler getCrawler() {
        return this.crawler;
    }

    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }

    public Filters crawler(Crawler crawler) {
        this.setCrawler(crawler);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filters)) {
            return false;
        }
        return id != null && id.equals(((Filters) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Filters{" +
            "id=" + getId() +
            ", configuration='" + getConfiguration() + "'" +
            "}";
    }
}
