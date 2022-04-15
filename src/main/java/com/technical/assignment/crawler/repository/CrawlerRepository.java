package com.technical.assignment.crawler.repository;

import com.technical.assignment.crawler.domain.Crawler;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Crawler entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrawlerRepository extends JpaRepository<Crawler, Long> {}
