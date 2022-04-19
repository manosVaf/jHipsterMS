package com.technical.assignment.crawler.domain;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.technical.assignment.crawler.config.Constants.NAME_REGEX;

/**
 * A Crawler.
 */
@Entity
@Table(name = "crawler")
public class Crawler implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Pattern(regexp = NAME_REGEX)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @NotNull
    @Min(value = -1)
    @Column(name = "fetch_interval", nullable = false)
    private Integer fetchInterval;

    @URL
    @Column(name = "source", nullable = false)
    private String source;

    @OneToMany(mappedBy = "crawler", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Filters> filters = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Crawler id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Crawler name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFetchInterval() {
        return this.fetchInterval;
    }

    public Crawler fetchInterval(Integer fetchInterval) {
        this.setFetchInterval(fetchInterval);
        return this;
    }

    public void setFetchInterval(Integer fetchInterval) {
        this.fetchInterval = fetchInterval;
    }

    public String getSource() {
        return this.source;
    }

    public Crawler source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Filters> getFilters() {
        return this.filters;
    }

    public void setFilters(List<Filters> filters) {
        if (this.filters != null) {
            this.filters.forEach(i -> i.setCrawler(null));
        }
        if (filters != null) {
            filters.forEach(i -> i.setCrawler(this));
        }
        this.filters = filters;
    }

    public Crawler configuration(List<Filters> filters) {
        this.setFilters(filters);
        return this;
    }

    public Crawler addFilters(Filters filters) {
        this.filters.add(filters);
        filters.setCrawler(this);
        return this;
    }

    public Crawler removeFilters(Filters filters) {
        this.filters.remove(filters);
        filters.setCrawler(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crawler)) {
            return false;
        }
        return id != null && id.equals(((Crawler) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Crawler{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fetchInterval=" + getFetchInterval() +
            ", source='" + getSource() + "'" +
            ", filters='" + getFilters() + "'" +
            "}";
    }
}
