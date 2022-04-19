package com.technical.assignment.crawler.service.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.technical.assignment.crawler.service.dto.json.ConfigurationDeserializer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.technical.assignment.crawler.domain.Filters} entity.
 */
public class FiltersDto implements Serializable {

    private Long id;

    @NotNull
    @JsonDeserialize(using = ConfigurationDeserializer.class)
    @JsonRawValue
    private String configuration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FiltersDto)) {
            return false;
        }

        FiltersDto filtersDto = (FiltersDto) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, filtersDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FiltersDto{" +
            "id=" + getId() +
            ", configuration='" + getConfiguration() + "'" +
            "}";
    }
}
