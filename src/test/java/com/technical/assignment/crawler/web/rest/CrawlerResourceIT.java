package com.technical.assignment.crawler.web.rest;

import com.technical.assignment.crawler.IntegrationTest;
import com.technical.assignment.crawler.domain.Crawler;
import com.technical.assignment.crawler.repository.CrawlerRepository;
import com.technical.assignment.crawler.service.dto.CrawlerDto;
import com.technical.assignment.crawler.service.mapper.CrawlerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CrawlerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CrawlerResourceIT {

    private static final String DEFAULT_NAME = "name_";
    private static final String UPDATED_NAME = "name-";

    private static final Integer DEFAULT_FETCH_INTERVAL = -1;
    private static final Integer UPDATED_FETCH_INTERVAL = 0;

    private static final String DEFAULT_SOURCE = "http://test.com";
    private static final String UPDATED_SOURCE = "http://test1.com";

    private static final String ENTITY_API_URL = "/api/crawlers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CrawlerRepository crawlerRepository;

    @Autowired
    private CrawlerMapper crawlerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCrawlerMockMvc;

    private Crawler crawler;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crawler createEntity(EntityManager em) {
        Crawler crawler = new Crawler().name(DEFAULT_NAME).fetchInterval(DEFAULT_FETCH_INTERVAL).source(DEFAULT_SOURCE);
        return crawler;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crawler createUpdatedEntity(EntityManager em) {
        Crawler crawler = new Crawler().name(UPDATED_NAME).fetchInterval(UPDATED_FETCH_INTERVAL).source(UPDATED_SOURCE);
        return crawler;
    }

    @BeforeEach
    public void initTest() {
        crawler = createEntity(em);
    }

    @Test
    @Transactional
    void createCrawler() throws Exception {
        int databaseSizeBeforeCreate = crawlerRepository.findAll().size();
        // Create the Crawler
        CrawlerDto crawlerDto = crawlerMapper.toDto(crawler);
        restCrawlerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isCreated());

        // Validate the Crawler in the database
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeCreate + 1);
        Crawler testCrawler = crawlerList.get(crawlerList.size() - 1);
        assertThat(testCrawler.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCrawler.getFetchInterval()).isEqualTo(DEFAULT_FETCH_INTERVAL);
        assertThat(testCrawler.getSource()).isEqualTo(DEFAULT_SOURCE);
    }

    @Test
    @Transactional
    void createCrawlerWithExistingId() throws Exception {
        // Create the Crawler with an existing ID
        crawler.setId(1L);
        CrawlerDto crawlerDto = crawlerMapper.toDto(crawler);

        int databaseSizeBeforeCreate = crawlerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrawlerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crawler in the database
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = crawlerRepository.findAll().size();
        // set the field null
        crawler.setName(null);

        // Create the Crawler, which fails.
        CrawlerDto crawlerDto = crawlerMapper.toDto(crawler);

        restCrawlerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isBadRequest());

        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFetchIntervalIsRequired() throws Exception {
        int databaseSizeBeforeTest = crawlerRepository.findAll().size();
        // set the field null
        crawler.setFetchInterval(null);

        // Create the Crawler, which fails.
        CrawlerDto crawlerDto = crawlerMapper.toDto(crawler);

        restCrawlerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isBadRequest());

        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = crawlerRepository.findAll().size();
        // set the field null
        crawler.setSource(null);

        // Create the Crawler, which fails.
        CrawlerDto crawlerDto = crawlerMapper.toDto(crawler);

        restCrawlerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isBadRequest());

        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCrawlers() throws Exception {
        // Initialize the database
        crawlerRepository.saveAndFlush(crawler);

        // Get all the crawlerList
        restCrawlerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crawler.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fetchInterval").value(hasItem(DEFAULT_FETCH_INTERVAL)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)));
    }

    @Test
    @Transactional
    void getCrawler() throws Exception {
        // Initialize the database
        crawlerRepository.saveAndFlush(crawler);

        // Get the crawler
        restCrawlerMockMvc
            .perform(get(ENTITY_API_URL_ID, crawler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crawler.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fetchInterval").value(DEFAULT_FETCH_INTERVAL))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE));
    }

    @Test
    @Transactional
    void getNonExistingCrawler() throws Exception {
        // Get the crawler
        restCrawlerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCrawler() throws Exception {
        // Initialize the database
        crawlerRepository.saveAndFlush(crawler);

        int databaseSizeBeforeUpdate = crawlerRepository.findAll().size();

        // Update the crawler
        Crawler updatedCrawler = crawlerRepository.findById(crawler.getId()).get();
        // Disconnect from session so that the updates on updatedCrawler are not directly saved in db
        em.detach(updatedCrawler);
        updatedCrawler.name(UPDATED_NAME).fetchInterval(UPDATED_FETCH_INTERVAL).source(UPDATED_SOURCE);
        CrawlerDto crawlerDto = crawlerMapper.toDto(updatedCrawler);

        restCrawlerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crawlerDto.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isOk());

        // Validate the Crawler in the database
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeUpdate);
        Crawler testCrawler = crawlerList.get(crawlerList.size() - 1);
        assertThat(testCrawler.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCrawler.getFetchInterval()).isEqualTo(UPDATED_FETCH_INTERVAL);
        assertThat(testCrawler.getSource()).isEqualTo(UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void putNonExistingCrawler() throws Exception {
        int databaseSizeBeforeUpdate = crawlerRepository.findAll().size();
        crawler.setId(count.incrementAndGet());

        // Create the Crawler
        CrawlerDto crawlerDto = crawlerMapper.toDto(crawler);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrawlerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crawlerDto.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crawler in the database
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCrawler() throws Exception {
        int databaseSizeBeforeUpdate = crawlerRepository.findAll().size();
        crawler.setId(count.incrementAndGet());

        // Create the Crawler
        CrawlerDto crawlerDto = crawlerMapper.toDto(crawler);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crawler in the database
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCrawler() throws Exception {
        int databaseSizeBeforeUpdate = crawlerRepository.findAll().size();
        crawler.setId(count.incrementAndGet());

        // Create the Crawler
        CrawlerDto crawlerDto = crawlerMapper.toDto(crawler);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlerMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crawler in the database
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCrawlerWithPatch() throws Exception {
        // Initialize the database
        crawlerRepository.saveAndFlush(crawler);

        int databaseSizeBeforeUpdate = crawlerRepository.findAll().size();

        // Update the crawler using partial update
        Crawler partialUpdatedCrawler = new Crawler();
        partialUpdatedCrawler.setId(crawler.getId());

        partialUpdatedCrawler.source(UPDATED_SOURCE);

        restCrawlerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrawler.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCrawler))
            )
            .andExpect(status().isOk());

        // Validate the Crawler in the database
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeUpdate);
        Crawler testCrawler = crawlerList.get(crawlerList.size() - 1);
        assertThat(testCrawler.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCrawler.getFetchInterval()).isEqualTo(DEFAULT_FETCH_INTERVAL);
        assertThat(testCrawler.getSource()).isEqualTo(UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void fullUpdateCrawlerWithPatch() throws Exception {
        // Initialize the database
        crawlerRepository.saveAndFlush(crawler);

        int databaseSizeBeforeUpdate = crawlerRepository.findAll().size();

        // Update the crawler using partial update
        Crawler partialUpdatedCrawler = new Crawler();
        partialUpdatedCrawler.setId(crawler.getId());

        partialUpdatedCrawler.name(UPDATED_NAME).fetchInterval(UPDATED_FETCH_INTERVAL).source(UPDATED_SOURCE);

        restCrawlerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrawler.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCrawler))
            )
            .andExpect(status().isOk());

        // Validate the Crawler in the database
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeUpdate);
        Crawler testCrawler = crawlerList.get(crawlerList.size() - 1);
        assertThat(testCrawler.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCrawler.getFetchInterval()).isEqualTo(UPDATED_FETCH_INTERVAL);
        assertThat(testCrawler.getSource()).isEqualTo(UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void patchNonExistingCrawler() throws Exception {
        int databaseSizeBeforeUpdate = crawlerRepository.findAll().size();
        crawler.setId(count.incrementAndGet());

        // Create the Crawler
        CrawlerDto crawlerDto = crawlerMapper.toDto(crawler);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrawlerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, crawlerDto.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crawler in the database
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCrawler() throws Exception {
        int databaseSizeBeforeUpdate = crawlerRepository.findAll().size();
        crawler.setId(count.incrementAndGet());

        // Create the Crawler
        CrawlerDto crawlerDto = crawlerMapper.toDto(crawler);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crawler in the database
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCrawler() throws Exception {
        int databaseSizeBeforeUpdate = crawlerRepository.findAll().size();
        crawler.setId(count.incrementAndGet());

        // Create the Crawler
        CrawlerDto crawlerDto = crawlerMapper.toDto(crawler);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crawlerDto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crawler in the database
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCrawler() throws Exception {
        // Initialize the database
        crawlerRepository.saveAndFlush(crawler);

        int databaseSizeBeforeDelete = crawlerRepository.findAll().size();

        // Delete the crawler
        restCrawlerMockMvc
            .perform(delete(ENTITY_API_URL_ID, crawler.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Crawler> crawlerList = crawlerRepository.findAll();
        assertThat(crawlerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
