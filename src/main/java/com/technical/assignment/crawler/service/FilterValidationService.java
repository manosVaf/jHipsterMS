package com.technical.assignment.crawler.service;

import com.technical.assignment.crawler.service.dto.FiltersDto;

@FunctionalInterface
public interface FilterValidationService {

    boolean hasValidConfigurations(FiltersDto filter);
}
