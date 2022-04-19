package com.technical.assignment.crawler.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technical.assignment.crawler.service.FilterValidationService;
import com.technical.assignment.crawler.service.dto.FiltersDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Predicate;

@Service
public class FilterValidationServiceImpl implements FilterValidationService {

    @Override
    public boolean hasValidConfigurations(FiltersDto filter) {
        Map<String, Object> configurations;
        try {
            configurations = parseConfiguration(filter.getConfiguration());
        } catch (JsonProcessingException e) {
            return false;
        }

        if(!configurations.containsKey("type")) return false;

        return configurations.keySet()
                             .stream()
                             .filter(key -> !key.equalsIgnoreCase("type"))
                             .map(configurations::get)
                             .allMatch(getValidationPredicate());

    }

    private Predicate<Object> getValidationPredicate(){
        return value -> {
            if(value instanceof Boolean) return true;

            if(value instanceof Integer){
                return (Integer) value > 0;
            }

            if(value instanceof String){
                return !((String) value).isEmpty();
            }

            return false;
        };
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parseConfiguration(String configuration) throws JsonProcessingException {
        return new ObjectMapper().readValue(configuration, Map.class);
    }
}
