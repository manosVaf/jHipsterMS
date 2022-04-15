package com.technical.assignment.crawler.service.mapper;

import com.technical.assignment.crawler.domain.Crawler;
import com.technical.assignment.crawler.service.dto.CrawlerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrawlerMapperTest {

    private CrawlerMapper crawlerMapper;

    @BeforeEach
    public void setUp() {
        crawlerMapper = new CrawlerMapperImpl();
    }

    @Test
    void fromDtoToDomain() {
        CrawlerDto nullCrawler = null;
        assertNull(crawlerMapper.toEntity(nullCrawler));

        final CrawlerDto dto = crawlerDto();
        final Crawler domain = crawlerMapper.toEntity(dto);

        assertAll(() -> {
            assertEquals(dto.getId(), domain.getId());
            assertEquals(dto.getName(), domain.getName());
            assertEquals(dto.getFetchInterval(), domain.getFetchInterval());
            assertEquals(dto.getSource(), domain.getSource());
        });
    }

    @Test
    void fromDomain() {
        Crawler nullCrawler = null;
        assertNull(crawlerMapper.toDto(nullCrawler));

        final CrawlerDto dto = crawlerDto();
        final Crawler domain = crawlerMapper.toEntity(dto);

        final CrawlerDto mappedDto = crawlerMapper.toDto(domain);
        assertEquals(dto, mappedDto);
    }

    @Test
    void fromDtoListToDomainList(){
        List<CrawlerDto> crawlerDtos = null;
        assertNull(crawlerMapper.toEntity(crawlerDtos));

        CrawlerDto crawlerDto = crawlerDto();
        CrawlerDto crawlerDto1 = crawlerDto();
        crawlerDto1.setId(1001L);

        crawlerDtos = Arrays.asList(crawlerDto, crawlerDto1);
        List<Crawler> entitiesList = crawlerMapper.toEntity(crawlerDtos);
        assertEquals(crawlerDtos.size(), entitiesList.size());
    }

    @Test
    void fromDomainListToDtoList(){
        List<Crawler> crawlers = null;
        assertNull(crawlerMapper.toDto(crawlers));

        Crawler crawler = crawler();
        Crawler crawler1 = crawler();
        crawler1.setId(1001L);

        crawlers = Arrays.asList(crawler, crawler);
        List<CrawlerDto> dtosList = crawlerMapper.toDto(crawlers);
        assertEquals(crawlers.size(), dtosList.size());
    }

    private CrawlerDto crawlerDto(){
        CrawlerDto dto = new CrawlerDto();
        dto.setId(100L);
        dto.setName("crawlerTest");
        dto.setFetchInterval(100);
        dto.setSource("http://test.com");

        return dto;
    }

    private Crawler crawler(){
        return crawlerMapper.toEntity(crawlerDto());
    }
}
