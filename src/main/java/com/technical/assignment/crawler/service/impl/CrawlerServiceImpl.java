package com.technical.assignment.crawler.service.impl;

import com.technical.assignment.crawler.domain.Crawler;
import com.technical.assignment.crawler.repository.CrawlerRepository;
import com.technical.assignment.crawler.service.CrawlerService;
import com.technical.assignment.crawler.service.dto.CrawlerDto;
import com.technical.assignment.crawler.service.mapper.CrawlerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Crawler}.
 */
@Service
@Transactional
public class CrawlerServiceImpl implements CrawlerService {

    private final Logger log = LoggerFactory.getLogger(CrawlerServiceImpl.class);

    private final CrawlerRepository crawlerRepository;

    private final CrawlerMapper crawlerMapper;

    public CrawlerServiceImpl(CrawlerRepository crawlerRepository, CrawlerMapper crawlerMapper) {
        this.crawlerRepository = crawlerRepository;
        this.crawlerMapper = crawlerMapper;
    }

    @Override
    public CrawlerDto save(CrawlerDto crawlerDto) {
        log.debug("Request to save Crawler : {}", crawlerDto);
        Crawler crawler = crawlerMapper.toEntity(crawlerDto);
        crawler = crawlerRepository.save(crawler);
        return crawlerMapper.toDto(crawler);
    }

    @Override
    public CrawlerDto update(CrawlerDto crawlerDto) {
        log.debug("Request to update Crawler : {}", crawlerDto);
        Crawler crawler = crawlerMapper.toEntity(crawlerDto);
        crawler = crawlerRepository.save(crawler);
        return crawlerMapper.toDto(crawler);
    }

    @Override
    public Optional<CrawlerDto> partialUpdate(CrawlerDto crawlerDto) {
        log.debug("Request to partially update Crawler : {}", crawlerDto);

        return crawlerRepository
            .findById(crawlerDto.getId())
            .map(existingCrawler -> {
                crawlerMapper.partialUpdate(existingCrawler, crawlerDto);
                existingCrawler.getFilters().forEach(filters -> filters.setCrawler(existingCrawler));
                return existingCrawler;
            })
            .map(crawlerRepository::save)
            .map(crawlerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CrawlerDto> findAll(Pageable pageable) {
        log.debug("Request to get all Crawlers");
        return crawlerRepository.findAll(pageable).map(crawlerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CrawlerDto> findOne(Long id) {
        log.debug("Request to get Crawler : {}", id);
        return crawlerRepository.findById(id).map(crawlerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Crawler : {}", id);
        crawlerRepository.deleteById(id);
    }
}
