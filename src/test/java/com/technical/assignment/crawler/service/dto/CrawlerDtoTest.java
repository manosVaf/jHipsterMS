package com.technical.assignment.crawler.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.technical.assignment.crawler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CrawlerDtoTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrawlerDto.class);
        CrawlerDto crawlerDto1 = new CrawlerDto();
        crawlerDto1.setId(1L);
        CrawlerDto crawlerDto2 = new CrawlerDto();
        assertThat(crawlerDto1).isNotEqualTo(crawlerDto2);
        crawlerDto2.setId(crawlerDto1.getId());
        assertThat(crawlerDto1).isEqualTo(crawlerDto2);
        crawlerDto2.setId(2L);
        assertThat(crawlerDto1).isNotEqualTo(crawlerDto2);
        crawlerDto1.setId(null);
        assertThat(crawlerDto1).isNotEqualTo(crawlerDto2);
    }
}
