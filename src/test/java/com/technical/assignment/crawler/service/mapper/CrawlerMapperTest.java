package com.technical.assignment.crawler.service.mapper;

import com.technical.assignment.crawler.domain.Crawler;
import com.technical.assignment.crawler.domain.Filters;
import com.technical.assignment.crawler.service.dto.CrawlerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrawlerMapperTest {
    private final static String FILTER = "\"{\\\"type\\\":\\\"boolean\\\",\\\"domain\\\":true,\\\"host\\\":false,\\\"schema\\\":false}\"";
    private final static String FILTER1 = "\"{\\\"type\\\":\\\"string\\\",\\\"inclusion\\\":\\\"^.*(map).*$\\\",\\\"exclusion\\\":\\\"^.*(contact).*$\\\"}\"";
    private FiltersMapper filtersMapper;
    private CrawlerMapper crawlerMapper;

    @BeforeEach
    public void setUp() {
        filtersMapper = new FiltersMapperImpl();
        crawlerMapper = new CrawlerMapperImpl(filtersMapper);
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

    @Test
    void addFilter(){
        Crawler domain = new Crawler();

        final Filters filter = new Filters();
        filter.setConfiguration(FILTER);

        final Filters filter1 = new Filters();
        filter1.setConfiguration(FILTER1);

        final Filters filter2 = new Filters();
        filter1.setConfiguration("FILTER1");

        List<Filters> filters = new ArrayList<>();
        filters.add(filter);
        filters.add(filter1);

        domain = domain.configuration(filters);
        assertEquals(2, domain.getFilters().size());

        domain = domain.addFilters(filter2);
        assertEquals(3, domain.getFilters().size());

        domain.removeFilters(filter).removeFilters(filter1).removeFilters(filter2);
        assertEquals(0, domain.getFilters().size());
    }

    @Test
    void partialUpdateTest(){
        CrawlerDto dto = crawlerDto();
        Crawler domain = crawlerMapper.toEntity(dto);

        crawlerMapper.partialUpdate(domain, dto);
        assertEquals(dto.getId(), domain.getId());
        assertEquals(dto.getName(), domain.getName());
        assertEquals(dto.getFetchInterval(), domain.getFetchInterval());
        assertEquals(new ArrayList<>(), dto.getFilters());
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
