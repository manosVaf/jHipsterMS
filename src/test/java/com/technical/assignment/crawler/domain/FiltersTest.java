package com.technical.assignment.crawler.domain;

import com.technical.assignment.crawler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FiltersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filters.class);
        Filters filters1 = new Filters();
        filters1.setId(1L);
        Filters filters2 = new Filters();
        filters2.setId(filters1.getId());
        assertThat(filters1).isEqualTo(filters2);
        filters2.setId(2L);
        assertThat(filters1).isNotEqualTo(filters2);
        filters1.setId(null);
        assertThat(filters1).isNotEqualTo(filters2);
    }
}
