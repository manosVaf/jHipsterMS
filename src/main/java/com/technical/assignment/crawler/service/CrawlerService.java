package com.technical.assignment.crawler.service;

import com.technical.assignment.crawler.service.dto.CrawlerDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.technical.assignment.crawler.domain.Crawler}.
 */
public interface CrawlerService {
    /**
     * Save a crawler.
     *
     * @param crawlerDto the entity to save.
     * @return the persisted entity.
     */
    CrawlerDto save(CrawlerDto crawlerDto);

    /**
     * Updates a crawler.
     *
     * @param crawlerDto the entity to update.
     * @return the persisted entity.
     */
    CrawlerDto update(CrawlerDto crawlerDto);

    /**
     * Partially updates a crawler.
     *
     * @param crawlerDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CrawlerDto> partialUpdate(CrawlerDto crawlerDto);

    /**
     * Get all the crawlers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CrawlerDto> findAll(Pageable pageable);

    /**
     * Get the "id" crawler.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CrawlerDto> findOne(Long id);

    /**
     * Delete the "id" crawler.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
