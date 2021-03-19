package com.wmes.appserver.web.rest;

import com.wmes.appserver.WmesApp;
import com.wmes.appserver.domain.BusinessPlace;
import com.wmes.appserver.repository.BusinessPlaceRepository;
import com.wmes.appserver.service.BusinessPlaceService;
import com.wmes.appserver.service.dto.BusinessPlaceDTO;
import com.wmes.appserver.service.mapper.BusinessPlaceMapper;
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
 * Integration tests for the {@link BusinessPlaceResource} REST controller.
 */
@SpringBootTest(classes = WmesApp.class)
public class BusinessPlaceResourceIT {

    private static final String DEFAULT_BP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BP_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_HEADQUARTER = false;
    private static final Boolean UPDATED_IS_HEADQUARTER = true;

    private static final String DEFAULT_BP_ZIP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BP_ZIP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_BP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_BP_DETAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BP_DETAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_BP_FAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BP_FAX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BP_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BP_NUMBER = "BBBBBBBBBB";

    @Autowired
    private BusinessPlaceRepository businessPlaceRepository;

    @Autowired
    private BusinessPlaceMapper businessPlaceMapper;

    @Autowired
    private BusinessPlaceService businessPlaceService;

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

    private MockMvc restBusinessPlaceMockMvc;

