package com.technical.assignment.crawler.service.mapper;

import com.technical.assignment.crawler.domain.Crawler;
import com.technical.assignment.crawler.service.dto.CrawlerDto;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Crawler} and its DTO {@link CrawlerDto}.
 */
@Mapper(componentModel = "spring")
public interface CrawlerMapper extends EntityMapper<CrawlerDto, Crawler> {}
