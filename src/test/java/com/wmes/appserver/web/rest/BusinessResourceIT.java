package com.wmes.appserver.web.rest;

import com.wmes.appserver.WmesApp;
import com.wmes.appserver.domain.Business;
import com.wmes.appserver.repository.BusinessRepository;
import com.wmes.appserver.service.BusinessService;
import com.wmes.appserver.service.dto.BusinessDTO;
import com.wmes.appserver.service.mapper.BusinessMapper;
import com.wmes.appserver.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.wmes.appserver.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BusinessResource} REST controller.
 */
@SpringBootTest(classes = WmesApp.class)
public class BusinessResourceIT {

    private static final String DEFAULT_BUSINESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_REPRESENTATIVE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_REPRESENTATIVE = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_REGISTRATION_NUM = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_REGISTRATION_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_REPRESENTATIVE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_REPRESENTATIVE_NUM = "BBBBBBBBBB";

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBusinessMockMvc;

    private Business business;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessResource businessResource = new BusinessResource(businessService, null);
        this.restBusinessMockMvc = MockMvcBuilders.standaloneSetup(businessResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Business createEntity(EntityManager em) {
        Business business = new Business()
            .businessName(DEFAULT_BUSINESS_NAME)
            .businessRepresentative(DEFAULT_BUSINESS_REPRESENTATIVE)
            .businessRegistrationNum(DEFAULT_BUSINESS_REGISTRATION_NUM)
            .businessType(DEFAULT_BUSINESS_TYPE)
            .businessCategory(DEFAULT_BUSINESS_CATEGORY)
            .businessRepresentativeNum(DEFAULT_BUSINESS_REPRESENTATIVE_NUM);
        return business;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Business createUpdatedEntity(EntityManager em) {
        Business business = new Business()
            .businessName(UPDATED_BUSINESS_NAME)
            .businessRepresentative(UPDATED_BUSINESS_REPRESENTATIVE)
            .businessRegistrationNum(UPDATED_BUSINESS_REGISTRATION_NUM)
            .businessType(UPDATED_BUSINESS_TYPE)
            .businessCategory(UPDATED_BUSINESS_CATEGORY)
            .businessRepresentativeNum(UPDATED_BUSINESS_REPRESENTATIVE_NUM);
        return business;
    }

    @BeforeEach
    public void initTest() {
        business = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusiness() throws Exception {
        int databaseSizeBeforeCreate = businessRepository.findAll().size();

        // Create the Business
        BusinessDTO businessDTO = businessMapper.toDto(business);
        restBusinessMockMvc.perform(post("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessDTO)))
            .andExpect(status().isCreated());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeCreate + 1);
        Business testBusiness = businessList.get(businessList.size() - 1);
        assertThat(testBusiness.getBusinessName()).isEqualTo(DEFAULT_BUSINESS_NAME);
        assertThat(testBusiness.getBusinessRepresentative()).isEqualTo(DEFAULT_BUSINESS_REPRESENTATIVE);
        assertThat(testBusiness.getBusinessRegistrationNum()).isEqualTo(DEFAULT_BUSINESS_REGISTRATION_NUM);
        assertThat(testBusiness.getBusinessType()).isEqualTo(DEFAULT_BUSINESS_TYPE);
        assertThat(testBusiness.getBusinessCategory()).isEqualTo(DEFAULT_BUSINESS_CATEGORY);
        assertThat(testBusiness.getBusinessRepresentativeNum()).isEqualTo(DEFAULT_BUSINESS_REPRESENTATIVE_NUM);
    }

    @Test
    @Transactional
    public void createBusinessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessRepository.findAll().size();

        // Create the Business with an existing ID
        business.setId(1L);
        BusinessDTO businessDTO = businessMapper.toDto(business);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessMockMvc.perform(post("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBusinessNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessRepository.findAll().size();
        // set the field null
        business.setBusinessName(null);

        // Create the Business, which fails.
        BusinessDTO businessDTO = businessMapper.toDto(business);

        restBusinessMockMvc.perform(post("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessDTO)))
            .andExpect(status().isBadRequest());

        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBusinessRepresentativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessRepository.findAll().size();
        // set the field null
        business.setBusinessRepresentative(null);

        // Create the Business, which fails.
        BusinessDTO businessDTO = businessMapper.toDto(business);

        restBusinessMockMvc.perform(post("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessDTO)))
            .andExpect(status().isBadRequest());

        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBusinesses() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList
        restBusinessMockMvc.perform(get("/api/businesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(business.getId().intValue())))
            .andExpect(jsonPath("$.[*].businessName").value(hasItem(DEFAULT_BUSINESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].businessRepresentative").value(hasItem(DEFAULT_BUSINESS_REPRESENTATIVE.toString())))
            .andExpect(jsonPath("$.[*].businessRegistrationNum").value(hasItem(DEFAULT_BUSINESS_REGISTRATION_NUM.toString())))
            .andExpect(jsonPath("$.[*].businessType").value(hasItem(DEFAULT_BUSINESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].businessCategory").value(hasItem(DEFAULT_BUSINESS_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].businessRepresentativeNum").value(hasItem(DEFAULT_BUSINESS_REPRESENTATIVE_NUM.toString())));
    }

    @Test
    @Transactional
    public void getBusiness() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get the business
        restBusinessMockMvc.perform(get("/api/businesses/{id}", business.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(business.getId().intValue()))
            .andExpect(jsonPath("$.businessName").value(DEFAULT_BUSINESS_NAME.toString()))
            .andExpect(jsonPath("$.businessRepresentative").value(DEFAULT_BUSINESS_REPRESENTATIVE.toString()))
            .andExpect(jsonPath("$.businessRegistrationNum").value(DEFAULT_BUSINESS_REGISTRATION_NUM.toString()))
            .andExpect(jsonPath("$.businessType").value(DEFAULT_BUSINESS_TYPE.toString()))
            .andExpect(jsonPath("$.businessCategory").value(DEFAULT_BUSINESS_CATEGORY.toString()))
            .andExpect(jsonPath("$.businessRepresentativeNum").value(DEFAULT_BUSINESS_REPRESENTATIVE_NUM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusiness() throws Exception {
        // Get the business
        restBusinessMockMvc.perform(get("/api/businesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusiness() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        int databaseSizeBeforeUpdate = businessRepository.findAll().size();

        // Update the business
        Business updatedBusiness = businessRepository.findById(business.getId()).get();
        // Disconnect from session so that the updates on updatedBusiness are not directly saved in db
        em.detach(updatedBusiness);
        updatedBusiness
            .businessName(UPDATED_BUSINESS_NAME)
            .businessRepresentative(UPDATED_BUSINESS_REPRESENTATIVE)
            .businessRegistrationNum(UPDATED_BUSINESS_REGISTRATION_NUM)
            .businessType(UPDATED_BUSINESS_TYPE)
            .businessCategory(UPDATED_BUSINESS_CATEGORY)
            .businessRepresentativeNum(UPDATED_BUSINESS_REPRESENTATIVE_NUM);
        BusinessDTO businessDTO = businessMapper.toDto(updatedBusiness);

        restBusinessMockMvc.perform(put("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessDTO)))
            .andExpect(status().isOk());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeUpdate);
        Business testBusiness = businessList.get(businessList.size() - 1);
        assertThat(testBusiness.getBusinessName()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testBusiness.getBusinessRepresentative()).isEqualTo(UPDATED_BUSINESS_REPRESENTATIVE);
        assertThat(testBusiness.getBusinessRegistrationNum()).isEqualTo(UPDATED_BUSINESS_REGISTRATION_NUM);
        assertThat(testBusiness.getBusinessType()).isEqualTo(UPDATED_BUSINESS_TYPE);
        assertThat(testBusiness.getBusinessCategory()).isEqualTo(UPDATED_BUSINESS_CATEGORY);
        assertThat(testBusiness.getBusinessRepresentativeNum()).isEqualTo(UPDATED_BUSINESS_REPRESENTATIVE_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingBusiness() throws Exception {
        int databaseSizeBeforeUpdate = businessRepository.findAll().size();

        // Create the Business
        BusinessDTO businessDTO = businessMapper.toDto(business);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessMockMvc.perform(put("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusiness() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        int databaseSizeBeforeDelete = businessRepository.findAll().size();

        // Delete the business
        restBusinessMockMvc.perform(delete("/api/businesses/{id}", business.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Business.class);
        Business business1 = new Business();
        business1.setId(1L);
        Business business2 = new Business();
        business2.setId(business1.getId());
        assertThat(business1).isEqualTo(business2);
        business2.setId(2L);
        assertThat(business1).isNotEqualTo(business2);
        business1.setId(null);
        assertThat(business1).isNotEqualTo(business2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessDTO.class);
        BusinessDTO businessDTO1 = new BusinessDTO();
        businessDTO1.setId(1L);
        BusinessDTO businessDTO2 = new BusinessDTO();
        assertThat(businessDTO1).isNotEqualTo(businessDTO2);
        businessDTO2.setId(businessDTO1.getId());
        assertThat(businessDTO1).isEqualTo(businessDTO2);
        businessDTO2.setId(2L);
        assertThat(businessDTO1).isNotEqualTo(businessDTO2);
        businessDTO1.setId(null);
        assertThat(businessDTO1).isNotEqualTo(businessDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(businessMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(businessMapper.fromId(null)).isNull();
    }
}