    private BusinessPlace businessPlace;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessPlaceResource businessPlaceResource = new BusinessPlaceResource(businessPlaceService);
        this.restBusinessPlaceMockMvc = MockMvcBuilders.standaloneSetup(businessPlaceResource)
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
    public static BusinessPlace createEntity(EntityManager em) {
        BusinessPlace businessPlace = new BusinessPlace()
            .bpName(DEFAULT_BP_NAME)
            .isHeadquarter(DEFAULT_IS_HEADQUARTER)
            .bpZipAddress(DEFAULT_BP_ZIP_ADDRESS)
            .bpAddress(DEFAULT_BP_ADDRESS)
            .bpDetailAddress(DEFAULT_BP_DETAIL_ADDRESS)
            .bpFaxNumber(DEFAULT_BP_FAX_NUMBER)
            .bpNumber(DEFAULT_BP_NUMBER);
        return businessPlace;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessPlace createUpdatedEntity(EntityManager em) {
        BusinessPlace businessPlace = new BusinessPlace()
            .bpName(UPDATED_BP_NAME)
            .isHeadquarter(UPDATED_IS_HEADQUARTER)
            .bpZipAddress(UPDATED_BP_ZIP_ADDRESS)
            .bpAddress(UPDATED_BP_ADDRESS)
            .bpDetailAddress(UPDATED_BP_DETAIL_ADDRESS)
            .bpFaxNumber(UPDATED_BP_FAX_NUMBER)
            .bpNumber(UPDATED_BP_NUMBER);
        return businessPlace;
    }

    @BeforeEach
    public void initTest() {
        businessPlace = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessPlace() throws Exception {
        int databaseSizeBeforeCreate = businessPlaceRepository.findAll().size();

        // Create the BusinessPlace
        BusinessPlaceDTO businessPlaceDTO = businessPlaceMapper.toDto(businessPlace);
        restBusinessPlaceMockMvc.perform(post("/api/business-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessPlaceDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessPlace in the database
        List<BusinessPlace> businessPlaceList = businessPlaceRepository.findAll();
        assertThat(businessPlaceList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessPlace testBusinessPlace = businessPlaceList.get(businessPlaceList.size() - 1);
        assertThat(testBusinessPlace.getBpName()).isEqualTo(DEFAULT_BP_NAME);
        assertThat(testBusinessPlace.isIsHeadquarter()).isEqualTo(DEFAULT_IS_HEADQUARTER);
        assertThat(testBusinessPlace.getBpZipAddress()).isEqualTo(DEFAULT_BP_ZIP_ADDRESS);
        assertThat(testBusinessPlace.getBpAddress()).isEqualTo(DEFAULT_BP_ADDRESS);
        assertThat(testBusinessPlace.getBpDetailAddress()).isEqualTo(DEFAULT_BP_DETAIL_ADDRESS);
        assertThat(testBusinessPlace.getBpFaxNumber()).isEqualTo(DEFAULT_BP_FAX_NUMBER);
        assertThat(testBusinessPlace.getBpNumber()).isEqualTo(DEFAULT_BP_NUMBER);
    }

    @Test
    @Transactional
    public void createBusinessPlaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessPlaceRepository.findAll().size();

        // Create the BusinessPlace with an existing ID
        businessPlace.setId(1L);
        BusinessPlaceDTO businessPlaceDTO = businessPlaceMapper.toDto(businessPlace);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessPlaceMockMvc.perform(post("/api/business-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessPlaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessPlace in the database
        List<BusinessPlace> businessPlaceList = businessPlaceRepository.findAll();
        assertThat(businessPlaceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBpNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessPlaceRepository.findAll().size();
        // set the field null
        businessPlace.setBpName(null);

        // Create the BusinessPlace, which fails.
        BusinessPlaceDTO businessPlaceDTO = businessPlaceMapper.toDto(businessPlace);

        restBusinessPlaceMockMvc.perform(post("/api/business-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessPlaceDTO)))
            .andExpect(status().isBadRequest());

        List<BusinessPlace> businessPlaceList = businessPlaceRepository.findAll();
        assertThat(businessPlaceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBusinessPlaces() throws Exception {
        // Initialize the database
        businessPlaceRepository.saveAndFlush(businessPlace);

        // Get all the businessPlaceList
        restBusinessPlaceMockMvc.perform(get("/api/business-places?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessPlace.getId().intValue())))
            .andExpect(jsonPath("$.[*].bpName").value(hasItem(DEFAULT_BP_NAME.toString())))
            .andExpect(jsonPath("$.[*].isHeadquarter").value(hasItem(DEFAULT_IS_HEADQUARTER.booleanValue())))
            .andExpect(jsonPath("$.[*].bpZipAddress").value(hasItem(DEFAULT_BP_ZIP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].bpAddress").value(hasItem(DEFAULT_BP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].bpDetailAddress").value(hasItem(DEFAULT_BP_DETAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].bpFaxNumber").value(hasItem(DEFAULT_BP_FAX_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].bpNumber").value(hasItem(DEFAULT_BP_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getBusinessPlace() throws Exception {
        // Initialize the database
        businessPlaceRepository.saveAndFlush(businessPlace);

        // Get the businessPlace
        restBusinessPlaceMockMvc.perform(get("/api/business-places/{id}", businessPlace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessPlace.getId().intValue()))
            .andExpect(jsonPath("$.bpName").value(DEFAULT_BP_NAME.toString()))
            .andExpect(jsonPath("$.isHeadquarter").value(DEFAULT_IS_HEADQUARTER.booleanValue()))
            .andExpect(jsonPath("$.bpZipAddress").value(DEFAULT_BP_ZIP_ADDRESS.toString()))
            .andExpect(jsonPath("$.bpAddress").value(DEFAULT_BP_ADDRESS.toString()))
            .andExpect(jsonPath("$.bpDetailAddress").value(DEFAULT_BP_DETAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.bpFaxNumber").value(DEFAULT_BP_FAX_NUMBER.toString()))
            .andExpect(jsonPath("$.bpNumber").value(DEFAULT_BP_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessPlace() throws Exception {
        // Get the businessPlace
        restBusinessPlaceMockMvc.perform(get("/api/business-places/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessPlace() throws Exception {
        // Initialize the database
        businessPlaceRepository.saveAndFlush(businessPlace);

        int databaseSizeBeforeUpdate = businessPlaceRepository.findAll().size();

        // Update the businessPlace
        BusinessPlace updatedBusinessPlace = businessPlaceRepository.findById(businessPlace.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessPlace are not directly saved in db
        em.detach(updatedBusinessPlace);
        updatedBusinessPlace
            .bpName(UPDATED_BP_NAME)
            .isHeadquarter(UPDATED_IS_HEADQUARTER)
            .bpZipAddress(UPDATED_BP_ZIP_ADDRESS)
            .bpAddress(UPDATED_BP_ADDRESS)
            .bpDetailAddress(UPDATED_BP_DETAIL_ADDRESS)
            .bpFaxNumber(UPDATED_BP_FAX_NUMBER)
            .bpNumber(UPDATED_BP_NUMBER);
        BusinessPlaceDTO businessPlaceDTO = businessPlaceMapper.toDto(updatedBusinessPlace);

        restBusinessPlaceMockMvc.perform(put("/api/business-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessPlaceDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessPlace in the database
        List<BusinessPlace> businessPlaceList = businessPlaceRepository.findAll();
        assertThat(businessPlaceList).hasSize(databaseSizeBeforeUpdate);
        BusinessPlace testBusinessPlace = businessPlaceList.get(businessPlaceList.size() - 1);
        assertThat(testBusinessPlace.getBpName()).isEqualTo(UPDATED_BP_NAME);
        assertThat(testBusinessPlace.isIsHeadquarter()).isEqualTo(UPDATED_IS_HEADQUARTER);
        assertThat(testBusinessPlace.getBpZipAddress()).isEqualTo(UPDATED_BP_ZIP_ADDRESS);
        assertThat(testBusinessPlace.getBpAddress()).isEqualTo(UPDATED_BP_ADDRESS);
        assertThat(testBusinessPlace.getBpDetailAddress()).isEqualTo(UPDATED_BP_DETAIL_ADDRESS);
        assertThat(testBusinessPlace.getBpFaxNumber()).isEqualTo(UPDATED_BP_FAX_NUMBER);
        assertThat(testBusinessPlace.getBpNumber()).isEqualTo(UPDATED_BP_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessPlace() throws Exception {
        int databaseSizeBeforeUpdate = businessPlaceRepository.findAll().size();

        // Create the BusinessPlace
        BusinessPlaceDTO businessPlaceDTO = businessPlaceMapper.toDto(businessPlace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessPlaceMockMvc.perform(put("/api/business-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessPlaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessPlace in the database
        List<BusinessPlace> businessPlaceList = businessPlaceRepository.findAll();
        assertThat(businessPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessPlace() throws Exception {
        // Initialize the database
        businessPlaceRepository.saveAndFlush(businessPlace);

        int databaseSizeBeforeDelete = businessPlaceRepository.findAll().size();

        // Delete the businessPlace
        restBusinessPlaceMockMvc.perform(delete("/api/business-places/{id}", businessPlace.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessPlace> businessPlaceList = businessPlaceRepository.findAll();
        assertThat(businessPlaceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessPlace.class);
        BusinessPlace businessPlace1 = new BusinessPlace();
        businessPlace1.setId(1L);
        BusinessPlace businessPlace2 = new BusinessPlace();
        businessPlace2.setId(businessPlace1.getId());
        assertThat(businessPlace1).isEqualTo(businessPlace2);
        businessPlace2.setId(2L);
        assertThat(businessPlace1).isNotEqualTo(businessPlace2);
        businessPlace1.setId(null);
        assertThat(businessPlace1).isNotEqualTo(businessPlace2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessPlaceDTO.class);
        BusinessPlaceDTO businessPlaceDTO1 = new BusinessPlaceDTO();
        businessPlaceDTO1.setId(1L);
        BusinessPlaceDTO businessPlaceDTO2 = new BusinessPlaceDTO();
        assertThat(businessPlaceDTO1).isNotEqualTo(businessPlaceDTO2);
        businessPlaceDTO2.setId(businessPlaceDTO1.getId());
        assertThat(businessPlaceDTO1).isEqualTo(businessPlaceDTO2);
        businessPlaceDTO2.setId(2L);
        assertThat(businessPlaceDTO1).isNotEqualTo(businessPlaceDTO2);
        businessPlaceDTO1.setId(null);
        assertThat(businessPlaceDTO1).isNotEqualTo(businessPlaceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(businessPlaceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(businessPlaceMapper.fromId(null)).isNull();
    }
}
