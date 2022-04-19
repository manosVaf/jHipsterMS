package com.technical.assignment.crawler.web.rest;

import com.technical.assignment.crawler.repository.CrawlerRepository;
import com.technical.assignment.crawler.service.CrawlerService;
import com.technical.assignment.crawler.service.FilterValidationService;
import com.technical.assignment.crawler.service.dto.CrawlerDto;
import com.technical.assignment.crawler.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.technical.assignment.crawler.domain.Crawler}.
 */
@RestController
@RequestMapping("/api")
public class CrawlerResource {

    private final Logger log = LoggerFactory.getLogger(CrawlerResource.class);

    private static final String ENTITY_NAME = "crawler";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CrawlerService crawlerService;

    private final FilterValidationService filterValidationService;

    private final CrawlerRepository crawlerRepository;

    public CrawlerResource(CrawlerService crawlerService, FilterValidationService filterValidationService, CrawlerRepository crawlerRepository) {
        this.crawlerService = crawlerService;
        this.filterValidationService = filterValidationService;
        this.crawlerRepository = crawlerRepository;
    }

    /**
     * {@code POST  /crawlers} : Create a new crawler.
     *
     * @param crawlerDto the crawlerDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crawlerDto, or with status {@code 400 (Bad Request)} if the crawler has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crawlers")
    public ResponseEntity<CrawlerDto> createCrawler(@Valid @RequestBody CrawlerDto crawlerDto) throws URISyntaxException {
        log.debug("REST request to save Crawler : {}", crawlerDto);
        if (crawlerDto.getId() != null) {
            throw new BadRequestAlertException("A new crawler cannot already have an ID", ENTITY_NAME, "idexists");
        }

        if(!crawlerDto.getFilters().stream().allMatch(filterValidationService::hasValidConfigurations)){
            throw new BadRequestAlertException("The configuration of the filters are not properly configured", ENTITY_NAME, "misconfiguration");
        }

        CrawlerDto result = crawlerService.save(crawlerDto);
        return ResponseEntity
            .created(new URI("/api/crawlers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crawlers/:id} : Updates an existing crawler.
     *
     * @param id the id of the crawlerDto to save.
     * @param crawlerDto the crawlerDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crawlerDto,
     * or with status {@code 400 (Bad Request)} if the crawlerDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crawlerDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crawlers/{id}")
    public ResponseEntity<CrawlerDto> updateCrawler(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CrawlerDto crawlerDto
    ) throws URISyntaxException {
        log.debug("REST request to update Crawler : {}, {}", id, crawlerDto);
        if (crawlerDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crawlerDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crawlerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        if(!crawlerDto.getFilters().stream().allMatch(filterValidationService::hasValidConfigurations)){
            throw new BadRequestAlertException("The configuration of the filters are not properly configured", ENTITY_NAME, "idexists");
        }

        CrawlerDto result = crawlerService.update(crawlerDto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, crawlerDto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /crawlers/:id} : Partial updates given fields of an existing crawler, field will ignore if it is null
     *
     * @param id the id of the crawlerDto to save.
     * @param crawlerDto the crawlerDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crawlerDto,
     * or with status {@code 400 (Bad Request)} if the crawlerDto is not valid,
     * or with status {@code 404 (Not Found)} if the crawlerDto is not found,
     * or with status {@code 500 (Internal Server Error)} if the crawlerDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/crawlers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CrawlerDto> partialUpdateCrawler(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CrawlerDto crawlerDto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Crawler partially : {}, {}", id, crawlerDto);
        if (crawlerDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crawlerDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crawlerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CrawlerDto> result = crawlerService.partialUpdate(crawlerDto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, crawlerDto.getId().toString())
        );
    }

    /**
     * {@code GET  /crawlers} : get all the crawlers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crawlers in body.
     */
    @GetMapping("/crawlers")
    public ResponseEntity<List<CrawlerDto>> getAllCrawlers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Crawlers");
        Page<CrawlerDto> page = crawlerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crawlers/:id} : get the "id" crawler.
     *
     * @param id the id of the crawlerDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crawlerDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crawlers/{id}")
    public ResponseEntity<CrawlerDto> getCrawler(@PathVariable Long id) {
        log.debug("REST request to get Crawler : {}", id);
        Optional<CrawlerDto> crawlerDto = crawlerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(crawlerDto);
    }

    /**
     * {@code DELETE  /crawlers/:id} : delete the "id" crawler.
     *
     * @param id the id of the crawlerDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crawlers/{id}")
    public ResponseEntity<Void> deleteCrawler(@PathVariable Long id) {
        log.debug("REST request to delete Crawler : {}", id);
        crawlerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
