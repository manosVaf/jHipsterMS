package com.technical.assignment.crawler.service.mapper;

import com.technical.assignment.crawler.domain.Filters;
import com.technical.assignment.crawler.service.dto.FiltersDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Filters} and its DTO {@link FiltersDto}.
 */
@Mapper(componentModel = "spring")
public interface FiltersMapper extends EntityMapper<FiltersDto, Filters> {}
