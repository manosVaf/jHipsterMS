package com.technical.assignment.crawler.service.mapper;

import com.technical.assignment.crawler.domain.Crawler;
import com.technical.assignment.crawler.domain.Filters;
import com.technical.assignment.crawler.service.dto.FiltersDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltersMapperTest {
    private final static String FILTER = "\"{\\\"type\\\":\\\"boolean\\\",\\\"domain\\\":true,\\\"host\\\":false,\\\"schema\\\":false}\"";

    private FiltersMapper filtersMapper;

    @BeforeEach
    public void setUp() {
        filtersMapper = new FiltersMapperImpl();
    }

    @Test
    void fromDtoToDomain() {
        FiltersDto nullFilters = null;
        assertNull(filtersMapper.toEntity(nullFilters));

        final FiltersDto dto = filtersDto();
        final Filters domain = filtersMapper.toEntity(dto);

        assertAll(() -> {
            assertEquals(dto.getId(), domain.getId());
            assertEquals(dto.getConfiguration(), domain.getConfiguration());
        });
    }

    @Test
    void fromDomain() {
        Filters nullFilters = null;
        assertNull(filtersMapper.toDto(nullFilters));

        final FiltersDto dto = filtersDto();
        final Filters domain = filtersMapper.toEntity(dto);

        final FiltersDto mappedDto = filtersMapper.toDto(domain);
        assertEquals(dto, mappedDto);
    }

    @Test
    void fromDtoListToDomainList(){
        List<FiltersDto> FiltersDtos = null;
        assertNull(filtersMapper.toEntity(FiltersDtos));

        FiltersDto FiltersDto = filtersDto();
        FiltersDto FiltersDto1 = filtersDto();
        FiltersDto1.setId(1001L);

        FiltersDtos = Arrays.asList(FiltersDto, FiltersDto1);
        List<Filters> entitiesList = filtersMapper.toEntity(FiltersDtos);
        assertEquals(FiltersDtos.size(), entitiesList.size());
    }

    @Test
    void fromDomainListToDtoList(){
        List<Filters> filters = null;
        assertNull(filtersMapper.toDto(filters));

        Filters Filters = filters();
        Filters Filters1 = filters();
        Filters1.setId(1001L);

        filters = Arrays.asList(Filters, Filters);
        List<FiltersDto> dtosList = filtersMapper.toDto(filters);
        assertEquals(filters.size(), dtosList.size());
    }

    @Test
    void addFilter(){
        Filters domain = new Filters();

        domain = domain.configuration(FILTER);
        assertNotNull(domain.getConfiguration());

        final Crawler crawler = new Crawler();
        crawler.setName("testCrawler");

        domain = domain.crawler(crawler);
        assertNotNull(domain.getCrawler());

    }

    @Test
    void partialUpdateTest(){
        FiltersDto dto = filtersDto();
        Filters domain = new Filters();
        domain.setId(1L);
        domain.setConfiguration("Test");

        filtersMapper.partialUpdate(domain, null);
        assertNotEquals(dto.getId(), domain.getId());
        assertNotEquals(dto.getConfiguration(), domain.getConfiguration());


        filtersMapper.partialUpdate(domain, dto);
        assertEquals(dto.getId(), domain.getId());
        assertEquals(dto.getConfiguration(), domain.getConfiguration());
    }

    private FiltersDto filtersDto(){
        FiltersDto dto = new FiltersDto();

        dto.setId(100L);
        dto.setConfiguration("FiltersTest");

        return dto;
    }

    private Filters filters(){
        return filtersMapper.toEntity(filtersDto());
    }
}
