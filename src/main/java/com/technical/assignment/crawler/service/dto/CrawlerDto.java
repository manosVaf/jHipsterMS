package com.technical.assignment.crawler.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.technical.assignment.crawler.config.Constants.NAME_REGEX;

/**
 * A DTO for the {@link com.technical.assignment.crawler.domain.Crawler} entity.
 */
public class CrawlerDto implements Serializable {

    private Long id;

    @NotNull
    @Pattern(regexp = NAME_REGEX)
    private String name;

    @NotNull
    @Min(value = -1)
    private Integer fetchInterval;

    @NotNull
    @URL
    private String source;

    @JsonDeserialize(as=ArrayList.class, contentAs=FiltersDto.class)
    private List<@Valid FiltersDto> filters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFetchInterval() {
        return fetchInterval;
    }

    public void setFetchInterval(Integer fetchInterval) {
        this.fetchInterval = fetchInterval;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<FiltersDto> getFilters() {
        return Objects.nonNull(filters) ? filters : new ArrayList<>();
    }

    public void setFilters(List<FiltersDto> filters) {
        this.filters = filters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrawlerDto)) {
            return false;
        }

        CrawlerDto crawlerDto = (CrawlerDto) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, crawlerDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrawlerDto{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fetchInterval=" + getFetchInterval() +
            ", source='" + getSource() + "'" +
            ", filters='" + getFilters() + "'" +
            "}";
    }
}
