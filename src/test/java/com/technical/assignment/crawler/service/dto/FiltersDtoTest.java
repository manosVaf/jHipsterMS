package com.technical.assignment.crawler.service.dto;

import com.technical.assignment.crawler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FiltersDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FiltersDto.class);
        FiltersDto filtersDto1 = new FiltersDto();
        filtersDto1.setId(1L);
        FiltersDto filtersDto2 = new FiltersDto();
        assertThat(filtersDto1).isNotEqualTo(filtersDto2);
        filtersDto2.setId(filtersDto1.getId());
        assertThat(filtersDto1).isEqualTo(filtersDto2);
        filtersDto2.setId(2L);
        assertThat(filtersDto1).isNotEqualTo(filtersDto2);
        filtersDto1.setId(null);
        assertThat(filtersDto1).isNotEqualTo(filtersDto2);
    }
}
