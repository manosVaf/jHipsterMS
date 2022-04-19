package com.technical.assignment.crawler.service.mapper;

import com.technical.assignment.crawler.domain.Crawler;
import com.technical.assignment.crawler.service.dto.CrawlerDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Crawler} and its DTO {@link CrawlerDto}.
 */
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = FiltersMapper.class)
public interface CrawlerMapper extends EntityMapper<CrawlerDto, Crawler> {
    @Override
    @Mapping(target = "filters", source = "filters")
    Crawler toEntity(CrawlerDto dto);

    @Override
    @Mapping(target = "filters", source = "filters")
    CrawlerDto toDto(Crawler entity);
}
